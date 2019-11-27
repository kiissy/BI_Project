import time

import nltk
import requests
from nltk.stem import WordNetLemmatizer
from nltk.tag import pos_tag
from nltk import FreqDist
from nltk.corpus import stopwords
from bs4 import BeautifulSoup as bs

import json
import re


# 데이터 전체 구조는 list로 되어있고 각각의 기사는 dictionary 구조로 되어있다.
# 각 데이터[i]번째의 딕셔너리 키는 (['short_description', 'headline', 'date', 'link', 'authors', 'category'])


# 해야할 것 1. data[i]의 키 중 카테고리로 분류할 수 있게
#         2. data[i]의 short_description과 headline을 자연어처리해서 특정 단어 추출
#         3. data[i]의 link로 web crawling하여 기사 추출


fname = 'new_list.json'

data = []
with open(fname, 'r') as f:
    data = json.load(f)

corpus = []

for i in range(len(data)):
    #print(json.dumps(data[i], indent="\t"))
    index = i
    category = data[i]['category']
    short_description = data[i]['short_description']
    headline = data[i]['headline']
    short_description = short_description.lower()
    headline = headline.lower()
    news_description = ""


    #url로 링크 크롤링 하는 부분#
    url = data[i]['link']
    # request를 통해 파싱한 html 문서를 beautifulsoup 객체로 데이터 추출
    headers = {'User-Agent': 'Mozilla/5.0'}

    try:
        req = requests.get(url, headers = headers)
        req.timeout=30
        req.verify = False
        soup = bs(req.content, 'html.parser')
        news_data = []

        for link in soup.find_all('p'):
            news_data.append(link.text.strip())
        # print(news_data)
        news_description = ", ".join(news_data)
    except requests.ConnectionError as e:
        print("Connection Error. Make sure you are connected to Internet. Technical Details given below.\n")
        print(str(e))
        news_description = ""
        pass
    except requests.Timeout as e:
        print("Timeout Error")
        print(str(e))
        news_description = ""
        pass
    except requests.RequestException as e:
        print("General Error")
        print(str(e))
        news_description = ""
        pass
    except KeyboardInterrupt:
        print("Someone closed the program")
        news_description = ""


    #print(i+1, " 번째 기사")
    short_description = data[i]['short_description']
    headline = data[i]['headline']
    short_description = short_description.lower()
    headline = headline.lower()
    news_description = news_description.lower()
    news_description = news_description.replace(",", "")


    #remove words less than three letters and remove stopword
    #nltk를 이용하여 토큰화한다 - short_Description, headline
    #nltk - punkt 라이브러리 설치

    shortword = re.compile(r'\W*\b\w{1,2}\b')

    short_description = shortword.sub('', short_description)
    headline = shortword.sub('', headline)


    #특수문 제거
    short_description = re.sub('[!@#$%^&“”*",('')/<>?.=]', '', short_description)
    short_description = short_description.replace("]", "")
    short_description = short_description.replace("[", "")
    short_description = short_description.replace("-", "")

    headline = re.sub('[!@#$%^&*,('')"“”/<>?.=]', '', headline)
    headline = headline.replace("]", "")
    headline = headline.replace("[", "")
    headline = headline.replace("-", "")

    news_description = shortword.sub('', news_description)
    news_description = re.sub('[!@#$%^&*,('')“”"/<>?.=]', '', news_description)
    news_description = news_description.replace("]", "")
    news_description = news_description.replace("[", "")
    news_description = news_description.replace("-", "")
    news_description = news_description.replace("_", "")



    stop = stopwords.words('english')

    tokens1 = nltk.word_tokenize(short_description)
    tokens2 = nltk.word_tokenize(headline)

    tokens1 = [word for word in tokens1 if not word in stop]
    tokens2 = [word for word in tokens2 if not word in stop]

    news_token = nltk.wordpunct_tokenize(news_description)
    news_token = [word for word in news_token if not word in stop]


    tokens = tokens1 + tokens2 + news_token



    #동사의 원형복원(lemmatizing)
    #nltk - wordnet 라이브러리 설치
    lm = WordNetLemmatizer()
    lm_tokens = [lm.lemmatize(w, pos = "v") for w in tokens]
    #print("lemmatizing : ", lm_tokens)

    #품사 구분하여 고유명사, 명사, 동사 출력
    tagged_list = pos_tag(lm_tokens)
    pnouns_tokens = [t[0] for t in tagged_list if t[1] == "NNP"]
    nouns_tokens = [t[0] for t in tagged_list if t[1] == "NN"]
    verb_tokens = [t[0] for t in tagged_list if t[1] == "VB"]

    #분류된 단어들을 합치기
    token_list = pnouns_tokens + nouns_tokens + verb_tokens
    #print(token_list)

    #단어의 사용빈도 체크  -->  ('단어', '나온 횟수')
    #사용 빈도가 높은 3가지 단어 추출
    freq_list = FreqDist(token_list)
    freq_word = freq_list.most_common(3)
    #print("frequency of word : ", freq_word)

    news_word_tag = ""
    for i in range(3):
        try:
            for j in range(freq_word[i][1]):
                news_word_tag += " " + freq_word[i][0]
        except IndexError:
            continue
    news_word_tag = news_word_tag.lstrip()
    dict_corpus = {'index': index, 'category': category, 'news_word_tag': news_word_tag}
    corpus.append(dict_corpus)

with open('corpus.json', 'w') as fout:
    json.dump(corpus, fout, indent="\t")

















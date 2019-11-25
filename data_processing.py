#!/usr/bin/env python
# coding: utf-8

# In[5]:


import nltk
from nltk.stem import WordNetLemmatizer
from nltk.tag import pos_tag
from nltk import FreqDist
from nltk.corpus import stopwords
import math

import json
import re


from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer

from sklearn.metrics.pairwise import linear_kernel

# 데이터 전체 구조는 list로 되어있고 각각의 기사는 dictionary 구조로 되어있다.
# 각 데이터[i]번째의 딕셔너리 키는 (['short_description', 'headline', 'date', 'link', 'authors', 'category'])


# 해야할 것 1. data[i]의 키 중 카테고리로 분류할 수 있게
#         2. data[i]의 short_description과 headline을 자연어처리해서 특정 단어 추출



fname = 'new_list.json'

data = []
with open(fname, "r") as st_json:
    data = json.load(st_json)
#data = []
#with open(fname) as st_json:
#     for line in st_json:
#         data.append(json.loads(line))
           
corpus = []
for i in range(len(data)):
    #print(json.dumps(data[i], indent="\t"))
    #print(i+1, " 번째 기사")
    index = i
    category = data[i]['category']
    short_description = data[i]['short_description']
    headline = data[i]['headline']
    short_description = short_description.lower()
    headline = headline.lower()

    #print(short_description)
    #print(headline)

    #remove words less than three letters and remove stopword
    #nltk를 이용하여 토큰화한다 - short_Description, headline
    #nltk - punkt 라이브러리 설치

    shortword = re.compile(r'\W*\b\w{1,2}\b')
    short_description = shortword.sub('', short_description)
    headline = shortword.sub('', headline)

    stop = stopwords.words('english')

    tokens1 = nltk.word_tokenize(short_description)
    tokens2 = nltk.word_tokenize(headline)

    tokens1 = [word for word in tokens1 if not word in stop]
    tokens2 = [word for word in tokens2 if not word in stop]

    #print(tokens1)
    #print(tokens2)
    tokens = tokens1 + tokens2

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
    #print("proper nouns : ", pnouns_tokens)
    #print("nouns : ", nouns_tokens)
    #print("verb : ", verb_tokens)

    #분류된 단어들을 합치기
    token_list = pnouns_tokens + nouns_tokens + verb_tokens
    #print(token_list)

    #단어의 사용빈도 체크  -->  ('단어', '나온 횟수')
    #사용 빈도가 높은 3가지 단어 추출
    freq_list = FreqDist(token_list)
    freq_word = freq_list.most_common(3)
    #print("frequency of word : ", freq_word)
    
    
    news_word_tag = ""
    for i in range (3):
        try:
            for j in range(freq_word[i][1]):
                news_word_tag += " "+freq_word[i][0]
        except IndexError:
            continue
    news_word_tag = news_word_tag.lstrip()
    dict_corpus = {'index' : index, 'category' : category, 'news_word_tag' : news_word_tag}
    corpus.append(dict_corpus)

with open('corpus.json', 'w') as fout:
    json.dump(corpus, fout, indent="\t")
    


# In[3]:





# In[8]:





# In[ ]:





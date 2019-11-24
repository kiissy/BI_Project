#!/usr/bin/env python
# coding: utf-8

# In[1]:


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



fname = 'News_Category_Dataset.json'

data = []
with open(fname) as st_json:
    for line in st_json:
        data.append(json.loads(line))

# 카테고리 리스트
category_list = []
for i in range(len(data)):
    category_list.append(data[i]['category'])
    category_list = list(set(category_list))
    
with open('category.json', 'w') as fout:
    json.dump(category_list, fout, indent="\t")


# In[ ]:





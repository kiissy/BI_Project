#!/usr/bin/env python
# coding: utf-8

# In[24]:


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



fname = 'News_Category_Dataset_v2.json'

data = []
with open(fname) as st_json:
    for line in st_json:
        data.append(json.loads(line))

# 카테고리 리스트
category_list = []
for i in range(len(data)):
    category_list.append(data[i]['category'])
    category_list = list(set(category_list))

news_list = []
for category in category_list:
    con_cg_list = []
    con_data_list = []
    
    for con_data in data:
        con_data_list.append(con_data)
        
    news_generator = (item for item in con_data_list if item['category'] == category)
    
    num = 0;
    for news_item in news_generator:
        con_cg_list.append(news_item)
        num += 1
        #print(num)
        #print(news_item['short_description'])
        news_list.append(news_item)
        if (num == 1000): break
    #print(category, " done")
#print(news_list)
    
with open('new_list.json', 'w') as fout:
    json.dump(news_list, fout, indent="\t")       
    
    
    
    
    
    
    
    


# In[ ]:





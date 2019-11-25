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
import sys

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import linear_kernel

def similarity(category, index):
    fname = 'corpus.json'

    data = []
    with open(fname, "r") as st_json:
        data = json.load(st_json)

    category = category # 인풋으로 받을 카테고리
    index = index # 인풋으로 받을 index
    cg_items = (item for item in data if item['category'] == category)
    
    cg_item_list = []
    for cg_item in cg_items:
        cg_item_list.append(cg_item)
    #print(cg_item_list)

    # 분류한 카테고리의 corpus
    cg_corpus = []
    for item in cg_item_list:
        cg_corpus.append(item['news_word_tag'])
    #print(cg_corpus)

    # TF-IDF 수행
    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(cg_corpus).toarray()
    print(tfidf_matrix.shape)
    #print(tfidf_matrix)

    #코사인 유사도 배열
    cosine_sim = linear_kernel(tfidf_matrix, tfidf_matrix)
    #print(cosine_sim)
    print(len(cosine_sim))

    dict_sim_list = []
    for i in range(len(cosine_sim)):
        for j in range(len(cosine_sim[i])):
            dict_sim = {'i_index' : cg_item_list[i]['index'], 'j_index' : cg_item_list[j]['index'], 'similarity' : round(cosine_sim[i][j], 10)}
            dict_sim_list.append(dict_sim)
        
    sel_sims = (item for item in dict_sim_list if item['i_index'] == index)

    sel_sim_list = []
    for sel_sim in sel_sims:
        print(sel_sim)
        sel_sim_list.append(sel_sim)
    print(sel_sim_list)
    
    return sel_sim_list

similarity('EDUCATION', 2) # input : category(string), index(int)
    


# In[ ]:





# In[ ]:





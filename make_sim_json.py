#!/usr/bin/env python
# coding: utf-8

# In[7]:


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

def similarity(category):
    fname = 'corpus.json'

    data = []
    with open(fname, "r") as st_json:
        data = json.load(st_json)

    category = category # 인풋으로 받을 카테고리
    #index = index # 인풋으로 받을 index
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

    dict_sim_list = []
    for i in range(len(cosine_sim)):
        for j in range(len(cosine_sim[i])):
            dict_sim = {'i_index' : cg_item_list[i]['index'], 'j_index' : cg_item_list[j]['index'], 'similarity' : round(cosine_sim[i][j], 10)}
            dict_sim_list.append(dict_sim)
        
#     sel_sims = (item for item in dict_sim_list if item['i_index'] == index)

#     sel_sim_list = []
#     for sel_sim in sel_sims:
#         print(sel_sim)
#         sel_sim_list.append(sel_sim)
#     print(sel_sim_list)
    
    return dict_sim_list

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

for category in category_list:
    fname = category+'_similarity.json'
    similarity_list = similarity(category) # input : category(string), index(int)
    with open(fname, 'w') as fout:
        json.dump(similarity_list, fout, indent="\t")
    


# In[ ]:





# In[ ]:





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
    fname = './similarity_json/'+category+'_similarity.json'

    data = []
    with open(fname, "r") as st_json:
        data = json.load(st_json)

    sel_sims = (item for item in data if item['i_index'] == int(index))

    sel_sim_list = []
    for sel_sim in sel_sims:
        print(sel_sim)
        sel_sim_list.append(sel_sim)
    print(len(sel_sim_list))

    return sel_sim_list

similarity(sys.argv[1], sys.argv[2]) # input : category(string), index(int)

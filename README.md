# BI_Project
 Business Intelligence Project - News Article Recommendation

category_list.py
 - 카테고리 목록 json 파일 쓰기
 - output : categoty.json

data_processing.py
 - 자연어 처리
 - input : News_Category_Dataset_v2.json
 - output : corpus.json

make_sim_json.py
 - 각 카테고리에 대한 유사도 배열 json 파일 생성
 - input : corpus.json
 - output : similarity_json 폴더 내 category_similarity.json

similarity.py
 - 카테고리, 인덱스를 받아 해당하는 기사의 유사도 배열 내용 return
 - input : category(string, index) // similarity.py 'ENTERTAINMENT' 26000
 - output : 해당 기사의 유사도 배열 list
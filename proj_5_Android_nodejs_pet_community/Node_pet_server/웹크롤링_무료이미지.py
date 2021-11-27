#!/usr/bin/env python
# coding: utf-8

# In[32]:


import os
import sys
import urllib.request
from selenium import webdriver
from time import sleep
from bs4 import BeautifulSoup

keyword = input('검색어 : ')
maxImages = int(input('다운로드 시도할 최대 이미지 수 : '))

# 프로젝트에 미리 생성해놓은 crawled_img폴더 안에 하위 폴더 생성
# 폴더명에는 입력한 키워드, 이미지 수 정보를 표시함
path = 'crawled_img/'+keyword+'_'+str(maxImages)

try:
    if not os.path.exists(path):
        os.makedirs(path)
    else:
        print('이전에 같은 [검색어, 이미지 수]로 다운로드한 폴더가 존재합니다.')
        sys.exit(0)
except OSError:
    print ('os error')
    sys.exit(0)

pages = int((maxImages-1)/100)+1
imgCount = 0 
success = 0
finish = False 


chromedriver = 'C://chromedriver.exe'
driver = webdriver.Chrome(chromedriver)
driver.implicitly_wait(3)

for i in range(1,int(pages)+1):
    driver.get('https://pixabay.com/images/search/'+keyword+'/?pagi='+str(i))
    sleep(1)
    html = driver.page_source
    soup = BeautifulSoup(html,'html.parser')
    imgs = soup.select('a.link--h3bPW img')
    lastPage=False
    if len(imgs)!=100:
        lastPage=True

    for img in imgs:
        print(img)
        noti='/static/img/blank.gif'
        srcset = ""
        if img.get('src')==noti:
            srcset = img.get('data-lazy-srcset')
        elif (img.get('src')==None):
            srcset = img.get('data-lazy-srcset')
        else: 
            srcset = img.get('src')
        print(srcset)


        src = ""
        if len(srcset):
            src = str(srcset).split()[0] 
            print(src)
            filename = src.split('/')[-1] 
            print(filename)
            saveUrl = path+'/'+filename 
            print(saveUrl)

            
            req = urllib.request.Request(src, headers={'User-Agent': 'Chrome/94.0.4606.54'})
            try:
                imgUrl = urllib.request.urlopen(req).read() 
                with open(saveUrl,"wb") as f: 
                    f.write(imgUrl) 
                success+=1
            except urllib.error.HTTPError:
                print('에러')
                sys.exit(0)

        imgCount+=1

        if imgCount==maxImages:
            finish = True 
            break
    
    #finish가 참이거나 더 이상 접근할 이미지가 없을 경우 크롤링 종료
    if finish or lastPage:
        break

print('성공 : '+str(success)+', 실패 : '+str(maxImages-success))







from PIL import Image
import os, glob, numpy as np
from keras.models import load_model
import sys
caltech_dir = "./public/images"
image_w = 64
image_h = 64
filename=sys.argv[1]
pixels = image_h * image_w * 3

X = []
filenames = []

files = glob.glob(caltech_dir+"/"+filename)

for i, f in enumerate(files):
    img = Image.open(f)
    img = img.convert("RGB")
    img = img.resize((image_w, image_h))
    data = np.asarray(img)
    filenames.append(f)
    X.append(data)

X = np.array(X)
model = load_model('./model/multi_img_classification.model')

prediction = model.predict(X)
np.set_printoptions(formatter={'float': lambda x: "{0:0.3f}".format(x)})
cnt = 0

for i in prediction:
    pre_ans = i.argmax()  # 예측 레이블
    pre_ans_str = ''
    if pre_ans == 0: pre_ans_str = "greyhound"
    elif pre_ans == 1: pre_ans_str = "maltese"
    elif pre_ans == 2: pre_ans_str = "bulldog"
    elif pre_ans == 3: pre_ans_str = "beagle"
    elif pre_ans == 4: pre_ans_str = "shepherd"
    elif pre_ans == 5: pre_ans_str = "Shiba Inu"
    elif pre_ans == 6: pre_ans_str = "Shih Tzu"
    elif pre_ans == 7: pre_ans_str = "yorkshire terrier"
    elif pre_ans == 8: pre_ans_str = "Welsh Corgi"
    elif pre_ans == 9: pre_ans_str = "Chihuahua"
    elif pre_ans == 10: pre_ans_str = "Pomerania"
    elif pre_ans == 11: pre_ans_str = "poodle"
    elif pre_ans == 12: pre_ans_str = "Husky"
    else: pre_ans_str = "?"
    print(pre_ans_str)
    cnt += 1







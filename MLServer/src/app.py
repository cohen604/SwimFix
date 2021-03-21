from datetime import datetime
import io
import json
import os
import pickle
import pdb
from typing import List, Tuple, Dict

import base64
import cv2
from flask import Flask, request, send_file, jsonify
import numpy as np
from tqdm import tqdm

from model import get_model
from utils import get_frame

app = Flask(__name__)

model = None
cfg = None

def load_video(path: str) -> np.ndarray:

    frames = []
    vidcap = cv2.VideoCapture(path)
    success, image = vidcap.read()
    frames.append(image)
    while success:
        success, image = vidcap.read()
        frames.append(image)
    
    return np.array(frames[:-1])

def get_predictions(frames: np.ndarray) -> np.ndarray:
    
    """
    Generates keypoint predictions for a video.

    Parameters
    ----------
    frames: np.ndarray
        Video as a numpy array of frames.


    Returns
    -------
    preds: List[np.ndarray]
        List of keypoints detected for each frame.

    """
    preds = []
    for i, frame in tqdm(enumerate(frames), desc='predicting'):
        outputs = model(frame)
        try:
            keypoints = outputs['instances'].get_fields()['pred_keypoints'].cpu().numpy()[0]
        except Exception as e:
            keypoints = [0] * 21
        preds.append(list(keypoints.ravel().astype(np.float64)))

    return preds

@app.route('/detect', methods=['GET', 'POST'])
def detect():
    date = datetime.now()
    str_date = str(date)
    print(f'detection request accepted {str(date)}')
    
    video_bytes = base64.b64decode(request.form['video'])
    with open(f"{str_date}{request.form['type']}", 'wb') as f:
        f.write(video_bytes) 
    
    frames = load_video(f"{str_date}{request.form['type']}")
    
    predictions = get_predictions(frames)
    print(f'returning request took: {str(datetime.now() - date)}')
    return jsonify(predictions)
    
@app.route('/')
def hello():
    return "Hello SwimFix"

if __name__ == "__main__":

    model, cfg = get_model(trained=True, load_path="model_final.pth")

    app.run(host='192.168.1.12', port='5050', debug=True)

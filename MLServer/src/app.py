import os
import io
import pickle
from typing import List, Tuple, Dict

from model import get_model
from utils import get_frame

import base64
import numpy as np
from flask import Flask, request, send_file, jsonify

app = Flask(__name__)

model = None
cfg = None

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
    for frame in frames:
        outputs = model(frame)
        keypoints = outputs['instances'].get_fields()['pred_keypoints'].cpu().numpy()[0]
        preds.append(keypoints)

    return preds

@app.route('/detect', methods=['GET', 'POST'])
def detect():

    len_ = int(request.form['len'])
    height = int(request.form['height'])
    width = int(request.form['width'])
    frames = []
    
    for i in range(len_):
        frame = get_frame(request.form[f'video{i}'], height, width)
        frames.append(frame)

    frames = np.array(frames)

    predictions = get_predictions(frames)

    return jsonify(predictions)
    
@app.route('/')
def hello():
    return "Hello SwimFix"

if __name__ == "__main__":

    model, cfg = get_model(trained=True, load_path="model_final.pth")

    app.run(host='192.168.1.46', port='5000', debug=True)

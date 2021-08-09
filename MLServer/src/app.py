from datetime import datetime
import os

import base64
import cv2
from flask import Flask, request, jsonify
import numpy as np
from tqdm import tqdm

from model import get_model

app = Flask(__name__)

model = None
cfg = None


def load_video(path: str) -> np.ndarray:
    """
    Receives a path of the video and loads it as numpy array

    Args:
        path: Location of the video on the disk.

    Returns:
        A numpy array containing all the frames
    """
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

    Args:
        frames: Video as a numpy array of frames.


    Returns:
        preds: A list of keypoints detected for each frame.
    """
    preds = []
    for frame in tqdm(frames, desc='predicting'):
        outputs = model(frame)
        try:
            keypoints = outputs['instances'].get_fields(
            )['pred_keypoints'].cpu().numpy()[0]
            keypoints = list(keypoints.ravel().astype(np.float64))
        except:
            keypoints = [0] * 21
        preds.append(keypoints)

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
    os.remove(f"{str_date}{request.form['type']}")
    return jsonify(predictions)


@app.route('/')
def hello():
    return "Hello SwimFix"


if __name__ == "__main__":

    model, cfg = get_model()

    app.run(host='0.0.0.0', port='5050', debug=True)

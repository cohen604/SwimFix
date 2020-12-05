import base64
import numpy as np
from flask import Flask, request, send_file
from detectron2.model_zoo import model_zoo
from detectron2.engine import DefaultPredictor
from detectron2.config import get_cfg
import cv2

app = Flask(__name__)

def init_detectron2():
    # Create config
    cfg = get_cfg()
    cfg.merge_from_file(model_zoo.get_config_file(f"COCO-Keypoints/keypoint_rcnn_R_50_FPN_3x.yaml"))
    cfg.MODEL.ROI_HEADS.SCORE_THRESH_TEST = 0.5  # set threshold for this model
    cfg.MODEL.WEIGHTS = "detectron2://COCO-Keypoints/keypoint_rcnn_R_50_FPN_3x/137849621/model_final_a6e10b.pkl"
    cfg.MODEL.DEVICE = "cpu"
    # Create predictor
    predictor = DefaultPredictor(cfg)
    return cfg, predictor

cfg, predictor = init_detectron2()

@app.route('/detect', methods=['GET', 'POST'])
def detect():
    print('received data')
    len = int(request.form['len'])
    height = int(request.form['height'])
    width = int(request.form['width'])
    frames = []
    for i in range(len):
        frame_string = request.form[f"video{i}"]
        frame_bytes = base64.b64decode(frame_string)
        frame = np.frombuffer(frame_bytes, dtype=np.uint8)
        frames.append(frame.reshape(height, width, 3))
    frames = np.array(frames)
    print(frames.shape)

    # Make prediction
    for i, frame in enumerate(frames):
        outputs = predictor(frame)
        print(f"frame{i} ", outputs)
    return "Hello"
    
@app.route('/')
def hello():
    return "Hello SwimFix"

if __name__ == "__main__":
    app.run(port='5000', debug=True)

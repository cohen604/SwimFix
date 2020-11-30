import os
import io
import pickle
from typing import List, Tuple, Dict

import base64
import numpy as np
from flask import Flask, request, send_file

app = Flask(__name__)

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
    return "Hello"
    
@app.route('/')
def hello():
    return "Hello SwimFix"

if __name__ == "__main__":
    app.run(port='5000', debug=True)

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
    #data = request.json
    movie_bytes = request.form['video']
    print(type(movie_bytes))
    with open('foo.mov', 'wb') as f:
        f.write(base64.b64decode(movie_bytes))
    #bytes_ = io.BytesIO(movie_bytes.encode('utf-8'))
    #print(type(movie_bytes))
    #movie = np.array(data['video'])
    movie = np.frombuffer(base64.b64decode(movie_bytes))
    print(movie.shape)
    
    return "Hello"
    
@app.route('/')
def hello():
    return "Hello SwimFix"

if __name__ == "__main__":
    app.run(port='5000', debug=True)

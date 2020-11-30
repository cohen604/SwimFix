import os
import pickle
from typing import List, Tuple, Dict

import numpy as np
from flask import Flask, request, send_file



app = Flask(__name__)


@app.route('/detect', methods=['GET'])
def detect():

    print('received data')
    #data = request.json
    print(request.files['video'])
    print(dir(request.files['video']))
    movie_bytes = request.files['video'].read()
    print('got movie')
    print(movie_bytes)
    #movie = np.array(data['video'])
    movie = pickle.loads(movie_bytes)
    print(movie.shape)
    
    return "Hello"
    
@app.route('/')
def hello():
    return "Hello SwimFix"

if __name__ == "__main__":
    app.run(port='8080', debug=True)

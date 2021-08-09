# SwimFix



## Introduction

This folder contains things related to the machine learning part of the application.

The machine learning model is build inside a flask RESTful server that serve requests from the main application.

## Quick install

In order to install and run the server, you need to do the following steps:

1.  ```virtualenv .env```
2. ```source .env/bin/activate```
3. For CUDA support `pip install -r requirements/cuda.txt` and for CPU support `pip install -r requirements/cuda.txt`
4. ```cd src```
5. ``` python3 app.py```

## Docker

In order to run the server using Docker, you need to first build the image:

`docker build -t mlserver .`

By default the image is built for CPU support so if you need CUDA support run the following:

`docker build -t mlserver --build-arg DEVICE=cuda .`

To run the server run the following:

`docker run -p 5050:5050 mlserver`

## Config

the app is running by default with CPU support and on data weights from detectron2 [model zoo](https://github.com/facebookresearch/detectron2/blob/master/MODEL_ZOO.md)

In order to change support for CUDA edit in `app.py` the following line accordingly:

`model, cfg = get_model()`

`get_model()` can have the following
```
def get_model(trained: bool = False, load_path: str = "model_final.pth", score_tresh: int = 0.75, device: str = "cpu")

trained: bool = False
if False the app will use data weights from model zoo

load_path: str = "model_final.pth"
if trained is True this should be set to the data weigths path

score_tresh: int = 0.75


device: str = "cpu"
can be "cpu" or "cuda"
```

In order to use a different IP or port edit in `app.py` the following line accordingly:

`app.run(host='0.0.0.0', port='5050', debug=True)`

## Utils

the utils folder in the src folder contains general utility scripts and notebooks that training the model and evaluating it.

### labels_to_csv
this file converts one movie labels from coco's Json format into csv files that is used in the main application.
the script has 3 command line arguments that are needed

1. --movie_name: the name of the movie we want to extract the labels for.
2. --metadata_path: the path to the coco's labels from coco annotator.
3. --save_path: the location which you want to save the csv file.

### train
this file trains facebook's detectron2 keypoint model
the script has the following command line arguments:

1. --data_path: path to the dataset images.
2. --metadata_path: path to the coco's labels.
3. --save_path: where to save the trained model.
4. --model_yaml: yaml file of a pretrained model from the detectron2 model zoo to use.
5. --lr, --learning_rate: model learning rate during training.
6. --max_iter: maximum number of iteration to run the model. 

### model evaluation
this notebook contains methods to evaluate the trained model.
the notebook evaluates the model by comparing the trained model to a pretrained model using 3 comparison techniques.

1. we print graphs of each keypoint the both the pretrained and trained model compared to the labels grouth label.
2. we print the rmse of each keypoint comparing the pretrained model vs the trained model.
3. we calculate the precision and recall of each keypoint.

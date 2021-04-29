import argparse
import os
import json
import random

# Some basic setup:
import torch, torchvision

# Setup detectron2 logger
import detectron2
from detectron2.utils.logger import setup_logger
setup_logger()

# import some common libraries
import numpy as np
import cv2

# import some common detectron2 utilities
from detectron2 import model_zoo
from detectron2.engine import DefaultPredictor
from detectron2.engine import DefaultTrainer
from detectron2.config import get_cfg
from detectron2.structures import Keypoints
from detectron2.utils.visualizer import Visualizer
from detectron2.data import MetadataCatalog, DatasetCatalog, build_detection_test_loader
from detectron2.data.datasets import register_coco_instances
from detectron2.evaluation import COCOEvaluator, inference_on_dataset

def parse_arg():
    parser = argparse.ArgumentParser()

    parser.add_argument('--data_path', default='./datasets/Detectron', type=str, help='Location on the disk on the image dataset')
    parser.add_argument('--metadata_path', default='./Detectron-4.json', type=str, help='Location on the disk on dataset labels (metadata)')
    parser.add_argument('--save_path', default='./outputs', type=str, help='Where to save the trained model')
    parser.add_argument('--model_yaml', default='keypoint_rcnn_R_50_FPN_3x', type=str, help='Base model')
    parser.add_argument('--lr', '--learning_rate', default=0.00025, type=float, help='Model learning rate')
    parser.add_argument('--max_iter', default=1000, type=int, help='Number of model iteations')

    return parser.parse_args()

def train_model(learning_rate: float, max_iter: int, save_path: str, model_yaml: str='keypoint_rcnn_R_50_FPN_3x'):
    """
    Trains the model with custom parameters.

    Args:
        learning rate: Model's learning rate.
        max_iter: The number of maximum iteratioh the model is trained for.
        save_path: Where to save the trained model.
        model_yaml: the pre-trained model to use as a baseline.
    """
    cfg = get_cfg()
    cfg.merge_from_file(
    model_zoo.get_config_file(f"COCO-Keypoints/{model_yaml}.yaml")
    )
    cfg.DATASETS.TRAIN = ("Swimming",)
    cfg.DATASETS.TEST = ()  # no metrics implemented for this dataset
    cfg.DATALOADER.NUM_WORKERS = 2
    cfg.MODEL.WEIGHTS = f"detectron2://COCO-Keypoints/{model_yaml}/137849621/model_final_a6e10b.pkl"  # initialize from model zoo
    cfg.SOLVER.IMS_PER_BATCH = 2
    cfg.SOLVER.BASE_LR = learning_rate  # pick a good LR
    cfg.SOLVER.MAX_ITER = max_iter    # 300 iterations seems good enough for this toy dataset; you will need to train longer for a practical dataset
    cfg.SOLVER.STEPS = []        # do not decay learning rate
    cfg.MODEL.ROI_HEADS.BATCH_SIZE_PER_IMAGE = 32
    cfg.MODEL.ROI_HEADS.NUM_CLASSES = 1
    cfg.MODEL.ROI_KEYPOINT_HEAD.NUM_KEYPOINTS = 7
    cfg.OUTPUT_DIR = save_path
    
    os.makedirs(cfg.OUTPUT_DIR, exist_ok=True)
    trainer = DefaultTrainer(cfg)
    trainer.resume_or_load(resume=False)
    trainer.train()

def load_dataset(metadata_path: str='Detectron-4.json', data_path: str='./datasets/Detectron/'):
    """
    Loads the dataset with the metadata from the paths.

    Args:
        metadata_path: Path of the metadata on the disk.
        data_path: Location of the image dataset.

    Returns:
        A detectron dataset object.
    """
    with open(metadata_path, 'r') as f:
        data = json.load(f)
            
    skeleton = dict(zip(list(range(len(data['categories'][0]['keypoints']))), data['categories'][0]['keypoints']))
    conncted = []
    for start, end in data['categories'][0]['skeleton']:
        conncted.append([skeleton[start - 1], skeleton[end - 1], (0, 255, 0)])
    
    if 'Swimming' not in MetadataCatalog.list():    
        register_coco_instances("Swimming", {}, metadata_path, data_path)

        MetadataCatalog.get("Swimming").set(keypoint_names=data['categories'][0]['keypoints'])
        MetadataCatalog.get("Swimming").set(keypoint_flip_map=[['RSholder', 'LShoulder'], ['RWrist', 'LWrist'], ['RElbow', 'LElbow']])
        MetadataCatalog.get("Swimming").set(keypoint_connection_rules=conncted)
    
    return data

def main():
    parser = parse_arg()

    dataset = load_dataset(parser.metadata_path, parser.data_path)

    train_model(parser.lr, parser.max_iter, parser.save_path, parser.model_yaml)

if __name__ == "__main__":
    main()
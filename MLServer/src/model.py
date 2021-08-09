import os

from detectron2 import model_zoo
from detectron2.engine import DefaultPredictor
from detectron2.config import get_cfg


def get_model(trained: bool = False, load_path: str = "model_final.pth", score_tresh: int = 0.75, device: str = "cpu"):

    cfg = get_cfg()

    cfg.merge_from_file(
        model_zoo.get_config_file(
            f"COCO-Keypoints/keypoint_rcnn_R_50_FPN_3x.yaml")
    )
    cfg.MODEL.DEVICE = device
    cfg.TEST.DETECTIONS_PER_IMAGE = 1
    cfg.MODEL.ROI_HEADS.SCORE_THRESH_TEST = score_tresh

    if trained:
        cfg.DATALOADER.NUM_WORKERS = 2
        cfg.MODEL.ROI_HEADS.BATCH_SIZE_PER_IMAGE = 4
        cfg.MODEL.ROI_HEADS.NUM_CLASSES = 1
        cfg.MODEL.ROI_KEYPOINT_HEAD.NUM_KEYPOINTS = 7
        cfg.TEST.KEYPOINT_OKS_SIGMAS = [1] * 7
        cfg.MODEL.WEIGHTS = os.path.join(cfg.OUTPUT_DIR, load_path)
        # set the testing threshold for this model
        cfg.DATASETS.TEST = ("Swimming", )
        predictor = DefaultPredictor(cfg)

    else:
        cfg.MODEL.WEIGHTS = f"detectron2://ImageNetPretrained/MSRA/R-50.pkl"  # initialize
        # set the testing threshold for this model
        predictor = DefaultPredictor(cfg)

    return predictor, cfg

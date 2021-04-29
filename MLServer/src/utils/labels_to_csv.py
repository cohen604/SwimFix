import argparse
import json
import os
from typing import Dict, List

import pandas as pd
from tqdm import tqdm

def parse_arg():
    parser = argparse.ArgumentParser()

    parser.add_argument('--movie_name', default='MVI_7934.mov', type=str, help='Name of the movie to extract the labels for.')
    parser.add_argument('--metadata_path', default='./Detectron-4.json', type=str, help='Location on the disk on dataset labels (metadata)')
    parser.add_argument('--save_path', default='./', type=str, help='Where to save the csv labels')

    return parser.parse_args()

def generate_labels_csv(labels: Dict[int, List[int]], save_path: str):
    names = ['head_x', 'head_y', 'right_shoulder_x', 'right_shoulder_y',
             'right_elbow_x', 'right_elbow_y', 'right_wrist_x', 'right_wrist_y',
             'left_shoulder_x', 'left_shoulder_y', 'left_elbow_x', 'left_elbow_y',
             'left_wrist_x', 'left_wrist_y'
            ]
    df = pd.DataFrame(columns=names)
    point_labels = [names[i:i+2] for i in range(0, len(names), 2)]
    for num, frame in (t := tqdm(labels.items())):
        t.set_description(f'Frame: {num}')
        points = [frame[i:i+3] for i in range(0, len(frame), 3)]
        di = {}
        for (x, y, vis), (label_x, label_y) in zip(points, point_labels):
            x, y = str(x), str(y)
            if vis == 0:
                x = y = ''
            di[label_x], di[label_y] = x, y
        series = pd.Series(di)
        df = df.append(series, ignore_index=True)

    if not os.path.exists(save_path):
        os.makedirs(save_path, exist_ok=True)
    df.to_csv(f'{save_path}/labels.csv', index=False)

def get_movie_labels(dataset, movie_path):
    movie_name = movie_path.split('/')[-1].split('.')[0]
    print(movie_name)
    name_len = len(movie_name)
    labels = {}
    for i, image_metadata in enumerate(dataset['images']):
        if image_metadata['file_name'].startswith(movie_name):
            frame_num = int(image_metadata['file_name'][name_len:].split('.')[0]) -1
            labels[frame_num] = dataset['annotations'][i]['keypoints']
            
    return labels

def load_dataset(metadata_path: str='Detectron-4.json'):
    with open(metadata_path, 'r') as f:
        data = json.load(f)

    return data

def main():
    parser = parse_arg()

    metadata = load_dataset(parser.metadata_path)

    labels = get_movie_labels(metadata, parser.movie_name)

    generate_labels_csv(labels, parser.save_path)

if __name__ == "__main__":
    main()
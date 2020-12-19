import base64

import numpy as np

def get_frame(frame_string: str, height: int, width: int) -> np.ndarray:

    """
    Converts base64 encoding into video frame

    Parameters
    ----------
    frame_string: str
        video frame bytes encoded in base64.

    height: int
        Height of the frame.

    width: int
        Width of the frame.

    Returns
    -------
    frame: np.ndarray
        Numpy array of the frame video.
    """

    frame_bytes = base64.b64decode(frame_string)
    frame = np.frombuffer(frame_bytes, dtype=np.uint8)
    frame = frame.reshape(height, width, 3)
    return frame
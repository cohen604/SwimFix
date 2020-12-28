package Domain.SwimmingData;

import org.opencv.core.Mat;

public interface SwimmingError {

    void draw(Mat frame, SwimmingSkeleton skeleton);
}

package Domain.SwimmingData.Errors;

import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class RightElbowError extends ElbowError {

    public RightElbowError(IDraw drawer, double angle) {
        super(drawer, angle);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint shoulder = skeleton.getPoint(KeyPoint.R_SHOULDER);
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.R_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
        drawShoulderElbowWrist(frame, shoulder, elbow, wrist, 50);
    }
}

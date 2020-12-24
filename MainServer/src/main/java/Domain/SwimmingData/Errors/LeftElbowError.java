package Domain.SwimmingData.Errors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class LeftElbowError extends ElbowError {

    public LeftElbowError(double angle) {
        super(angle);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint shoulder = skeleton.getPoint(KeyPoint.L_SHOULDER);
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.L_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
        drawShoulderElbowWrist(frame, shoulder, elbow, wrist, -50);
    }
}

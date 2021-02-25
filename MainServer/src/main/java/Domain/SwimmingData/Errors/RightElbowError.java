package Domain.SwimmingData.Errors;

import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class RightElbowError extends ElbowError {

    public RightElbowError(IDraw drawer, double angle) {
        super(drawer, angle);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint shoulder = skeleton.getRightShoulder();
        SkeletonPoint elbow = skeleton.getRightElbow();
        SkeletonPoint wrist = skeleton.getRightWrist();
        drawShoulderElbowWrist(frame, shoulder, elbow, wrist, 50);
    }
}

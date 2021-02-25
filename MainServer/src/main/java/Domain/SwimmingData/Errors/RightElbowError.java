package Domain.SwimmingData.Errors;

import Domain.SwimmingData.*;
import org.opencv.core.Mat;

public class RightElbowError extends ElbowError {

    public RightElbowError(IDraw drawer, double angle) {
        super(drawer, angle);
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getRightShoulder();
        IPoint elbow = skeleton.getRightElbow();
        IPoint wrist = skeleton.getRightWrist();
        drawShoulderElbowWrist(frame, shoulder, elbow, wrist, 50);
    }
}

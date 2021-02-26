package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;
import org.opencv.core.Mat;

public class LeftElbowError extends ElbowError {

    public LeftElbowError(IDraw drawer, double angle) {
        super(drawer, angle);
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getLeftShoulder();
        IPoint elbow = skeleton.getLeftElbow();
        IPoint wrist = skeleton.getLeftWrist();
        drawShoulderElbowWrist(frame, shoulder, elbow, wrist, -50);
    }
}

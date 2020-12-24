package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Draw;
import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

public abstract class ElbowError extends Draw implements SwimmingError {

    private double angle;

    public ElbowError(double angle) {
        this.angle = angle;
    }

    public void drawShoulderElbowWrist(Mat frame, SkeletonPoint shoulder, SkeletonPoint elbow,
                                       SkeletonPoint wrist) {
        double r = 255.0, g = 0.0, b = 0.0, a = 0.0;
        int thickness = 3;
        drawLine(frame, shoulder, elbow, r, g, b, a, thickness);
        drawLine(frame, elbow, wrist, r, g, b, a, thickness);
    }
}

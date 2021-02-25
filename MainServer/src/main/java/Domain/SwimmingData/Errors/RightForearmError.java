package Domain.SwimmingData.Errors;

import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.IPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class RightForearmError extends ForearmError{

    public RightForearmError(IDraw drawer, double angle) {
        super(drawer, angle);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        IPoint elbow = skeleton.getRightElbow();
        IPoint wrist = skeleton.getRightWrist();
        int thickness = 2;
        double r = 240, g = 88, b = 248, a = 255.0;
        drawLine(frame, elbow, wrist, r, g, b, a, thickness);
    }
}

package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Draw;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

public abstract class PalmCrossHeadError extends Draw implements SwimmingError {

    public void drawPalmCrossHead(Mat frame, SkeletonPoint head, SkeletonPoint wrist) {
        double r = 255.0, g = 0.0, b = 0.0, a = 0.0;
        int thickness = 3;
        SkeletonPoint verticalHead = new SkeletonPoint(head.getX(),wrist.getY() + 25, head.getX());
        drawLine(frame, head, verticalHead, r, g, b, a, thickness);
    }

}

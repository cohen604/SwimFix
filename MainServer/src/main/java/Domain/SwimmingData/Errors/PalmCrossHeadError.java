package Domain.SwimmingData.Errors;

import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

public abstract class PalmCrossHeadError extends SwimmingError {


    public PalmCrossHeadError(IDraw drawer) {
        super(drawer);
    }

    public void drawPalmCrossHead(Mat frame, SkeletonPoint head, SkeletonPoint wrist) {
        double r = 253.0, g = 139.0, b = 31.0, a = 255.0;
        int thickness = 3;
        SkeletonPoint verticalHead = new SkeletonPoint(head.getX(),wrist.getY() + 25, -1);
        drawLine(frame, head, verticalHead, r, g, b, a, thickness);
    }

}

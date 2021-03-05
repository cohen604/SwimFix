package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonGraph.SkeletonPoint;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

public abstract class PalmCrossHeadError extends SwimmingError {

    protected boolean inside;

    public PalmCrossHeadError(IDraw drawer, boolean inside) {
        super(drawer);
        this.inside = inside;
    }

    public void drawPalmCrossHead(Mat frame, IPoint head, IPoint wrist) {
        double r = 253.0, g = 139.0, b = 31.0, a = 255.0;
        int thickness = 3;
        SkeletonPoint verticalHead = new SkeletonPoint(head.getX(),wrist.getY() + 25, -1);
        drawLine(frame, head, verticalHead, r, g, b, a, thickness);
    }

}

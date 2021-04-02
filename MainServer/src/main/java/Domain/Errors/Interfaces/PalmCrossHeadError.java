package Domain.Errors.Interfaces;

import Domain.Drawing.IDraw;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SkeletonPoint;
import org.opencv.core.Mat;

public abstract class PalmCrossHeadError extends SwimmingError {

    protected boolean inside;

    public PalmCrossHeadError(IDraw drawer, boolean inside, String tag) {
        super(drawer, tag);
        this.inside = inside;
    }

    public void drawPalmCrossHead(Mat frame, IPoint head, IPoint wrist) {
        double r = 253.0, g = 139.0, b = 31.0, a = 255.0;
        int thickness = 3;
        SkeletonPoint verticalHead = new SkeletonPoint(head.getX(),wrist.getY() + 25, -1);
        drawLine(frame, head, verticalHead, r, g, b, a, thickness);
    }

    public boolean getInside() {
        return this.inside;
    }

}

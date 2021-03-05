package Domain.SwimmingData.Errors;

import Domain.SwimmingData.*;
import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.Points.IPointUtils;
import org.opencv.core.Mat;

public class RightPalmCrossHeadError extends PalmCrossHeadError{

    public RightPalmCrossHeadError(IDraw drawer, boolean inside) {
        super(drawer, inside);
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint head = skeleton.getHead();
        IPoint wrist = skeleton.getRightWrist();
        drawPalmCrossHead(frame, head, wrist);

        //double slope = 1 / IPointUtils.calcSlope(wrist, skeleton.getRightElbow());
        IPoint endArrow = null;
        double additionX = inside ? -20 : 35;
        endArrow = IPointUtils.addByScalars(wrist, additionX, 0);
        drawArrow(frame, wrist, endArrow);
    }
}

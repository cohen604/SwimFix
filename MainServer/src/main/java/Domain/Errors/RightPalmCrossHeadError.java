package Domain.Errors;

import Domain.Errors.Interfaces.PalmCrossHeadError;
import Domain.SwimmingSkeletonsData.*;
import Domain.Drawing.IDraw;
import Domain.Points.IPoint;
import Domain.Points.IPointUtils;
import org.opencv.core.Mat;

public class RightPalmCrossHeadError extends PalmCrossHeadError {

    public RightPalmCrossHeadError(IDraw drawer, boolean inside) {
        super(drawer, inside, "Right Palm Error");
    }

    @Override
    public void drawBefore(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint head = skeleton.getHead();
        IPoint wrist = skeleton.getRightWrist();
        drawPalmCrossHead(frame, head, wrist);
    }

    @Override
    public void drawAfter(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint wrist = skeleton.getRightWrist();

        double additionX = inside ? -20 : 35;
        IPoint endArrow = IPointUtils.addByScalars(wrist, additionX, 0);
        drawArrow(frame, wrist, endArrow);
    }
}

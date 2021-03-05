package Domain.SwimmingData.Errors;
import Domain.SwimmingData.*;
import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.Points.IPointUtils;
import org.opencv.core.Mat;

public class LeftPalmCrossHeadError extends PalmCrossHeadError {


    public LeftPalmCrossHeadError(IDraw drawer, boolean inside) {
        super(drawer, inside);
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint head = skeleton.getHead();
        IPoint wrist = skeleton.getLeftWrist();
        drawPalmCrossHead(frame, head, wrist);

        IPoint endArrow = null;
        //double slope = 1 / IPointUtils.calcSlope(wrist, skeleton.getLeftElbow());
        double additionX = inside ? 20 : -35;
        endArrow = IPointUtils.addByScalars(wrist, additionX, 0);
        drawArrow(frame, wrist, endArrow);
    }
}

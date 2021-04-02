package Domain.Errors;
import Domain.Errors.Interfaces.PalmCrossHeadError;
import Domain.SwimmingSkeletonsData.*;
import Domain.Drawing.IDraw;
import Domain.Points.IPoint;
import Domain.Points.IPointUtils;
import org.opencv.core.Mat;

public class LeftPalmCrossHeadError extends PalmCrossHeadError {


    public LeftPalmCrossHeadError(IDraw drawer, boolean inside) {
        super(drawer, inside, "Left Palm Error");
    }

    @Override
    public void drawBefore(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint head = skeleton.getHead();
        IPoint wrist = skeleton.getLeftWrist();
        drawPalmCrossHead(frame, head, wrist);

    }

    @Override
    public void drawAfter(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint wrist = skeleton.getLeftWrist();

        double additionX = inside ? 20 : -35;
        IPoint endArrow = IPointUtils.addByScalars(wrist, additionX, 0);
        drawArrow(frame, wrist, endArrow);
    }
}

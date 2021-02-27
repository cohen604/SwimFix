package Domain.SwimmingData.Errors;
import Domain.SwimmingData.*;
import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import org.opencv.core.Mat;

public class LeftPalmCrossHeadError extends PalmCrossHeadError {


    public LeftPalmCrossHeadError(IDraw drawer) {
        super(drawer);
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint head = skeleton.getHead();
        IPoint wrist = skeleton.getLeftWrist();
        drawPalmCrossHead(frame, head, wrist);
    }
}

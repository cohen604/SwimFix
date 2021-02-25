package Domain.SwimmingData.Errors;
import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.IPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class LeftPalmCrossHeadError extends PalmCrossHeadError {


    public LeftPalmCrossHeadError(IDraw drawer) {
        super(drawer);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        IPoint head = skeleton.getHead();
        IPoint wrist = skeleton.getLeftWrist();
        drawPalmCrossHead(frame, head, wrist);
    }
}

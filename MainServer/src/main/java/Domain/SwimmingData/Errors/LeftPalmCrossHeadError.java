package Domain.SwimmingData.Errors;
import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class LeftPalmCrossHeadError extends PalmCrossHeadError {


    public LeftPalmCrossHeadError(IDraw drawer) {
        super(drawer);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint head = skeleton.getHead();
        SkeletonPoint wrist = skeleton.getLeftWrist();
        drawPalmCrossHead(frame, head, wrist);
    }
}

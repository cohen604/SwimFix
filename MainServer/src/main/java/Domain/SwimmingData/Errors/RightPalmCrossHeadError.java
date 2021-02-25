package Domain.SwimmingData.Errors;

import Domain.SwimmingData.*;
import org.opencv.core.Mat;

public class RightPalmCrossHeadError extends PalmCrossHeadError{

    public RightPalmCrossHeadError(IDraw drawer) {
        super(drawer);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        IPoint head = skeleton.getHead();
        IPoint wrist = skeleton.getRightWrist();
        drawPalmCrossHead(frame, head, wrist);
    }
}

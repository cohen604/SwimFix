package IntegrationTests.ErrorsTests.SkeletonGraph;

import Domain.Drawing.Draw;
import Domain.Drawing.IDraw;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import UnitTests.Erros.LeftForearmErrorTests;

public class LeftForearmErrorIntegrationTests extends LeftForearmErrorTests {

    private SetUpGraph setUpGraph;

    @Override
    protected void setUp() {
        super.setUp();
        setUpGraph = new SetUpGraph();
    }

    @Override
    protected IDraw setUpDraw() {
        return new Draw();
    }

    @Override
    protected IPoint setUpPoint(double x, double y) {
        return setUpGraph.setUpPoint(x,y);
    }

    @Override
    protected ISwimmingSkeleton setUpSwimmingSkeleton(
            double xHead, double yHead,
            double xRightShoulder, double yRightShoulder,
            double xRightElbow, double yRightElbow,
            double xRightWrist, double yRightWrist,
            double xLeftShoulder, double yLeftShoulder,
            double xLeftElbow, double yLeftElbow,
            double xLeftWrist, double yLeftWrist) {
        return setUpGraph.setUpSwimmingSkeleton(
                xHead, yHead,
                xRightShoulder, yRightShoulder,
                xRightElbow, yRightElbow,
                xRightWrist, yRightWrist,
                xLeftShoulder, yLeftShoulder,
                xLeftElbow, yLeftElbow,
                xLeftWrist, yLeftWrist);
    }
}

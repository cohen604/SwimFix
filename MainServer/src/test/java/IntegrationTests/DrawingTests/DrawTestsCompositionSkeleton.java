package IntegrationTests.DrawingTests;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SwimmingSkeleton;
import UnitTests.DrawingTests.DrawTests;

public class DrawTestsCompositionSkeleton extends DrawTests {

    @Override
    protected IPoint setUpPoint(double x, double y) {
        return new SkeletonPoint(x, y);
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
        IPoint head = new SkeletonPoint(xHead, yHead);
        IPoint rightShoulder = new SkeletonPoint(xRightShoulder, yRightShoulder);
        IPoint rightElbow = new SkeletonPoint(xRightElbow, yRightElbow);
        IPoint rightWrist = new SkeletonPoint(xRightWrist, yRightWrist);
        IPoint leftShoulder = new SkeletonPoint(xLeftShoulder, yRightShoulder);
        IPoint leftElbow = new SkeletonPoint(xLeftElbow, yLeftElbow);
        IPoint leftWrist = new SkeletonPoint(xLeftWrist, yLeftWrist);
        return new SwimmingSkeleton(head,
                rightShoulder,
                rightElbow,
                rightWrist,
                leftShoulder,
                leftElbow,
                leftWrist);
    }
}

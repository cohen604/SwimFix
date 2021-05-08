package IntegrationTests.DrawingTests;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SkeletonPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SwimmingSkeleton;
import UnitTests.DrawingTests.DrawTests;

import java.util.LinkedList;
import java.util.List;

public class DrawTestsGraphSkeleton extends DrawTests {

    @Override
    protected IPoint setUpPoint(double x, double y) {
        return new SkeletonPoint(x, y, 1);
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
        List<Double> dots = new LinkedList<>();
        dots.add(xHead);
        dots.add(yHead);
        dots.add(1.0);
        dots.add(xRightShoulder);
        dots.add(yRightShoulder);
        dots.add(1.0);
        dots.add(xRightElbow);
        dots.add(yRightElbow);
        dots.add(1.0);
        dots.add(xRightWrist);
        dots.add(yRightWrist);
        dots.add(1.0);
        dots.add(xLeftShoulder);
        dots.add(yLeftShoulder);
        dots.add(1.0);
        dots.add(xLeftElbow);
        dots.add(yLeftElbow);
        dots.add(1.0);
        dots.add(xLeftWrist);
        dots.add(yLeftWrist);
        dots.add(1.0);
        return new SwimmingSkeleton(dots);
    }
}

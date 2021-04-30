package UnitTests.SwimmingErrorDetectorsTests;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SwimmingSkeleton;

import java.util.LinkedList;
import java.util.List;

public class SkeletonsForTests {

    private void addPointToList(List<Double> list, double x, double y, double confidence) {
        list.add(x);
        list.add(y);
        list.add(confidence);
    }


    public ISwimmingSkeleton getSkeletonNoError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wrist
        return new SwimmingSkeleton(points);
    }

    public ISwimmingSkeleton getSkeletonPalmRightError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 2.5, 1, 1); //Right Elbow
        addPointToList(points, 2.0, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.5, 1); //Left Wrist
        return new SwimmingSkeleton(points);
    }

    public ISwimmingSkeleton getSkeletonPalmLeftError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 2, 0, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1); //Right Wrist
        addPointToList(points, 1.5, 0.5, 1); //Left Shoulder
        addPointToList(points, 1.5, 1, 1); //Left Elbow
        addPointToList(points, 2.0, 1.5, 1); //Left Wrist
        return new SwimmingSkeleton(points);
    }


    public ISwimmingSkeleton getSkeletonElbowRightError() {
        List<Double> points = new LinkedList<>();
        addPointToList(points, 1.5, 0.5, 1); //HEAD
        addPointToList(points, 2.5, 0.5, 1); //Right Shoulder
        addPointToList(points, 3, 1, 1); //Right Elbow
        addPointToList(points, 3, 1.5, 1);
        addPointToList(points, 1, 0.5, 1); //Left Shoulder
        addPointToList(points, 0.5, 1, 1); //Left Elbow
        addPointToList(points, 1, 1.6, 1); //Left Wrist
        return new SwimmingSkeleton(points);
    }

    // TODO - add more skeletons for other errors

}

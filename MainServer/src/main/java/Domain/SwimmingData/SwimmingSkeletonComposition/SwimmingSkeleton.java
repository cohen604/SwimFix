package Domain.SwimmingData.SwimmingSkeletonComposition;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class SwimmingSkeleton implements ISwimmingSkeleton {

    private IPoint head;
    private IPoint rightShoulder;
    private IPoint rightElbow;
    private IPoint rightWrist;
    private IPoint leftShoulder;
    private IPoint leftElbow;
    private IPoint leftWrist;
    private List<Pair<IPoint, IPoint>> edges;

    public SwimmingSkeleton(IPoint head,
                            IPoint rightShoulder, IPoint rightElbow, IPoint rightWrist,
                            IPoint leftShoulder, IPoint leftElbow, IPoint leftWrist) {
        this.head = head;
        this.rightShoulder = rightShoulder;
        this.rightElbow = rightElbow;
        this.rightWrist = rightWrist;
        this.leftShoulder = leftShoulder;
        this.leftElbow = leftElbow;
        this.leftWrist = leftWrist;
        setUpEdges();
    }

    private void setUpEdges() {
        edges = new LinkedList<>();
        edges.add(new Pair<>(head, rightShoulder));
        edges.add(new Pair<>(head, leftShoulder));
        edges.add(new Pair<>(rightShoulder, rightElbow));
        edges.add(new Pair<>(rightElbow, rightWrist));
        edges.add(new Pair<>(leftShoulder, leftElbow));
        edges.add(new Pair<>(leftElbow, leftWrist));
    }

    @Override
    public List<Pair<IPoint, IPoint>> getLines() {
        return this.edges;
    }

    @Override
    public boolean containsHead() {
        return this.head!=null;
    }

    @Override
    public boolean containsRightShoulder() {
        return this.rightShoulder!=null;
    }

    @Override
    public boolean containsRightElbow() {
        return this.rightElbow!=null;
    }

    @Override
    public boolean containsRightWrist() {
        return this.rightWrist!=null;
    }

    @Override
    public boolean containsLeftShoulder() {
        return this.leftShoulder!=null;
    }

    @Override
    public boolean containsLeftElbow() {
        return this.leftElbow!=null;
    }

    @Override
    public boolean containsLeftWrist() {
        return this.leftWrist!=null;
    }

    @Override
    public List<IPoint> getPoints() {
        List<IPoint> output = new LinkedList<>();
        output.add(this.head);
        output.add(this.rightShoulder);
        output.add(this.rightElbow);
        output.add(this.rightWrist);
        output.add(this.leftShoulder);
        output.add(this.leftElbow);
        output.add(this.leftWrist);
        return output;
    }

    @Override
    public IPoint getHead() {
        return this.head;
    }

    @Override
    public IPoint getRightShoulder() {
        return this.rightShoulder;
    }

    @Override
    public IPoint getRightElbow() {
        return this.rightElbow;
    }

    @Override
    public IPoint getRightWrist() {
        return this.rightWrist;
    }

    @Override
    public IPoint getLeftShoulder() {
        return this.leftShoulder;
    }

    @Override
    public IPoint getLeftElbow() {
        return this.leftElbow;
    }

    @Override
    public IPoint getLeftWrist() {
        return this.leftWrist;
    }
}

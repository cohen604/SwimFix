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

    public SwimmingSkeleton(IPoint head) {
        this.head = head;
        setUpAllEdges(false, false);
    }

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
        setUpAllEdges(true, true);
    }

    public SwimmingSkeleton(IPoint head,
                            IPoint shoulder, IPoint elbow, IPoint wrist, boolean side) {
        this.head = head;
        if(side) {
            this.rightShoulder = shoulder;
            this.rightElbow = elbow;
            this.rightWrist = wrist;
            setUpAllEdges(true, false);
        }
        else {
            this.leftShoulder = shoulder;
            this.leftElbow = elbow;
            this.leftWrist = wrist;
            setUpAllEdges(false, true);
        }
    }

    private void setUpAllEdges(boolean right, boolean left) {
        edges = new LinkedList<>();
        if(right) {
            setUpRightEdges();
        }
        if(left) {
            setUpLeftEdges();
        }
    }

    private void setUpRightEdges() {
        edges.add(new Pair<>(head, rightShoulder));
        edges.add(new Pair<>(rightShoulder, rightElbow));
        edges.add(new Pair<>(rightElbow, rightWrist));
    }

    private void setUpLeftEdges() {
        edges.add(new Pair<>(head, leftShoulder));
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
    public boolean hasRightSide() {
        return containsRightShoulder() && containsRightElbow()
                || containsRightElbow() && containsRightWrist();
    }

    @Override
    public boolean hasLeftSide() {
        return containsLeftShoulder() &&  containsLeftElbow()
                || containsLeftElbow() && containsLeftWrist();
    }

    private void addPointIfNotNull(List<IPoint> points, IPoint point) {
        if(point!=null) {
            points.add(point);
        }
    }

    @Override
    public List<IPoint> getPoints() {
        List<IPoint> output = new LinkedList<>();
        addPointIfNotNull(output, this.head);
        addPointIfNotNull(output, this.head);
        addPointIfNotNull(output, this.rightShoulder);
        addPointIfNotNull(output, this.rightElbow);
        addPointIfNotNull(output, this.rightWrist);
        addPointIfNotNull(output, this.leftShoulder);
        addPointIfNotNull(output, this.leftElbow);
        addPointIfNotNull(output, this.leftWrist);
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

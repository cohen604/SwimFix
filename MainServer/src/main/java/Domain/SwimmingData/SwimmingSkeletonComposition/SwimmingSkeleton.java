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

    public SwimmingSkeleton() {
        this.edges = new LinkedList<>();
    }

    public SwimmingSkeleton(IPoint head) {
        this.head = head;
        setUpAllEdges();
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
        setUpAllEdges();
    }

    private void setUpAllEdges() {
        edges = new LinkedList<>();
        addEdgeIfNotNull(head, rightShoulder);
        addEdgeIfNotNull(rightShoulder, rightElbow);
        addEdgeIfNotNull(rightElbow, rightWrist);
        addEdgeIfNotNull(head, leftShoulder);
        addEdgeIfNotNull(leftShoulder, leftElbow);
        addEdgeIfNotNull(leftElbow, leftWrist);
    }

    private void addEdgeIfNotNull(IPoint a, IPoint b) {
        if(a != null && b != null) {
            edges.add(new Pair<>(a, b));
        }
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

    public void setHead(IPoint head) {
        if( head != null) {
            this.head = head;
            addEdgeIfNotNull(head, rightShoulder);
            addEdgeIfNotNull(head, leftShoulder);
        }
    }

    public void setRightShoulder(IPoint rightShoulder) {
        if( rightShoulder != null) {
            this.rightShoulder = rightShoulder;
            addEdgeIfNotNull(head, rightShoulder);
            addEdgeIfNotNull(rightShoulder, rightElbow);
        }
    }

    public void setRightElbow(IPoint rightElbow) {
        if(rightElbow != null) {
            this.rightElbow = rightElbow;
            addEdgeIfNotNull(rightShoulder, rightElbow);
            addEdgeIfNotNull(rightElbow, rightWrist);
        }
    }

    public void setRightWrist(IPoint rightWrist) {
        if(rightWrist != null) {
            this.rightWrist = rightWrist;
            addEdgeIfNotNull(rightElbow, rightWrist);
        }
    }

    public void setLeftShoulder(IPoint leftShoulder) {
        if(leftShoulder != null) {
            this.leftShoulder = leftShoulder;
            addEdgeIfNotNull(head, leftShoulder);
            addEdgeIfNotNull(leftShoulder, leftElbow);
        }
    }

    public void setLeftElbow(IPoint leftElbow) {
        if(leftElbow != null) {
            this.leftElbow = leftElbow;
            addEdgeIfNotNull(leftShoulder, leftElbow);
            addEdgeIfNotNull(leftElbow, leftWrist);
        }
    }

    public void setLeftWrist(IPoint leftWrist) {
        if(leftWrist != null) {
            this.leftWrist = leftWrist;
            addEdgeIfNotNull(leftElbow, leftWrist);
        }
    }
}

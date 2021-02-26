package Domain.SwimmingData.SwimmingSkeletonGraph;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import javafx.util.Pair;

import java.util.*;

public class SwimmingSkeleton implements ISwimmingSkeleton {

    private Map<KeyPoint, IPoint> pointMap;
    private List<Pair<KeyPoint, KeyPoint>> edges;

    // TODO should be a builder class that do this complex building (its better)
    public SwimmingSkeleton(List<Double> frame) {
        this.pointMap = new HashMap<>();
        for(KeyPoint keyPoint : KeyPoint.values()) {
            SkeletonPoint point = toPoint(frame, keyPoint);
            if(point.isConfident()) {
                this.pointMap.put(keyPoint, point);
            }
        }
        edges = new LinkedList<>();
        edges.add(new Pair<>(KeyPoint.HEAD, KeyPoint.R_SHOULDER));
        edges.add(new Pair<>(KeyPoint.R_SHOULDER, KeyPoint.R_ELBOW));
        edges.add(new Pair<>(KeyPoint.R_ELBOW, KeyPoint.R_WRIST));
        edges.add(new Pair<>(KeyPoint.HEAD, KeyPoint.L_SHOULDER));
        edges.add(new Pair<>(KeyPoint.L_SHOULDER, KeyPoint.L_ELBOW));
        edges.add(new Pair<>(KeyPoint.L_ELBOW, KeyPoint.L_WRIST));
    }

    /**
     * The function return a Skeleton Point from the frame and keyPoint
      * @param frame - the frame points
     * @param keyPoint - the keyPoint to create the point
     * @return new skeleton point
     */
    private SkeletonPoint toPoint(List<Double> frame, KeyPoint keyPoint) {
        Double x = frame.get(keyPoint.value);
        Double y = frame.get(keyPoint.value+1);
        Double confident = frame.get(keyPoint.value+2);
        return new SkeletonPoint(x, y, confident);
    }

    /**
     * The function return a list of edges of skeleton points
     * @return the a list of edges
     */
    public List<Pair<IPoint, IPoint>> getLines() {
        List<Pair<IPoint, IPoint>> output = new LinkedList<>();
        for(Pair<KeyPoint, KeyPoint> edge: this.edges) {
            if(this.pointMap.containsKey(edge.getKey()) &&
                    this.pointMap.containsKey(edge.getValue())) {
                IPoint first = this.pointMap.get(edge.getKey());
                IPoint second = this.pointMap.get(edge.getValue());
                output.add(new Pair<>(first, second));
            }
        }
        return output;
    }

    public boolean containsHead() {
        return this.pointMap.containsKey(KeyPoint.HEAD);
    }

    public boolean containsRightShoulder() {
        return this.pointMap.containsKey(KeyPoint.R_SHOULDER);
    }

    public boolean containsRightElbow() {
        return this.pointMap.containsKey(KeyPoint.R_ELBOW);
    }

    public boolean containsRightWrist() {
        return this.pointMap.containsKey(KeyPoint.R_WRIST);
    }

    public boolean containsLeftShoulder() {
        return this.pointMap.containsKey(KeyPoint.L_SHOULDER);
    }

    public boolean containsLeftElbow() {
        return this.pointMap.containsKey(KeyPoint.L_ELBOW);
    }

    public boolean containsLeftWrist() {
        return this.pointMap.containsKey(KeyPoint.L_WRIST);
    }

    /**
     * Getters
     */

    public List<IPoint> getPoints() {
        return new LinkedList<>(this.pointMap.values());
    }

    public IPoint getHead() {
        return this.pointMap.get(KeyPoint.HEAD);
    }

    public IPoint getRightShoulder() {
        return this.pointMap.get(KeyPoint.R_SHOULDER);
    }

    public IPoint getRightElbow() {
        return this.pointMap.get(KeyPoint.R_ELBOW);
    }

    public IPoint getRightWrist() {
        return this.pointMap.get(KeyPoint.R_WRIST);
    }

    public IPoint getLeftShoulder() {
        return this.pointMap.get(KeyPoint.L_SHOULDER);
    }

    public IPoint getLeftElbow() {
        return this.pointMap.get(KeyPoint.L_ELBOW);
    }

    public IPoint getLeftWrist() {
        return this.pointMap.get(KeyPoint.L_WRIST);
    }
}
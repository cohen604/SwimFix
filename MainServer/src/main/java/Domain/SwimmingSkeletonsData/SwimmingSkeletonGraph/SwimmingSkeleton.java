package Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Points.IPoint;

import java.util.*;

public class SwimmingSkeleton implements ISwimmingSkeleton {

    private final static Map<KeyPoint, Double> CONFIDENCE_MAP = initConfidenceMap();
    private final static double DEFAULT_THRESHOLD = 0.35;

    private Map<KeyPoint, IPoint> pointMap;
    private List<Pair<KeyPoint, KeyPoint>> edges;

    public SwimmingSkeleton() {
        pointMap = new HashMap<>();
        edges = new LinkedList<>();
    }

    public SwimmingSkeleton(List<Double> frame) {
        this.pointMap = new HashMap<>();
        for(KeyPoint keyPoint : KeyPoint.values()) {
            SkeletonPoint point = toPoint(frame, keyPoint);
            if(isConfident(point, keyPoint)) {
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

    private static Map<KeyPoint, Double> initConfidenceMap() {
        HashMap<KeyPoint, Double> confidenceMap = new HashMap<>();
        confidenceMap.put(KeyPoint.HEAD, 0.02);
        confidenceMap.put(KeyPoint.R_SHOULDER, 0.02);
        confidenceMap.put(KeyPoint.R_ELBOW, 0.07);
        confidenceMap.put(KeyPoint.R_WRIST, 0.07);
        confidenceMap.put(KeyPoint.L_SHOULDER, 0.02);
        confidenceMap.put(KeyPoint.L_ELBOW, 0.07);
        confidenceMap.put(KeyPoint.L_WRIST, 0.07);
        return confidenceMap;
    }

    private boolean isConfident(SkeletonPoint point, KeyPoint keyPoint) {
        return point.getConfident() > CONFIDENCE_MAP.getOrDefault(keyPoint, DEFAULT_THRESHOLD);
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

    @Override
    public boolean hasRightSide() {
//        return containsRightShoulder() || containsRightElbow() || containsRightWrist();
        return containsRightShoulder() && containsRightElbow()
                || containsRightElbow() && containsRightWrist();
    }

    @Override
    public boolean hasLeftSide() {
//        return containsLeftShoulder() ||  containsLeftElbow() || containsLeftWrist();
        return containsLeftShoulder() &&  containsLeftElbow()
                || containsLeftElbow() && containsLeftWrist();
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
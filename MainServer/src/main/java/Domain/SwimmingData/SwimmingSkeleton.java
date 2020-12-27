package Domain.SwimmingData;

import javafx.util.Pair;

import java.util.*;

public class SwimmingSkeleton {

    private Map<KeyPoint, SkeletonPoint> pointMap;
    private List<Pair<KeyPoint, KeyPoint>> edges;

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
    public List<Pair<SkeletonPoint, SkeletonPoint>> getLines() {
        List<Pair<SkeletonPoint, SkeletonPoint>> output = new LinkedList<>();
        for(Pair<KeyPoint, KeyPoint> edge: this.edges) {
            if(this.pointMap.containsKey(edge.getKey()) &&
                    this.pointMap.containsKey(edge.getValue())) {
                SkeletonPoint first = this.pointMap.get(edge.getKey());
                SkeletonPoint second = this.pointMap.get(edge.getValue());
                output.add(new Pair<>(first, second));
            }
        }
        return output;
    }

    /**
     * The function return if the keys exits in the map
     * @param keys - the keys
     * @return true if all the keys contains
     */
    public boolean contatinsKeys(List<KeyPoint> keys) {
        for(KeyPoint key: keys) {
            if(!this.pointMap.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Getters
     */

    public List<SkeletonPoint> getPoints() {
        return new LinkedList<>(this.pointMap.values());
    }

    public Set<KeyPoint> getKeyPoints() {
        return this.pointMap.keySet();
    }

    public SkeletonPoint getPoint(KeyPoint key) {
        return this.pointMap.get(key);
    }

}
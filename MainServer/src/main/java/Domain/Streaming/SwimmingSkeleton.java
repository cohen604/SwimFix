package Domain.Streaming;

import javafx.util.Pair;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SwimmingSkeleton {

    private Map<KeyPoint, SkeletonPoint> pointMap;
    private List<Pair<KeyPoint, KeyPoint>> edges;

    public SwimmingSkeleton(List<Double> frame) {
        this.pointMap = new HashMap<>();
        for(KeyPoint keyPoint : KeyPoint.values()) {
            this.pointMap.put(keyPoint, toPoint(frame, keyPoint));
        }
        edges = new LinkedList<>();
        edges.add(new Pair<>(KeyPoint.HEAD, KeyPoint.R_SHOULDER));
        edges.add(new Pair<>(KeyPoint.R_SHOULDER, KeyPoint.R_ELBOW));
        edges.add(new Pair<>(KeyPoint.R_ELBOW, KeyPoint.R_WRIST));
        edges.add(new Pair<>(KeyPoint.HEAD, KeyPoint.L_SHOULDER));
        edges.add(new Pair<>(KeyPoint.L_SHOULDER, KeyPoint.L_ELBOW));
        edges.add(new Pair<>(KeyPoint.L_ELBOW, KeyPoint.L_WRIST));
    }

    private SkeletonPoint toPoint(List<Double> frame, KeyPoint keyPoint) {
        Double x = frame.get(keyPoint.value);
        Double y = frame.get(keyPoint.value+1);
        Double confident = frame.get(keyPoint.value+2);
        return new SkeletonPoint(x, y, confident);
    }

    public List<SkeletonPoint> getPoints() {
        List<SkeletonPoint> output = new LinkedList<>();
        for(SkeletonPoint point: this.pointMap.values()) {
            if(point.isConfedent()) {
                output.add(point);
            }
        }
        return output;
    }

    public List<Pair<SkeletonPoint, SkeletonPoint>> getLines() {
        List<Pair<SkeletonPoint, SkeletonPoint>> output = new LinkedList<>();
        for(Pair<KeyPoint, KeyPoint> edge: this.edges) {
            SkeletonPoint first = this.pointMap.get(edge.getKey());
            SkeletonPoint second = this.pointMap.get(edge.getValue());
            if(first.isConfedent() && second.isConfedent()) {
                output.add(new Pair<>(first, second));
            }
        }
        return output;
    }
}
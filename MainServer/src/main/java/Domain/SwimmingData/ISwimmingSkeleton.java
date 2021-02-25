package Domain.SwimmingData;

import javafx.util.Pair;
import java.util.List;

public interface ISwimmingSkeleton {

    List<Pair<IPoint, IPoint>> getLines();

    boolean containsHead();

    boolean containsRightShoulder();

    boolean containsRightElbow();

    boolean containsRightWrist();

    boolean containsLeftShoulder();

    boolean containsLeftElbow();

    boolean containsLeftWrist();

    List<IPoint> getPoints();

    IPoint getHead();

    IPoint getRightShoulder();

    IPoint getRightElbow();

    IPoint getRightWrist();

    IPoint getLeftShoulder();

    IPoint getLeftElbow();

    IPoint getLeftWrist();
}

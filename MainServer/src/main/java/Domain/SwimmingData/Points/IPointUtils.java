package Domain.SwimmingData.Points;

public class IPointUtils {

    public static double calcDistance(IPoint current, IPoint other) {
        double dx = Math.pow(current.getX() - other.getX(), 2);
        double dy = Math.pow(current.getY() - other.getY(), 2);
        return Math.sqrt(dx + dy);
    }

    public static double calcSlope(IPoint current, IPoint other) {
        double dy = other.getY() - current.getY();
        double dx = other.getX() - current.getX();
        return dy / dx;
    }
}

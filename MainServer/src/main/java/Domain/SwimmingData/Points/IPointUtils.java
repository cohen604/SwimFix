package Domain.SwimmingData.Points;

import Domain.SwimmingData.SwimmingSkeletonGraph.SkeletonPoint;

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

    public static double dotProduct(IPoint current, IPoint other) {
        return current.getX() * other.getX() + current.getY() * other.getY();
    }

    public static double getSize(IPoint current) {
        return Math.sqrt(current.getX() * current.getX() + current.getY() * current.getY());
    }

    public static IPoint getNormalVec(IPoint current, IPoint other) {
        double x = other.getX() - current.getX();
        double y = other.getY() - current.getY();
        double size = Math.sqrt(x*x + y*y);
        return new SkeletonPoint( x/size, y/size, -1);
    }

    public static double getAngleBetween(IPoint current, IPoint other) {
        double top = dotProduct(current, other);
        double bottom = getSize(current) * getSize(other);
        double angleRad = Math.acos(top/bottom);
        return Math.toDegrees(angleRad);
    }

}

package Domain.SwimmingData.Points;

import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;

public class IPointUtils {

    public static double calcDistance(IPoint current, IPoint other) {
        double dx = Math.pow(current.getX() - other.getX(), 2);
        double dy = Math.pow(current.getY() - other.getY(), 2);
        return Math.sqrt(dx + dy);
    }

    public static double calcDistance(double x0, double x1, double y0, double y1) {
        double dx = Math.pow(x0 - x1, 2);
        double dy = Math.pow(y0 - y1, 2);
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
        return new SkeletonPoint( x/size, y/size);
    }

    public static IPoint getVec(IPoint current, IPoint other) {
        double x = other.getX() - current.getX();
        double y = other.getY() - current.getY();
        return new SkeletonPoint(x, y);
    }

    public static double getAngleBetween(IPoint current, IPoint other) {
        double top = dotProduct(current, other);
        double bottom = getSize(current) * getSize(other);
        double angleRad = Math.acos(top/bottom);
        return Math.toDegrees(angleRad);
    }

    public static IPoint mulByScalar(IPoint a, double scalar) {
        return new SkeletonPoint(a.getX() * scalar, a.getY() * scalar);
    }

    public static IPoint addByScalars(IPoint a, double scalarX, double scalarY) {
        return new SkeletonPoint(a.getX() + scalarX, a.getY() + scalarY);
    }

    public static IPoint getMiddlePoint(IPoint a, IPoint b) {
        double x = (a.getX() + b.getX()) / 2;
        double y = (a.getY() + b.getY()) / 2;
        return new SkeletonPoint( x, y);
    }

    public static IPoint calcPointOnLinearLineLowerThenDistance(double slop, IPoint point, double y) {
        double distance = -1;
        double x = -1;
        double thresholdDistance = 30;
        do {
            x = (point.getY() - y) / slop + point.getX();
            distance = calcDistance(point.getX(), x, point.getY(), y);
            if(distance > thresholdDistance) {
                if( y < point.getY()) {
                    y ++;
                }
                else if(y > point.getY()){
                    y --;
                }
            }
        }
        while (distance > thresholdDistance);
        return new SkeletonPoint(x, y);
    }

    public static IPoint calcPointOnLinearForGivenX(double slope, IPoint point, double x) {
        double y = point.getY() + slope * (x - point.getX());
        return new SkeletonPoint(x, y);
    }

    public static IPoint pivotVector(IPoint point, double theta) {
        double py = point.getY();
        double px = point.getX();
        double x = px * Math.cos(theta) - py * Math.sin(theta);
        double y = px * Math.sin(theta) + py * Math.cos(theta);
        return new SkeletonPoint(x, y);
    }

}

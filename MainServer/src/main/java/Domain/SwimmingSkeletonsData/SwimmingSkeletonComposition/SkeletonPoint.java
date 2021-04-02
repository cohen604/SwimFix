package Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition;

import Domain.Points.IPoint;

public class SkeletonPoint implements IPoint {

    private double x;
    private double y;

    public SkeletonPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getters
     */

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}

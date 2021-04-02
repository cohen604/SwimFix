package Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph;

import Domain.Points.IPoint;

public class SkeletonPoint implements IPoint {

    private double x;
    private double y;
    private double confident;

    public SkeletonPoint(double x, double y, double confident) {
        this.x = x;
        this.y = y;
        this.confident = confident;
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

    public double getConfident() {
        return confident;
    }

    public void setConfident(double confident) {
        this.confident = confident;
    }

}

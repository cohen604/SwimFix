package Domain.SwimmingData.SwimmingSkeletonGraph;

import Domain.SwimmingData.Points.IPoint;

public class SkeletonPoint implements IPoint {

    private final static double THRESHOLD =  0.05;

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

    public boolean isConfident() {
        return this.confident > THRESHOLD;
    }

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

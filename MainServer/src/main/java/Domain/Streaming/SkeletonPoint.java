package Domain.Streaming;

public class SkeletonPoint {

    private final static double ThRESHOLD =  0.05;

    private double x;
    private double y;
    private double confident;

    public SkeletonPoint(double x, double y, double confident) {
        this.x = x;
        this.y = y;
        this.confident = confident;
    }

    public boolean isConfedent() {
        return this.confident > ThRESHOLD;
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

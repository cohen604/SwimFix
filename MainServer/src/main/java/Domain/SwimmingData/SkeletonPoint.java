package Domain.SwimmingData;

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

    /**
     * The function calc a distance between this and the other point
     * @param other
     * @return
     */
    public double calcDistance(SkeletonPoint other) {
        double dx = Math.pow(this.x - other.x, 2);
        double dy = Math.pow(this.y - other.y, 2);
        return Math.sqrt(dx + dy);
    }

    /**
     *
     * @param other
     * @return
     */
    public double dotProduct(SkeletonPoint other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     *
     * @return
     */
    public double getSize() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     *
     * @param other
     * @return
     */
    public SkeletonPoint getNormalVec(SkeletonPoint other) {
        double x = other.getX() - this.x;
        double y = other.getY() - this.y;
        double size = Math.sqrt(x*x + y*y);
        return new SkeletonPoint( x/size, y/size, -1);
    }

    /**
     *
     * @param other
     * @return
     */
    public double getAngleBetween(SkeletonPoint other) {
        double top = dotProduct(other);
        double bottom = getSize() * other.getSize();
        double angleRad = Math.acos(top/bottom);
        return Math.toDegrees(angleRad);
    }

    /**
     * Getters
     */

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

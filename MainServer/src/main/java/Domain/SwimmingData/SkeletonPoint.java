package Domain.SwimmingData;

public class SkeletonPoint {

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
     * The function calc a distance between this and the other point
     * @param other - skeleton point
     * @return the distance between the points
     */
    public double calcDistance(SkeletonPoint other) {
        double dx = Math.pow(this.x - other.x, 2);
        double dy = Math.pow(this.y - other.y, 2);
        return Math.sqrt(dx + dy);
    }

    /**
     * The function calc the slop between this point and the other point
     * @param other - skeleton point
     * @return the slop
     */
    public double calcSlope(SkeletonPoint other) {
        double dy = other.y - y;
        double dx = other.x - x;
        return dy / dx;
    }

    /**
     * The function calc dot product between 2 points.
     * @param other - skeleton point
     * @return the dot product between them
     */
    public double dotProduct(SkeletonPoint other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * The function calc the size of the skeleton point
     * @return the size of the skeleton point
     */
    public double getSize() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * The function return the normal vector direction between 2 skeleton points
     * @param other - skeleton point
     * @return the normal vector direction
     */
    public SkeletonPoint getNormalVec(SkeletonPoint other) {
        double x = other.getX() - this.x;
        double y = other.getY() - this.y;
        double size = Math.sqrt(x*x + y*y);
        return new SkeletonPoint( x/size, y/size, -1);
    }

    /**
     * The function return the angle this skeleton point and other skeletons point
     * @param other - skeleton point
     * @return the angle between them
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

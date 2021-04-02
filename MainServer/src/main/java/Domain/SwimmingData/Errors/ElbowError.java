package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

public abstract class ElbowError extends SwimmingError {

    protected double maxAngle;
    protected double minAngle;
    private double angle;
    protected boolean inside; // means if the arrow is to inside or outside

    public ElbowError(IDraw drawer,
                      double maxAngle,
                      double minAngle,
                      double angle,
                      boolean inside,
                      String tag) {
        super(drawer, tag);
        this.maxAngle = maxAngle;
        this.minAngle = minAngle;
        this.minAngle = minAngle;
        this.angle = angle;
        this.inside = inside;
    }

    public double getAngle() {
        return angle;
    }

    public boolean getIndise() {
        return this.inside;
    }

    public double getMaxAngle() {
        return maxAngle;
    }

    public double getMinAngle() {
        return minAngle;
    }
}

package Domain.Errors.Interfaces;

import Domain.Drawing.IDraw;

public abstract class ForearmError extends SwimmingError{

    private double angle;
    protected double maxAngle;
    protected double minAngle;
    protected boolean inside;

    public ForearmError(IDraw drawer,
                        double angle,
                        double maxAngle,
                        double minAngle,
                        boolean inside, String tag){
        super(drawer, tag);
        this.angle = angle;
        this.maxAngle = maxAngle;
        this.minAngle = minAngle;
        this.inside = inside;
    }

    public double getAngle() {
        return angle;
    }

    public boolean getInside() {
        return this.inside;
    }

    public double getMaxAngle() {
        return maxAngle;
    }

    public double getMinAngle() {
        return minAngle;
    }
}

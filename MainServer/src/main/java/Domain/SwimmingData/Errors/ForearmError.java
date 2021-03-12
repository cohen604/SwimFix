package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.SwimmingError;

public abstract class ForearmError extends SwimmingError{

    private double angle;
    protected boolean inside;

    public ForearmError(IDraw drawer, double angle, boolean inside, String tag){
        super(drawer, tag);
        this.angle = angle;
        this.inside = inside;
    }

    public double getAngle() {
        return angle;
    }

    public boolean getInside() {
        return this.inside;
    }
}

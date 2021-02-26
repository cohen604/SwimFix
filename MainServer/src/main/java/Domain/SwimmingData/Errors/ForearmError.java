package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.SwimmingError;

public abstract class ForearmError extends SwimmingError{
    private double angle;

    public ForearmError(IDraw drawer, double angle){
        super(drawer);
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}

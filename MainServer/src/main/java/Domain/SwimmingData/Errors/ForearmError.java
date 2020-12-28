package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Draw;
import Domain.SwimmingData.SwimmingError;

public abstract class ForearmError extends Draw implements SwimmingError{
    private double angle;

    public ForearmError(double angle){
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}

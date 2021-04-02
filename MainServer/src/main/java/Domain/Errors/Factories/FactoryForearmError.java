package Domain.Errors.Factories;

import Domain.Drawing.IFactoryDraw;
import Domain.Errors.LeftForearmError;
import Domain.Errors.RightForearmError;
import Domain.Drawing.IDraw;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.Interfaces.IFactoryForearmError;

public class FactoryForearmError implements IFactoryForearmError {

    private IFactoryDraw iFactoryDraw;

    public FactoryForearmError(IFactoryDraw iFactoryDraw) {
        this.iFactoryDraw = iFactoryDraw;
    }

    @Override
    public SwimmingError createLeft(double angle, double maxAngle, double minAngle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new LeftForearmError(drawer, angle, maxAngle, minAngle, inside);
    }

    @Override
    public SwimmingError createRight(double angle, double maxAngle, double minAngle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new RightForearmError(drawer, angle, maxAngle, minAngle, inside);
    }
}

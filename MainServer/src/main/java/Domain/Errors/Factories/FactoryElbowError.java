package Domain.Errors.Factories;

import Domain.Drawing.IFactoryDraw;
import Domain.Errors.LeftElbowError;
import Domain.Errors.RightElbowError;
import Domain.Drawing.IDraw;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.Interfaces.IFactoryElbowError;

public class FactoryElbowError implements IFactoryElbowError {

    private IFactoryDraw iFactoryDraw;

    public FactoryElbowError(IFactoryDraw iFactoryDraw) {
        this.iFactoryDraw = iFactoryDraw;
    }

    @Override
    public SwimmingError createLeft(double maxAngle, double minAngle, double angle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new LeftElbowError(drawer, maxAngle, minAngle, angle, inside);
    }

    @Override
    public SwimmingError createRight(double maxAngle, double minAngle, double angle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new RightElbowError(drawer, maxAngle, minAngle,  angle, inside);
    }
}

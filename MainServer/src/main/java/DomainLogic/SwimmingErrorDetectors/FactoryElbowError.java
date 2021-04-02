package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.Errors.LeftElbowError;
import Domain.SwimmingData.Errors.RightElbowError;
import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.SwimmingError;

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

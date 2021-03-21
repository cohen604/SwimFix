package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.Errors.LeftForearmError;
import Domain.SwimmingData.Errors.RightForearmError;
import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.SwimmingError;

public class FactoryForearmError implements IFactoryForearmError {

    private IFactoryDraw iFactoryDraw;

    public FactoryForearmError(IFactoryDraw iFactoryDraw) {
        this.iFactoryDraw = iFactoryDraw;
    }

    @Override
    public SwimmingError createLeft(double angle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new LeftForearmError(drawer, angle, inside);
    }

    @Override
    public SwimmingError createRight(double angle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new RightForearmError(drawer, angle, inside);
    }
}

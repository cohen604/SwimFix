package mainServer.SwimmingErrorDetectors;

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
    public SwimmingError createLeft(double angle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new LeftElbowError(drawer, angle, inside);
    }

    @Override
    public SwimmingError createRight(double angle, boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new RightElbowError(drawer, angle, inside);
    }
}

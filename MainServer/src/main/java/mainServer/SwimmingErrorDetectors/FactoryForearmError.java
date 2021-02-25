package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.Errors.LeftForearmError;
import Domain.SwimmingData.Errors.RightForearmError;
import Domain.SwimmingData.Errors.RightPalmCrossHeadError;
import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.SwimmingError;

public class FactoryForearmError implements IFactoryForearmError {

    private IFactoryDraw iFactoryDraw;

    public FactoryForearmError(IFactoryDraw iFactoryDraw) {
        this.iFactoryDraw = iFactoryDraw;
    }

    @Override
    public SwimmingError createLeft(double angle) {
        IDraw drawer = iFactoryDraw.create();
        return new LeftForearmError(drawer,angle);
    }

    @Override
    public SwimmingError createRight(double angle) {
        IDraw drawer = iFactoryDraw.create();
        return new RightForearmError(drawer,angle);
    }
}

package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.Errors.LeftPalmCrossHeadError;
import Domain.SwimmingData.Errors.RightPalmCrossHeadError;
import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.SwimmingError;

public class FactoryPalmCrossHeadError implements IFactoryPalmCrossHeadError {

    private IFactoryDraw iFactoryDraw;

    public FactoryPalmCrossHeadError(IFactoryDraw iFactoryDraw) {
        this.iFactoryDraw = iFactoryDraw;
    }

    @Override
    public SwimmingError createLeft(boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new LeftPalmCrossHeadError(drawer, inside);
    }

    @Override
    public SwimmingError createRight(boolean inside) {
        IDraw drawer = iFactoryDraw.create();
        return new RightPalmCrossHeadError(drawer, inside);
    }
}

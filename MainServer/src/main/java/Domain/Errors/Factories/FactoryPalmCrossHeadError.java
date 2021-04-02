package Domain.Errors.Factories;

import Domain.Drawing.IFactoryDraw;
import Domain.Errors.LeftPalmCrossHeadError;
import Domain.Errors.RightPalmCrossHeadError;
import Domain.Drawing.IDraw;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.Interfaces.IFactoryPalmCrossHeadError;

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

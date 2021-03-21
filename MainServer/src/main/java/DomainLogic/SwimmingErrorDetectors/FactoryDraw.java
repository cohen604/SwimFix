package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.Drawing.Draw;
import Domain.SwimmingData.Drawing.IDraw;

public class FactoryDraw implements IFactoryDraw {

    @Override
    public IDraw create() {
        return new Draw();
    }
}

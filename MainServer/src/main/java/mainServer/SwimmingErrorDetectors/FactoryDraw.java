package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.Draw;
import Domain.SwimmingData.IDraw;

public class FactoryDraw implements IFactoryDraw {

    @Override
    public IDraw create() {
        return new Draw();
    }
}

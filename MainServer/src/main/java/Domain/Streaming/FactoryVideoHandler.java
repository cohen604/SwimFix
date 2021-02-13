package Domain.Streaming;

import Domain.SwimmingData.IDraw;

public class FactoryVideoHandler implements IFactoryVideoHandler {

    @Override
    public IVideoHandler create(IDraw drawer) {
        return new VideoHandler(drawer);
    }

}

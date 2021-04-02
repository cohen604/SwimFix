package Domain.Streaming;

import Domain.Drawing.IDraw;

public class FactoryVideoHandler implements IFactoryVideoHandler {

    @Override
    public IVideoHandler create(IDraw drawer) {
        return new VideoHandler(drawer);
    }

}

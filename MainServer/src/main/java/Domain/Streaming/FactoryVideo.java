package Domain.Streaming;

import DTO.ConvertedVideoDTO;
import Domain.SwimmingData.Draw;
import Domain.SwimmingData.IDraw;
import mainServer.SwimmingErrorDetectors.IFactoryDraw;

public class FactoryVideo implements IFactoryVideo{

    private IFactoryDraw iFactoryDraw;
    private IFactoryVideoHandler iFactoryVideoHandler;

    public FactoryVideo(IFactoryDraw iFactoryDraw, IFactoryVideoHandler iFactoryVideoHandler) {
        this.iFactoryDraw = iFactoryDraw;
        this.iFactoryVideoHandler = iFactoryVideoHandler;
    }

    @Override
    public IVideo create(ConvertedVideoDTO convertedVideoDTO) {
        IDraw drawer = iFactoryDraw.create();
        IVideoHandler iVideoHandler = iFactoryVideoHandler.create(drawer);
        return new Video(iVideoHandler, convertedVideoDTO);
    }

    @Override
    public IVideo create(ConvertedVideoDTO convertedVideoDTO, String path) {
        IDraw drawer = iFactoryDraw.create();
        IVideoHandler iVideoHandler = iFactoryVideoHandler.create(drawer);
        return new Video(iVideoHandler, convertedVideoDTO, path);
    }

    @Override
    public IVideo create(String path, String videoType) {
        IDraw drawer = iFactoryDraw.create();
        IVideoHandler iVideoHandler = iFactoryVideoHandler.create(drawer);
        return new Video(iVideoHandler, path, videoType);
    }

}

package Domain.Streaming;

import DTO.ConvertedVideoDTO;
import Domain.SwimmingData.Drawing.IDraw;
import mainServer.SwimmingErrorDetectors.IFactoryDraw;

import java.time.LocalDateTime;

public class FactoryVideo implements IFactoryVideo{

    private IFactoryDraw iFactoryDraw;
    private IFactoryVideoHandler iFactoryVideoHandler;

    public FactoryVideo(IFactoryDraw iFactoryDraw, IFactoryVideoHandler iFactoryVideoHandler) {
        this.iFactoryDraw = iFactoryDraw;
        this.iFactoryVideoHandler = iFactoryVideoHandler;
    }

    @Override
    public IVideo create(ConvertedVideoDTO convertedVideoDTO, String path) {
        //IDraw drawer = iFactoryDraw.create();
        //IVideoHandler iVideoHandler = iFactoryVideoHandler.create(drawer);
        return new Video(convertedVideoDTO, path);
    }

    @Override
    public IVideo create(String path, String videoType) {
        return new Video(path, videoType);
    }

}

package Domain.Streaming;

import DTO.ConvertedVideoDTO;

public class FactoryVideo implements IFactoryVideo{


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

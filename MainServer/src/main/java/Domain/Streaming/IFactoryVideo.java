package Domain.Streaming;

import DTO.ConvertedVideoDTO;

public interface IFactoryVideo {

    IVideo create(ConvertedVideoDTO convertedVideoDTO);
    IVideo create(ConvertedVideoDTO convertedVideoDTO, String path);
    IVideo create(String path, String videoType);
}

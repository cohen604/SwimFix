package Domain.Streaming;

import DTO.ConvertedVideoDTO;

import java.time.LocalDateTime;

public interface IFactoryVideo {

    IVideo create(ConvertedVideoDTO convertedVideoDTO);

    IVideo create(ConvertedVideoDTO convertedVideoDTO, String path, LocalDateTime localDateTime);

    IVideo create(String path, String videoType);
}

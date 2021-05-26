package Domain.Streaming;

import DTO.FeedbackDTOs.ConvertedVideoDTO;

public interface IFactoryVideo {

    IVideo create(ConvertedVideoDTO convertedVideoDTO, String path);

    IVideo create(String path, String videoType);
}

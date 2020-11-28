package Domain.Streaming;

import DTO.ConvertedVideoDTO;

public class Video {
    byte[] video;
    //TODO: change to correct data structure

    public Video(ConvertedVideoDTO convertedVideoDTO) {
        this.video = convertedVideoDTO.getBytes();
    }

    /***
     * Copy Constructor
     * @param other
     */
    public Video(Video other) {
        this.video = other.video;
    }
}

package Domain.Streaming;

import DTO.ConvertedVideoDTO;

public class Video {

    protected byte[] video;

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

    public byte[] getVideo() {
        return this.video;
    }
}

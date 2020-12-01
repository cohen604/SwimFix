package DTO;

import java.io.File;

public class ConvertedVideoDTO {

    private String videoType; //this is the video type must be in the format ".type"
    private byte[] video;

    public ConvertedVideoDTO(String videoType, byte[] video) {
        this.videoType = videoType;
        this.video = video;
    }

    public byte[] getBytes() {
        return video;
    }

    public String getVideoType() {
        return this.videoType;
    }
}

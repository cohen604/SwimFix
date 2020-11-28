package DTO;

import java.io.File;

public class ConvertedVideoDTO {
    private byte[] video;

    public ConvertedVideoDTO(byte[] video) {
        // TODO: Convert video to .mob file
        this.video = video;
    }

    public byte[] getBytes() {
        return video;
    }
}

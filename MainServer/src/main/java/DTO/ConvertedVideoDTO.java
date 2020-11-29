package DTO;

import java.io.File;

public class ConvertedVideoDTO {
    private byte[] video;

    public ConvertedVideoDTO(byte[] video) {
        // TODO: Convert video to .mob file
        this.video = video;
        System.out.println("Converted Video");
    }

    public byte[] getBytes() {
        return video;
    }
}

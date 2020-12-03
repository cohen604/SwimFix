package DTO;

public class ConvertedVideoDTO {

    //this is the video type must be in the format ".type"
    private String videoType;
    private byte[] video;

    public ConvertedVideoDTO(String videoName, byte[] video) {
        if(videoName != null) {
            this.videoType = videoName.substring(videoName.lastIndexOf("."));
        }
        else {
            this.videoType = ".mp4";
        }
        this.video = video;
    }

    public byte[] getBytes() {
        return video;
    }

    public String getVideoType() {
        return this.videoType;
    }
}

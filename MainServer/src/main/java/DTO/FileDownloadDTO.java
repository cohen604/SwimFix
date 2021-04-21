package DTO;

public class FileDownloadDTO {

    private String name;
    private String path;
    private byte[] bytes;

    public FileDownloadDTO(String name, String path, byte[] bytes) {
        this.name = name;
        this.path = path;
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public byte[] getBytes() {
        return bytes;
    }
}

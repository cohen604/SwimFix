package DTO;

public class FileDTO {

    private String _name;
    private byte[] _bytes;

    public FileDTO(String name, byte[] bytes) {
        this._name = name;
        this._bytes = bytes;
    }

    public byte[] getBytes() {
        return _bytes;
    }
}

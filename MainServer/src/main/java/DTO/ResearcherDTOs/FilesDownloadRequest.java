package DTO.ResearcherDTOs;

import DTO.UserDTOs.UserDTO;

public class FilesDownloadRequest {

    private UserDTO user;
    private String[] files;

    public FilesDownloadRequest(UserDTO userDTO, String[] files) {
        this.user = userDTO;
        this.files = files;
    }

    public UserDTO getUser() {
        return user;
    }

    public String[] getFiles() {
        return files;
    }
}

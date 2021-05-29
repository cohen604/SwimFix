package DTO.AdminDTOs;

import DTO.UserDTOs.UserDTO;

public class AdminAddRequestDTO {

    private UserDTO admin;
    private UserDTO user;

    public AdminAddRequestDTO(UserDTO admin, UserDTO user) {
        this.admin = admin;
        this.user = user;
    }

    public UserDTO getAdmin() {
        return admin;
    }

    public UserDTO getUser() {
        return user;
    }
}

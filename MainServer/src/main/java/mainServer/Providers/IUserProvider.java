package mainServer.Providers;

import DTO.UserDTO;

public interface IUserProvider {

    boolean login(UserDTO userDTO);

}

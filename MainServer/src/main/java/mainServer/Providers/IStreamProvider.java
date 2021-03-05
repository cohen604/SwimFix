package mainServer.Providers;

import DTO.FeedbackVideoDTO;

public interface IStreamProvider {

    /**
     * the function create a feedback video representation from a file
     * @param path - the path of the file
     * @return - feedback video representation
     */
    FeedbackVideoDTO streamFile(String path);



}

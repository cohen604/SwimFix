package Domain.UserData;

import java.nio.file.Files;
import java.nio.file.Paths;

public class PathManager {

    private String _root;
    private String _mainFolder;
    private String[] _folders;

    public PathManager(String folderName) {
        _root = "clients";
        _mainFolder = combinePaths(_root, folderName);
        _folders = new String[]{"videos", "feedbacks"};
        createDirs();
    }

    private void createDirs() {
        try {
            Files.createDirectory(Paths.get(_mainFolder));
            for (String folderName : _folders) {
                Files.createDirectory(Paths.get(combinePaths(_mainFolder, folderName)));
            }
        }
        catch (Exception e) {
            //TODO write to loogger
            e.printStackTrace();
        }
    }

    public String getVideosPath() {
        return combinePaths(_mainFolder, "videos");
    }

    public String getFeedbacksPath() {
        return combinePaths(_mainFolder, "feedbacks");
    }

    private String combinePaths(String prefix, String suffix) {
        return prefix + "\\" + suffix;
    }
}

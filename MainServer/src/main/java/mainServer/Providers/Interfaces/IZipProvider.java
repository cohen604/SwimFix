package mainServer.Providers.Interfaces;

import java.io.File;

public interface IZipProvider {

    File createZip(String folderPath, String[] filesPaths);
}

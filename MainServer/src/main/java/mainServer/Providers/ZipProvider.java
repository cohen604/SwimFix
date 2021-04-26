package mainServer.Providers;

import mainServer.Providers.Interfaces.IZipProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipProvider implements IZipProvider {

    @Override
    public File createZip(String folderPath, String[] filesPaths) {
        String zipFileName = generateName(folderPath, ".zip", LocalDateTime.now());
        File zipfile = new File(zipFileName);
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipfile));
            for (String filePath : filesPaths) {
                File file = new File(filePath);
                FileInputStream inputStream = new FileInputStream(file);
                outputStream.putNextEntry(new ZipEntry(file.getName()));
                int length;
                while((length = inputStream.read(buf)) > 0) {
                    outputStream.write(buf, 0, length);
                }
                outputStream.closeEntry();
                inputStream.close();
            }
            outputStream.close();
            return zipfile;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateName(String folderPath, String fileType, LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return folderPath + "\\" + time.format(formatter) + fileType;
    }
}

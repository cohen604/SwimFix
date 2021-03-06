package mainServer.FileLoaders;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SkeletonsLoader implements ISkeletonsLoader {


    @Override
    public boolean save(List<ISwimmingSkeleton> swimmingSkeletons, String folderPath, LocalDateTime time) {
        try {
            // create file
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String name =  folderPath + "\\" + time.format(formatter) + ".csv";
            File file = new File(name);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
                // write
                String headers = "head x, head y, " +
                        "right shoulder x, right shoulder y," +
                        "right elbow x, right elbow y," +
                        "right wrist x, right wrist y," +
                        "left shoulder x, left shoulder y," +
                        "left elbow x, left elbow y," +
                        "left wrist x, left wrist y";
                fileWriter.append(headers);
                fileWriter.append('\n');
                // head right s,e,w left s,e,w
                for (ISwimmingSkeleton skeleton : swimmingSkeletons) {
                    writeOrgan(fileWriter, skeleton.getHead());
                    writeOrgan(fileWriter, skeleton.getRightShoulder());
                    writeOrgan(fileWriter, skeleton.getRightElbow());
                    writeOrgan(fileWriter, skeleton.getRightWrist());
                    writeOrgan(fileWriter, skeleton.getLeftShoulder());
                    writeOrgan(fileWriter, skeleton.getLeftElbow());
                    writeOrgan(fileWriter, skeleton.getLeftWrist());
                    fileWriter.append('\n');
                }
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                fileWriter.flush();
                fileWriter.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void writeOrgan(FileWriter fileWriter, IPoint point) throws IOException {
        if(point == null) {
            fileWriter.append(" , ,");
        }
        else {
            fileWriter.append(String.valueOf(point.getX()))
                    .append(",")
                    .append(String.valueOf(point.getY()))
                    .append(",");
        }
    }

    @Override
    public List<ISwimmingSkeleton> read(String path) {
        return null;
    }
}

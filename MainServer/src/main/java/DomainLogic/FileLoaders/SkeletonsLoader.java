package DomainLogic.FileLoaders;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SkeletonsLoader implements ISkeletonsLoader {


    @Override
    public boolean save(List<ISwimmingSkeleton> swimmingSkeletons, String path) {
        try {
            // create file
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            File file = new File(path);
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
        try {
            List<ISwimmingSkeleton> skeletons = new LinkedList<>();
            List<String> lines = Files.readAllLines(Paths.get(path));
            if(lines.size() > 0) {
                HashMap<Integer, String> headers = readHeaders(lines.get(0));
                for(int i=1; i<lines.size(); i++) {
                    skeletons.add(readSkeleton(lines.get(i), headers));
                }
            }
            return skeletons;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private HashMap<Integer, String> readHeaders(String line) {
        HashMap<Integer, String> output = new HashMap<>();
        String[] headers = line.split(",");
        for(int i =0; i<headers.length; i++) {
            output.put(i, headers[i]);
        }
        return output;
    }

    private ISwimmingSkeleton readSkeleton(String line, HashMap<Integer, String> headers) {
        SkeletonValues values = new SkeletonValues();
        String[] elements = line.split(",");
        for(int i=0; i<elements.length; i++) {
            if(!elements[i].isEmpty() && !elements[i].replaceAll(" ","").isEmpty()) {
                double value = Double.valueOf(elements[i]);
                switch (headers.get(i)) {
                    case "head x":
                        values.headX = value;
                        break;
                    case "head y":
                        values.headY = value;
                        break;
                    case "right shoulder x":
                        values.rShoulderX = value;
                        break;
                    case "right shoulder y":
                        values.rShoulderY = value;
                        break;
                    case "right elbow x":
                        values.rElbowX = value;
                        break;
                    case "right elbow y":
                        values.rElbowY = value;
                        break;
                    case "right wrist x":
                        values.rWristX = value;
                        break;
                    case "right wrist y":
                        values.rWristY = value;
                        break;
                    case "left shoulder x":
                        values.lShoulderX = value;
                        break;
                    case "left shoulder y":
                        values.lShoulderY = value;
                        break;
                    case "left elbow x":
                        values.lElbowX = value;
                        break;
                    case "left elbow y":
                        values.lElbowY = value;
                        break;
                    case "left wrist x":
                        values.lWristX = value;
                        break;
                    case "left wrist y":
                        values.lWristY = value;
                        break;
                }
            }
        }
        return createSkeleton(values);
    }

    private ISwimmingSkeleton createSkeleton(SkeletonValues values) {
        SwimmingSkeleton skeleton = new SwimmingSkeleton();
        if( values.headX != null && values.headY != null) {
            skeleton.setHead(new SkeletonPoint(values.headX, values.headY));
        }
        if(values.rShoulderX != null && values.rShoulderY != null) {
            skeleton.setRightShoulder(new SkeletonPoint(values.rShoulderX, values.rShoulderY));
        }
        if(values.rElbowX != null && values.rElbowY != null) {
            skeleton.setRightElbow(new SkeletonPoint(values.rElbowX, values.rElbowY));
        }
        if(values.rWristX != null && values.rWristY != null) {
            skeleton.setRightWrist(new SkeletonPoint(values.rWristX, values.rWristY));
        }
        if(values.lShoulderX != null && values.lShoulderY != null) {
            skeleton.setLeftShoulder(new SkeletonPoint(values.lShoulderX, values.lShoulderY));
        }
        if(values.lElbowX != null && values.lElbowY != null) {
            skeleton.setLeftElbow(new SkeletonPoint(values.lElbowX, values.lElbowY));
        }
        if(values.lWristX != null && values.lWristY != null) {
            skeleton.setLeftWrist(new SkeletonPoint(values.lWristX, values.lWristY));
        }
        return skeleton;
    }


    private class SkeletonValues {
        Double headX;
        Double headY;
        Double rShoulderX;
        Double rShoulderY;
        Double rElbowX;
        Double rElbowY;
        Double rWristX;
        Double rWristY;
        Double lShoulderX;
        Double lShoulderY;
        Double lElbowX;
        Double lElbowY;
        Double lWristX;
        Double lWristY;

    }

}

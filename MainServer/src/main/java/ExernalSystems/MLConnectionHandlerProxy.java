package ExernalSystems;

import Domain.Streaming.IVideo;
import Domain.Streaming.TaggedVideo;
import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.FileLoaders.SkeletonsLoader;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class MLConnectionHandlerProxy implements MLConnectionHandler{
    private MLConnectionHandler mlConnectionHandler = null;

    public MLConnectionHandlerProxy() {
        mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5050");
    }


    @Override
    public List<ISwimmingSkeleton> getSkeletons(IVideo video, int size, int height, int width) {
        if (mlConnectionHandler != null) {
//            System.out.println("Send Skeletons to ML " + LocalDateTime.now());
//            List<ISwimmingSkeleton> skeletons = mlConnectionHandler.getSkeletons(video, size, height, width);
//            System.out.println("Received Skeletons to ML " + LocalDateTime.now());
//            return skeletons;
            //TODO for testing when ml server down load skeleton from local testing videos from csv
            String path = "./src/test/java/TestingVideos/example/2021-03-31-19-39-18 - ml.csv";
            SkeletonsLoader skeletonsLoader = new SkeletonsLoader();
            return skeletonsLoader.read(path);
        }
        return null;
    }

}

package DomainLogic.FileLoaders;

import Domain.SwimmingData.ISwimmingSkeleton;

import java.time.LocalDateTime;
import java.util.List;

public interface ISkeletonsLoader {

    boolean save(List<ISwimmingSkeleton> swimmingSkeletons, String folderPath, LocalDateTime time);

    List<ISwimmingSkeleton> read(String path);

}

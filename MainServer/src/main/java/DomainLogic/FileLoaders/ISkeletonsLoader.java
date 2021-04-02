package DomainLogic.FileLoaders;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;

public interface ISkeletonsLoader {

    boolean save(List<ISwimmingSkeleton> swimmingSkeletons, String path);

    List<ISwimmingSkeleton> read(String path);

    List<ISwimmingSkeleton> read(byte[] bytes);

}

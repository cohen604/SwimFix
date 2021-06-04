package mainServer.Providers.Interfaces;

import DTO.FeedbackDTOs.FeedbackGraphsDTO;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;
import java.util.Map;

public interface IGraphProvider {

    FeedbackGraphsDTO createGraph(List<ISwimmingSkeleton> skeletons, Map<Integer, List<SwimmingError>> errors);

}

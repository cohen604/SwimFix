package mainServer.Providers;

import DTO.FeedbackDTOs.FeedbackGraphsDTO;
import DTO.FeedbackDTOs.PointDTO;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import mainServer.Providers.Interfaces.IGraphProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphProvider implements IGraphProvider {

    @Override
    public FeedbackGraphsDTO createGraph(List<ISwimmingSkeleton> skeletons,
                                         Map<Integer, List<SwimmingError>> errors) {
        HashMap<Integer, PointDTO> head = new HashMap<>();
        HashMap<Integer, PointDTO> rightShoulder = new HashMap<>();
        HashMap<Integer, PointDTO> rightElbow = new HashMap<>();
        HashMap<Integer, PointDTO> rightWrist = new HashMap<>();
        HashMap<Integer, PointDTO> leftShoulder = new HashMap<>();
        HashMap<Integer, PointDTO> leftElbow = new HashMap<>();
        HashMap<Integer, PointDTO> leftWrist = new HashMap<>();
        HashMap<Integer, Integer> errorsCounter = new HashMap<>();
        for(int i=0; i<skeletons.size(); i++) {
            ISwimmingSkeleton swimmingSkeleton = skeletons.get(i);
            if(swimmingSkeleton != null) {
                if(swimmingSkeleton.containsHead()) {
                    IPoint iPoint = swimmingSkeleton.getHead();
                    head.put(i, new PointDTO((int)iPoint.getX(), (int)iPoint.getY()));
                }
                if(swimmingSkeleton.containsRightShoulder()) {
                    IPoint iPoint = swimmingSkeleton.getRightShoulder();
                    rightShoulder.put(i, new PointDTO((int)iPoint.getX(), (int)iPoint.getY()));
                }
                if(swimmingSkeleton.containsRightElbow()) {
                    IPoint iPoint = swimmingSkeleton.getRightElbow();
                    rightElbow.put(i, new PointDTO((int)iPoint.getX(), (int)iPoint.getY()));
                }
                if(swimmingSkeleton.containsRightWrist()) {
                    IPoint iPoint = swimmingSkeleton.getRightWrist();
                    rightWrist.put(i, new PointDTO((int)iPoint.getX(), (int)iPoint.getY()));
                }
                if(swimmingSkeleton.containsLeftShoulder()) {
                    IPoint iPoint = swimmingSkeleton.getLeftShoulder();
                    leftShoulder.put(i, new PointDTO((int)iPoint.getX(), (int)iPoint.getY()));
                }
                if(swimmingSkeleton.containsLeftElbow()) {
                    IPoint iPoint = swimmingSkeleton.getLeftElbow();
                    leftElbow.put(i, new PointDTO((int)iPoint.getX(), (int)iPoint.getY()));
                }
                if(swimmingSkeleton.containsLeftWrist()) {
                    IPoint iPoint = swimmingSkeleton.getLeftWrist();
                    leftWrist.put(i, new PointDTO((int)iPoint.getX(), (int)iPoint.getY()));
                }
                if (errors.containsKey(i)) {
                    errorsCounter.put(i, errors.get(i).size());
                }
            }
        }
        return new FeedbackGraphsDTO(
                skeletons.size(),
                head,
                rightShoulder,
                rightElbow,
                rightWrist,
                leftShoulder,
                leftElbow,
                leftWrist,
                errorsCounter);
    }
}

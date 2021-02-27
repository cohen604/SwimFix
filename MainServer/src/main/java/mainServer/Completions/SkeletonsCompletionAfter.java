package mainServer.Completions;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;

import java.util.LinkedList;
import java.util.List;

public class SkeletonsCompletionAfter implements ISkeletonsCompletion {

    @Override
    public List<ISwimmingSkeleton> complete(List<ISwimmingSkeleton> skeletons) {
        List<ISwimmingSkeleton> output = new LinkedList<>(skeletons);
        for(int i=0; i<skeletons.size(); i+=2) {
           output.add(i, completeShoulder(skeletons.get(i)));
        }
        return output;
    }

    private ISwimmingSkeleton completeShoulder(ISwimmingSkeleton current) {
        if(current.containsHead()) {
            if(current.containsRightShoulder() && !current.containsLeftShoulder()) {
                //TODO
                // if shoulder is higher then head then complete with reflection
                // if shoulder is lower then head then complete with liner line
            }
            else if(!current.containsRightShoulder() && current.containsLeftShoulder()) {

            }
        }
        return current;
    }
}

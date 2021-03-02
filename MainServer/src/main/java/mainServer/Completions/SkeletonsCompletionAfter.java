package mainServer.Completions;

import Domain.SwimmingData.ISwimmingSkeleton;

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
            completeRightShoulderIfNeeded(current);
            completeLeftShoulderIfNeeded(current);
        }
        return current;
    }

    private boolean rightShoulderAboveHead(ISwimmingSkeleton current) {
        return current.getHead().getY() > current.getRightShoulder().getY();
    }

    private ISwimmingSkeleton completeRightShoulderIfNeeded(ISwimmingSkeleton current) {
        if(current.containsRightShoulder() && !current.containsLeftShoulder()) {
            if(rightShoulderAboveHead(current)) {
                //TODO
                // if shoulder is higher then head then complete with reflection
            }
            else {
                //TODO
                // if shoulder is lower then head then complete with liner line
            }
        }
        return current;
    }

    private boolean leftShoulderAboveHead(ISwimmingSkeleton current) {
        return current.getHead().getY() > current.getLeftShoulder().getY();
    }

    private ISwimmingSkeleton completeLeftShoulderIfNeeded(ISwimmingSkeleton current) {
        if (!current.containsRightShoulder() && current.containsLeftShoulder()) {
            if(leftShoulderAboveHead(current)) {
                //TODO
                // if shoulder is higher then head then complete with reflection
            }
            else {
                //TODO
                // if shoulder is lower then head then complete with liner line
            }
        }
        return current;
    }

}

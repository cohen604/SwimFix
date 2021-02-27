package mainServer.Completions;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;

import java.util.LinkedList;
import java.util.List;

public class SkeletonsCompletion implements ISkeletonsCompletion {

    @Override
    public List<ISwimmingSkeleton> complete(List<ISwimmingSkeleton> skeletons) {
        List<ISwimmingSkeleton> output = new LinkedList<>(skeletons);
        for(int i=0; i<skeletons.size(); i+=2) {
            if(i + 2 < skeletons.size()) {
                ISwimmingSkeleton first = skeletons.get(i);
                ISwimmingSkeleton second = skeletons.get(i + 1);
                ISwimmingSkeleton third = skeletons.get(i + 2);
                output.set(i + 1, completeSecond(first, second, third));
            }
        }
        return output;
    }

    public ISwimmingSkeleton completeSecond(ISwimmingSkeleton first,
                                            ISwimmingSkeleton second,
                                            ISwimmingSkeleton third) {
        SwimmingSkeleton output = new SwimmingSkeleton();
        IPoint tmp = complete(first.getHead(), second.getHead(), third.getHead());
        output.setHead(tmp);
        tmp = complete(first.getRightShoulder(), second.getRightShoulder(), third.getRightShoulder());
        output.setRightShoulder(tmp);
        tmp = complete(first.getRightElbow(), second.getRightElbow(), third.getRightElbow());
        output.setRightElbow(tmp);
        tmp = complete(first.getRightWrist(), second.getRightWrist(), third.getRightWrist());
        output.setRightWrist(tmp);
        tmp = complete(first.getLeftShoulder(), second.getLeftShoulder(), third.getLeftShoulder());
        output.setLeftShoulder(tmp);
        tmp = complete(first.getLeftElbow(), second.getLeftElbow(), third.getLeftElbow());
        output.setLeftElbow(tmp);
        tmp = complete(first.getLeftWrist(), second.getLeftWrist(), third.getLeftWrist());
        output.setLeftWrist(tmp);
        return output;
    }

    private IPoint complete(IPoint first, IPoint second, IPoint third) {
        if(first == null && second == null && third == null) {
            return null;
        }
        if(second != null) {
            return new SkeletonPoint(second.getX(), second.getY());
        }
        double x = 0;
        double y = 0;
        int counter = 0;
        if(first != null) {
            x += first.getX();
            y += first.getY();
            counter++;
        }
        if(third != null) {
            x += third.getX();
            y += third.getY();
            counter++;
        }
        return new SkeletonPoint(x / counter,y / counter);
    }
}

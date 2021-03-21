package DomainLogic.Completions;

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
        for(int i=0; i<skeletons.size(); i++) {
           output.add(i, completeShoulder(skeletons.get(i)));
        }
        return output;
    }

    private ISwimmingSkeleton completeShoulder(ISwimmingSkeleton current) {
        if(current.containsHead()) {
            ISwimmingSkeleton skeleton = completeLeftShoulderBasedOnHeadRightShoulder(current);
            return completeRightShoulderBasedOnHeadLeftShoulder(skeleton);
        }
        return current;
    }

    private double calcY(double x, double x0, double y0, double slope) {
        // liner equation
        return y0 + (x - x0) * slope;
    }

    private double caclSlope(double x0, double y0, double x1, double y1) {
        return (y1 - y0) / (x1 - x0);
    }

    private ISwimmingSkeleton completeLeftShoulderBasedOnHeadRightShoulder(ISwimmingSkeleton current) {
        if(current.containsRightShoulder() && !current.containsLeftShoulder()) {
            IPoint rightShoulder = current.getRightShoulder();
            IPoint head = current.getHead();
            double dx = rightShoulder.getX() - head.getX();
            double x = head.getX() - dx;
            double slop = caclSlope(head.getX(), head.getY(), rightShoulder.getX(), rightShoulder.getY());
            double y = calcY(x, head.getX(), head.getY(), -slop);
            SwimmingSkeleton swimmingSkeleton = new SwimmingSkeleton(current);
            swimmingSkeleton.setLeftShoulder(new SkeletonPoint(x, y));
            return swimmingSkeleton;
        }
        return current;
    }


    private ISwimmingSkeleton completeRightShoulderBasedOnHeadLeftShoulder(ISwimmingSkeleton current) {
        if (!current.containsRightShoulder() && current.containsLeftShoulder()) {
            IPoint leftShoulder = current.getLeftShoulder();
            IPoint head = current.getHead();
            double dx = head.getX() - leftShoulder .getX();
            double x = head.getX() + dx;
            double slop = caclSlope(head.getX(), head.getY(), leftShoulder.getX(), leftShoulder.getY());
            double y = calcY(x, head.getX(), head.getY(), -slop);
            SwimmingSkeleton swimmingSkeleton = new SwimmingSkeleton(current);
            swimmingSkeleton.setRightShoulder(new SkeletonPoint(x, y));
            return swimmingSkeleton;
        }
        return current;
    }

}

package DomainLogic.Interpolations;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SwimmingSkeleton;
import DomainLogic.Interpolations.TimeSkeletonInterpolation.TimeSkeletonInterpolation;

import java.util.LinkedList;
import java.util.List;

public class SkeletonInterpolation implements ISkeletonInterpolation {

    private Interpolation interpolation;
    private Interpolation interpolationHead;

    private List<IPoint> heads;
    private List<IPoint> rightShoulders;
    private List<IPoint> rightElbows;
    private List<IPoint> rightWrists;
    private List<IPoint> leftShoulders;
    private List<IPoint> leftElbows;
    private List<IPoint> leftWrists;

    public SkeletonInterpolation(Interpolation interpolation, Interpolation interpolationHead) {
        this.interpolation = interpolation;
        this.interpolationHead = interpolationHead;
        this.heads = new LinkedList<>();
        this.rightShoulders = new LinkedList<>();
        this.rightElbows = new LinkedList<>();
        this.rightWrists = new LinkedList<>();
        this.leftShoulders = new LinkedList<>();
        this.leftElbows = new LinkedList<>();
        this.leftWrists = new LinkedList<>();
    }

    @Override
    public List<ISwimmingSkeleton> interpolate(List<ISwimmingSkeleton> skeletons) {
        collectPoints(skeletons);
        runInterpolation();
        List<ISwimmingSkeleton> output = buildNewSwimmingSkeletons(skeletons);
        return timeInterpolation(output);
    }

    /***
     * The function collect all the lists of points
     * be aware: if there isn't point in the skeleton it will return null
     *           means there is lists with null elements !!!!
     * @param skeletons the skeletons of the swimmer
     */
    private void collectPoints(List<ISwimmingSkeleton> skeletons) {
        for(ISwimmingSkeleton skeleton : skeletons) {
            heads.add(skeleton.getHead());
            rightShoulders.add(skeleton.getRightShoulder());
            rightElbows.add(skeleton.getRightElbow());
            rightWrists.add(skeleton.getRightWrist());
            leftShoulders.add(skeleton.getLeftShoulder());
            leftElbows.add(skeleton.getLeftElbow());
            leftWrists.add(skeleton.getLeftWrist());
        }
    }

    private void runInterpolation() {
        heads = interpolationHead.interpolate(heads);
        rightShoulders = interpolation.interpolate(rightShoulders);
        rightElbows = interpolation.interpolate(rightElbows);
        rightWrists = interpolation.interpolate(rightWrists);
        leftShoulders = interpolation.interpolate(leftShoulders);
        leftElbows = interpolation.interpolate(leftElbows);
        leftWrists = interpolation.interpolate(leftWrists);
    }

    private List<ISwimmingSkeleton> buildNewSwimmingSkeletons(List<ISwimmingSkeleton> origns) {
        List<ISwimmingSkeleton> output = new LinkedList<>();
        for(int i=0; i<origns.size(); i++) {
            //TODO change to factory
            SwimmingSkeleton swimmingSkeleton = new SwimmingSkeleton(heads.get(i));
            ISwimmingSkeleton origin = origns.get(i);
            if(origin.hasRightSide()) {
                swimmingSkeleton.setRightShoulder(rightShoulders.get(i));
                swimmingSkeleton.setRightElbow(rightElbows.get(i));
                swimmingSkeleton.setRightWrist(rightWrists.get(i));
            }
            else if(origin.containsRightShoulder()) {
                swimmingSkeleton.setRightShoulder(rightShoulders.get(i));
            }
            if(origin.hasLeftSide()) {
                swimmingSkeleton.setLeftShoulder(leftShoulders.get(i));
                swimmingSkeleton.setLeftElbow(leftElbows.get(i));
                swimmingSkeleton.setLeftWrist(leftWrists.get(i));
            }
            else if(origin.containsLeftShoulder()) {
                swimmingSkeleton.setLeftShoulder(leftShoulders.get(i));
            }
            output.add(swimmingSkeleton);
        }
        return output;
    }

    private List<ISwimmingSkeleton> timeInterpolation(List<ISwimmingSkeleton> skeletons) {
        //TODO need to be factory
        TimeSkeletonInterpolation periodInterpolation = new TimeSkeletonInterpolation(
                this.rightShoulders,
                this.rightElbows,
                this.rightWrists,
                this.leftShoulders,
                this.leftElbows,
                this.leftWrists
        );
        return periodInterpolation.interpolate(skeletons);
    }
}
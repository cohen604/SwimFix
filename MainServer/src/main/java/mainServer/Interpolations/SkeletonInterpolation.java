package mainServer.Interpolations;

import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;

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
        return buildNewSwimmingSkeletons(skeletons);
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
        for(int i=0; i<this.heads.size(); i++) {
            //TODO change to factory
            SwimmingSkeleton swimmingSkeleton = null;
            ISwimmingSkeleton origin = origns.get(i);
            if(origin.hasRightSide() && origin.hasLeftSide()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        rightShoulders.get(i),
                        rightElbows.get(i),
                        rightWrists.get(i),
                        leftShoulders.get(i),
                        leftElbows.get(i),
                        leftWrists.get(i)
                );
            }
            else if(origin.hasRightSide() && origin.containsLeftShoulder()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        rightShoulders.get(i),
                        leftShoulders.get(i),
                        rightElbows.get(i),
                        rightWrists.get(i),
                        true
                );
            }
            else if(origin.hasRightSide()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        rightShoulders.get(i),
                        rightElbows.get(i),
                        rightWrists.get(i),
                        true
                );
            }

            else if(origin.hasLeftSide() && origin.containsRightShoulder()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        rightShoulders.get(i),
                        leftShoulders.get(i),
                        leftElbows.get(i),
                        leftWrists.get(i),
                        false
                );
            }
            else if(origin.hasLeftSide()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        leftShoulders.get(i),
                        leftElbows.get(i),
                        leftWrists.get(i),
                        false
                );
            }
            else if(origin.containsRightShoulder() && origin.containsLeftShoulder()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        rightShoulders.get(i),
                        leftShoulders.get(i)
                );
            }
            else if(origin.containsRightShoulder()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        rightShoulders.get(i),
                        true
                );
            }
            else if(origin.containsLeftShoulder()) {
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i),
                        leftShoulders.get(i),
                        false
                );
            }
            else {
                // only head
                swimmingSkeleton = new SwimmingSkeleton(
                        heads.get(i));
            }
            output.add(swimmingSkeleton);
        }
        return output;
    }
}
package mainServer.Interpolations;

import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;

import java.util.LinkedList;
import java.util.List;

public class SkeletonInterpolation implements ISkeletonInterpolation {

    private Interpolation interpolation;
    private List<IPoint> heads;
    private List<IPoint> rightShoulders;
    private List<IPoint> rightElbows;
    private List<IPoint> rightWrists;
    private List<IPoint> leftShoulders;
    private List<IPoint> leftElbows;
    private List<IPoint> leftWrists;

    public SkeletonInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
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
        return buildNewSwimmingSkeletons();
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
        heads = interpolation.interpolate(heads);
        rightShoulders = interpolation.interpolate(rightShoulders);
        rightElbows = interpolation.interpolate(rightElbows);
        rightWrists = interpolation.interpolate(rightWrists);
        leftShoulders = interpolation.interpolate(leftShoulders);
        leftElbows = interpolation.interpolate(leftElbows);
        leftWrists = interpolation.interpolate(leftWrists);
    }

    private List<ISwimmingSkeleton> buildNewSwimmingSkeletons() {
        List<ISwimmingSkeleton> output = new LinkedList<>();
        for(int i=0; i<this.heads.size(); i++) {
            //TODO change to factory
            output.add(new SwimmingSkeleton(
                    heads.get(i),
                    rightShoulders.get(i),
                    rightElbows.get(i),
                    rightWrists.get(i),
                    leftShoulders.get(i),
                    leftElbows.get(i),
                    leftWrists.get(i)
            ));
        }
        return output;
    }
}

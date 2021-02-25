package mainServer.Interpolations;

import Domain.SwimmingData.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public class SkeletonInterpolation implements ISkeletonInterpolation {

    private List<IPoint> heads;
    private List<IPoint> rightShoulders;
    private List<IPoint> rightElbows;
    private List<IPoint> rightWrists;
    private List<IPoint> leftShoulders;
    private List<IPoint> leftElbows;
    private List<IPoint> leftWrists;

    Interpolation interpolation;

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

    }

    private List<ISwimmingSkeleton> buildNewSwimmingSkeletons() {
        return null;
    }
}

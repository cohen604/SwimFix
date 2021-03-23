package DomainLogic.Interpolations.TimeSkeletonInterpolation;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;
import DomainLogic.Interpolations.ISkeletonInterpolation;

import java.util.HashMap;
import java.util.List;

public class TimeSkeletonInterpolation implements ISkeletonInterpolation {

    private static int PERIOD_TIME_FRAME_THRESHOLD = 6;

    private List<IPoint> heads;
    private List<IPoint> rightShoulders;
    private List<IPoint> rightElbows;
    private List<IPoint> rightWrists;
    private List<IPoint> leftShoulders;
    private List<IPoint> leftElbows;
    private List<IPoint> leftWrists;

    private HashMap<Integer, Complete> skeletonsToComplete;
    private Places rightShoulder;
    private Places rightElbow;
    private Places rightWrist;
    private Places leftShoulder;
    private Places leftElbow;
    private Places leftWrist;

    public TimeSkeletonInterpolation(List<IPoint> heads,
                                     List<IPoint> rightShoulders,
                                     List<IPoint> rightElbows,
                                     List<IPoint> rightWrists,
                                     List<IPoint> leftShoulders,
                                     List<IPoint> leftElbows,
                                     List<IPoint> leftWrists) {
        this.heads = heads;
        this.rightShoulders = rightShoulders;
        this.rightElbows = rightElbows;
        this.rightWrists = rightWrists;
        this.leftShoulders = leftShoulders;
        this.leftElbows = leftElbows;
        this.leftWrists = leftWrists;
    }

    private void initialize() {
        this.skeletonsToComplete = new HashMap<>();
        this.rightShoulder = new Places();
        this.rightElbow = new Places();
        this.rightWrist = new Places();
        this.leftShoulder = new Places();
        this.leftElbow = new Places();
        this.leftWrist = new Places();

    }

    @Override
    public List<ISwimmingSkeleton> interpolate(List<ISwimmingSkeleton> skeletons) {
        initialize();
        for(int i=0; i< skeletons.size(); i++) {
            ISwimmingSkeleton skeleton = skeletons.get(i);
            interpolate(skeleton, i, rightShoulder, new RightShoulderUpdater());
            interpolate(skeleton, i, rightElbow, new RightElbowUpdater());
            interpolate(skeleton, i, rightWrist, new RightWristUpdater());
            interpolate(skeleton, i, leftShoulder, new LeftShoulderUpdater());
            interpolate(skeleton, i, leftElbow, new LeftElbowUpdater());
            interpolate(skeleton, i, leftWrist, new LeftWristUpdater());
        }
        for(Integer key: this.skeletonsToComplete.keySet()) {
            SwimmingSkeleton skeleton = new SwimmingSkeleton(skeletons.get(key));
            Complete complete = this.skeletonsToComplete.get(key);
            if(complete.isRightShoulder()) {
                skeleton.setRightShoulder(rightShoulders.get(key));
            }
            if(complete.isRightElbow()) {
                skeleton.setRightElbow(rightElbows.get(key));
            }
            if(complete.isRightWrist()) {
                skeleton.setRightWrist(rightWrists.get(key));
            }
            if(complete.isLeftShoulder()) {
                skeleton.setLeftShoulder(leftShoulders.get(key));
            }
            if(complete.isLeftElbow()) {
                skeleton.setLeftElbow((leftElbows.get(key)));
            }
            if(complete.isLeftWrist()) {
                skeleton.setLeftWrist(leftWrists.get(key));
            }
            skeletons.set(key, skeleton);
        }
        return skeletons;
    }

    private void interpolate(ISwimmingSkeleton skeleton,
                             int index,
                             Places places,
                             ICompleteUpdater updater) {
        if(skeleton.containsRightShoulder() && places.start == -1) {
            places.start = index;
        }
        else if(!skeleton.containsRightShoulder()
                && places.start != -1
                && places.end == -1) {
            places.end = index;
        }
        else if(skeleton.containsRightShoulder() && places.end != -1) {
            if(index - places.end < PERIOD_TIME_FRAME_THRESHOLD) {
                for(int j = places.end; j<index; j++) {
                    Complete complete = addComplete(index);
                    updater.update(complete);
                }
            }
            places.start = index;
            places.end = -1;
        }
    }

    private Complete addComplete(int index) {
        if(!this.skeletonsToComplete.containsKey(index)) {
            this.skeletonsToComplete.put(index, new Complete());
        }
        return this.skeletonsToComplete.get(index);
    }
}




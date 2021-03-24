package DomainLogic.Interpolations.TimeSkeletonInterpolation;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;
import DomainLogic.Interpolations.ISkeletonInterpolation;
import DomainLogic.SkeletonFilters.*;

import java.util.HashMap;
import java.util.List;

public class TimeSkeletonInterpolation implements ISkeletonInterpolation {

    private static int PERIOD_TIME_FRAME_THRESHOLD = 6;

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

    public TimeSkeletonInterpolation(List<IPoint> rightShoulders,
                                     List<IPoint> rightElbows,
                                     List<IPoint> rightWrists,
                                     List<IPoint> leftShoulders,
                                     List<IPoint> leftElbows,
                                     List<IPoint> leftWrists) {
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
            interpolate(skeleton, i,
                    new RightShoulderFilter(),
                    rightShoulder,
                    new RightShoulderUpdater());
            interpolate(skeleton, i,
                    new RightElbowFilter(),
                    rightElbow,
                    new RightElbowUpdater());
            interpolate(skeleton, i,
                    new RightWristFilter(),
                    rightWrist,
                    new RightWristUpdater());
            interpolate(skeleton, i,
                    new LeftShoulderFilter(),
                    leftShoulder,
                    new LeftShoulderUpdater());
            interpolate(skeleton, i,
                    new LeftElbowFilter(),
                    leftElbow,
                    new LeftElbowUpdater());
            interpolate(skeleton, i,
                    new LeftWristFilter(),
                    leftWrist,
                    new LeftWristUpdater());
        }

        return complete(skeletons);
    }

    private List<ISwimmingSkeleton> complete(List<ISwimmingSkeleton> skeletons) {
//        System.out.println(skeletonsToComplete.keySet());
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
                             ISkeletonFilter skeletonFilter,
                             Places places,
                             ICompleteUpdater updater) {
        if(skeletonFilter.check(skeleton) && places.start == -1) {
            places.start = index;
        }
        else if(!skeletonFilter.check(skeleton)
                && places.start != -1
                && places.end == -1) {
            places.end = index;
        }
        else if(skeletonFilter.check(skeleton) && places.end != -1) {
            if(index - places.end < PERIOD_TIME_FRAME_THRESHOLD) {
//                System.out.println("completing between " + places.end +" to "+ index);
                for(int j = places.end; j<index; j++) {
                    Complete complete = addComplete(j);
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





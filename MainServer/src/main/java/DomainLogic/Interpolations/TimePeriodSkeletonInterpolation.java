package DomainLogic.Interpolations;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;

import java.util.HashMap;
import java.util.List;

public class TimePeriodSkeletonInterpolation implements ISkeletonInterpolation {

    private static int PERIOD_TIME_FRAME_THRESHOLD = 6;

    private List<IPoint> heads;
    private List<IPoint> rightShoulders;
    private List<IPoint> rightElbows;
    private List<IPoint> rightWrists;
    private List<IPoint> leftShoulders;
    private List<IPoint> leftElbows;
    private List<IPoint> leftWrists;

    private HashMap<Integer, Complete> skeletonsToComplete;
    private int startRightShoulder;
    private int endRightShoulder;
    private int startRightElbow;
    private int endRightElbow;
    private int startRightWrist;
    private int endRightWrist;

    private int startLeftShoulder;
    private int endLeftShoulder;
    private int startLeftElbow;
    private int endLeftElbow;
    private int startLeftWrist;
    private int endLeftWrist;

    public TimePeriodSkeletonInterpolation(List<IPoint> heads,
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


    private void ipos() {
        this.skeletonsToComplete = new HashMap<>();
        this.startRightShoulder = -1;
        this.endRightShoulder = -1;
        this.startRightElbow = -1;
        this.endRightElbow = -1;
        this.startRightWrist = -1;
        this.endRightWrist = -1;
        this.startLeftShoulder = -1;
        this.endLeftShoulder = -1;
        this.startLeftElbow = -1;
        this.endLeftElbow = -1;
        this.startLeftWrist = -1;
        this.endLeftWrist = -1;
    }

    @Override
    public List<ISwimmingSkeleton> interpolate(List<ISwimmingSkeleton> skeletons) {
        ipos();
        for(int i=0; i< skeletons.size(); i++) {
            ISwimmingSkeleton skeleton = skeletons.get(i);
            interpolateRightSoulder(skeleton, i);

        }
        return skeletons;
    }

    private void interpolateRightSoulder(ISwimmingSkeleton skeleton, int index) {
        if(skeleton.containsRightShoulder() && this.startRightShoulder == -1) {
            this.startRightShoulder = index;
        }
        else if(!skeleton.containsRightShoulder()
                && this.startRightShoulder != -1
                && this.endRightShoulder == -1) {
            this.endRightShoulder = index;
        }
        else if(skeleton.containsRightShoulder() && this.endRightShoulder != -1) {
            if(index - this.endRightShoulder < PERIOD_TIME_FRAME_THRESHOLD) {
                for(int j = this.endRightShoulder; j<index; j++) {

                }
            }
            this.startRightShoulder = index;
            this.endRightShoulder = -1;
        }
    }

    private Complete addComplete(int index) {
        if(!this.skeletonsToComplete.containsKey(index)) {
            this.skeletonsToComplete.put(index, new Complete());
        }
        return this.skeletonsToComplete.get(index);
    }

    private void addCompleteHead(int index) {
        Complete complete = addComplete(index);
        complete.head = true;
    }

    private void addCompleteRightShoulder(int index) {
        Complete complete = addComplete(index);
        complete.rightShoulder = true;
    }

    private void addCompleteRightElbow(int index) {
        Complete complete = addComplete(index);
        complete.rightElbow = true;
    }

    private void addCompleteRightWrist(int index) {
        Complete complete = addComplete(index);
        complete.rightWrist = true;
    }

    private void addCompleteLeftShoulder(int index) {
        Complete complete = addComplete(index);
        complete.leftShoulder = true;
    }

    private void addCompleteLeftElbow(int index) {
        Complete complete = addComplete(index);
        complete.leftElbow = true;
    }

    private void addCompleteLeftWrist(int index) {
        Complete complete = addComplete(index);
        complete.leftWrist = true;
    }

}

class Complete {
    boolean head;
    boolean rightShoulder;
    boolean rightElbow;
    boolean rightWrist;
    boolean leftShoulder;
    boolean leftElbow;
    boolean leftWrist;
}


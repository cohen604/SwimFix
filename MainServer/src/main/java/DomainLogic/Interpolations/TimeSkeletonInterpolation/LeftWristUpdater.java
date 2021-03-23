package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class LeftWristUpdater implements ICompleteUpdater{

    @Override
    public Complete update(Complete complete) {
        complete.setLeftWrist(true);
        return complete;
    }

}
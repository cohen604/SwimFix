package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class RightWristUpdater implements ICompleteUpdater{

    @Override
    public Complete update(Complete complete) {
        complete.setRightWrist(true);
        return complete;
    }

}
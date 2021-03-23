package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class RightShoulderUpdater implements ICompleteUpdater{
    @Override
    public Complete update(Complete complete) {
        complete.setRightShoulder(true);
        return complete;
    }
}
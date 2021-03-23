package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class RightShoulderCompleteUpdater implements ICompleteUpdater{
    @Override
    public Complete update(Complete complete) {
        complete.setRightShoulder(false);
        return complete;
    }
}
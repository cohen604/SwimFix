package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class LeftShoulderUpdater implements ICompleteUpdater{
    @Override
    public Complete update(Complete complete) {
        complete.setLeftShoulder(true);
        return complete;
    }
}
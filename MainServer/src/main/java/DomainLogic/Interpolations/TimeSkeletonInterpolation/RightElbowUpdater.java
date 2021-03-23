package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class RightElbowUpdater implements ICompleteUpdater{

    @Override
    public Complete update(Complete complete) {
        complete.setRightElbow(true);
        return complete;
    }

}
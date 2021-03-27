package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class LeftElbowUpdater implements ICompleteUpdater{

    @Override
    public Complete update(Complete complete) {
        complete.setLeftElbow(true);
        return complete;
    }

}
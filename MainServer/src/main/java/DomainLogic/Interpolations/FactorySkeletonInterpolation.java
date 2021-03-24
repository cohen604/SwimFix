package DomainLogic.Interpolations;

public class FactorySkeletonInterpolation implements IFactorySkeletonInterpolation{

    @Override
    public ISkeletonInterpolation factory() {
        Interpolation head = new LinearInterpolation();
        Interpolation other = new LinearInterpolation();
        return new SkeletonInterpolation(head, other);
    }

}

package DomainLogic.Interpolations;

public class FactorySkeletonInterpolation implements IFactorySkeletonInterpolation{

    @Override
    public ISkeletonInterpolation factory() {
        Interpolation head = new LinearInterpolation();
        Interpolation linear = new LinearInterpolation();
        Interpolation spline = new SplineInterpolation();
        Interpolation interExter = new InterExter(spline, linear);
        return new SkeletonInterpolation(interExter, head);
    }

}

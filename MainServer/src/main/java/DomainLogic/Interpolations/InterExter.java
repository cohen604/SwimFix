package DomainLogic.Interpolations;

import Domain.Points.IPoint;

import java.util.List;

public class InterExter implements Interpolation {

    private Interpolation inter;
    private Interpolation exter;

    public InterExter(Interpolation inter, Interpolation exter) {
        this.inter = inter;
        this.exter = exter;
    }

    @Override
    public List<IPoint> interpolate(List<IPoint> points) {
        List<IPoint> output = inter.interpolate(points);
        return exter.interpolate(output);
    }
}

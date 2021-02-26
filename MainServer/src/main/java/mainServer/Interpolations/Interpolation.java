package mainServer.Interpolations;

import Domain.SwimmingData.Points.IPoint;

import java.util.List;

public interface Interpolation {

    List<IPoint> interpolate(List<IPoint> points);
}

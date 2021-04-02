package DomainLogic.Interpolations;

import Domain.Points.IPoint;

import java.util.List;

/***
 *  the interface that defines the interpolation behavior.
 */
public interface Interpolation {

    /***
     * The function receives a list of points and interpolate over them
     * @param points - the points
     * @return the new points after the interpolate
     */
    List<IPoint> interpolate(List<IPoint> points);
}

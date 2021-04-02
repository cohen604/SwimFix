package DomainLogic.Interpolations;

import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonComposition.SkeletonPoint;
import org.apache.commons.math3.analysis.interpolation.AkimaSplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.LinkedList;
import java.util.List;

public class SplineInterpolation implements Interpolation {

    @Override
    public List<IPoint> interpolate(List<IPoint> points) {
        int validPoints = countValidPoints(points);
        // collect the valid data of size
        double[] xs = new double[validPoints];
        double[] ys = new double[validPoints];
        double[] ts = new double[validPoints];
        int index =0;
        int start = -1;
        int end = -1;
        for(int i =0; i< points.size(); i++) {
            IPoint point = points.get(i);
            if(point != null) {
                xs[index] = point.getX();
                ys[index] = point.getY();
                ts[index] = i;
                index ++;
                if(start == -1) {
                    start = i;
                }
                end = i;
            }
        }
        // interpolate step
        AkimaSplineInterpolator interpolator = new AkimaSplineInterpolator();
        PolynomialSplineFunction functionX = interpolator.interpolate(ts, xs);
        PolynomialSplineFunction functionY = interpolator.interpolate(ts, ys);
        // collect all the points and interpolation
        return interpolate(points, functionX, functionY, start, end);
    }

    private int countValidPoints(List<IPoint> points) {
        int size = 0;
        for(int i=0; i<points.size(); i++) {
            if(points.get(i) != null) {
                size++;
            }
        }
        return size;
    }

    private List<IPoint> interpolate(List<IPoint> points,
                                     PolynomialSplineFunction fx,
                                     PolynomialSplineFunction fy,
                                     int start,
                                     int end) {
        List<IPoint> output = new LinkedList<>();
        for(int i=0; i< points.size(); i++) {
            IPoint point = points.get(i);
            if(point!=null || i < start || i > end) {
                output.add(point);
            }
            else {
                double x = fx.value(i);
                double y = fy.value(i);
                output.add(new SkeletonPoint(x, y));
            }
        }
        return output;
    }

}

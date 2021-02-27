package mainServer.Interpolations;

import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;

import java.util.LinkedList;
import java.util.List;

public class MedianInterpolation implements Interpolation {

    private int _firstIndex;
    private IPoint _first;

    private int _missedPointsBeforeFirst;
    private int _missedPointsAfterFirst;

    private int _prevFirstIndex;
    private IPoint _prevFirst;

    public MedianInterpolation() {
        beforeInterpolate();
    }

    private void beforeInterpolate() {
        _firstIndex = -1;
        _first = null;

        _missedPointsBeforeFirst = 0;
        _missedPointsAfterFirst = 0;

        _prevFirstIndex = -1;
        _prevFirst = null;

    }

    @Override
    public List<IPoint> interpolate(List<IPoint> points) {
        List<IPoint> output = new LinkedList<>(points);
        beforeInterpolate();
        for(int t = 0; t < points.size(); t++) {
            IPoint p = points.get(t);
            if(p == null && _first == null) {
                _missedPointsBeforeFirst++;
            }
            else if(p == null) {
                _missedPointsAfterFirst++;
            }
            else if(p!=null && _first == null) {
                _first = p;
                _firstIndex = t;
            }
            else if(p!=null && _first !=null){
                interpolateFunc(output, p, t);
            }
        }
        afterInterpolate(output);
        for(IPoint ipoint : output) {
            if(ipoint == null) {
                System.out.println("Found null");
            }
        }
        return output;
    }

    private void afterInterpolate(List<IPoint> points) {
        if(_missedPointsBeforeFirst > 0 && _missedPointsAfterFirst > 0) {
            // the state there is only 1 point found
            System.out.println("Bug in interpolation");
            // TODO
        }
        else if(_missedPointsBeforeFirst > 0){
            // the state there is no point found
            System.out.println("Bug in interpolation");
            // TODO
        }
        else if(_missedPointsAfterFirst > 0) {
            // the state there is at least 2 points found and in the end there nulls
            computePointsAfter(points, _prevFirst, _first, _prevFirstIndex, _firstIndex,
                    _missedPointsAfterFirst);
        }
    }

    private void interpolateFunc(List<IPoint> points, IPoint second, int secondIndex) {
        computePoints(points, _first, second, _firstIndex, secondIndex);
        if(_missedPointsBeforeFirst > 0) {
            computePointsBefore(points, _first, second, _firstIndex, secondIndex, _missedPointsBeforeFirst);
            _missedPointsBeforeFirst = 0;
        }
        _prevFirst = _first;
        _prevFirstIndex = _firstIndex;
        _first = second;
        _firstIndex = secondIndex;
        _missedPointsAfterFirst = 0;
    }

    private double calcMedian(double m0, double m1) {
        return (m0 + m1) / 2;
    }

    private void compute(List<IPoint> points, int startIndex, int lastIndex, IPoint first, IPoint second,
                         int firstIndex, int secondIndex) {
        for(int i=startIndex; i<lastIndex; i++) {
            double xPoint = calcMedian(first.getX(), second.getX());
            double yPoint = calcMedian(first.getY(), second.getY());
            //TODO change to factory
            points.set(i, new SkeletonPoint(xPoint, yPoint));
        }
    }

    private void computePointsBefore(List<IPoint> points, IPoint first, IPoint second,
                               int firstIndex, int secondIndex,
                               int missedPointsBeforeFirst) {
        compute(points, firstIndex - missedPointsBeforeFirst, firstIndex,
                first, second, firstIndex, secondIndex);
    }

    private void computePoints(List<IPoint> points, IPoint first, IPoint second,
                                         int firstIndex, int secondIndex) {
        compute(points, firstIndex +1, secondIndex, first, second, firstIndex, secondIndex);
    }

    private void computePointsAfter(List<IPoint> points, IPoint first, IPoint second,
                                        int firstIndex, int secondIndex,
                                        int missedPointsAfter) {
        compute(points, secondIndex +1, secondIndex + missedPointsAfter + 1,
                first, second, firstIndex, secondIndex);
    }

}

package DomainLogic.Interpolations;

import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;

import java.util.LinkedList;
import java.util.List;

public class LinearInterpolation implements Interpolation {

    private int _firstIndex;
    private IPoint _first;

    private int _missedPointsBeforeFirst;
    private int _missedPointsAfterFirst;

    private int _prevFirstIndex;
    private IPoint _prevFirst;

    public LinearInterpolation() {
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

    private double findY(double x, double x0, double x1, double y0, double y1) {
        // liner equation
        return y0 + (x - x0) * (y1 - y0) / (x1 - x0);
    }

    private void compute(List<IPoint> points, int startIndex, int lastIndex, IPoint first, IPoint second,
                         int firstIndex, int secondIndex, double threshold) {
        for(int i=startIndex; i<lastIndex; i++) {
            double xPoint = findY(i, firstIndex, secondIndex, first.getX(), second.getX());
            double yPoint = findY(i, firstIndex, secondIndex, first.getY(), second.getY());
            // check that a new point is not too far away from the previous points
            IPoint averagePrev = averagePrev(points, i);
            if (Math.abs(averagePrev.getX() - xPoint) < threshold &&
                    Math.abs(averagePrev.getY() - yPoint) < threshold) {
                points.set(i, new SkeletonPoint(xPoint, yPoint));
            }
            else {
                points.set(i, averagePrev);
            }
        }
    }

    /**
     * calculate the average of the 5 prev points
     * @param points - the list of the points
     * @param index - the index of the prev points
     * @return - the average of the prev points
     */
    private IPoint averagePrev (List<IPoint> points, int index) {
        double xAverage = 0;
        double yAverage = 0;
        int total = 0;
        for (int i = 1; i <= 5; i++){
            if (index - i > 0) {
                IPoint iPoint = points.get(index - i);
                if (iPoint != null) {
                    xAverage += iPoint.getX();
                    yAverage += iPoint.getY();
                    total += 1;
                }
            }
        }
        return new SkeletonPoint(xAverage / total,yAverage / total);
    }

    private void computePointsBefore(List<IPoint> points, IPoint first, IPoint second,
                               int firstIndex, int secondIndex,
                               int missedPointsBeforeFirst) {
        /*compute(points, firstIndex - missedPointsBeforeFirst, firstIndex,
                first, second, firstIndex, secondIndex);*/
        for(int i=firstIndex - missedPointsBeforeFirst; i<firstIndex; i++) {
            points.set(i, first);
        }
    }

    private void computePoints(List<IPoint> points, IPoint first, IPoint second,
                                         int firstIndex, int secondIndex) {
        compute(points, firstIndex +1, secondIndex, first, second, firstIndex, secondIndex, 50);
    }

    private void computePointsAfter(List<IPoint> points, IPoint first, IPoint second,
                                        int firstIndex, int secondIndex,
                                        int missedPointsAfter) {
        compute(points, secondIndex +1, secondIndex + missedPointsAfter + 1,
                first, second, firstIndex, secondIndex, 50);
    }

}

package mainServer.Interpolations;

import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingSkeletonComposition.SkeletonPoint;

import java.util.LinkedList;
import java.util.List;

public class VarianceInterpolation implements Interpolation {

    private double _averageX;
    private double _averageXSquare;
    private double _varXSquare;
    private double _averageY;
    private double _averageYSquare;
    private double _varYSqaure;
    private int _realSize;

    @Override
    public List<IPoint> interpolate(List<IPoint> points) {
        List<IPoint> output = new LinkedList<>();

        Init();
        computeAverages(points);
        computeVariance();
        for(IPoint point : points) {
            output.add(computePoint(point));
        }
        return output;
    }

    private void Init() {
        _averageX = 0;
        _averageXSquare = 0;
        _varXSquare = 0;
        _averageY = 0;
        _averageYSquare = 0;
        _varYSqaure = 0;
        _realSize =0;
    }

    private void computeAverages(List<IPoint> points) {
        double x;
        double y;
        for(IPoint point : points) {
            if(point!=null) {
                _realSize++;
                x = point.getX();
                y = point.getY();
                _averageX += x;
                _averageXSquare += x * x;
                _averageY += y;
                _averageYSquare += y * y;
            }
        }
        System.out.println("found points " + _realSize);
        _averageX = _averageX / _realSize;
        _averageXSquare = _averageXSquare / _realSize;
        _averageY = _averageY / _realSize;
        _averageYSquare = _averageYSquare / _realSize;
    }

    private void computeVariance() {
        _varXSquare = _averageXSquare - _averageX;
        _varYSqaure = _averageYSquare - _averageY;
    }

    private IPoint computePoint(IPoint current) {
        if(current == null) {
            return new SkeletonPoint(_averageX, _averageY);
        }
        double x = current.getX();
        double y = current.getY();
        double dx = x - _averageX;
        double dy = y - _averageY;

        if( _realSize * dx * dx > _varXSquare) {
            x = _averageX;
            System.out.println("found x > var x");
        }
        else {
            System.out.println("var x " + _varXSquare + " average x "+_averageX + " origin x "+ x);
        }
        if( _realSize * dy * dy > _varYSqaure) {
            y = _averageY;
            System.out.println("found y > var y");
        }
        else {
            System.out.println("var y " + _varYSqaure + " average y "+_averageY + " origin y "+ y);
        }
        return new SkeletonPoint(x, y);
    }

}

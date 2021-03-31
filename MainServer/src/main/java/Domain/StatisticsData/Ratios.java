package Domain.StatisticsData;

public class Ratios {

    private int _modelCount;
    private int _modelAndInterpolationCount;
    private int _actualCount;

    Ratios() {
        _modelCount = 0;
        _actualCount = 0;
        _modelAndInterpolationCount = 0;
    }

    void addModel() {
        _modelCount++;
    }

    public void addModelAndInterpolation() {
        _modelAndInterpolationCount++;
    }

    void addActual() {
        _actualCount++;
    }

    int getModelCount() {
        return _modelCount;
    }

    int getmodelAndInterpolationCount() {
        return _modelAndInterpolationCount;
    }

    int getActualCount() {
        return _actualCount;
    }


    double getRatioModel() {
        return getRatio(_modelCount, _actualCount);
    }

    double getRatioModelAndInterpolation() {
        return getRatio(_modelAndInterpolationCount, _actualCount);
    }

    double getImprovment() {
        double val = getRatioModelAndInterpolation() - getRatioModel();
        return Math.floor(val * 10) / 10;
    }

    private double getRatio(double a, double b) {
        if(b==0) {
            return 0;
        }
        return Math.floor(a / b * 1000) / 10;
    }
}

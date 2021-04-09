package Domain.StatisticsData;

public class Ratios {

    private int _actualCount; //Ground truth
    private int _modelCount;
    private int _modelAndInterpolationCount;
    private int _modelCorrect;
    private int _modelWrong;
    private int _modelAndInterCorrect;
    private int _modelAndInterWrong;

    Ratios() {
        _actualCount = 0;
        _modelCount = 0;
        _modelAndInterpolationCount = 0;
        _modelCorrect = 0;
        _modelWrong = 0;
        _modelAndInterCorrect = 0;
        _modelAndInterWrong = 0;
    }

    void addActual() {
        _actualCount++;
    }

    void addModel() {
        _modelCount++;
    }

    void addModelAndInterpolation() {
        _modelAndInterpolationCount++;
    }

    void addModelCorrect() {
        _modelCorrect++;
    }

    void addModelWrong() {
        _modelWrong++;
    }

    void addModelAndInterpolationCorrect() {
        _modelAndInterCorrect++;
    }

    void addModelAndInterpolationWrong() {
        _modelAndInterWrong++;
    }


    int getActualCount() {
        return _actualCount;
    }

    int getModelCount() {
        return _modelCount;
    }

    int getModelAndInterpolationCount() {
        return _modelAndInterpolationCount;
    }

    int getModelCorrect() { return _modelCorrect; }

    int getModelWrong() { return _modelWrong; }

    int getModelAndInterCorrect() { return _modelAndInterCorrect; }

    int getModelAndInterWrong() { return _modelAndInterWrong; }


    double getRatioModel() {
        return getRatio(_modelCount, _actualCount);
    }

    double getRatioModelAndInterpolation() {
        return getRatio(_modelAndInterpolationCount, _actualCount);
    }

    double getImprovement() {
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

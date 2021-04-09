package Domain.StatisticsData;

public class Ratios {

    private int _frames;
    private int _actualCount; //Ground truth
    private int _modelCount;
    private int _modelAndInterpolationCount;
    private int _modelTP;
    private int _modelTN;
    private int _modelFP;
    private int _modelFN;

    Ratios(int frames) {
        _frames = frames;
        _actualCount = 0;
        _modelCount = 0;
        _modelAndInterpolationCount = 0;
        _modelTP = 0;
        _modelTN = 0;
        _modelFP = 0;
        _modelFN = 0;
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

    void addModelTP() {
        _modelTP++;
    }

    void addModelTN() {
        _modelTN++;
    }

    void addModelFP() {
        _modelFP++;
    }

    void addModelFN() {
        _modelFN++;
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

    int getModelTP() { return _modelTP; }

    int getModelTN() { return _modelTN; }

    int getModelFP() { return _modelFP; }

    int getModelFN() { return _modelFN; }

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

    double getRatioModelTP() {
        return getRatio(_modelTP, _frames);
    }

    double getRationModelTN() {
        return getRatio(_modelTN, _frames);
    }

    double getRationModelFP() {
        return getRatio(_modelFP, _frames);
    }

    double getRationModelFN() {
        return getRatio(_modelFN, _frames);
    }

    private double getRatio(double a, double b) {
        if(b==0) {
            return 0;
        }
        return Math.floor(a / b * 1000) / 10;
    }


}

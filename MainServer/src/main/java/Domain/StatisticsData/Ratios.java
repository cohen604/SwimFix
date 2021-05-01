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
    private int _modelAndInterTP;
    private int _modelAndInterTN;
    private int _modelAndInterFP;
    private int _modelAndInterFN;

    public Ratios(int frames) {
        _frames = frames;
        _actualCount = 0;
        _modelCount = 0;
        _modelAndInterpolationCount = 0;
        _modelTP = 0;
        _modelTN = 0;
        _modelFP = 0;
        _modelFN = 0;
        _modelAndInterTP = 0;
        _modelAndInterTN = 0;
        _modelAndInterFP = 0;
        _modelAndInterFN = 0;
    }

    public void addActual() {
        _actualCount++;
    }

    public void addModel() {
        _modelCount++;
    }

    public void addModelAndInterpolation() {
        _modelAndInterpolationCount++;
    }

    public void addModelTP() {
        _modelTP++;
    }

    public void addModelTN() {
        _modelTN++;
    }

    public void addModelFP() {
        _modelFP++;
    }

    public void addModelFN() {
        _modelFN++;
    }

    public void addModelAndInterTP() {
        _modelAndInterTP++;
    }

    public void addModelAndInterTN() {
        _modelAndInterTN++;
    }

    public void addModelAndInterFP() {
        _modelAndInterFP++;
    }

    public void addModelAndInterFN() {
        _modelAndInterFN++;
    }

    public int getActualCount() {
        return _actualCount;
    }

    public int getModelCount() {
        return _modelCount;
    }

    public int getModelAndInterpolationCount() {
        return _modelAndInterpolationCount;
    }

    public int getModelTP() { return _modelTP; }

    public int getModelTN() { return _modelTN; }

    public int getModelFP() { return _modelFP; }

    public int getModelFN() { return _modelFN; }

    public int getModelAndInterTP() { return _modelAndInterTP; }

    public int getModelAndInterTN() { return _modelAndInterTN; }

    public int getModelAndInterFP() { return _modelAndInterFP; }

    public int getModelAndInterFN() { return _modelAndInterFN; }

    public double getRatioModel() {
        return getRatio(_modelCount, _actualCount);
    }

    public double getRatioModelAndInterpolation() {
        return getRatio(_modelAndInterpolationCount, _actualCount);
    }

    public double getImprovement() {
        double val = getRatioModelAndInterpolation() - getRatioModel();
        return Math.floor(val * 10) / 10;
    }

    public double getRatioModelTP() {
        return getRatio(_modelTP, _actualCount);
    }

    public double getRatioModelTN() {
        return getRatio(_modelTN, _frames - _actualCount);
    }

    public double getRatioModelFP() {
        return getRatio(_modelFP, _frames - _actualCount);
    }

    public double getRatioModelFN() {
        return getRatio(_modelFN, _actualCount);
    }

    public double getRatioModelAndInterTP() {
        return getRatio(_modelAndInterTP, _actualCount);
    }

    public double getRatioModelAndInterTN() {
        return getRatio(_modelAndInterTN, _frames - _actualCount);
    }

    public double getRatioModelAndInterFP() {
        return getRatio(_modelAndInterFP, _frames - _actualCount);
    }

    public double getRatioModelAndInterFN() {
        return getRatio(_modelAndInterFN, _actualCount);
    }

    private double getRatio(double a, double b) {
        if(b==0) {
            return 0;
        }
        return Math.floor(a / b * 1000) / 10;
    }


}

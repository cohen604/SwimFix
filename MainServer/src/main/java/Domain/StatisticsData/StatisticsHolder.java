package Domain.StatisticsData;

import Domain.SwimmingData.ISwimmingSkeleton;

import java.util.List;

public class StatisticsHolder implements IStatistic {

    private Ratio _headRecognizeRatio;
    private Ratio _rightShoulderRecognizeRatio;
    private Ratio _rightElbowRecognizeRatio;
    private Ratio _rightWristRecognizeRatio;
    private Ratio _leftShoulderRecognizeRatio;
    private Ratio _leftElbowRecognizeRatio;
    private Ratio _leftWristRecognizeRatio;

    /**
     * Constructor
     * @param raw - the list of raw skeletons
     * @param current - the list of actual skeletons
     */
    public StatisticsHolder(List<ISwimmingSkeleton> raw, List<ISwimmingSkeleton> current) {
        initialize();
        if(raw.size() != current.size()) {
            throw new IllegalArgumentException("raw list size dont match to current list size");
        }
        evalStatistic(raw, current);
    }

    private void initialize() {
        _headRecognizeRatio = new Ratio();
        _rightShoulderRecognizeRatio = new Ratio();
        _rightElbowRecognizeRatio = new Ratio();
        _rightWristRecognizeRatio = new Ratio();
        _leftShoulderRecognizeRatio = new Ratio();
        _leftElbowRecognizeRatio = new Ratio();
        _leftWristRecognizeRatio = new Ratio();
    }

    private void evalStatistic(List<ISwimmingSkeleton> raw, List<ISwimmingSkeleton> current) {
        for(int i=0; i<raw.size(); i++) {
            ISwimmingSkeleton r = raw.get(i);
            ISwimmingSkeleton c = current.get(i);
            tryAddHeadToStatistic(r,c);
            tryAddRightShoulderToStatistic(r,c);
            tryAddRightElbowToStatistic(r,c);
            tryAddRightWristToStatistic(r,c);
            tryAddLeftShoulderToStatistic(r,c);
            tryAddLeftElbowToStatistic(r,c);
            tryAddLeftWristToStatistic(r,c);
        }
    }

    private void tryAddHeadToStatistic(ISwimmingSkeleton raw, ISwimmingSkeleton current) {
        if(raw.containsHead()) {
            _headRecognizeRatio.addExpected();
        }
        if(current.containsHead()) {
            _headRecognizeRatio.addActual();
        }
    }

    private void tryAddRightShoulderToStatistic(ISwimmingSkeleton raw, ISwimmingSkeleton current) {
        if(raw.containsRightShoulder()) {
            _rightShoulderRecognizeRatio.addExpected();
        }
        if(current.containsRightShoulder()) {
            _rightShoulderRecognizeRatio.addActual();
        }
    }

    private void tryAddRightElbowToStatistic(ISwimmingSkeleton raw, ISwimmingSkeleton current) {
        if(raw.containsRightElbow()) {
            _rightElbowRecognizeRatio.addExpected();
        }
        if(current.containsRightElbow()) {
            _rightElbowRecognizeRatio.addActual();
        }
    }

    private void tryAddRightWristToStatistic(ISwimmingSkeleton raw, ISwimmingSkeleton current) {
        if(raw.containsRightWrist()) {
            _rightWristRecognizeRatio.addExpected();
        }
        if(current.containsRightWrist()) {
            _rightWristRecognizeRatio.addActual();
        }
    }

    private void tryAddLeftShoulderToStatistic(ISwimmingSkeleton raw, ISwimmingSkeleton current) {
        if(raw.containsLeftShoulder()) {
            _leftShoulderRecognizeRatio.addExpected();
        }
        if(current.containsLeftShoulder()) {
            _leftShoulderRecognizeRatio.addActual();
        }
    }

    private void tryAddLeftElbowToStatistic(ISwimmingSkeleton raw, ISwimmingSkeleton current) {
        if(raw.containsLeftElbow()) {
            _leftElbowRecognizeRatio.addExpected();
        }
        if(current.containsLeftElbow()) {
            _leftElbowRecognizeRatio.addActual();
        }
    }

    private void tryAddLeftWristToStatistic(ISwimmingSkeleton raw, ISwimmingSkeleton current) {
        if(raw.containsLeftWrist()) {
            _leftWristRecognizeRatio.addExpected();
        }
        if(current.containsLeftWrist()) {
            _leftWristRecognizeRatio.addActual();
        }
    }

    @Override
    public double getHeadRecognitionPercent() {
        return _headRecognizeRatio.getRatio();
    }

    @Override
    public int getHeadExpected() {
        return _headRecognizeRatio.getExpectedCount();
    }

    @Override
    public int getHeadActual() {
        return _headRecognizeRatio.getActualCount();
    }

    @Override
    public double getRightShoulderRecognitionPercent() {
        return _rightShoulderRecognizeRatio.getRatio();
    }

    @Override
    public int getRightShoulderExpected() {
        return _rightShoulderRecognizeRatio.getExpectedCount();
    }

    @Override
    public int getRightShoulderActual() {
        return _rightShoulderRecognizeRatio.getActualCount();
    }

    @Override
    public double getRightElbowRecognitionPercent() {
        return _rightElbowRecognizeRatio.getRatio();
    }

    @Override
    public int getRightElbowExpected() {
        return _rightElbowRecognizeRatio.getExpectedCount();
    }

    @Override
    public int getRightElbowActual() {
        return _rightElbowRecognizeRatio.getActualCount();
    }

    @Override
    public double getRightWristRecognitionPercent() {
        return _rightWristRecognizeRatio.getRatio();
    }

    @Override
    public int getRightWristExpected() {
        return _rightWristRecognizeRatio.getExpectedCount();
    }

    @Override
    public int getRightWristActual() {
        return _rightWristRecognizeRatio.getActualCount();
    }

    @Override
    public double getLeftShoulderRecognitionPercent() {
        return _leftShoulderRecognizeRatio.getRatio();
    }

    @Override
    public int getLeftShoulderExpected() {
        return _leftShoulderRecognizeRatio.getExpectedCount();
    }

    @Override
    public int getLeftShoulderActual() {
        return _leftShoulderRecognizeRatio.getActualCount();
    }

    @Override
    public double getLeftElbowRecognitionPercent() {
        return _leftElbowRecognizeRatio.getRatio();
    }

    @Override
    public int getLeftElbowExpected() {
        return _leftElbowRecognizeRatio.getExpectedCount();
    }

    @Override
    public int getLeftElbowActual() {
        return _leftElbowRecognizeRatio.getActualCount();
    }

    @Override
    public double getLeftWristRecognitionPercent() {
        return _leftWristRecognizeRatio.getRatio();
    }

    @Override
    public int getLeftWristExpected() {
        return _leftWristRecognizeRatio.getExpectedCount();
    }

    @Override
    public int getLeftWristActual() {
        return _leftWristRecognizeRatio.getActualCount();
    }
}

class Ratio {

    private int _expectedCount;
    private int _actualCount;

    Ratio() {
        _expectedCount = 0;
        _actualCount = 0;
    }

    void addExpected() {
        _expectedCount++;
    }

    void addActual() {
        _actualCount++;
    }

    double getRatio() {
        return (double)_actualCount / (double)_expectedCount;
    }

    int getExpectedCount() {
        return _expectedCount;
    }

    int getActualCount() {
        return _actualCount;
    }
}
package Domain.StatisticsData;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;

import java.util.List;

public class StatisticsHolder implements IStatistic {

    private Ratios _headRecognizeRatios;
    private Ratios _rightShoulderRecognizeRatios;
    private Ratios _rightElbowRecognizeRatios;
    private Ratios _rightWristRecognizeRatios;
    private Ratios _leftShoulderRecognizeRatios;
    private Ratios _leftElbowRecognizeRatios;
    private Ratios _leftWristRecognizeRatios;

    /**
     * Constructor
     * @param raw - the list of raw skeletons
     * @param modelSkeletons - the list of model skeletons
     * @param modelAndInterpolationSkeletons - the list of model and interpolation skeletons
     */
    public StatisticsHolder(List<ISwimmingSkeleton> raw,
                            List<ISwimmingSkeleton> modelSkeletons,
                            List<ISwimmingSkeleton> modelAndInterpolationSkeletons) {
        initialize();
        if(raw.size() != modelSkeletons.size()) {
            throw new IllegalArgumentException("raw list size dont match to current list size");
        }
        evalStatistic(raw, modelSkeletons, modelAndInterpolationSkeletons);
    }

    private void initialize() {
        _headRecognizeRatios = new Ratios();
        _rightShoulderRecognizeRatios = new Ratios();
        _rightElbowRecognizeRatios = new Ratios();
        _rightWristRecognizeRatios = new Ratios();
        _leftShoulderRecognizeRatios = new Ratios();
        _leftElbowRecognizeRatios = new Ratios();
        _leftWristRecognizeRatios = new Ratios();
    }

    private void evalStatistic(List<ISwimmingSkeleton> raw,
                               List<ISwimmingSkeleton> modelSkeletons,
                               List<ISwimmingSkeleton> modelAndInterpolationSkeletons) {
        for(int i=0; i<raw.size(); i++) {
            ISwimmingSkeleton rs = tryGetSkeleton(raw, i);
            ISwimmingSkeleton ms = tryGetSkeleton(modelSkeletons, i);
            ISwimmingSkeleton mis = tryGetSkeleton(modelAndInterpolationSkeletons,i);
            tryAddHeadToStatistic(rs, ms, mis);
            tryAddRightShoulderToStatistic(rs, ms, mis);
            tryAddRightElbowToStatistic(rs, ms, mis);
            tryAddRightWristToStatistic(rs, ms, mis);
            tryAddLeftShoulderToStatistic(rs, ms, mis);
            tryAddLeftElbowToStatistic(rs, ms, mis);
            tryAddLeftWristToStatistic(rs, ms, mis);
        }
    }

    private ISwimmingSkeleton tryGetSkeleton(List<ISwimmingSkeleton> skeletons, int index) {
        if(skeletons == null || index > skeletons.size() || index < 0) {
            return null;
        }
        return skeletons.get(index);
    }

    private void tryAddHeadToStatistic(ISwimmingSkeleton actual,
                                       ISwimmingSkeleton model,
                                       ISwimmingSkeleton modelAndInterpolation) {
        if(actual != null && actual.containsHead()) {
            _headRecognizeRatios.addActual();
        }
        if(model != null && model.containsHead()) {
            _headRecognizeRatios.addModel();
        }
        if(modelAndInterpolation!=null && modelAndInterpolation.containsHead()) {
            _headRecognizeRatios.addModelAndInterpolation();
        }
    }

    private void tryAddRightShoulderToStatistic(ISwimmingSkeleton actual,
                                                ISwimmingSkeleton model,
                                                ISwimmingSkeleton modelAndInterpolation) {
        if(actual!=null && actual.containsRightShoulder()) {
            _rightShoulderRecognizeRatios.addActual();
        }
        if(model!=null && model.containsRightShoulder()) {
            _rightShoulderRecognizeRatios.addModel();
        }
        if(modelAndInterpolation!=null && modelAndInterpolation.containsRightShoulder()) {
            _rightShoulderRecognizeRatios.addModelAndInterpolation();
        }
    }

    private void tryAddRightElbowToStatistic(ISwimmingSkeleton actual,
                                             ISwimmingSkeleton model,
                                             ISwimmingSkeleton modelAndInterpolation) {
        if(actual!=null && actual.containsRightElbow()) {
            _rightElbowRecognizeRatios.addActual();
        }
        if(model!=null && model.containsRightElbow()) {
            _rightElbowRecognizeRatios.addModel();
        }
        if(modelAndInterpolation!=null && modelAndInterpolation.containsRightElbow()) {
            _rightElbowRecognizeRatios.addModelAndInterpolation();
        }
    }

    private void tryAddRightWristToStatistic(ISwimmingSkeleton actual,
                                             ISwimmingSkeleton model,
                                             ISwimmingSkeleton modelAndInterpolation) {
        if(actual!=null && actual.containsRightWrist()) {
            _rightWristRecognizeRatios.addActual();
        }
        if(model!=null && model.containsRightWrist()) {
            _rightWristRecognizeRatios.addModel();
        }
        if(modelAndInterpolation!=null && modelAndInterpolation.containsRightWrist()) {
            _rightWristRecognizeRatios.addModelAndInterpolation();
        }
    }

    private void tryAddLeftShoulderToStatistic(ISwimmingSkeleton actual,
                                               ISwimmingSkeleton model,
                                               ISwimmingSkeleton modelAndInterpolation) {
        if(actual!=null && actual.containsLeftShoulder()) {
            _leftShoulderRecognizeRatios.addActual();
        }
        if(model!=null && model.containsLeftShoulder()) {
            _leftShoulderRecognizeRatios.addModel();
        }
        if(modelAndInterpolation!=null && modelAndInterpolation.containsLeftShoulder()) {
            _leftShoulderRecognizeRatios.addModelAndInterpolation();
        }
    }

    private void tryAddLeftElbowToStatistic(ISwimmingSkeleton actual,
                                            ISwimmingSkeleton model,
                                            ISwimmingSkeleton modelAndInterpolation) {
        if(actual!=null && actual.containsLeftElbow()) {
            _leftElbowRecognizeRatios.addActual();
        }
        if(model!=null && model.containsLeftElbow()) {
            _leftElbowRecognizeRatios.addModel();
        }
        if(modelAndInterpolation!=null && modelAndInterpolation.containsLeftElbow()) {
            _leftElbowRecognizeRatios.addModelAndInterpolation();
        }
    }

    private void tryAddLeftWristToStatistic(ISwimmingSkeleton actual,
                                            ISwimmingSkeleton model,
                                            ISwimmingSkeleton modelAndInterpolation) {
        if(actual!=null && actual.containsLeftWrist()) {
            _leftWristRecognizeRatios.addActual();
        }
        if(model!=null && model.containsLeftWrist()) {
            _leftWristRecognizeRatios.addModel();
        }
        if(modelAndInterpolation!=null && modelAndInterpolation.containsLeftWrist()) {
            _leftWristRecognizeRatios.addModelAndInterpolation();
        }
    }

    @Override
    public double getHeadImprove() {
        return _headRecognizeRatios.getImprovment();
    }

    @Override
    public double getHeadRatioModel() {
        return _headRecognizeRatios.getRatioModel();
    }

    @Override
    public double getHeadRatioModelAndInterpolation() {
        return _headRecognizeRatios.getRatioModelAndInterpolation();
    }

    @Override
    public int getHeadModel() {
        return _headRecognizeRatios.getModelCount();
    }

    @Override
    public int getHeadModelAndInterpolation() {
        return _headRecognizeRatios.getmodelAndInterpolationCount();
    }

    @Override
    public int getHeadActual() {
        return _headRecognizeRatios.getActualCount();
    }

    @Override
    public double getRightShoulderImprove() {
        return _rightShoulderRecognizeRatios.getImprovment();
    }

    @Override
    public double getRightShoulderRatioModel() {
        return _rightShoulderRecognizeRatios.getRatioModel();
    }

    @Override
    public double getRightShoulderRatioModelAndInterpolation() {
        return _rightShoulderRecognizeRatios.getRatioModelAndInterpolation();
    }

    @Override
    public int getRightShoulderModel() {
        return _rightShoulderRecognizeRatios.getModelCount();
    }

    @Override
    public int getRightShoulderModelAndInterpolation() {
        return _rightShoulderRecognizeRatios.getmodelAndInterpolationCount();
    }

    @Override
    public int getRightShoulderActual() {
        return _rightShoulderRecognizeRatios.getActualCount();
    }

    @Override
    public double getRightElbowImprove() {
        return _rightElbowRecognizeRatios.getImprovment();
    }

    @Override
    public double getRightElbowRatioModel() {
        return _rightElbowRecognizeRatios.getRatioModel();
    }

    @Override
    public double getRightElbowRatioModelAndInterpolation() {
        return _rightElbowRecognizeRatios.getRatioModelAndInterpolation();
    }

    @Override
    public int getRightElbowModel() {
        return _rightElbowRecognizeRatios.getModelCount();
    }

    @Override
    public int getRightElbowModelAndInterpolation() {
        return _rightElbowRecognizeRatios.getmodelAndInterpolationCount();
    }

    @Override
    public int getRightElbowActual() {
        return _rightElbowRecognizeRatios.getActualCount();
    }

    @Override
    public double getRightWristImprove() {
        return _rightWristRecognizeRatios.getImprovment();
    }

    @Override
    public double getRightWristRatioModel() {
        return _rightWristRecognizeRatios.getRatioModel();
    }

    @Override
    public double getRightWristRatioModelAndInterpolation() {
        return _rightWristRecognizeRatios.getRatioModelAndInterpolation();
    }

    @Override
    public int getRightWristModel() {
        return _rightWristRecognizeRatios.getModelCount();
    }

    @Override
    public int getRightWristModelAndInterpolation() {
        return _rightWristRecognizeRatios.getmodelAndInterpolationCount();
    }

    @Override
    public int getRightWristActual() {
        return _rightWristRecognizeRatios.getActualCount();
    }

    @Override
    public double getLeftShoulderImprove() {
        return _leftShoulderRecognizeRatios.getImprovment();
    }

    @Override
    public double getLeftShoulderRatioModel() {
        return _leftShoulderRecognizeRatios.getRatioModel();
    }

    @Override
    public double getLeftShoulderRatioModelAndInterpolation() {
        return _leftShoulderRecognizeRatios.getRatioModelAndInterpolation();
    }

    @Override
    public int getLeftShoulderModel() {
        return _leftShoulderRecognizeRatios.getModelCount();
    }

    @Override
    public int getLeftShoulderModelAndInterpolation() {
        return _leftShoulderRecognizeRatios.getmodelAndInterpolationCount();
    }

    @Override
    public int getLeftShoulderActual() {
        return _leftShoulderRecognizeRatios.getActualCount();
    }

    @Override
    public double getLeftElbowImprove() {
        return _leftElbowRecognizeRatios.getImprovment();
    }

    @Override
    public double getLeftElbowRatioModel() {
        return _leftElbowRecognizeRatios.getRatioModel();
    }

    @Override
    public double getLeftElbowRatioModelAndInterpolation() {
        return _leftElbowRecognizeRatios.getRatioModelAndInterpolation();
    }

    @Override
    public int getLeftElbowModel() {
        return _leftElbowRecognizeRatios.getModelCount();
    }

    @Override
    public int getLeftElbowModelAndInterpolation() {
        return _leftElbowRecognizeRatios.getmodelAndInterpolationCount();
    }

    @Override
    public int getLeftElbowActual() {
        return _leftElbowRecognizeRatios.getActualCount();
    }

    @Override
    public double getLeftWristImprove() {
        return _leftWristRecognizeRatios.getImprovment();
    }

    @Override
    public double getLeftWristRatioModel() {
        return _leftWristRecognizeRatios.getRatioModel();
    }

    @Override
    public double getLeftWristRatioModelAndInterpolation() {
        return _leftWristRecognizeRatios.getRatioModelAndInterpolation();
    }

    @Override
    public int getLeftWristModel() {
        return _leftWristRecognizeRatios.getModelCount();
    }

    @Override
    public int getLeftWristModelAndInterpolation() {
        return _leftWristRecognizeRatios.getmodelAndInterpolationCount();
    }

    @Override
    public int getLeftWristActual() {
        return _leftWristRecognizeRatios.getActualCount();
    }
}
package Domain.StatisticsData;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonFilters.*;

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
        // TODO: change flow if raw data is null
        if(raw.size() != modelSkeletons.size()) {
            throw new IllegalArgumentException("raw list size doesn't match to current list size");
        }
        ISkeletonFilter headFilter = new HeadFilter();
        ISkeletonFilter leftElbowFilter = new LeftElbowFilter();
        ISkeletonFilter rightElbowFilter = new RightElbowFilter();
        ISkeletonFilter leftShoulderFilter = new LeftShoulderFilter();
        ISkeletonFilter rightShoulderFilter = new RightShoulderFilter();
        ISkeletonFilter leftWristFilter = new LeftWristFilter();
        ISkeletonFilter rightWristFilter = new RightWristFilter();

        for(int i=0; i<raw.size(); i++) {
            ISwimmingSkeleton rs = tryGetSkeleton(raw, i);
            ISwimmingSkeleton ms = tryGetSkeleton(modelSkeletons, i);
            ISwimmingSkeleton mis = tryGetSkeleton(modelAndInterpolationSkeletons,i);
            tryAddHeadToStatistic(rs, ms, mis, headFilter);
            tryAddRightShoulderToStatistic(rs, ms, mis, rightShoulderFilter);
            tryAddRightElbowToStatistic(rs, ms, mis, rightElbowFilter);
            tryAddRightWristToStatistic(rs, ms, mis, rightWristFilter);
            tryAddLeftShoulderToStatistic(rs, ms, mis, leftShoulderFilter);
            tryAddLeftElbowToStatistic(rs, ms, mis, leftElbowFilter);
            tryAddLeftWristToStatistic(rs, ms, mis, leftWristFilter);
        }
    }

    private ISwimmingSkeleton tryGetSkeleton(List<ISwimmingSkeleton> skeletons, int index) {
        if(skeletons == null || index > skeletons.size() || index < 0) {
            return null;
        }
        return skeletons.get(index);
    }

    private void tryAddRatioToStatistic(ISwimmingSkeleton actual,
                                        ISwimmingSkeleton model,
                                        ISwimmingSkeleton modelAndInterpolation,
                                        Ratios ratio,
                                        ISkeletonFilter filter) {
        if(filter.check(actual)) {
            ratio.addActual();
        }
        if(filter.check(model)) {
            ratio.addModel();
        }
        if(filter.check(modelAndInterpolation)) {
            ratio.addModelAndInterpolation();
        }

        if (filter.check(actual)) {
            if (filter.check(model)) {
                ratio.addModelCorrect();
            }
            else {
                ratio.addModelWrong();
            }
            if (filter.check(modelAndInterpolation)) {
                ratio.addModelAndInterpolationCorrect();
            }
            else {
                ratio.addModelAndInterpolationWrong();
            }
        }
        else { // No actual point
            if (filter.check(model)) {
                ratio.addModelWrong();
            }
            else {
                ratio.addModelCorrect();
            }
            if (filter.check(modelAndInterpolation)) {
                ratio.addModelAndInterpolationWrong();
            }
            else {
                ratio.addModelAndInterpolationCorrect();
            }
        }
    }

    private void tryAddHeadToStatistic(ISwimmingSkeleton actual,
                                       ISwimmingSkeleton model,
                                       ISwimmingSkeleton modelAndInterpolation,
                                       ISkeletonFilter filter) {
        tryAddRatioToStatistic(actual, model, modelAndInterpolation, _headRecognizeRatios, filter);
    }

    private void tryAddRightShoulderToStatistic(ISwimmingSkeleton actual,
                                                ISwimmingSkeleton model,
                                                ISwimmingSkeleton modelAndInterpolation,
                                                ISkeletonFilter filter) {
        tryAddRatioToStatistic(actual, model, modelAndInterpolation, _rightShoulderRecognizeRatios, filter);
    }

    private void tryAddRightElbowToStatistic(ISwimmingSkeleton actual,
                                             ISwimmingSkeleton model,
                                             ISwimmingSkeleton modelAndInterpolation,
                                             ISkeletonFilter filter) {
        tryAddRatioToStatistic(actual, model, modelAndInterpolation, _rightElbowRecognizeRatios, filter);
    }

    private void tryAddRightWristToStatistic(ISwimmingSkeleton actual,
                                             ISwimmingSkeleton model,
                                             ISwimmingSkeleton modelAndInterpolation,
                                             ISkeletonFilter filter) {
        tryAddRatioToStatistic(actual, model, modelAndInterpolation, _rightWristRecognizeRatios, filter);
    }

    private void tryAddLeftShoulderToStatistic(ISwimmingSkeleton actual,
                                               ISwimmingSkeleton model,
                                               ISwimmingSkeleton modelAndInterpolation,
                                               ISkeletonFilter filter) {
        tryAddRatioToStatistic(actual, model, modelAndInterpolation, _leftShoulderRecognizeRatios, filter);
    }

    private void tryAddLeftElbowToStatistic(ISwimmingSkeleton actual,
                                            ISwimmingSkeleton model,
                                            ISwimmingSkeleton modelAndInterpolation,
                                            ISkeletonFilter filter) {
        tryAddRatioToStatistic(actual, model, modelAndInterpolation, _leftElbowRecognizeRatios, filter);
    }

    private void tryAddLeftWristToStatistic(ISwimmingSkeleton actual,
                                            ISwimmingSkeleton model,
                                            ISwimmingSkeleton modelAndInterpolation,
                                            ISkeletonFilter filter) {
        tryAddRatioToStatistic(actual, model, modelAndInterpolation, _leftWristRecognizeRatios, filter);
    }

    @Override
    public double getHeadImprove() {
        return _headRecognizeRatios.getImprovement();
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
        return _headRecognizeRatios.getModelAndInterpolationCount();
    }

    @Override
    public int getHeadActual() {
        return _headRecognizeRatios.getActualCount();
    }

    @Override
    public double getRightShoulderImprove() {
        return _rightShoulderRecognizeRatios.getImprovement();
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
        return _rightShoulderRecognizeRatios.getModelAndInterpolationCount();
    }

    @Override
    public int getRightShoulderActual() {
        return _rightShoulderRecognizeRatios.getActualCount();
    }

    @Override
    public double getRightElbowImprove() {
        return _rightElbowRecognizeRatios.getImprovement();
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
        return _rightElbowRecognizeRatios.getModelAndInterpolationCount();
    }

    @Override
    public int getRightElbowActual() {
        return _rightElbowRecognizeRatios.getActualCount();
    }

    @Override
    public double getRightWristImprove() {
        return _rightWristRecognizeRatios.getImprovement();
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
        return _rightWristRecognizeRatios.getModelAndInterpolationCount();
    }

    @Override
    public int getRightWristActual() {
        return _rightWristRecognizeRatios.getActualCount();
    }

    @Override
    public double getLeftShoulderImprove() {
        return _leftShoulderRecognizeRatios.getImprovement();
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
        return _leftShoulderRecognizeRatios.getModelAndInterpolationCount();
    }

    @Override
    public int getLeftShoulderActual() {
        return _leftShoulderRecognizeRatios.getActualCount();
    }

    @Override
    public double getLeftElbowImprove() {
        return _leftElbowRecognizeRatios.getImprovement();
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
        return _leftElbowRecognizeRatios.getModelAndInterpolationCount();
    }

    @Override
    public int getLeftElbowActual() {
        return _leftElbowRecognizeRatios.getActualCount();
    }

    @Override
    public double getLeftWristImprove() {
        return _leftWristRecognizeRatios.getImprovement();
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
        return _leftWristRecognizeRatios.getModelAndInterpolationCount();
    }

    @Override
    public int getLeftWristActual() {
        return _leftWristRecognizeRatios.getActualCount();
    }
}
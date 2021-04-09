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
        evalStatistic(raw, modelSkeletons, modelAndInterpolationSkeletons);
    }

    private void initialize(int frames) {
        _headRecognizeRatios = new Ratios(frames);
        _rightShoulderRecognizeRatios = new Ratios(frames);
        _rightElbowRecognizeRatios = new Ratios(frames);
        _rightWristRecognizeRatios = new Ratios(frames);
        _leftShoulderRecognizeRatios = new Ratios(frames);
        _leftElbowRecognizeRatios = new Ratios(frames);
        _leftWristRecognizeRatios = new Ratios(frames);
    }

    private void evalStatistic(List<ISwimmingSkeleton> raw,
                               List<ISwimmingSkeleton> modelSkeletons,
                               List<ISwimmingSkeleton> modelAndInterpolationSkeletons) {
        // TODO: change flow if raw data is null
        if(raw.size() != modelSkeletons.size()) {
            throw new IllegalArgumentException("raw list size doesn't match to current list size");
        }
        initialize(raw.size());
        ISkeletonFilter headFilter = new HeadFilter();
        ISkeletonFilter leftElbowFilter = new LeftElbowFilter();
        ISkeletonFilter rightElbowFilter = new RightElbowFilter();
        ISkeletonFilter leftShoulderFilter = new LeftShoulderFilter();
        ISkeletonFilter rightShoulderFilter = new RightShoulderFilter();
        ISkeletonFilter leftWristFilter = new LeftWristFilter();
        ISkeletonFilter rightWristFilter = new RightWristFilter();

        for(int i=0; i<raw.size(); i++) {
            ISwimmingSkeleton actual = tryGetSkeleton(raw, i);
            ISwimmingSkeleton model = tryGetSkeleton(modelSkeletons, i);
            ISwimmingSkeleton modelInter = tryGetSkeleton(modelAndInterpolationSkeletons,i);
            tryAddRatioToStatistic(actual, model, modelInter, _headRecognizeRatios, headFilter);
            tryAddRatioToStatistic(actual, model, modelInter, _rightShoulderRecognizeRatios, rightShoulderFilter);
            tryAddRatioToStatistic(actual, model, modelInter, _rightElbowRecognizeRatios, rightElbowFilter);
            tryAddRatioToStatistic(actual, model, modelInter, _rightWristRecognizeRatios, rightWristFilter);
            tryAddRatioToStatistic(actual, model, modelInter, _leftShoulderRecognizeRatios, leftShoulderFilter);
            tryAddRatioToStatistic(actual, model, modelInter, _leftElbowRecognizeRatios, leftElbowFilter);
            tryAddRatioToStatistic(actual, model, modelInter, _leftWristRecognizeRatios, leftWristFilter);
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
        //true positive -> actual=true model=true
        if (filter.check(actual) && filter.check(model)) {
            ratio.addModelTP();
        }
        // true negative -> actual=false model=false
        else if(!filter.check(actual) && !filter.check(model)) {
            ratio.addModelTN();
        }
        // false positive -> actual=false model=true
        else if(!filter.check(actual) && filter.check(model)) {
            ratio.addModelFP();
        }
        // false negative -> actual=true model==false
        else if(filter.check(actual) && !filter.check(model)) {
            ratio.addModelFN();
        }

        // TODO add to modelAndInter
        // true positive -> actual=true modelAndInter=true
        // true negative -> actual=false modelAndInter=false
        // false positive -> actual=false modelAndInter=true
        // false negative -> actual=true modelAndInter=false
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
    public int getHeadModelTP() {
        return _headRecognizeRatios.getModelTP();
    }

    @Override
    public int getHeadModelTN() {
        return _headRecognizeRatios.getModelTN();
    }

    @Override
    public int getHeadModelFP() {
        return _headRecognizeRatios.getModelFP();
    }

    @Override
    public int getHeadModelFN() {
        return _headRecognizeRatios.getModelFN();
    }

    @Override
    public double getHeadRatioModelTP() {
        return _headRecognizeRatios.getRatioModelTP();
    }

    @Override
    public double getHeadRatioModelTN() {
        return _headRecognizeRatios.getRationModelTN();
    }

    @Override
    public double getHeadRatioModelFP() {
        return _headRecognizeRatios.getRationModelFP();
    }

    @Override
    public double getHeadRatioModelFN() {
        return _headRecognizeRatios.getRationModelFN();
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
    public int getRightShoulderModelTP() {
        return _rightShoulderRecognizeRatios.getModelTP();
    }

    @Override
    public int getRightShoulderModelTN() {
        return _rightShoulderRecognizeRatios.getModelTN();
    }

    @Override
    public int getRightShoulderModelFP() {
        return _rightShoulderRecognizeRatios.getModelFP();
    }

    @Override
    public int getRightShoulderModelFN() {
        return _rightShoulderRecognizeRatios.getModelFN();
    }

    @Override
    public double getRightShoulderRatioModelTP() {
        return _rightShoulderRecognizeRatios.getRatioModelTP();
    }

    @Override
    public double getRightShoulderRatioModelTN() {
        return _rightShoulderRecognizeRatios.getRationModelTN();
    }

    @Override
    public double getRightShoulderRatioModelFP() {
        return _rightShoulderRecognizeRatios.getRationModelFP();
    }

    @Override
    public double getRightShoulderRatioModelFN() {
        return _rightShoulderRecognizeRatios.getRationModelFN();
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
    public int getRightElbowModelTP() {
        return _rightElbowRecognizeRatios.getModelTP();
    }

    @Override
    public int getRightElbowModelTN() {
        return _rightElbowRecognizeRatios.getModelTN();
    }

    @Override
    public int getRightElbowModelFP() {
        return _rightElbowRecognizeRatios.getModelFP();
    }

    @Override
    public int getRightElbowModelFN() {
        return _rightElbowRecognizeRatios.getModelFN();
    }

    @Override
    public double getRightElbowRatioModelTP() {
        return _rightElbowRecognizeRatios.getRatioModelTP();
    }

    @Override
    public double getRightElbowRatioModelTN() {
        return _rightElbowRecognizeRatios.getRationModelTN();
    }

    @Override
    public double getRightElbowRatioModelFP() {
        return _rightElbowRecognizeRatios.getRationModelFP();
    }

    @Override
    public double getRightElbowRatioModelFN() {
        return _rightElbowRecognizeRatios.getRationModelFN();
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
    public int getRightWristModelTP() {
        return _rightWristRecognizeRatios.getModelTP();
    }

    @Override
    public int getRightWristModelTN() {
        return _rightWristRecognizeRatios.getModelTN();
    }

    @Override
    public int getRightWristModelFP() {
        return _rightWristRecognizeRatios.getModelFP();
    }

    @Override
    public int getRightWristModelFN() {
        return _rightWristRecognizeRatios.getModelFN();
    }

    @Override
    public double getRightWristRatioModelTP() {
        return _rightWristRecognizeRatios.getRatioModelTP();
    }

    @Override
    public double getRightWristRatioModelTN() {
        return _rightWristRecognizeRatios.getRationModelTN();
    }

    @Override
    public double getRightWristRatioModelFP() {
        return _rightWristRecognizeRatios.getRationModelFP();
    }

    @Override
    public double getRightWristRatioModelFN() {
        return _rightWristRecognizeRatios.getRationModelFN();
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
    public int getLeftShoulderModelTP() {
        return _leftShoulderRecognizeRatios.getModelTP();
    }

    @Override
    public int getLeftShoulderModelTN() {
        return _leftShoulderRecognizeRatios.getModelTN();
    }

    @Override
    public int getLeftShoulderModelFP() {
        return _leftShoulderRecognizeRatios.getModelFP();
    }

    @Override
    public int getLeftShoulderModelFN() {
        return _leftShoulderRecognizeRatios.getModelFN();
    }

    @Override
    public double getLeftShoulderRatioModelTP() {
        return _leftShoulderRecognizeRatios.getRatioModelTP();
    }

    @Override
    public double getLeftShoulderRationModelTN() {
        return _leftShoulderRecognizeRatios.getRationModelTN();
    }

    @Override
    public double getLeftShoulderRatioModelFP() {
        return _leftShoulderRecognizeRatios.getRationModelFP();
    }

    @Override
    public double getLeftShoulderRatioModelFN() {
        return _leftShoulderRecognizeRatios.getRationModelFN();
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
    public int getLeftElbowModelTP() {
        return _leftElbowRecognizeRatios.getModelTP();
    }

    @Override
    public int getLeftElbowModelTN() {
        return _leftElbowRecognizeRatios.getModelTN();
    }

    @Override
    public int getLeftElbowModelFP() {
        return _leftElbowRecognizeRatios.getModelFP();
    }

    @Override
    public int getLeftElbowModelFN() {
        return _leftElbowRecognizeRatios.getModelFN();
    }

    @Override
    public double getLeftElbowRatioModelTP() {
        return _leftElbowRecognizeRatios.getRatioModelTP();
    }

    @Override
    public double getLeftElbowRatioModelTN() {
        return _leftElbowRecognizeRatios.getRationModelTN();
    }

    @Override
    public double getLeftElbowRatioModelFP() {
        return _leftElbowRecognizeRatios.getRationModelFP();
    }

    @Override
    public double getLeftElbowRatioModelFN() {
        return _leftElbowRecognizeRatios.getRationModelFN();
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

    @Override
    public int getLeftWristModelTP() {
        return _leftWristRecognizeRatios.getModelTP();
    }

    @Override
    public int getLeftWristModelTN() {
        return _leftWristRecognizeRatios.getModelTN();
    }

    @Override
    public int getLeftWristModelFP() {
        return _leftWristRecognizeRatios.getModelFP();
    }

    @Override
    public int getLeftWristModelFN() {
        return _leftWristRecognizeRatios.getModelFN();
    }

    @Override
    public double getLeftWristRatioModelTP() {
        return _leftWristRecognizeRatios.getRatioModelTP();
    }

    @Override
    public double getLeftWristRatioModelTN() {
        return _leftWristRecognizeRatios.getRationModelTN();
    }

    @Override
    public double getLeftWristRatioModelFP() {
        return _leftWristRecognizeRatios.getRationModelFP();
    }

    @Override
    public double getLeftWristRatioModelFN() {
        return _leftWristRecognizeRatios.getRationModelFN();
    }
}
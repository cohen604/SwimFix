package DomainLogic.SwimmingErrorDetectors;

import Domain.Errors.Interfaces.SwimmingError;
import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import DomainLogic.SkeletonsValueFilters.LeftWristFilters.YLeftWristFilter;
import DomainLogic.SkeletonsValueFilters.RightWristFilters.YRightWristFilter;
import DomainLogic.SwimmingErrorDetectors.SkeletonDetoctors.ISkeletonErrorDetector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxDetector implements ISwimmingTimeErrorDetector {

    private List<ISkeletonErrorDetector> detectors;

    public MaxDetector(List<ISkeletonErrorDetector> detectors) {
        this.detectors = detectors;
    }

    @Override
    public Map<Integer, List<SwimmingError>> detect(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime periodTime) {
        Map<Integer, List<SwimmingError>> errors = new HashMap<>();
        detectRight(skeletons, periodTime.getRightTimes(), errors);
        detectLefts(skeletons, periodTime.getLeftTimes(), errors);
        return errors;
    }

    private void detectRight(List<ISwimmingSkeleton> skeletons,
                             List<IPeriodTime> rights,
                             Map<Integer, List<SwimmingError>> errorMap) {
        int tresholdMax = 4;
        ISkeletonValueFilter filter = new YRightWristFilter();
        detect(skeletons, rights, errorMap, tresholdMax, filter);
    }

    private void detectLefts(List<ISwimmingSkeleton> skeletons,
                             List<IPeriodTime> lefts,
                             Map<Integer, List<SwimmingError>> errorMap) {
        int tresholdMax = 4;
        ISkeletonValueFilter filter = new YLeftWristFilter();
        detect(skeletons, lefts, errorMap, tresholdMax, filter);
    }


    private void detect(List<ISwimmingSkeleton> skeletons,
                        List<IPeriodTime> periodTimes,
                        Map<Integer, List<SwimmingError>> errorMap,
                        int tresholdMax,
                        ISkeletonValueFilter filter) {
        for(IPeriodTime periodTime: periodTimes) {
            int frameIndex = findMaxFrame(skeletons, periodTime, filter);
            int index = frameIndex - tresholdMax;
            if(index <0) {
                index =0;
            }
            for(; index < frameIndex+tresholdMax || index < skeletons.size(); index++) {
                ISwimmingSkeleton skeleton = skeletons.get(index);
                for(ISkeletonErrorDetector detector: this.detectors) {
                    addToMap(errorMap, index, detector.detect(skeleton));
                }
            }
        }
    }

    private int findMaxFrame(List<ISwimmingSkeleton> skeletons,
                             IPeriodTime periodTime,
                             ISkeletonValueFilter filter) {
        double max = 0;
        double value;
        int output = 0;
        for(int i=periodTime.getStart(); i<periodTime.getEnd(); i++) {
            value = filter.filter(skeletons.get(i));
            if(max < value) {
                max = value;
                output = i;
            }
        }
        return output;
    }

    private void addToMap(Map<Integer, List<SwimmingError>> errorMap, int index, List<SwimmingError> newErrors) {
        if(newErrors!=null && !newErrors.isEmpty()) {
            if (errorMap.containsKey(index)) {
                List<SwimmingError> errors = errorMap.get(index);
                errors.addAll(newErrors);
            } else {
                errorMap.put(index, newErrors);
            }
        }
    }

}

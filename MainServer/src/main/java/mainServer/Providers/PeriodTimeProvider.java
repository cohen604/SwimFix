package mainServer.Providers;

import Domain.PeriodTimeData.IFactorySwimmingPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.PeriodTimeData.PeriodTime;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingSkeletonComposition.SwimmingSkeleton;
import DomainLogic.SkeletonFilters.ISkeletonFilter;
import DomainLogic.SkeletonFilters.LeftFilter;
import DomainLogic.SkeletonFilters.RightFilter;
import mainServer.Providers.Interfaces.IPeriodTimeProvider;

import java.util.LinkedList;
import java.util.List;

public class PeriodTimeProvider implements IPeriodTimeProvider {

    private IFactorySwimmingPeriodTime factorySwimmingPeriodTime;

    public PeriodTimeProvider(IFactorySwimmingPeriodTime factorySwimmingPeriodTime) {
        this.factorySwimmingPeriodTime = factorySwimmingPeriodTime;
    }

    @Override
    public ISwimmingPeriodTime analyzeTimes(List<ISwimmingSkeleton> skeletons) {
        List<PeriodTime> lefts = analyze(skeletons, new LeftFilter());
        List<PeriodTime> rights = analyze(skeletons, new RightFilter());
        return factorySwimmingPeriodTime.factory(rights, lefts);
    }

    @Override
    public List<ISwimmingSkeleton> correctSkeletons(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime times) {
        int tresholdPeriod = 5;
        List<PeriodTime> rights = times.getRightTimes();
        List<Integer> deleteRight = new LinkedList<>();
        for(int j =0; j<rights.size(); j++) {
            PeriodTime period = rights.get(j);
            if(period.getTimeLength() < tresholdPeriod) {
                for(int i=period.getStart(); i<period.getEnd(); i++) {
                    ISwimmingSkeleton skeleton = skeletons.get(i);
                    SwimmingSkeleton newSkeleton = new SwimmingSkeleton(skeleton);
                    newSkeleton.removeRightElbow();
                    newSkeleton.removeRightWrist();
                    skeletons.set(i, newSkeleton);
                }
                deleteRight.add(j);
            }
        }

        List<PeriodTime> lefts = times.getLeftTimes();
        List<Integer> deleteLeft = new LinkedList<>();
        for(int j =0; j<lefts.size(); j++) {
            PeriodTime period = lefts.get(j);
            if(period.getTimeLength() < tresholdPeriod) {
                for(int i=period.getStart(); i<period.getEnd(); i++) {
                    ISwimmingSkeleton skeleton = skeletons.get(i);
                    SwimmingSkeleton newSkeleton = new SwimmingSkeleton(skeleton);
                    newSkeleton.removeLeftElbow();
                    newSkeleton.removeLeftWrist();
                    skeletons.set(i, newSkeleton);
                }
                deleteLeft.add(j);
            }
        }
        int removed = 0;
        for(Integer i: deleteRight) {
            rights.remove(i - removed);
            removed++;
        }
        removed = 0;
        for(Integer i: deleteLeft) {
            lefts.remove(i - removed);
            removed++;
        }

        return skeletons;
    }

    private List<PeriodTime> analyze(List<ISwimmingSkeleton> skeletons, ISkeletonFilter filter) {
        List<PeriodTime> output = new LinkedList<>();
        int start = -1;
        for(int i=0; i < skeletons.size(); i++) {
            boolean check = filter.check(skeletons.get(i));
            if(check && start == -1) {
               start = i;
            }
            else if(!check && start!=-1) {
                output.add(new PeriodTime(start, i));
                start = -1;
            }
        }
        return output;
    }

}

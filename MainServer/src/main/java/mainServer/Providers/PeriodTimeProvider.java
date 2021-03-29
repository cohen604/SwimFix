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
        List<PeriodTime> rights = analyze(skeletons, new RightFilter());
        List<PeriodTime> lefts = analyze(skeletons, new LeftFilter());
        return factorySwimmingPeriodTime.factory(rights, lefts);
    }

    @Override
    public List<ISwimmingSkeleton> correctSkeletons(List<ISwimmingSkeleton> skeletons, ISwimmingPeriodTime times) {
        int tresholdPeriod = 6;
        List<PeriodTime> rights = times.getRightTimes();
        for(int j =0; j<rights.size(); j++) {
            PeriodTime period = rights.get(j);
            if(period.getTimeLength() < tresholdPeriod) {
                for(int i=period.getStart(); i<period.getEnd(); i++) {
                    ISwimmingSkeleton skeleton = skeletons.get(i);
                    SwimmingSkeleton newSkeleton = new SwimmingSkeleton(skeleton);
                    newSkeleton.removeRightElbow();
                    newSkeleton.removeRightWrist();
                    skeletons.set(i, newSkeleton);
                    System.out.println("correct skeleton by time period right "+ i);
                }
            }
        }

        List<PeriodTime> lefts = times.getLeftTimes();
        for(int j =0; j<lefts.size(); j++) {
            PeriodTime period = lefts.get(j);
            if(period.getTimeLength() < tresholdPeriod) {
                for(int i=period.getStart(); i<period.getEnd(); i++) {
                    ISwimmingSkeleton skeleton = skeletons.get(i);
                    SwimmingSkeleton newSkeleton = new SwimmingSkeleton(skeleton);
                    newSkeleton.removeLeftElbow();
                    newSkeleton.removeLeftWrist();
                    skeletons.set(i, newSkeleton);
                    System.out.println("correct skeleton by time left right "+ i);
                }
            }
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
        if(start!=-1) {
            output.add(new PeriodTime(start, skeletons.size()));
        }
        return output;
    }

}

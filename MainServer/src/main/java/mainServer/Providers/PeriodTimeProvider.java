package mainServer.Providers;

import Domain.PeriodTimeData.IFactorySwimmingPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.PeriodTimeData.PeriodTime;
import Domain.SwimmingData.ISwimmingSkeleton;
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

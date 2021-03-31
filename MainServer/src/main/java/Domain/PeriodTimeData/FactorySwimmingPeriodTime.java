package Domain.PeriodTimeData;

import java.util.List;

public class FactorySwimmingPeriodTime implements  IFactorySwimmingPeriodTime {

    @Override
    public ISwimmingPeriodTime factory(List<PeriodTime> rights, List<PeriodTime> lefts) {
        return new SwimmingPeriodTime(rights, lefts);
    }

}

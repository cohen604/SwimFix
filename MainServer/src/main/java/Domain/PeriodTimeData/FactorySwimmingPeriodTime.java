package Domain.PeriodTimeData;

import java.util.List;

public class FactorySwimmingPeriodTime implements  IFactorySwimmingPeriodTime {

    @Override
    public ISwimmingPeriodTime factory(List<IPeriodTime> rights, List<IPeriodTime> lefts) {
        return new SwimmingPeriodTime(rights, lefts);
    }

}

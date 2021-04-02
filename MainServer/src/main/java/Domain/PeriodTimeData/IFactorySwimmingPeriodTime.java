package Domain.PeriodTimeData;

import java.util.List;

public interface IFactorySwimmingPeriodTime {

    ISwimmingPeriodTime factory(List<IPeriodTime> rights, List<IPeriodTime> lefts);
}

package Domain.PeriodTimeData;

import java.util.List;

public interface IFactorySwimmingPeriodTime {

    ISwimmingPeriodTime factory(List<PeriodTime> rights, List<PeriodTime> lefts);
}

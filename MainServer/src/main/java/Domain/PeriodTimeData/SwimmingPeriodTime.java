package Domain.PeriodTimeData;

import java.util.List;

public class SwimmingPeriodTime implements ISwimmingPeriodTime {

    private List<IPeriodTime> rights;
    private List<IPeriodTime> lefts;

    public SwimmingPeriodTime(List<IPeriodTime> rights, List<IPeriodTime> lefts) {
        this.rights = rights;
        this.lefts = lefts;
    }

    @Override
    public List<IPeriodTime> getRightTimes() {
        return this.rights;
    }

    @Override
    public List<IPeriodTime> getLeftTimes() {
        return this.lefts;
    }

}

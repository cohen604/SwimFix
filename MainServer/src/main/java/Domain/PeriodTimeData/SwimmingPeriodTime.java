package Domain.PeriodTimeData;

import java.util.List;

public class SwimmingPeriodTime implements ISwimmingPeriodTime {

    private List<PeriodTime> rights;
    private List<PeriodTime> lefts;

    public SwimmingPeriodTime(List<PeriodTime> rights, List<PeriodTime> lefts) {
        this.rights = rights;
        this.lefts = lefts;
    }

    @Override
    public List<PeriodTime> getRightTimes() {
        return this.rights;
    }

    @Override
    public List<PeriodTime> getLeftTimes() {
        return this.lefts;
    }

}

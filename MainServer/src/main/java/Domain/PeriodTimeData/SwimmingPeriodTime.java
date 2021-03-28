package Domain.PeriodTimeData;

import java.util.List;

public class SwimmingPeriodTime implements ISwimmingPeriodTime {

    private List<PeriodTime> rights;
    private List<PeriodTime> lefts;

    public SwimmingPeriodTime(List<PeriodTime> rigths, List<PeriodTime> lefts) {
        this.lefts = lefts;
        this.rights = rigths;
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

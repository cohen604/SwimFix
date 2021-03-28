package Domain.PeriodTimeData;

public class PeriodTime {

    private int start;
    private int end;

    public PeriodTime(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}

package Domain.PeriodTimeData;

public class PeriodTime implements IPeriodTime{

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

    public int getTimeLength() {
        return end - start;
    }
}

package Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph;

public enum KeyPoint {
    HEAD(0),
    R_SHOULDER(3),
    R_ELBOW(6),
    R_WRIST(9),
    L_SHOULDER(12),
    L_ELBOW(15),
    L_WRIST(18);

    public final int value;

    KeyPoint(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static KeyPoint toOrigin(int n) {
        for(KeyPoint o : values())
            if(o.getValue() == n)
                return o;
        return null;
    }
}

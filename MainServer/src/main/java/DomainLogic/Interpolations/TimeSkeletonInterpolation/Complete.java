package DomainLogic.Interpolations.TimeSkeletonInterpolation;

public class Complete {
    private boolean head;
    private boolean rightShoulder;
    private boolean rightElbow;
    private boolean rightWrist;
    private boolean leftShoulder;
    private boolean leftElbow;
    private boolean leftWrist;

    public Complete() {
        this.head = false;
        this.rightShoulder = false;
        this.rightElbow = false;
        this.rightWrist = false;
        this.leftShoulder = false;
        this.leftElbow = false;
        this.leftWrist = false;
    }

    public boolean isHead() {
        return head;
    }

    public void setHead(boolean head) {
        this.head = head;
    }

    public boolean isRightShoulder() {
        return rightShoulder;
    }

    public void setRightShoulder(boolean rightShoulder) {
        this.rightShoulder = rightShoulder;
    }

    public boolean isRightElbow() {
        return rightElbow;
    }

    public void setRightElbow(boolean rightElbow) {
        this.rightElbow = rightElbow;
    }

    public boolean isRightWrist() {
        return rightWrist;
    }

    public void setRightWrist(boolean rightWrist) {
        this.rightWrist = rightWrist;
    }

    public boolean isLeftShoulder() {
        return leftShoulder;
    }

    public void setLeftShoulder(boolean leftShoulder) {
        this.leftShoulder = leftShoulder;
    }

    public boolean isLeftElbow() {
        return leftElbow;
    }

    public void setLeftElbow(boolean leftElbow) {
        this.leftElbow = leftElbow;
    }

    public boolean isLeftWrist() {
        return leftWrist;
    }

    public void setLeftWrist(boolean leftWrist) {
        this.leftWrist = leftWrist;
    }
}

package Domain.Streaming;

public class SwimmingTag {

    private int neckX;
    private int neckY;
    //right body positions
    private int rightSholderX;
    private int rightSholderY;
    private int rightElbowX;
    private int rightElbowY;
    private int rightWristX;
    private int rightWristY;
    //left body positions
    private int leftSholderX;
    private int leftSholderY;
    private int leftElbowX;
    private int leftElbowY;
    private int leftWristX;
    private int leftWristY;

    public SwimmingTag(int neckX, int neckY, int rightSholderX, int rightSholderY, int rightElbowX, int rightElbowY,
                       int rightWristX, int rightWristY, int leftSholderX, int leftSholderY, int leftElbowX,
                       int leftElbowY, int leftWristX, int leftWristY) {
        this.neckX = neckX;
        this.neckY = neckY;
        this.rightSholderX = rightSholderX;
        this.rightSholderY = rightSholderY;
        this.rightElbowX = rightElbowX;
        this.rightElbowY = rightElbowY;
        this.rightWristX = rightWristX;
        this.rightWristY = rightWristY;
        this.leftSholderX = leftSholderX;
        this.leftSholderY = leftSholderY;
        this.leftElbowX = leftElbowX;
        this.leftElbowY = leftElbowY;
        this.leftWristX = leftWristX;
        this.leftWristY = leftWristY;
    }
}

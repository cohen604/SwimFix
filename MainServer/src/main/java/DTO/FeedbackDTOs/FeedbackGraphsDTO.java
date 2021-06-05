package DTO.FeedbackDTOs;
import java.util.Map;

public class FeedbackGraphsDTO {

    private int numberOfFrames;
    private Map<Integer, PointDTO> head;
    private Map<Integer, PointDTO> rightShoulder;
    private Map<Integer, PointDTO> rightElbow;
    private Map<Integer, PointDTO> rightWrist;
    private Map<Integer, PointDTO> leftShoulder;
    private Map<Integer, PointDTO> leftElbow;
    private Map<Integer, PointDTO> leftWrist;
    private Map<Integer, Integer> errors;

    public FeedbackGraphsDTO(int numberOfFrames,
                             Map<Integer, PointDTO> head,
                             Map<Integer, PointDTO> rightShoulder,
                             Map<Integer, PointDTO> rightElbow,
                             Map<Integer, PointDTO> rightWrist,
                             Map<Integer, PointDTO> leftShoulder,
                             Map<Integer, PointDTO> leftElbow,
                             Map<Integer, PointDTO> leftWrist,
                             Map<Integer, Integer> errors) {
        this.numberOfFrames = numberOfFrames;
        this.head = head;
        this.rightShoulder = rightShoulder;
        this.rightElbow = rightElbow;
        this.rightWrist = rightWrist;
        this.leftShoulder = leftShoulder;
        this.leftElbow = leftElbow;
        this.leftWrist = leftWrist;
        this.errors = errors;
    }

    public int getNumberOfFrames() {
        return numberOfFrames;
    }

    public void setNumberOfFrames(int numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
    }

    public Map<Integer, PointDTO> getHead() {
        return head;
    }

    public void setHead(Map<Integer, PointDTO> head) {
        this.head = head;
    }

    public Map<Integer, PointDTO> getRightShoulder() {
        return rightShoulder;
    }

    public void setRightShoulder(Map<Integer, PointDTO> rightShoulder) {
        this.rightShoulder = rightShoulder;
    }

    public Map<Integer, PointDTO> getRightElbow() {
        return rightElbow;
    }

    public void setRightElbow(Map<Integer, PointDTO> rightElbow) {
        this.rightElbow = rightElbow;
    }

    public Map<Integer, PointDTO> getRightWrist() {
        return rightWrist;
    }

    public void setRightWrist(Map<Integer, PointDTO> rightWrist) {
        this.rightWrist = rightWrist;
    }

    public Map<Integer, PointDTO> getLeftShoulder() {
        return leftShoulder;
    }

    public void setLeftShoulder(Map<Integer, PointDTO> leftShoulder) {
        this.leftShoulder = leftShoulder;
    }

    public Map<Integer, PointDTO> getLeftElbow() {
        return leftElbow;
    }

    public void setLeftElbow(Map<Integer, PointDTO> leftElbow) {
        this.leftElbow = leftElbow;
    }

    public Map<Integer, PointDTO> getLeftWrist() {
        return leftWrist;
    }

    public void setLeftWrist(Map<Integer, PointDTO> leftWrist) {
        this.leftWrist = leftWrist;
    }

    public Map<Integer, Integer> getErrors() {
        return errors;
    }

    public void setErrors(Map<Integer, Integer> errors) {
        this.errors = errors;
    }
}

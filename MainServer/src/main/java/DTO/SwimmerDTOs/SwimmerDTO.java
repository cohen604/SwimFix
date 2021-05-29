package DTO.SwimmerDTOs;

public class SwimmerDTO {

    private String email;
    private int feedbacksCounter;

    public SwimmerDTO(String email, int feedbacksCounter) {
        this.email = email;
        this.feedbacksCounter = feedbacksCounter;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFeedbacksCounter() {
        return feedbacksCounter;
    }

    public void setFeedbacksCounter(int feedbacksCounter) {
        this.feedbacksCounter = feedbacksCounter;
    }
}

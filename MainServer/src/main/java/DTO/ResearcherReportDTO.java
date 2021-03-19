package DTO;

public class ResearcherReportDTO {

    private String videoLink;
    private String csvLink;
    private String pdfLink;

    public ResearcherReportDTO(String videolink, String csvLink, String pdfLink) {
        if(videolink != null) {
            this.videoLink = videolink.replace('\\', '/');
        }
        if(csvLink != null) {
            this.csvLink = csvLink.replace('\\','/');
        }
        if(pdfLink != null) {
            this.pdfLink = pdfLink.replace('\\','/');
        }
    }
}

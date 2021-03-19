package DTO;

public class ResearcherReportDTO {

    private String _videolink;
    private String _csvLink;
    private String _pdfLink;

    public ResearcherReportDTO(String videolink, String csvLink, String pdfLink) {
        this._videolink = videolink;
        this._csvLink = csvLink;
        this._pdfLink = pdfLink;
    }
}

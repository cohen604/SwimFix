class ResearcherReport {

  String videoLink;
  String csvLink;
  String pdfLink;

  ResearcherReport(String videoLink, String csvLink, String pdfLink) {
    this.videoLink = videoLink;
    this.csvLink = csvLink;
    this.pdfLink = pdfLink;
  }

  static ResearcherReport factory(Map map) {
    return new ResearcherReport(
      map['videoLink'],
      map['csvLink'],
      map['pdfLink'],
    );
  }
}
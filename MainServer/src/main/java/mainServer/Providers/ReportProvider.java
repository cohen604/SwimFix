package mainServer.Providers;

import Domain.Errors.Interfaces.SwimmingError;
import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.StatisticsData.IStatistic;
import DomainLogic.PdfDrawing.IPdfDrawer;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.HeadFilters.*;
import DomainLogic.SkeletonsValueFilters.RightShoulderFilters.*;
import DomainLogic.SkeletonsValueFilters.RightElbowFilters.*;
import DomainLogic.SkeletonsValueFilters.RightWristFilters.*;
import DomainLogic.SkeletonsValueFilters.LeftShoulderFilters.*;
import DomainLogic.SkeletonsValueFilters.LeftElbowFilters.*;
import DomainLogic.SkeletonsValueFilters.LeftWristFilters.*;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mainServer.Providers.Interfaces.IReportProvider;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ReportProvider implements IReportProvider {

    private IPdfDrawer _graphDrawer;

    public ReportProvider(IPdfDrawer graphDrawer) {
        _graphDrawer = graphDrawer;
    }


    private String generateName(String folderPath, String fileType, LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return folderPath + "\\" + time.format(formatter) + fileType;
    }

    @Override
    public String generateReport(
            List<ISwimmingSkeleton> raw,
            List<ISwimmingSkeleton> model,
            List<ISwimmingSkeleton> modelAndInterpolation,
            String pdfFolderPath,
            IStatistic statistic,
            ISwimmingPeriodTime periodTime,
            Map<Integer, List<SwimmingError>> errors) {

        try {
            LocalDateTime time = LocalDateTime.now();
            String pdfName = generateName(pdfFolderPath, ".pdf", time);
            // create the pdf file
            Document document = new Document();
            FileOutputStream stream = new FileOutputStream(pdfName);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, stream);
            // open document
            document.open();
            // write meta on the file
            addMeta(document);
            addSummary(document, statistic, periodTime, modelAndInterpolation.size(), errors);
            addHeadGraphs(document, pdfWriter, raw, model, modelAndInterpolation, statistic);
            addRightShoulderGraphs(document, pdfWriter, raw, model, modelAndInterpolation, statistic);
            addRightElbowGraphs(document, pdfWriter, raw, model, modelAndInterpolation, statistic);
            addRightWristGraphs(document, pdfWriter, raw, model, modelAndInterpolation, statistic);
            addLeftShoulderGraphs(document, pdfWriter, raw, model, modelAndInterpolation, statistic);
            addLeftElbowGraphs(document, pdfWriter, raw, model, modelAndInterpolation, statistic);
            addLeftWristGraphs(document, pdfWriter, raw, model, modelAndInterpolation, statistic);

            document.close();
            return pdfName;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addMeta(Document document) {
        document.addTitle("Report");
        document.addAuthor("Swim Analytics");
        document.addCreator("Swim Analytics");
        document.addCreationDate();
    }

    private void addSummary(Document document,
                            IStatistic statistic,
                            ISwimmingPeriodTime periodTime,
                            int size,
                            Map<Integer, List<SwimmingError>> errors) throws DocumentException {
        document.newPage();
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(new Font(Font.FontFamily.HELVETICA,24));
        paragraph.add("Report Summary");
        paragraph.add("\n");
        document.add(paragraph);
        addPercentSummary(document, statistic);
        addTrueFalseSummary(document, statistic);
        document.newPage();
        addTimePeriod(document, periodTime);
        addSwimmingErrorSummary(document, size, errors);
    }

    private void addPercentSummary(Document document,
                                   IStatistic statistic) throws DocumentException {
        PdfPTable table = new PdfPTable( 5);
        addRowToPdfTable(table, "Point", "Model", "Model & Interpolation", "Actual", "Improvement");
        addRowToTable(table, "Head",
                statistic.getHeadModel(),
                statistic.getHeadRatioModel(),
                statistic.getHeadModelAndInterpolation(),
                statistic.getHeadRatioModelAndInterpolation(),
                statistic.getHeadActual(),
                statistic.getHeadImprove());
        addRowToTable(table, "Right shoulder",
                statistic.getRightShoulderModel(),
                statistic.getRightShoulderRatioModel(),
                statistic.getRightShoulderModelAndInterpolation(),
                statistic.getRightShoulderRatioModelAndInterpolation(),
                statistic.getRightShoulderActual(),
                statistic.getRightShoulderImprove());
        addRowToTable(table, "Right elbow",
                statistic.getRightElbowModel(),
                statistic.getRightElbowRatioModel(),
                statistic.getRightElbowModelAndInterpolation(),
                statistic.getRightElbowRatioModelAndInterpolation(),
                statistic.getRightElbowActual(),
                statistic.getRightElbowImprove());
        addRowToTable(table, "Right wrist",
                statistic.getRightWristModel(),
                statistic.getRightWristRatioModel(),
                statistic.getRightWristModelAndInterpolation(),
                statistic.getRightWristRatioModelAndInterpolation(),
                statistic.getRightWristActual(),
                statistic.getRightWristImprove());
        addRowToTable(table, "Left shoulder",
                statistic.getLeftShoulderModel(),
                statistic.getLeftShoulderRatioModel(),
                statistic.getLeftShoulderModelAndInterpolation(),
                statistic.getLeftShoulderRatioModelAndInterpolation(),
                statistic.getLeftShoulderActual(),
                statistic.getLeftShoulderImprove());
        addRowToTable(table, "Left elbow",
                statistic.getLeftElbowModel(),
                statistic.getLeftElbowRatioModel(),
                statistic.getLeftElbowModelAndInterpolation(),
                statistic.getLeftElbowRatioModelAndInterpolation(),
                statistic.getLeftElbowActual(),
                statistic.getLeftElbowImprove());
        addRowToTable(table, "Left wrist",
                statistic.getLeftWristModel(),
                statistic.getLeftWristRatioModel(),
                statistic.getLeftWristModelAndInterpolation(),
                statistic.getLeftWristRatioModelAndInterpolation(),
                statistic.getLeftWristActual(),
                statistic.getLeftWristImprove());
        Paragraph paragraph = new Paragraph("Skeletons Summary");
        paragraph.add(table);
        document.add(paragraph);
    }

    private void addTrueFalseSummary(Document document, IStatistic statistic) {
        PdfPTable table = new PdfPTable( 5);
        addRowToPdfTable(table, "Point", "Model Correct", "Model Wrong",
                "Model & Interpolation Correct", "Model & Interpolation Wrong");
        addRowToTable(table, "Head",
                statistic.getHeadModelCorrect(),
                statistic.getHeadModelWrong(),
                statistic.getHeadModelAndInterCorrect(),
                statistic.getHeadModelAndInterWrong());
        addRowToTable(table, "Right shoulder",
                statistic.getRightShoulderModelCorrect(),
                statistic.getRightShoulderModelWrong(),
                statistic.getRightShoulderModelAndInterCorrect(),
                statistic.getRightShoulderModelAndInterWrong());
        addRowToTable(table, "Right elbow",
                statistic.getRightElbowModelCorrect(),
                statistic.getRightElbowModelWrong(),
                statistic.getRightElbowModelAndInterCorrect(),
                statistic.getRightElbowModelAndInterWrong());
        addRowToTable(table, "Right wrist",
                statistic.getRightWristModelCorrect(),
                statistic.getRightWristModelWrong(),
                statistic.getRightWristModelAndInterCorrect(),
                statistic.getRightWristModelAndInterWrong());
        addRowToTable(table, "Left shoulder",
                statistic.getLeftShoulderModelCorrect(),
                statistic.getLeftShoulderModelWrong(),
                statistic.getLeftShoulderModelAndInterCorrect(),
                statistic.getLeftShoulderModelAndInterWrong());
        addRowToTable(table, "Left elbow",
                statistic.getLeftElbowModelCorrect(),
                statistic.getLeftElbowModelWrong(),
                statistic.getLeftElbowModelAndInterCorrect(),
                statistic.getLeftElbowModelAndInterWrong());
        addRowToTable(table, "Left wrist",
                statistic.getLeftWristModelCorrect(),
                statistic.getLeftWristModelWrong(),
                statistic.getLeftWristModelAndInterCorrect(),
                statistic.getLeftWristModelAndInterWrong());

    }

    private void addTimePeriod(Document document,
                               ISwimmingPeriodTime periodTime) throws DocumentException {
        Paragraph paragraph = new Paragraph("Time Period Summary");
        List<IPeriodTime> rights = periodTime.getRightTimes();
        List<IPeriodTime> lefts = periodTime.getLeftTimes();
        PdfPTable table = new PdfPTable(4);
        addRowToPdfTable(table, "Category", "Start frame", "End frame", "Length (Frames)");
        int indexR = 0;
        int indexL = 0;
        while(indexR < rights.size() && indexL < lefts.size()) {
            IPeriodTime rp = rights.get(indexR);
            IPeriodTime lp = lefts.get(indexL);
            if(rp.getStart() < lp.getStart()) {
                addRowToTable(table, "right", rp.getStart(), rp.getEnd(), rp.getTimeLength());
                indexR++;
            }
            else {
                addRowToTable(table, "left", lp.getStart(), lp.getEnd(), lp.getTimeLength());
                indexL++;
            }
        }
        while(indexR < rights.size()) {
            IPeriodTime rp = rights.get(indexR);
            addRowToTable(table, "right", rp.getStart(), rp.getEnd(), rp.getTimeLength());
            indexR++;
        }
        while(indexL < lefts.size()) {
            IPeriodTime lp = lefts.get(indexL);
            addRowToTable(table, "left", lp.getStart(), lp.getEnd(), lp.getTimeLength());
            indexL++;
        }
        paragraph.add(table);
        document.add(paragraph);
    }

    private void addSwimmingErrorSummary(Document document,
                                         int size,
                                         Map<Integer, List<SwimmingError>> errors) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(new Font(Font.FontFamily.HELVETICA,12));
        paragraph.add("Error Summary\n");
        PdfPTable table = new PdfPTable(2);
        table.addCell("Frame");
        table.addCell("Errors");
        for(int i=0; i<size; i++) {
            if(errors.containsKey(i)) {
                table.addCell(String.valueOf(i));
                String errorsString = "";
                for(SwimmingError error: errors.get(i)) {
                    errorsString += error.getTag() + ", ";
                }
                table.addCell(errorsString);
            }
        }
        paragraph.add(table);
        document.add(paragraph);
    }

    //TODO refactor this to class
    private void addRowToPdfTable(PdfPTable table, String c1, String c2, String c3, String c4) {
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
    }

    //TODO refactor this to class
    private void addRowToPdfTable(PdfPTable table, String c1, String c2, String c3, String c4, String c5) {
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
    }

    //TODO refactor this to class
    private void addRowToTable(PdfPTable table, String name, int start, int end, int length) {
        table.addCell(name);
        table.addCell(String.valueOf(start));
        table.addCell(String.valueOf(end));
        table.addCell(String.valueOf(length));
    }

    //TODO refactor this to class
    private void addRowToTable(PdfPTable table, String name,
                               int count1, double precent1,
                               int count2, double precent2,
                               int actual,
                               double improvement) {
        table.addCell(name);
        table.addCell(getCountAndPrecent(count1, precent1));
        table.addCell(getCountAndPrecent(count2, precent2));
        table.addCell(String.valueOf(actual));
        table.addCell(improvement + "%");
    }

    //TODO refactor this to class
    private void addRowToTable(PdfPTable table, String name,
                               int count1, int count2, int count3, int count4) {
        table.addCell(name);
        table.addCell(String.valueOf(count1));
        table.addCell(String.valueOf(count2));
        table.addCell(String.valueOf(count3));
        table.addCell(String.valueOf(count4));
    }

    private String getCountAndPrecent( int count, double precent) {
        return count +" (" + precent + "%)";
    }

    private void addHeadGraphs(Document document,
                               PdfWriter pdfWriter,
                               List<ISwimmingSkeleton> raw,
                               List<ISwimmingSkeleton> model,
                               List<ISwimmingSkeleton> modelAndInterpolation,
                               IStatistic statistic) {
        document.newPage();
        String subject = "Head";
        ISkeletonValueFilter xFilter = new XHeadFilter();
        ISkeletonValueFilter yFilter = new YHeadFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, model, modelAndInterpolation, subject, xFilter , yFilter,
                statistic.getHeadRatioModel(),
                statistic.getHeadRatioModelAndInterpolation(),
                statistic.getHeadActual(),
                statistic.getHeadModelAndInterpolation(),
                statistic.getHeadModel());
    }

    private void addRightShoulderGraphs(Document document,
                                        PdfWriter pdfWriter,
                                        List<ISwimmingSkeleton> raw,
                                        List<ISwimmingSkeleton> model,
                                        List<ISwimmingSkeleton> modelAndInterpolation,
                                        IStatistic statistic) {
        document.newPage();
        String subject = "Right shoulder";
        ISkeletonValueFilter xFilter = new XRightShoulderFilter();
        ISkeletonValueFilter yFilter = new YRightShoulderFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, model, modelAndInterpolation, subject, xFilter , yFilter,
                statistic.getRightShoulderRatioModel(),
                statistic.getRightShoulderRatioModelAndInterpolation(),
                statistic.getRightShoulderActual(),
                statistic.getRightShoulderModelAndInterpolation(),
                statistic.getRightShoulderModel());
    }

    private void addRightElbowGraphs(Document document,
                                     PdfWriter pdfWriter,
                                     List<ISwimmingSkeleton> raw,
                                     List<ISwimmingSkeleton> model,
                                     List<ISwimmingSkeleton> modelAndInterpolation,
                                     IStatistic statistic) {
        document.newPage();
        String subject = "Right elbow";
        ISkeletonValueFilter xFilter = new XRightElbowFilter();
        ISkeletonValueFilter yFilter = new YRightElbowFilter();
        _graphDrawer.drawGraphs(pdfWriter,raw, model, modelAndInterpolation, subject, xFilter , yFilter,
                statistic.getRightElbowRatioModel(),
                statistic.getRightElbowRatioModelAndInterpolation(),
                statistic.getRightElbowActual(),
                statistic.getRightElbowModelAndInterpolation(),
                statistic.getRightElbowModel());
    }

    private void addRightWristGraphs(Document document,
                                     PdfWriter pdfWriter,
                                     List<ISwimmingSkeleton> raw,
                                     List<ISwimmingSkeleton> model,
                                     List<ISwimmingSkeleton> modelAndInterpolation,
                                     IStatistic statistic) {
        document.newPage();
        String subject = "Right wrist";
        ISkeletonValueFilter xFilter = new XRightWristFilter();
        ISkeletonValueFilter yFilter = new YRightWristFilter();
        _graphDrawer.drawGraphs(pdfWriter,raw, model, modelAndInterpolation, subject, xFilter , yFilter,
                statistic.getRightWristRatioModel(),
                statistic.getRightWristRatioModelAndInterpolation(),
                statistic.getRightWristActual(),
                statistic.getRightWristModelAndInterpolation(),
                statistic.getRightWristModel());
    }

    private void addLeftShoulderGraphs(Document document,
                                       PdfWriter pdfWriter,
                                       List<ISwimmingSkeleton> raw,
                                       List<ISwimmingSkeleton> model,
                                       List<ISwimmingSkeleton> modelAndInterpolation,
                                       IStatistic statistic) {
        document.newPage();
        String subject = "Left shoulder";
        ISkeletonValueFilter xFilter = new XLeftShoulderFilter();
        ISkeletonValueFilter yFilter = new YLeftShoulderFilter();
        _graphDrawer.drawGraphs(pdfWriter,raw, model, modelAndInterpolation, subject, xFilter , yFilter,
                statistic.getLeftShoulderRatioModel(),
                statistic.getLeftShoulderRatioModelAndInterpolation(),
                statistic.getLeftShoulderActual(),
                statistic.getLeftShoulderModelAndInterpolation(),
                statistic.getLeftShoulderModel());
    }

    private void addLeftElbowGraphs(Document document,
                                    PdfWriter pdfWriter,
                                    List<ISwimmingSkeleton> raw,
                                    List<ISwimmingSkeleton> model,
                                    List<ISwimmingSkeleton> modelAndInterpolation,
                                    IStatistic statistic) {
        document.newPage();
        String subject = "Left elbow";
        ISkeletonValueFilter xFilter = new XLeftElbowFilter();
        ISkeletonValueFilter yFilter = new YLeftElbowFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, model, modelAndInterpolation, subject, xFilter , yFilter,
                statistic.getLeftElbowRatioModel(),
                statistic.getLeftElbowRatioModelAndInterpolation(),
                statistic.getLeftElbowActual(),
                statistic.getLeftElbowModelAndInterpolation(),
                statistic.getLeftElbowModel());
    }

    private void addLeftWristGraphs(Document document,
                                    PdfWriter pdfWriter,
                                    List<ISwimmingSkeleton> raw,
                                    List<ISwimmingSkeleton> model,
                                    List<ISwimmingSkeleton> modelAndInterpolation,
                                    IStatistic statistic) {
        document.newPage();
        String subject = "Left wrist";
        ISkeletonValueFilter xFilter = new XLeftWristFilter();
        ISkeletonValueFilter yFilter = new YLeftWristFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, model, modelAndInterpolation, subject, xFilter , yFilter,
                statistic.getLeftWristRatioModel(),
                statistic.getLeftWristRatioModelAndInterpolation(),
                statistic.getLeftWristActual(),
                statistic.getLeftWristModelAndInterpolation(),
                statistic.getLeftWristModel());
    }



}
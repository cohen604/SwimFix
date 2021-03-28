package mainServer.Providers;

import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.PeriodTimeData.PeriodTime;
import Domain.StatisticsData.IStatistic;
import DomainLogic.PdfDrawing.IGraphDrawer;
import Domain.SwimmingData.ISwimmingSkeleton;
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

public class ReportProvider implements IReportProvider {

    private IGraphDrawer _graphDrawer;

    public ReportProvider(IGraphDrawer graphDrawer) {
        _graphDrawer = graphDrawer;
    }


    private String generateName(String folderPath, String fileType, LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return folderPath + "\\" + time.format(formatter) + fileType;
    }

    @Override
    public String generateReport(
            List<ISwimmingSkeleton> raw,
            List<ISwimmingSkeleton> current,
            String pdfFolderPath,
            IStatistic statistic,
            ISwimmingPeriodTime periodTime) {

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
            addTimePeriod(document, periodTime);
            addHeadGraphs(document, pdfWriter, raw, current, statistic);
            addRightShoulderGraphs(document, pdfWriter, raw, current, statistic);
            addRightElbowGraphs(document, pdfWriter, raw, current, statistic);
            addRightWristGraphs(document, pdfWriter, raw, current, statistic);
            addLeftShoulderGraphs(document, pdfWriter, raw, current, statistic);
            addLeftElbowGraphs(document, pdfWriter, raw, current, statistic);
            addLeftWristGraphs(document, pdfWriter, raw, current, statistic);

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

    private void addTimePeriod(Document document,
                               ISwimmingPeriodTime periodTime) throws DocumentException {
        document.newPage();
        String text = "Time Period Summary";
        Paragraph paragraph = new Paragraph(text);
        List<PeriodTime> rights = periodTime.getRightTimes();
        List<PeriodTime> lefts = periodTime.getLeftTimes();
        PdfPTable table = new PdfPTable(4);
        table.addCell("Category");
        table.addCell("Start frame");
        table.addCell("End frame");
        table.addCell("Length");
        int indexR = 0;
        int indexL = 0;
        while(indexR < rights.size() && indexL < lefts.size()) {
            PeriodTime rp = rights.get(indexR);
            PeriodTime lp = lefts.get(indexL);
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
            PeriodTime rp = rights.get(indexR);
            addRowToTable(table, "right", rp.getStart(), rp.getEnd(), rp.getTimeLength());
            indexR++;
        }
        while(indexL < lefts.size()) {
            PeriodTime lp = lefts.get(indexL);
            addRowToTable(table, "left", lp.getStart(), lp.getEnd(), lp.getTimeLength());
            indexL++;
        }
        paragraph.add(table);
        document.add(paragraph);
    }

    private void addRowToTable(PdfPTable table, String name, int start, int end, int length) {
        table.addCell(name);
        table.addCell(String.valueOf(start));
        table.addCell(String.valueOf(end));
        table.addCell(String.valueOf(length));
    }

    private void addHeadGraphs(Document document,
                               PdfWriter pdfWriter,
                               List<ISwimmingSkeleton> raw,
                               List<ISwimmingSkeleton> current,
                               IStatistic statistic) {
        document.newPage();
        String subject = "Head";
        ISkeletonValueFilter xFilter = new XHeadFilter();
        ISkeletonValueFilter yFilter = new YHeadFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getHeadRecognitionPercent(),
                statistic.getHeadExpected(),
                statistic.getHeadActual());
    }

    private void addRightShoulderGraphs(Document document,
                                        PdfWriter pdfWriter,
                                        List<ISwimmingSkeleton> raw,
                                        List<ISwimmingSkeleton> current,
                                        IStatistic statistic) {
        document.newPage();
        String subject = "Right shoulder";
        ISkeletonValueFilter xFilter = new XRightShoulderFilter();
        ISkeletonValueFilter yFilter = new YRightShoulderFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getRightShoulderRecognitionPercent(),
                statistic.getRightShoulderExpected(),
                statistic.getRightShoulderActual());
    }

    private void addRightElbowGraphs(Document document,
                                     PdfWriter pdfWriter,
                                     List<ISwimmingSkeleton> raw,
                                     List<ISwimmingSkeleton> current,
                                     IStatistic statistic) {
        document.newPage();
        String subject = "Right elbow";
        ISkeletonValueFilter xFilter = new XRightElbowFilter();
        ISkeletonValueFilter yFilter = new YRightElbowFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getRightElbowRecognitionPercent(),
                statistic.getRightElbowExpected(),
                statistic.getRightElbowActual());
    }

    private void addRightWristGraphs(Document document,
                                     PdfWriter pdfWriter,
                                     List<ISwimmingSkeleton> raw,
                                     List<ISwimmingSkeleton> current,
                                     IStatistic statistic) {
        document.newPage();
        String subject = "Right wrist";
        ISkeletonValueFilter xFilter = new XRightWristFilter();
        ISkeletonValueFilter yFilter = new YRightWristFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getRightWristRecognitionPercent(),
                statistic.getRightWristExpected(),
                statistic.getRightWristActual());
    }

    private void addLeftShoulderGraphs(Document document,
                                       PdfWriter pdfWriter,
                                       List<ISwimmingSkeleton> raw,
                                       List<ISwimmingSkeleton> current,
                                       IStatistic statistic) {
        document.newPage();
        String subject = "Left shoulder";
        ISkeletonValueFilter xFilter = new XLeftShoulderFilter();
        ISkeletonValueFilter yFilter = new YLeftShoulderFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getLeftShoulderRecognitionPercent(),
                statistic.getLeftShoulderExpected(),
                statistic.getLeftShoulderActual());
    }

    private void addLeftElbowGraphs(Document document,
                                    PdfWriter pdfWriter,
                                    List<ISwimmingSkeleton> raw,
                                    List<ISwimmingSkeleton> current,
                                    IStatistic statistic) {
        document.newPage();
        String subject = "Left elbow";
        ISkeletonValueFilter xFilter = new XLeftElbowFilter();
        ISkeletonValueFilter yFilter = new YLeftElbowFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getLeftElbowRecognitionPercent(),
                statistic.getLeftElbowExpected(),
                statistic.getLeftElbowActual());
    }

    private void addLeftWristGraphs(Document document,
                                    PdfWriter pdfWriter,
                                    List<ISwimmingSkeleton> raw,
                                    List<ISwimmingSkeleton> current,
                                    IStatistic statistic) {
        document.newPage();
        String subject = "Left wrist";
        ISkeletonValueFilter xFilter = new XLeftWristFilter();
        ISkeletonValueFilter yFilter = new YLeftWristFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getLeftWristRecognitionPercent(),
                statistic.getLeftWristExpected(),
                statistic.getLeftWristActual());
    }



}
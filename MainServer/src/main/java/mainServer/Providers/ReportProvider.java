package mainServer.Providers;

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
            IStatistic statistic) {

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

            addHeadGraphs(pdfWriter, raw, current, statistic);

            document.newPage();
            addRightShoulderGraphs(pdfWriter, raw, current, statistic);

            document.newPage();
            addRightElbowGraphs(pdfWriter, raw, current, statistic);

            document.newPage();
            addRightWristGraphs(pdfWriter, raw, current, statistic);

            document.newPage();
            addLeftShoulderGraphs(pdfWriter, raw, current, statistic);

            document.newPage();
            addLeftElbowGraphs(pdfWriter, raw, current, statistic);

            document.newPage();
            addLeftWristGraphs(pdfWriter, raw, current, statistic);

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

    private void addHeadGraphs(PdfWriter pdfWriter,
                               List<ISwimmingSkeleton> raw,
                               List<ISwimmingSkeleton> current,
                               IStatistic statistic) {
        String subject = "Head";
        ISkeletonValueFilter xFilter = new XHeadFilter();
        ISkeletonValueFilter yFilter = new YHeadFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getHeadRecognitionPercent(),
                statistic.getHeadExpected(),
                statistic.getHeadActual());
    }

    private void addRightShoulderGraphs(PdfWriter pdfWriter,
                                        List<ISwimmingSkeleton> raw,
                                        List<ISwimmingSkeleton> current,
                                        IStatistic statistic) {
        String subject = "Right shoulder";
        ISkeletonValueFilter xFilter = new XRightShoulderFilter();
        ISkeletonValueFilter yFilter = new YRightShoulderFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getRightShoulderRecognitionPercent(),
                statistic.getRightShoulderExpected(),
                statistic.getRightShoulderActual());
    }

    private void addRightElbowGraphs(PdfWriter pdfWriter,
                                     List<ISwimmingSkeleton> raw,
                                     List<ISwimmingSkeleton> current,
                                     IStatistic statistic) {
        String subject = "Right elbow";
        ISkeletonValueFilter xFilter = new XRightElbowFilter();
        ISkeletonValueFilter yFilter = new YRightElbowFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getRightElbowRecognitionPercent(),
                statistic.getRightElbowExpected(),
                statistic.getRightElbowActual());
    }

    private void addRightWristGraphs(PdfWriter pdfWriter,
                                     List<ISwimmingSkeleton> raw,
                                     List<ISwimmingSkeleton> current,
                                     IStatistic statistic) {
        String subject = "Right wrist";
        ISkeletonValueFilter xFilter = new XRightWristFilter();
        ISkeletonValueFilter yFilter = new YRightWristFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getRightWristRecognitionPercent(),
                statistic.getRightWristExpected(),
                statistic.getRightWristActual());
    }

    private void addLeftShoulderGraphs(PdfWriter pdfWriter,
                                       List<ISwimmingSkeleton> raw,
                                       List<ISwimmingSkeleton> current,
                                       IStatistic statistic) {
        String subject = "Left shoulder";
        ISkeletonValueFilter xFilter = new XLeftShoulderFilter();
        ISkeletonValueFilter yFilter = new YLeftShoulderFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getLeftShoulderRecognitionPercent(),
                statistic.getLeftShoulderExpected(),
                statistic.getLeftShoulderActual());
    }

    private void addLeftElbowGraphs(PdfWriter pdfWriter,
                                    List<ISwimmingSkeleton> raw,
                                    List<ISwimmingSkeleton> current,
                                    IStatistic statistic) {
        String subject = "Left elbow";
        ISkeletonValueFilter xFilter = new XLeftElbowFilter();
        ISkeletonValueFilter yFilter = new YLeftElbowFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getLeftElbowRecognitionPercent(),
                statistic.getLeftElbowExpected(),
                statistic.getLeftElbowActual());
    }

    private void addLeftWristGraphs(PdfWriter pdfWriter,
                                    List<ISwimmingSkeleton> raw,
                                    List<ISwimmingSkeleton> current,
                                    IStatistic statistic) {
        String subject = "Left wrist";
        ISkeletonValueFilter xFilter = new XLeftWristFilter();
        ISkeletonValueFilter yFilter = new YLeftWristFilter();
        _graphDrawer.drawGraphs(pdfWriter, raw, current, subject, xFilter , yFilter,
                statistic.getLeftWristRecognitionPercent(),
                statistic.getLeftWristExpected(),
                statistic.getLeftWristActual());
    }



}
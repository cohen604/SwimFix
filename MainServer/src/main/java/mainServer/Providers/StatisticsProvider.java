package mainServer.Providers;

import DTO.FileDTO;
import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import DomainLogic.FileLoaders.SkeletonsLoader;
import DomainLogic.SkeletonsValueFilters.HeadFilters.*;
import DomainLogic.SkeletonsValueFilters.RightShoulderFilters.*;
import DomainLogic.SkeletonsValueFilters.RightElbowFilters.*;
import DomainLogic.SkeletonsValueFilters.RightWristFilters.*;
import DomainLogic.SkeletonsValueFilters.LeftShoulderFilters.*;
import DomainLogic.SkeletonsValueFilters.LeftElbowFilters.*;
import DomainLogic.SkeletonsValueFilters.LeftWristFilters.*;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatisticsProvider implements IStatisticsProvider {

    private ISkeletonsLoader _skeletonsLoader = new SkeletonsLoader();

    private String generateName(String folderPath, String fileType, LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return folderPath + "\\" + time.format(formatter) + fileType;
    }

    @Override
    public String getStatistics(
            FileDTO rawSkeletonFileDTO,
            String skeletonsPath,
            String pdfFolderPath) {

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
            // add a graph to the file
            List<ISwimmingSkeleton> raw = _skeletonsLoader.read(rawSkeletonFileDTO.getBytes());
            List<ISwimmingSkeleton> current = _skeletonsLoader.read(skeletonsPath);

            addHeadGraphs(pdfWriter, raw, current);

            document.newPage();
            addRightShoulderGraphs(pdfWriter, raw, current);

            document.newPage();
            addRightElbowGraphs(pdfWriter, raw, current);

            document.newPage();
            addRightWristGraphs(pdfWriter, raw, current);

            document.newPage();
            addLeftShoulderGraphs(pdfWriter, raw, current);

            document.newPage();
            addLeftElbowGraphs(pdfWriter, raw, current);

            document.newPage();
            addLeftWristGraphs(pdfWriter, raw, current);

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

    private void addHeadGraphs(PdfWriter pdfWriter, List<ISwimmingSkeleton> raw,  List<ISwimmingSkeleton> current) {
        String subject = "Head";
        ISkeletonValueFilter xFilter = new XHeadFilter();
        ISkeletonValueFilter yFilter = new YHeadFilter();
        PdfDrawer.addGraphsToPage(pdfWriter, raw, current, subject, xFilter , yFilter);
    }

    private void addRightShoulderGraphs(PdfWriter pdfWriter, List<ISwimmingSkeleton> raw,  List<ISwimmingSkeleton> current) {
        String subject = "Right shoulder";
        ISkeletonValueFilter xFilter = new XRightShoulderFilter();
        ISkeletonValueFilter yFilter = new YRightShoulderFilter();
        PdfDrawer.addGraphsToPage(pdfWriter, raw, current, subject, xFilter , yFilter);
    }

    private void addRightElbowGraphs(PdfWriter pdfWriter, List<ISwimmingSkeleton> raw,  List<ISwimmingSkeleton> current) {
        String subject = "Right elbow";
        ISkeletonValueFilter xFilter = new XRightElbowFilter();
        ISkeletonValueFilter yFilter = new YRightElbowFilter();
        PdfDrawer.addGraphsToPage(pdfWriter, raw, current, subject, xFilter , yFilter);
    }

    private void addRightWristGraphs(PdfWriter pdfWriter, List<ISwimmingSkeleton> raw,  List<ISwimmingSkeleton> current) {
        String subject = "Right wrist";
        ISkeletonValueFilter xFilter = new XRightWristFilter();
        ISkeletonValueFilter yFilter = new YRightWristFilter();
        PdfDrawer.addGraphsToPage(pdfWriter, raw, current, subject, xFilter , yFilter);
    }

    private void addLeftShoulderGraphs(PdfWriter pdfWriter, List<ISwimmingSkeleton> raw,  List<ISwimmingSkeleton> current) {
        String subject = "Left shoulder";
        ISkeletonValueFilter xFilter = new XLeftShoulderFilter();
        ISkeletonValueFilter yFilter = new YLeftShoulderFilter();
        PdfDrawer.addGraphsToPage(pdfWriter, raw, current, subject, xFilter , yFilter);
    }

    private void addLeftElbowGraphs(PdfWriter pdfWriter, List<ISwimmingSkeleton> raw,  List<ISwimmingSkeleton> current) {
        String subject = "Left elbow";
        ISkeletonValueFilter xFilter = new XLeftElbowFilter();
        ISkeletonValueFilter yFilter = new YLeftElbowFilter();
        PdfDrawer.addGraphsToPage(pdfWriter, raw, current, subject, xFilter , yFilter);
    }

    private void addLeftWristGraphs(PdfWriter pdfWriter, List<ISwimmingSkeleton> raw,  List<ISwimmingSkeleton> current) {
        String subject = "Left wrist";
        ISkeletonValueFilter xFilter = new XLeftWristFilter();
        ISkeletonValueFilter yFilter = new YLeftWristFilter();
        PdfDrawer.addGraphsToPage(pdfWriter, raw, current, subject, xFilter , yFilter);
    }



}

class PdfDrawer {

    public static void addGraphsToPage(PdfWriter pdfWriter,
                                 List<ISwimmingSkeleton> raw,
                                 List<ISwimmingSkeleton> current,
                                 String subject,
                                 ISkeletonValueFilter xFilter,
                                 ISkeletonValueFilter yFilter){
        double x = 50;
        double y = 800;

        PdfContentByte canvas = pdfWriter.getDirectContent();
        canvas.beginText();
        y = PdfDrawer.drawText(canvas, subject, x, y, 18);
        y = PdfDrawer.drawText(canvas, "Number of frames: " + raw.size(), x, y, 14);
        y = PdfDrawer.drawText(canvas, "Expected", x, y, 14, BaseColor.RED);
        y = PdfDrawer.drawText(canvas, "Predict", x, y, 14, BaseColor.BLUE);
        y = PdfDrawer.drawText(canvas, "X " + subject, x, y, 14);
        canvas.endText();

        y = PdfDrawer.drawGraph(canvas, 50, y - 250, 500, 250, raw, current, xFilter);
        canvas.beginText();
        y = PdfDrawer.drawText(canvas, "Y " +subject, x, y, 14);
        canvas.endText();
        PdfDrawer.drawGraph(canvas, 50, y - 250, 500, 250, raw, current, yFilter);
        canvas.closePathStroke();
    }

    private static double drawText(
            PdfContentByte canvas,
            String text,
            double x,
            double y,
            float fontSize) {
        Font font = new Font(Font.FontFamily.HELVETICA, fontSize );
        canvas.setColorFill(BaseColor.BLACK);
        canvas.setFontAndSize(font.getCalculatedBaseFont(false), fontSize);
        canvas.showTextAligned(Element.ALIGN_LEFT, text , (float) x, (float)y, 0);
        return y - fontSize - 2;
    }

    private static double drawText(
            PdfContentByte canvas,
            String text,
            double x,
            double y,
            float fontSize,
            BaseColor color) {
        Font font = new Font(Font.FontFamily.HELVETICA, fontSize );
        canvas.setFontAndSize(font.getCalculatedBaseFont(false), fontSize);
        canvas.setColorFill(color);
        canvas.showTextAligned(Element.ALIGN_LEFT, text , (float) x, (float)y, 0);
        return y - fontSize - 2;
    }


    private static double drawGraph(PdfContentByte canvas,
                             double xStart, double yStart, double xSize, double ySize,
                             List<ISwimmingSkeleton> raw,
                             List<ISwimmingSkeleton> current,
                             ISkeletonValueFilter filter) {
        // draw the axis
        canvas.moveTo(xStart, yStart);
        canvas.lineTo(xStart, yStart + ySize); // draw y line
        canvas.moveTo(xStart, yStart);
        canvas.lineTo(xStart + xSize, yStart); // draw x line
        canvas.fillStroke();
        //find y max
        double yMax = findMaxValue(raw, current, filter);
        // draw points in the graph
        double xAdd = xSize / raw.size();
        double radius = 2;
        double yPrevRaw = 0;
        double yPrevCurrent = 0;
        for(int i=0; i<raw.size(); i++) {
            double x = xStart + i * xAdd;
            // get raw i value
            double y = filter.filter(raw.get(i));
            y = yStart + changeRange(yMax, 0, ySize, 0,  y);
            if( i > 0) {
                drawLineOnCanvas(canvas, x, y, x - xAdd, yPrevRaw, BaseColor.RED, 5);
            }
            //drawPointOnCanvas(canvas, x, y, BaseColor.RED);
            yPrevRaw = y;
            // get current i value
            y = filter.filter(current.get(i));
            y = yStart + changeRange(yMax, 0, ySize, 0,  y);
            if( i > 0) {
                drawLineOnCanvas(canvas, x, y, x - xAdd, yPrevCurrent, BaseColor.BLUE, 1);
            }
            //drawPointOnCanvas(canvas, x, y, BaseColor.BLUE);
            yPrevCurrent = y;
        }
        return yStart - 30;
    }

    private static void drawPointOnCanvas(PdfContentByte canvas, double x, double y, BaseColor color) {
        double radius = 2;
        canvas.setColorFill(color);
        canvas.circle(x, y, radius);
        canvas.fillStroke();
    }

    private static void drawLineOnCanvas(PdfContentByte canvas,
                                         double x0, double y0, double x1, double y1,
                                         BaseColor color,
                                         double width) {
        canvas.setColorFill(color);
        canvas.setLineWidth(width);
        canvas.moveTo(x0, y0);
        canvas.lineTo(x1, y1); // draw y line
        canvas.fill();
    }


    private static double findMaxValue(List<ISwimmingSkeleton> raw,
                                List<ISwimmingSkeleton> current,
                                ISkeletonValueFilter filter) {
        double yMax = 200;
        double value = 0;
        for(int i=0; i<raw.size(); i++) {
            value = filter.filter(raw.get(i));
            if(value > yMax) {
                yMax = value;
            }
            value = filter.filter(current.get(i));
            if(value > yMax) {
                yMax = value;
            }

        }
        return yMax;
    }

    private static double changeRange(double oldMax, double oldMin, double newMax, double newMin, double oldValue) {
        double oldRange = oldMax - oldMin;
        double newRange = newMax - newMin;
        return (((oldValue - oldMin) * newRange) / oldRange) + newMin;
    }

}
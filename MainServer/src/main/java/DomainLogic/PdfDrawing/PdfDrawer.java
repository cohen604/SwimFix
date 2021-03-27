package DomainLogic.PdfDrawing;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

public class PdfDrawer implements IGraphDrawer{

    @Override
    public void drawGraphs(PdfWriter pdfWriter,
                           List<ISwimmingSkeleton> raw,
                           List<ISwimmingSkeleton> current,
                           String subject,
                           ISkeletonValueFilter xFilter,
                           ISkeletonValueFilter yFilter,
                           double ratio,
                           int expectedCount,
                           int actualCount){
        double x = 50;
        double y = 800;

        PdfContentByte canvas = pdfWriter.getDirectContent();
        canvas.beginText();
        y = drawText(canvas, subject, x, y, 18);
        y = drawText(canvas, "Number of frames: " + raw.size(), x, y, 14);
        y = drawText(canvas, "Percent: " + ratio*100, x, y, 14);
        y = drawText(canvas, "Expected: " + expectedCount + " , Actual: "+actualCount, x, y, 14);
        y = drawText(canvas, "Expected", x, y, 14, BaseColor.RED);
        y = drawText(canvas, "Predict", x, y, 14, BaseColor.BLUE);
        y = drawText(canvas, "X " + subject, x, y, 14);
        canvas.endText();

        y = drawGraph(canvas, 50, y - 250, 500, 250, raw, current, xFilter);
        canvas.beginText();
        y = drawText(canvas, "Y " +subject, x, y, 14);
        canvas.endText();
        drawGraph(canvas, 50, y - 250, 500, 250, raw, current, yFilter);
        canvas.closePathStroke();
    }

    private double drawText(
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

    private double drawText(
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


    private double drawGraph(PdfContentByte canvas,
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

    private void drawPointOnCanvas(PdfContentByte canvas, double x, double y, BaseColor color) {
        double radius = 2;
        canvas.setColorFill(color);
        canvas.circle(x, y, radius);
        canvas.fillStroke();
    }

    private void drawLineOnCanvas(PdfContentByte canvas,
                                         double x0, double y0, double x1, double y1,
                                         BaseColor color,
                                         double width) {
        canvas.setColorFill(color);
        canvas.setLineWidth(width);
        canvas.moveTo(x0, y0);
        canvas.lineTo(x1, y1); // draw y line
        canvas.fill();
    }


    private double findMaxValue(List<ISwimmingSkeleton> raw,
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

    private double changeRange(double oldMax, double oldMin, double newMax, double newMin, double oldValue) {
        double oldRange = oldMax - oldMin;
        double newRange = newMax - newMin;
        return (((oldValue - oldMin) * newRange) / oldRange) + newMin;
    }

}

package DomainLogic.PdfDrawing;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

public class PdfDrawer implements IPdfDrawer {

    private static BaseColor RED = new BaseColor(255, 53, 53, 178);
    private static BaseColor BLUE = BaseColor.BLUE; //new BaseColor(50, 115, 255, 255);
    private static BaseColor GREEN = BaseColor.GREEN; //new BaseColor(56, 244, 75,);


    @Override
    public void drawGraphs(PdfWriter pdfWriter,
                           List<ISwimmingSkeleton> raw,
                           List<ISwimmingSkeleton> model,
                           List<ISwimmingSkeleton> modelAndInterpolation,
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
        y = drawText(canvas, "Percent: " + ratio, x, y, 14);
        y = drawText(canvas, "Expected: " + expectedCount + " , Actual: "+actualCount, x, y, 14);
        y = drawText(canvas, "Model", x, y, 14, RED);
        y = drawText(canvas, "Model And Interpolation", x, y, 14, BLUE);
        y = drawText(canvas, "Actual", x, y, 14, GREEN);
        y = drawText(canvas, "X axis " + subject, x, y, 14);
        canvas.endText();

        y = drawGraph(canvas, 60, y - 250, 500, 250, raw, model, modelAndInterpolation, xFilter);
        canvas.beginText();
        y = drawText(canvas, "Y axis " +subject, x, y, 14);
        canvas.endText();
        drawGraph(canvas, 60, y - 250, 500, 250, raw, model, modelAndInterpolation, yFilter);
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
                             List<ISwimmingSkeleton> model,
                             List<ISwimmingSkeleton> modelAndInterpolation,
                             ISkeletonValueFilter filter) {
        // draw the axis
        drawLineOnCanvas(canvas, xStart, yStart, xStart, yStart + ySize, BaseColor.BLACK, 2);
        drawLineOnCanvas(canvas, xStart, yStart, xStart + xSize, yStart, BaseColor.BLACK, 2);
        drawText(canvas, "Frames", (xStart + xSize) / 2, yStart - 20, 12 );
        drawText(canvas, "Value", xStart - 35, yStart + ySize / 2, 12 );
        double yMax = findMaxValue(raw, model, modelAndInterpolation, filter);
        double xAdd = xSize / model.size();

        drawGraphModel(canvas, model, filter, xStart, yStart, xSize, ySize, xAdd, yMax, RED, 4);
        drawGraphModel(canvas, modelAndInterpolation, filter, xStart, yStart, xSize, ySize, xAdd, yMax,
                BLUE, 1.5);
        drawGraphActual(canvas, raw, filter, xStart, yStart, xSize, ySize, xAdd, yMax, GREEN);
        return yStart - 30;
    }

    private void drawGraphModel(PdfContentByte canvas,
                                List<ISwimmingSkeleton> model,
                                ISkeletonValueFilter filter,
                                double xStart, double yStart, double xSize, double ySize,
                                double xAdd, double yMax,
                                BaseColor color, double width){
        if(model != null) {
            double yPrev = 0;
            double y = 0;
            double x = 0;
            for (int i = 0; i < model.size(); i++) {
                x = xStart + i * xAdd;
                // model draw
                y = filter.filter(model.get(i));
                y = yStart + changeRange(yMax, 0, ySize, 0, y);
                if (i > 0) {
                    drawLineOnCanvas(canvas, x, y, x - xAdd, yPrev, color, width);
                }
                yPrev = y;
            }
        }
    }

    private void drawGraphActual(PdfContentByte canvas,
                                List<ISwimmingSkeleton> model,
                                ISkeletonValueFilter filter,
                                double xStart, double yStart, double xSize, double ySize,
                                double xAdd, double yMax,
                                BaseColor color){
        if(model != null) {
            double y = 0;
            double x = 0;
            for (int i = 0; i < model.size(); i++) {
                x = xStart + i * xAdd;
                // actual draw
                y = filter.filter(model.get(i));
                y = yStart + changeRange(yMax, 0, ySize, 0, y);
                drawPointOnCanvas(canvas, x, y, color);
            }
        }
    }

    private void drawPointOnCanvas(PdfContentByte canvas, double x, double y, BaseColor color) {
        double radius = 2;
        PdfContentByte dup = canvas.getDuplicate();
        dup.setColorStroke(color);
        dup.setColorFill(color);
        dup.circle(x, y, radius);
        dup.fillStroke();
        dup.fill();
        canvas.add(dup);
    }

    private void drawLineOnCanvas(PdfContentByte canvas,
                                  double x0, double y0, double x1, double y1,
                                  BaseColor color,
                                  double width) {
        PdfContentByte dup = canvas.getDuplicate();
        dup.setColorFill(color);
        dup.setColorStroke(color);
        dup.setLineWidth(width);
        dup.moveTo(x0, y0);
        dup.lineTo(x1, y1); // draw y line
        dup.fillStroke();
        canvas.add(dup);
    }


    private double findMaxValue(List<ISwimmingSkeleton> raw,
                                List<ISwimmingSkeleton> model,
                                List<ISwimmingSkeleton> modelAndInterpolation,
                                ISkeletonValueFilter filter) {
        double yMax = 200;
        for(int i=0; i<raw.size(); i++) {
            yMax = findMax(raw, i, filter, yMax);
            yMax = findMax(model, i, filter, yMax);
            yMax = findMax(modelAndInterpolation, i, filter, yMax);
        }
        return yMax;
    }

    private double findMax(List<ISwimmingSkeleton> skeletons,
                           int index,
                           ISkeletonValueFilter filter,
                           double max) {
        if(skeletons==null) {
            return max;
        }
        double value = filter.filter(skeletons.get(index));
        if(value > max) {
            return value;
        }
        return max;
    }

    private double changeRange(double oldMax, double oldMin, double newMax, double newMin, double oldValue) {
        double oldRange = oldMax - oldMin;
        double newRange = newMax - newMin;
        return (((oldValue - oldMin) * newRange) / oldRange) + newMin;
    }

}

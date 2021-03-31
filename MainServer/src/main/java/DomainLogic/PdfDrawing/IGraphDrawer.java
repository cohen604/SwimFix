package DomainLogic.PdfDrawing;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

public interface IGraphDrawer {

    /**
     * The function draw graphs to pdfWriter
     * @param pdfWriter
     * @param raw
     * @param current
     * @param subject
     * @param xFilter
     * @param yFilter
     */
    void drawGraphs(PdfWriter pdfWriter,
                    List<ISwimmingSkeleton> raw,
                    List<ISwimmingSkeleton> current,
                    String subject,
                    ISkeletonValueFilter xFilter,
                    ISkeletonValueFilter yFilter,
                    double ratio,
                    int expectedCount,
                    int actualCount);


}

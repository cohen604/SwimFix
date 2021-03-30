package DomainLogic.PdfDrawing;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

public interface IGraphDrawer {

    void drawGraphs(PdfWriter pdfWriter,
                    List<ISwimmingSkeleton> raw,
                    List<ISwimmingSkeleton> model,
                    List<ISwimmingSkeleton> modelAndInterpolation,
                    String subject,
                    ISkeletonValueFilter xFilter,
                    ISkeletonValueFilter yFilter,
                    double ratio,
                    int expectedCount,
                    int actualCount);


}

package DomainLogic.PdfDrawing;

import Domain.SwimmingData.ISwimmingSkeleton;
import DomainLogic.SkeletonsValueFilters.ISkeletonValueFilter;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

public interface IPdfDrawer {

    void drawGraphs(PdfWriter pdfWriter,
                    List<ISwimmingSkeleton> raw,
                    List<ISwimmingSkeleton> model,
                    List<ISwimmingSkeleton> modelAndInterpolation,
                    String subject,
                    ISkeletonValueFilter xFilter,
                    ISkeletonValueFilter yFilter,
                    double ratioModel,
                    double ratioModelAndInterpolation,
                    int modelCount,
                    int modelAndInterpolationCount,
                    int actualCount);


}

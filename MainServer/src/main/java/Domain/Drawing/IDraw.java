package Domain.Drawing;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Points.IPoint;
import org.opencv.core.Mat;

public interface IDraw {

    /**
     * The function drawBefore a circle
     * @param frame
     * @param skeletonPoint
     * @param radius
     */
    Mat drawCircle(Mat frame, IPoint skeletonPoint, int radius);

    /***
     * The function drawBefore circle
     * @param frame
     * @param skeletonPoint
     * @param thickness
     * @param radius
     * @param red
     * @param green
     * @param blue
     * @return
     */
    Mat drawCircle(Mat frame,
                   IPoint skeletonPoint,
                   int radius,
                   int thickness,
                   double red,
                   double green ,
                   double blue);

    /**
     *
     * @param frame
     * @param radius
     * @param angle
     * @param startAngle
     * @param endAngle
     * @param center
     * @param r
     * @param g
     * @param b
     * @param a
     */
    Mat drawEclipse(Mat frame, double radius, double angle, double startAngle,
                    double endAngle, IPoint center, double r, double g,
                    double b, double a);

    /**
     * The function drawBefore line
     * @param frame
     * @param a
     * @param b
     */
    Mat drawLine(Mat frame, IPoint a, IPoint b);

    /**
     * The function drawBefore a line
     * @param frame
     * @param s1
     * @param s2
     * @param r
     * @param g
     * @param b
     * @param a
     * @param thickness
     */
    Mat drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness);

    /**
     * The function drawBefore swimming tag into the frame
     * @param frame the current frame
     * @param skeleton the image tag to print
     * @return the new frame
     */
    Mat drawSwimmer(Mat frame, ISwimmingSkeleton skeleton);

    Mat drawLogo(Mat frame);

    Mat drawMessage(Mat frame, String message, double x, double y, int thickness);

    Mat drawArrow(Mat frame,
                  IPoint a,
                  IPoint b,
                  double red,
                  double green,
                  double blue);

    Mat drawVerticalArrow(Mat frame,
                          IPoint pointA,
                          IPoint pointB,
                          boolean verticalSide,
                          double red,
                          double green,
                          double blue);
}

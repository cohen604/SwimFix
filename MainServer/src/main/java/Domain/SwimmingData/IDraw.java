package Domain.SwimmingData;

import org.opencv.core.Mat;

public interface IDraw {

    /**
     * The function draw a circle
     * @param frame
     * @param skeletonPoint
     * @param radius
     */
    void drawCircle(Mat frame, IPoint skeletonPoint, int radius);

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
    void drawElipce(Mat frame, double radius, double angle, double startAngle,
                           double endAngle, IPoint center, double r, double g,
                           double b, double a);

    /**
     * The function draw line
     * @param frame
     * @param a
     * @param b
     */
    void drawLine(Mat frame, IPoint a, IPoint b);

    /**
     * The function draw a line
     * @param frame
     * @param s1
     * @param s2
     * @param r
     * @param g
     * @param b
     * @param a
     * @param thickness
     */
    void drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness);

    /**
     * The function draw swimming tag into the frame
     * @param frame the current frame
     * @param skeleton the image tag to print
     * @return the new frame
     */
    //TODO change the type of swimmingSkeleton to interface when you have it
    void drawSwimmer(Mat frame, SwimmingSkeleton skeleton);
}

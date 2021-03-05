package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.SwimmingError;
import org.opencv.core.Mat;

public abstract class ElbowError extends SwimmingError {

    private double angle;
    protected boolean inside; // means if the arrow is to inside or outside

    public ElbowError(IDraw drawer, double angle, boolean inside) {
        super(drawer);
        this.angle = angle;
        this.inside = inside;
    }

    public void drawShoulderElbowWrist(Mat frame, IPoint shoulder, IPoint elbow,
                                       IPoint wrist, double delta) {
        double r = 255.0, g = 0.0, b = 0.0, a = 255.0;
        double raduis = 5.0;
        int thickness = 3;
        drawLine(frame, shoulder, elbow, r, g, b, a, thickness);
        drawLine(frame, elbow, wrist, r, g, b, a, thickness);
        /*
        TODO draw it better
        SkeletonPoint newPoint = new SkeletonPoint(elbow.getX() + delta, elbow.getY(), -1);
        r = 0;
        g = 255.0;
        a = 10.0;
        raduis = Math.abs(delta);
        SkeletonPoint vecW = elbow.getNormalVec(newPoint);
        SkeletonPoint vecV = shoulder.getNormalVec(elbow);
        double angle = vecW.getAngleBetween(vecV);
        //angle = (180 - angle) / 2;
        drawElipce(frame, raduis, angle, 0, 85, elbow, r, g, b, a); */
    }

    public double getAngle() {
        return angle;
    }
}

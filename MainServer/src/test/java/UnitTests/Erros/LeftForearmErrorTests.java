package UnitTests.Erros;

import Domain.Drawing.IDraw;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.LeftForearmError;
import Domain.Points.IPoint;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.Iterator;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeftForearmErrorTests extends ErrorDrawTests {
    @Override
    protected SwimmingError setUpSwimmingError() {
        IDraw draw = setUpDraw();
        Random random = new Random();
        Iterator<Double> iterator = random.doubles(0, 180)
                .iterator();
        double minAngle = iterator.next();
        double maxAngle = iterator.next();
        double angle = iterator.next();
        boolean inside = random.nextBoolean();
        return new LeftForearmError(draw, angle, maxAngle, minAngle, inside);
    }

    protected IDraw setUpDraw() {
        IDraw draw = mock(IDraw.class);
        when(draw.drawLine(
                any(Mat.class),
                any(IPoint.class),
                any(IPoint.class),
                any(Double.class),
                any(Double.class),
                any(Double.class),
                any(Double.class),
                any(Integer.class)))
                .thenAnswer(new Answer<Mat>() {
                    public Mat answer(InvocationOnMock invocation) {
                        Object[] args = invocation.getArguments();
                        return draw((Mat)args[0]);
                    }
                });
        return draw;
    }

    private Mat draw(Mat frame) {
        return frame.setTo(new Scalar(255, 255, 255));
    }
}

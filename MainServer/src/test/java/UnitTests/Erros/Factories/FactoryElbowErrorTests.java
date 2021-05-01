package UnitTests.Erros.Factories;

import Domain.Drawing.IDraw;
import Domain.Drawing.IFactoryDraw;
import Domain.Errors.Factories.FactoryElbowError;
import Domain.Errors.Interfaces.IFactoryElbowError;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.LeftElbowError;
import Domain.Errors.RightElbowError;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.Iterator;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FactoryElbowErrorTests extends TestCase {

    IFactoryElbowError _factoryElbowError;

    @Before
    public void setUp() {
        IFactoryDraw factoryDraw = setUpIFactoryDraw();
        _factoryElbowError = new FactoryElbowError(factoryDraw);
    }

    public void testCreateLeft() {
        // Arrange
        Random random = new Random();
        Iterator<Double> iterator = random.doubles(0, 180)
                .iterator();
        double minAngle = iterator.next();
        double maxAngle = iterator.next();
        double angle = iterator.next();
        double delta = 0.01;
        boolean inside = random.nextBoolean();
        String expectedTag = "Left Elbow Error";
        // Act
        SwimmingError swimmingError = _factoryElbowError.createLeft(maxAngle, minAngle, angle, inside);
        // Assert
        assertNotNull(swimmingError);
        assertEquals(expectedTag, swimmingError.getTag());
        LeftElbowError leftElbowError = (LeftElbowError) swimmingError;
        assertEquals(minAngle, leftElbowError.getMinAngle(), delta);
        assertEquals(maxAngle, leftElbowError.getMaxAngle(), delta);
        assertEquals(angle, leftElbowError.getAngle(), delta);
        assertEquals(inside, leftElbowError.getIndise());
    }

    public void testCreateRight() {
        // Arrange
        Random random = new Random();
        Iterator<Double> iterator = random.doubles(0, 180)
                .iterator();
        double minAngle = iterator.next();
        double maxAngle = iterator.next();
        double angle = iterator.next();
        double delta = 0.01;
        boolean inside = random.nextBoolean();
        String expectedTag = "Right Elbow Error";
        // Act
        SwimmingError swimmingError = _factoryElbowError.createRight(maxAngle, minAngle, angle, inside);
        // Assert
        assertNotNull(swimmingError);
        assertEquals(expectedTag, swimmingError.getTag());
        RightElbowError rightElbowError = (RightElbowError) swimmingError;
        assertEquals(minAngle, rightElbowError.getMinAngle(), delta);
        assertEquals(maxAngle, rightElbowError.getMaxAngle(), delta);
        assertEquals(angle, rightElbowError.getAngle(), delta);
        assertEquals(inside, rightElbowError.getIndise());
    }

    protected IFactoryDraw setUpIFactoryDraw() {
        IDraw draw = mock(IDraw.class);
        IFactoryDraw factoryDraw = mock(IFactoryDraw.class);
        when(factoryDraw.create()).thenReturn(draw);
        return factoryDraw;
    }
}

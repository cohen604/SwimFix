package UnitTests.Erros.Factories;

import Domain.Drawing.IDraw;
import Domain.Drawing.IFactoryDraw;
import Domain.Errors.Factories.FactoryForearmError;
import Domain.Errors.Interfaces.IFactoryForearmError;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.LeftForearmError;
import Domain.Errors.RightForearmError;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.Iterator;
import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FactoryForearmErrorTests extends TestCase {

    private IFactoryForearmError _factoryForearmError;

    @Before
    public void setUp() {
        IFactoryDraw factoryDraw = setUpFactoryDraw();
        _factoryForearmError = new FactoryForearmError(factoryDraw);
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
        String expectedTag = "Left Forearm Error";
        // Act
        SwimmingError error =_factoryForearmError.createLeft(angle, maxAngle, minAngle, inside);
        // Assert
        assertNotNull(error);
        assertEquals(expectedTag, error.getTag());
        LeftForearmError left = (LeftForearmError) error;
        assertEquals(angle, left.getAngle(), delta);
        assertEquals(maxAngle, left.getMaxAngle(), delta);
        assertEquals(minAngle, left.getMinAngle(), delta);
        assertEquals(inside, left.getInside());
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
        String expectedTag = "Right Forearm Error";
        // Act
        SwimmingError error =_factoryForearmError.createRight(angle, maxAngle, minAngle, inside);
        // Assert
        assertNotNull(error);
        assertEquals(expectedTag, error.getTag());
        RightForearmError right = (RightForearmError) error;
        assertEquals(angle, right.getAngle(), delta);
        assertEquals(maxAngle, right.getMaxAngle(), delta);
        assertEquals(minAngle, right.getMinAngle(), delta);
        assertEquals(inside, right.getInside());
    }

    protected IFactoryDraw setUpFactoryDraw() {
        IDraw draw = mock(IDraw.class);
        IFactoryDraw factoryDraw = mock(IFactoryDraw.class);
        when(factoryDraw.create()).thenReturn(draw);
        return factoryDraw;
    }
}

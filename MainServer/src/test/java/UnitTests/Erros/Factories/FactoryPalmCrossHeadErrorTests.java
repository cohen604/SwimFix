package UnitTests.Erros.Factories;

import Domain.Drawing.IDraw;
import Domain.Drawing.IFactoryDraw;
import Domain.Errors.Factories.FactoryPalmCrossHeadError;
import Domain.Errors.Interfaces.IFactoryPalmCrossHeadError;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Errors.LeftPalmCrossHeadError;
import Domain.Errors.RightPalmCrossHeadError;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FactoryPalmCrossHeadErrorTests extends TestCase {

    IFactoryPalmCrossHeadError _factoryPalmCrossHeadError;

    @Before
    public void setUp() {
        IFactoryDraw factoryDraw = setUpFactoryDraw();
        _factoryPalmCrossHeadError = new FactoryPalmCrossHeadError(factoryDraw);
    }

    public void testCreateLeft() {
        // Arrange
        Random random = new Random();
        boolean inside = random.nextBoolean();
        String expectedTag = "Left Palm Error";
        // Act
        SwimmingError error = _factoryPalmCrossHeadError.createLeft(inside);
        // Assert
        assertNotNull(error);
        assertEquals(expectedTag, error.getTag());
        LeftPalmCrossHeadError left = (LeftPalmCrossHeadError)error;
        assertEquals(inside, left.getInside());
    }

    public void testCreateRight() {
        // Arrange
        Random random = new Random();
        boolean inside = random.nextBoolean();
        String expectedTag = "Right Palm Error";
        // Act
        SwimmingError error = _factoryPalmCrossHeadError.createRight(inside);
        // Assert
        assertNotNull(error);
        assertEquals(expectedTag, error.getTag());
        RightPalmCrossHeadError right = (RightPalmCrossHeadError) error;
        assertEquals(inside, right.getInside());
    }

    protected IFactoryDraw setUpFactoryDraw(){
        IDraw draw = mock(IDraw.class);
        IFactoryDraw factoryDraw = mock(IFactoryDraw.class);
        when(factoryDraw.create()).thenReturn(draw);
        return factoryDraw;
    }
}

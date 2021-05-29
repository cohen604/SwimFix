package UnitTests.StreamingTests;

import Domain.Drawing.IDraw;
import Domain.Streaming.FactoryVideoHandler;
import Domain.Streaming.IVideoHandler;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public class FactoryVideoHandlerTests extends TestCase {

    private FactoryVideoHandler factoryVideoHandler;

    @Before
    public void setUp() {
        factoryVideoHandler = new FactoryVideoHandler();
    }

    public void testCreate() {
        // Arrange
        IDraw draw = mock(IDraw.class);
        // Act
        IVideoHandler videoHandler = factoryVideoHandler.create(draw);
        // Assert
        Assert.assertNotNull(videoHandler);
    }
}

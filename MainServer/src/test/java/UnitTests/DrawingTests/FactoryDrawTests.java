package UnitTests.DrawingTests;

import Domain.Drawing.FactoryDraw;
import Domain.Drawing.IDraw;
import junit.framework.TestCase;

public class FactoryDrawTests  extends TestCase {

    public void testCreate() {
        // Arrange
        FactoryDraw factoryDraw = new FactoryDraw();
        // Act
        IDraw draw = factoryDraw.create();
        // Assert
        assertNotNull(draw);
    }
}

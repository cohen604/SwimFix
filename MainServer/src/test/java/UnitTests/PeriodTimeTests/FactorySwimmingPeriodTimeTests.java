package UnitTests.PeriodTimeTests;
import Domain.PeriodTimeData.FactorySwimmingPeriodTime;
import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;

import java.util.LinkedList;
import java.util.List;
import static org.mockito.Mockito.*;


public class FactorySwimmingPeriodTimeTests extends TestCase {

    FactorySwimmingPeriodTime _factory;

    @BeforeClass
    public void setUp() {
        _factory = new FactorySwimmingPeriodTime();
    }

    public void testFactoryEmptyLists() {
        // Arrange
        List<IPeriodTime> rights = new LinkedList<>();
        List<IPeriodTime> lefts = new LinkedList<>();
        // Act
        ISwimmingPeriodTime result = _factory.factory(rights, lefts);
        // Assert
        Assert.assertNotNull(result);
        compareLists(rights, result.getRightTimes());
        compareLists(lefts, result.getLeftTimes());
    }

    public void testFactoryNulls() {
        // Arrange
        // Act
        ISwimmingPeriodTime result = _factory.factory(null, null);
        // Assert
        Assert.assertNotNull(result);
        compareLists(null, result.getRightTimes());
        compareLists(null, result.getLeftTimes());
    }

    public void testFactoryEmptyListNull() {
        // Arrange
        List<IPeriodTime> rights = new LinkedList<>();
        // Act
        ISwimmingPeriodTime result = _factory.factory(rights, null);
        // Assert
        Assert.assertNotNull(result);
        compareLists(rights, result.getRightTimes());
        compareLists(null, result.getLeftTimes());

    }

    public void testFactoryNullEmptyList() {
        // Arrange
        List<IPeriodTime> lefts = new LinkedList<>();
        // Act
        ISwimmingPeriodTime result = _factory.factory(null, lefts);
        // Assert
        Assert.assertNotNull(result);
        compareLists(null, result.getRightTimes());
        compareLists(lefts, result.getLeftTimes());
    }

    public void testFactoryEmptyRight() {
        // Arrange
        List<IPeriodTime> rights = new LinkedList<>();
        rights.add(mock(IPeriodTime.class));
        List<IPeriodTime> lefts = new LinkedList<>();
        // Act
        ISwimmingPeriodTime result = _factory.factory(rights, lefts);
        // Assert
        Assert.assertNotNull(result);
        compareLists(rights, result.getRightTimes());
        compareLists(lefts, result.getLeftTimes());
    }

    public void testFactoryEmptyLeft() {
        // Arrange
        List<IPeriodTime> rights = new LinkedList<>();
        List<IPeriodTime> lefts = new LinkedList<>();
        lefts.add(mock(IPeriodTime.class));
        // Act
        ISwimmingPeriodTime result = _factory.factory(rights, lefts);
        // Assert
        Assert.assertNotNull(result);
        compareLists(rights, result.getRightTimes());
        compareLists(lefts, result.getLeftTimes());
    }

    public void testFactoryValid() {
        // Arrange
        List<IPeriodTime> rights = new LinkedList<>();
        rights.add(mock(IPeriodTime.class));
        List<IPeriodTime> lefts = new LinkedList<>();
        lefts.add(mock(IPeriodTime.class));
        // Act
        ISwimmingPeriodTime result = _factory.factory(rights, lefts);
        // Assert
        Assert.assertNotNull(result);
        compareLists(rights, result.getRightTimes());
        compareLists(lefts, result.getLeftTimes());
    }

    private void compareLists(List<IPeriodTime> expected, List<IPeriodTime> result) {
        if(expected == null) {
            assertNull(result);
        }
        else {
            assertNotNull(result);
            assertEquals(expected.size(), result.size());
            for (int i = 0; i < expected.size(); i++) {
                IPeriodTime current = expected.get(i);
                IPeriodTime other = result.get(i);
                assertEquals(current, other);
            }
        }
    }


}

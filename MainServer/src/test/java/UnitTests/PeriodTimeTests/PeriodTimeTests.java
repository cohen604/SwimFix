package UnitTests.PeriodTimeTests;
import Domain.PeriodTimeData.PeriodTime;
import junit.framework.TestCase;
import org.junit.Assert;
import java.util.Random;

public class PeriodTimeTests extends TestCase {

    public void testPeriodTime() {
        // Arrange
        Random random = new Random();
        int start = random.nextInt();
        int end = random.nextInt();
        // Act
        PeriodTime periodTime = new PeriodTime(start, end);
        // Assert
        Assert.assertEquals(start, periodTime.getStart());
        Assert.assertEquals(end, periodTime.getEnd());
        Assert.assertEquals(end - start, periodTime.getTimeLength());
    }

}

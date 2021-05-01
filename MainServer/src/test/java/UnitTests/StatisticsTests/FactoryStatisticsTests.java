package UnitTests.StatisticsTests;

import Domain.StatisticsData.FactoryStatistic;
import Domain.StatisticsData.IStatistic;
import Domain.StatisticsData.StatisticsHolder;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import junit.framework.TestCase;

import java.util.LinkedList;
import java.util.List;

public class FactoryStatisticsTests extends TestCase {

    public void testCreate() {
        // Arrange
        FactoryStatistic factoryStatistic = new FactoryStatistic();
        List<ISwimmingSkeleton> raw = new LinkedList<>();
        List<ISwimmingSkeleton> model = new LinkedList<>();
        List<ISwimmingSkeleton> modelAndInterpolation = new LinkedList<>();
        // Act
        IStatistic statisticsHolder =
                factoryStatistic.create(raw, model, modelAndInterpolation);
        // Assert
        assertNotNull(statisticsHolder);
    }

}

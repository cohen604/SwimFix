package UnitTests.StreamingTests;

import Domain.Errors.Interfaces.SwimmingError;
import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.Streaming.*;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FactoryFeedbackVideoTests extends TestCase {

    private FactoryFeedbackVideo _factory;

    @Before
    public void setUp() {
        this._factory = new FactoryFeedbackVideo();
    }

    public void testCreate() {
        // Arrange
        IVideo iVideo = getIVideo();
        TaggedVideo taggedVideo = getTaggedVideo();
        Map<Integer, List<SwimmingError>> errors = getErrorMap();
        String path = "test";
        ISwimmingPeriodTime periodTime = getSwimmingPeriodTime();
        // Act
        IFeedbackVideo feedbackVideo =_factory.create(iVideo, taggedVideo, errors, path,  periodTime);
        // Assert
        Assert.assertNotNull(feedbackVideo);
        Assert.assertEquals(path, feedbackVideo.getPath());
        Assert.assertEquals(path, feedbackVideo.getIVideo().getPath());
    }

    private IVideo getIVideo() {
        IVideo iVideo = mock(IVideo.class);
        when(iVideo.getPath()).thenReturn("koko");
        return iVideo;
    }

    private TaggedVideo getTaggedVideo() {
        TaggedVideo taggedVideo = mock(TaggedVideo.class);
        when(taggedVideo.getskeletonsPath()).thenReturn("koko");
        return taggedVideo;
    }

    private Map<Integer, List<SwimmingError>> getErrorMap(){
        Map<Integer, List<SwimmingError>> map = new HashMap<>();
        List<SwimmingError> list = new LinkedList<>();
        map.put(0, list);
        SwimmingError swimmingError = mock(SwimmingError.class);
        when(swimmingError.getTag()).thenReturn("koko");
        list.add(swimmingError);
        return map;
    }

    private ISwimmingPeriodTime getSwimmingPeriodTime() {
        ISwimmingPeriodTime periodTime = mock(ISwimmingPeriodTime.class);
        IPeriodTime iPeriodTime = mock(IPeriodTime.class);
        when(iPeriodTime.getTimeLength()).thenReturn(99);
        LinkedList<IPeriodTime> list = new LinkedList<>();
        list.add(iPeriodTime);
        when(periodTime.getLeftTimes()).thenReturn(list);
        return periodTime;
    }
}

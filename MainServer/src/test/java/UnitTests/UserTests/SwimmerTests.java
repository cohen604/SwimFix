package UnitTests.UserTests;

import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Swimmer;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.mockito.Mockito.*;


public class SwimmerTests extends TestCase {

    private Swimmer swimmer;
    private IFeedbackVideo feedbackVideo_1;
    private IFeedbackVideo feedbackVideo_2;


    @BeforeClass
    public void setUp() {
        this.swimmer = new Swimmer();
        feedbackVideo_1 = mock(IFeedbackVideo.class);
        feedbackVideo_2 = mock(IFeedbackVideo.class);
    }


    @AfterClass
    public void tearDown() {
    }

    public void setUpDelete(String path) {
        when(feedbackVideo_1.getPath()).thenReturn(path);
        assertTrue(swimmer.addFeedback(feedbackVideo_1));
    }

    public void testAddNullFeedback() {
        assertFalse(swimmer.addFeedback(null));
    }

    public void testOneAddFeedback() {
        when(feedbackVideo_1.getPath()).thenReturn("test_path");
        assertTrue(swimmer.addFeedback(feedbackVideo_1));
        assertEquals(1, swimmer.getFeedbacks().size());
    }

    public void testFeedbackExist() {
        when(feedbackVideo_1.getPath()).thenReturn("test_path");
        assertTrue(swimmer.addFeedback(feedbackVideo_1));
        assertFalse(swimmer.addFeedback(feedbackVideo_1));
        assertEquals(1, swimmer.getFeedbacks().size());
    }

    public void testAddManyFeedBacks() {
        when(feedbackVideo_1.getPath()).thenReturn("test_path1");
        when(feedbackVideo_2.getPath()).thenReturn("test_path2");
        assertTrue(swimmer.addFeedback(feedbackVideo_1));
        assertTrue(swimmer.addFeedback(feedbackVideo_2));
        assertEquals(2, swimmer.getFeedbacks().size());
    }

    public void testDeleteFeedback() {
        String path = "test_path";
        setUpDelete(path);
        assertNotNull(swimmer.deleteFeedback(path));
        assertEquals(swimmer.getFeedbacks().size(), 0);
    }

    public void testDeleteWrongPath() {
        String path = "test_path";
        setUpDelete(path);
        assertNull(swimmer.deleteFeedback("wrong_path"));
        assertEquals(swimmer.getFeedbacks().size(), 1);
    }


}

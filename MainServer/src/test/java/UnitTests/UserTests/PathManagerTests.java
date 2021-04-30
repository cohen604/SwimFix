package UnitTests.UserTests;

import Domain.UserData.PathManager;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class PathManagerTests  extends TestCase {


    private static String ROOT_PATH = "\\testPathManager";
    private static String FOLDER_PATH = "test@test.test.com";
    private List<String> _paths;
    private PathManager _pathManager;

    @BeforeClass
    public void setUp() {
        _paths = new LinkedList<>();
        _paths.add(ROOT_PATH + "\\" + FOLDER_PATH + "\\" + "videos");
        _paths.add(ROOT_PATH + "\\" + FOLDER_PATH + "\\" + "feedbacks");
        _paths.add(ROOT_PATH + "\\" + FOLDER_PATH + "\\" + "feedbacksSkeletons");
        _paths.add(ROOT_PATH + "\\" + FOLDER_PATH + "\\" + "mlSkeletons");
        _paths.add(ROOT_PATH + "\\" + FOLDER_PATH + "\\" + "reports");
        _paths.add(ROOT_PATH + "\\" + FOLDER_PATH);
        _paths.add(ROOT_PATH);
        try {
            System.out.println(Paths.get(ROOT_PATH).toAbsolutePath().toString());
            Files.createDirectory(Paths.get(ROOT_PATH));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
        _pathManager = new PathManager(ROOT_PATH, FOLDER_PATH, true);
    }

    @AfterClass
    public void tearDown() {
        for (String path: _paths) {
            try {
                Files.deleteIfExists(Paths.get(path));
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void testCreateDirs() {
        // assert
        for (String path: _paths) {
            assertTrue(Files.isDirectory(Paths.get(path)));
        }
    }

    public void testGetVideoPath() {
        assertEquals(_paths.get(0), _pathManager.getVideosPath());
    }

    public void testGetFeedbacksPath() {
        assertEquals(_paths.get(1), _pathManager.getFeedbacksPath());
    }

    public void testGetSkeletonsPath() {
        assertEquals(_paths.get(2), _pathManager.getSkeletonsPath());
    }

    public void testGetMLSkeletonsPath() {
        assertEquals(_paths.get(3), _pathManager.getMLSkeletonsPath());
    }

    public void testGetReportsPath() {
        assertEquals(_paths.get(4), _pathManager.getReportsPath());
    }

}

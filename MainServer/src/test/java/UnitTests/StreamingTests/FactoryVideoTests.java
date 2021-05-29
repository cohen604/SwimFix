package UnitTests.StreamingTests;

import DTO.FeedbackDTOs.ConvertedVideoDTO;
import Domain.Streaming.FactoryVideo;
import Domain.Streaming.IVideo;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;

public class FactoryVideoTests extends TestCase {

    private FactoryVideo factoryVideo;

    @Before
    public void setUp() {
        factoryVideo = new FactoryVideo();
    }

    public void testCreateConvertedVideoPathNotExists() {
        // Arrange
        String videoName = "name.mp4";
        byte[] bytes = {1};
        ConvertedVideoDTO convertedVideoDTO = new ConvertedVideoDTO(videoName, bytes);
        String path = "path";
        String videoType = ".mp4";
        // Act
        IVideo video = factoryVideo.create(convertedVideoDTO, path);
        // Assert
        Assert.assertNotNull(video);
        Assert.assertNull(video.getPath());
        Assert.assertEquals(videoType, video.getVideoType());
    }

    public void testCreateConvertedVideo() {
        // Arrange
        String videoName = "name.mp4";
        byte[] bytes = {1};
        ConvertedVideoDTO convertedVideoDTO = new ConvertedVideoDTO(videoName, bytes);
        String path = "./src/test/java/TestingVideos/example/2021-03-31-19-39-18.mov";
        String videoType = ".mp4";
        // Act
        IVideo video = factoryVideo.create(convertedVideoDTO, path);
        // Assert
        Assert.assertNotNull(video);
        Assert.assertEquals(path, video.getPath());
        Assert.assertEquals(videoType, video.getVideoType());
    }

    public void testCreatePathsVideoNotExists() {
        // Arrange
        String path = "name.mp4";
        String type = ".mp4";
        // Act
        IVideo video = factoryVideo.create(path, type);
        // Assert
        Assert.assertNotNull(video);
        Assert.assertNull(video.getPath());
        Assert.assertEquals(type, video.getVideoType());
    }

    public void testCreatePathsVideo() {
        // Arrange
        String path = "./src/test/java/TestingVideos/example/2021-03-31-19-39-18.mov";
        String type = ".mp4";
        // Act
        IVideo video = factoryVideo.create(path, type);
        // Assert
        Assert.assertNotNull(video);
        Assert.assertEquals(path, video.getPath());
        Assert.assertEquals(type, video.getVideoType());
    }
}

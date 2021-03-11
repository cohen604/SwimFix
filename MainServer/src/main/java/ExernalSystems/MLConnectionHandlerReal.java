package ExernalSystems;

import Domain.Streaming.IVideo;
import Domain.Streaming.TaggedVideo;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingSkeletonGraph.SwimmingSkeleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

public class MLConnectionHandlerReal implements MLConnectionHandler{

    private String ip;
    private String port;

    public MLConnectionHandlerReal(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String postMessage(String data, String url, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
        body.add(param, data);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, requestEntity, String.class);
    }

    public String postMessage(List<String> data, String url, String param, int len, int height, int width) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
        for (int i=0; i<data.size() && i < 30; i++) {
            body.add(param + i, data.get(i));
        }
        body.add("len", len);
        body.add("height", height);
        body.add("width", width);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, requestEntity, String.class);
        return res;
    }

    public String postMessage(byte[] data, String url, int len, int height, int width, String type) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
        body.add("video", Base64.getEncoder().encode(data));
        body.add("len", len);
        body.add("height", height);
        body.add("width", width);
        body.add("type", type);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.postForObject(url, requestEntity, String.class);
        return res;
    }

    public String getURL(String prefix) {
        return "http://"+this.ip + ":" + this.port + prefix;
    }

    /*@Override
    public TaggedVideo getSkeletons(IVideo video) {
        try {
            List<byte[]> frames = video.getVideo();
            List<String> frames_string = new LinkedList<>();
            for (byte[] frame : frames) {
                String frame_string = Base64.getEncoder().encodeToString(frame);
                frames_string.add(frame_string);
            }
            System.out.println("size "+frames.size());
            System.out.println("height "+video.getHeight());
            System.out.println("width " +video.getWidth());
            System.out.println("frames" +frames);
            System.out.println(LocalDateTime.now());
            String res = postMessage(frames_string, getURL("/detect"), "video", frames.size(),
                    video.getHeight(), video.getWidth());
            return createTaggedVideo(res);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }*/

    @Override
    public List<ISwimmingSkeleton> getSkeletons(IVideo video) {
        try {
            int size = video.getVideo().size();
            byte[] data = Files.readAllBytes(Paths.get(video.getPath()));
            System.out.println("height "+video.getHeight());
            System.out.println("width " +video.getWidth());
            System.out.println(LocalDateTime.now());
            String res = postMessage(data, getURL("/detect"), size,
                    video.getHeight(), video.getWidth(), video.getVideoType());
            return createTaggedVideo(res);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * create a TaggedVideo from a json
     * @param json - the json from the ML
     * @return - TaggedVidoe
     */
    private List<ISwimmingSkeleton> createTaggedVideo(String json) {
        List<ISwimmingSkeleton> output = new LinkedList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<LinkedList<Double>>>(){}.getType();
        List<List<Double>> list = gson.fromJson(json, listType);
        for (List<Double> frame: list) {
            //TODO remove NEW!!!
            output.add(new SwimmingSkeleton(frame));
        }
        return output;
    }

}

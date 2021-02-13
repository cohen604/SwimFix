package ExernalSystems;

import Domain.Streaming.IVideo;
import Domain.Streaming.TaggedVideo;
import Domain.SwimmingData.SwimmingSkeleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
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
        for (int i=0; i<data.size(); i++) {
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

    public String getURL(String prefix) {
        return "http://"+this.ip + ":" + this.port + prefix;
    }

    @Override
    public TaggedVideo getSkeletons(IVideo video) {
        try {
            List<byte[]> frames = video.getVideo();
            List<String> frames_string = new LinkedList<>();
            for (byte[] frame : frames) {
                String frame_string = Base64.getEncoder().encodeToString(frame);
                frames_string.add(frame_string);
            }
            String res = postMessage(frames_string, getURL("/detect"), "video", frames.size(),
                    video.getHeight(), video.getWidth());
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
    private TaggedVideo createTaggedVideo(String json) {
        TaggedVideo taggedVideo = new TaggedVideo();
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<LinkedList<Double>>>(){}.getType();
        List<List<Double>> list = gson.fromJson(json, listType);
        for (List<Double> frame: list) {
            SwimmingSkeleton skeleton = new SwimmingSkeleton(frame);
            taggedVideo.addTag(skeleton);
        }
        return taggedVideo;
    }

}

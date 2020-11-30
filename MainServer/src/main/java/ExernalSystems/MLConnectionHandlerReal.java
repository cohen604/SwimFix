package ExernalSystems;

import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;

public class MLConnectionHandlerReal implements MLConnectionHandler{

    private String ip;
    private String port;

    public MLConnectionHandlerReal(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public void sendGetMessage(Video video, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body  = new LinkedMultiValueMap<>();
        body.add("video", video);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, requestEntity);
    }

    public String getURL(String prefix) {
        return "http://"+this.ip + ":" + this.port + prefix;
    }

    public void htmlGetMessage(String url) {
//        String method = "GET";
//        byte[] message = video.getVideo();
//        try {
//            URL url = new URL(uri);
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            http.setR
//            http.setRequestMethod(method);
//            http.connect();
//        }
//        catch(Exception e ){
//
//        }
    }

    @Override
    public TaggedVideo tagVideo(Video video) {
        sendGetMessage(video, getURL("/detect"));
        return null;
    }
}

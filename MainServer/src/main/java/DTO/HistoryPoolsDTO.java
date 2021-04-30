package DTO;

import java.util.Map;

public class HistoryPoolsDTO {

    private Map<String, FeedbackVideoStreamer> pools;

    public HistoryPoolsDTO(Map<String, FeedbackVideoStreamer> pools) {
        this.pools = pools;
    }

    public Map getPools() {
        return this.pools;
    }

}

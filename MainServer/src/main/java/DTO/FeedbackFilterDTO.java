package DTO;

import java.util.List;

public class FeedbackFilterDTO {
    private String path;
    private List<String> filters;

    public FeedbackFilterDTO(String path, List<String> filters) {
        this.path = path;
        this.filters = filters;
    }

    public String getPath() {
        return path;
    }

    public List<String> getFilters() {
        return filters;
    }
}

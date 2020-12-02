package DTO;

import com.google.gson.Gson;

public class ActionResult<T> {

    Response response;
    T value;

    public ActionResult(Response response,T value) {
        this.response = response;
        this.value=value;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public T getValue() {
        return this.value;
    }

}

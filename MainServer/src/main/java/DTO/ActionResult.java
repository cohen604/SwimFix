package DTO;

public class ActionResult<T> {

    Response response;
    T value;

    public ActionResult(Response response,T value) {
        this.response = response;
        this.value=value;
    }

}

package Domain;

import DTO.UserDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User {

    private String uid;
    private String email;
    private String name;

    // <key, state> if state is null need to get him form dao.
    private Map<String, State> states;

    public User(UserDTO userDTO) {
        this.uid = userDTO.getUid();
        this.email = userDTO.getEmail();
        this.name = userDTO.getName();
        this.states = new HashMap<>();
    }

    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.states = new HashMap<>();
    }

    public User(String uid, String email, String name, List<String> keys) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.states = new HashMap<>();
        for(String key: keys) {
            this.states.put(key, null);
        }
    }

    public void addState(State state) {
        String key = state.getState();
        this.states.put(key, state);
    }

    //TODO:
    void sortStatesByPriority(Map<String, State> states) {
        this.states = states;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getStateKeys() {
        return this.states.keySet();
    }

}

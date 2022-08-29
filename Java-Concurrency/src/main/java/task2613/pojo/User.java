package task2613.pojo;


import lombok.Data;

@Data
public class User {
    private int id;
    private boolean hasTicket;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, boolean hasTicket) {
        this(id);
        this.hasTicket = hasTicket;
    }
}

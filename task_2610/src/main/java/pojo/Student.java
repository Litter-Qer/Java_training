package pojo;

public class Student {
    private final Integer id;
    private String name;
    private String gender;

    public Student() {
        this.id = 10000;
    }

    public Student(String name) {
        this();
        this.name = name;
    }

    public Student(String name, String gender) {
        this(name);
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

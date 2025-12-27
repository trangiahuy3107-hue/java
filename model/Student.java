package model;

public class Student {
    private String id;
    private String name;
    private String clazz;

    public Student(String id, String name, String clazz) {
        this.id = id;
        this.name = name;
        this.clazz = clazz;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getClazz() { return clazz; }
}

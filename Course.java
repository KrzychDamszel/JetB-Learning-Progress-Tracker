package tracker;

public enum Course {

    JAVA("Java", 600), DSA("DSA", 400), DATABASES("Databases", 480), SPRING("Spring", 550);

    String name;
    int points;

    Course(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
}

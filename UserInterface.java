package tracker;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Scanner;

import static tracker.Descriptions.errorDescriptions;
import static tracker.Descriptions.userAction;
import static tracker.Statistics.*;

public class UserInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String nameRegex = "[a-zA-z]+([ '-][a-zA-Z]+)*";
    private static final String emailRegex = "[A-Za-z0-9.]+@[A-Za-z0-9]+\\.[a-zA-Z0-9]+";
    private static final LinkedList<Student> students = new LinkedList<>();
    private static int id = 1_000;


    public static void launchUI() {
        String command;
        System.out.println("Learning Progress Tracker");
        boolean end = true;
        while (end) {
            command = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (userAction.containsValue(command)) {
                if (userAction.get("addStudent").equals(command)) {
                    int num = 0;
                    boolean newStudent = true;
                    System.out.println("Enter student credentials or 'back' to return:");
                    while (newStudent) {
                        String lineData = scanner.nextLine().trim();
                        if (!"back".equals(lineData)) {
                            if (addStudents(lineData)) {
                                num++;
                            }
                        } else {
                            System.out.printf("Total %d students have been added.\n", num);
                            newStudent = false;
                        }
                    }
                }

                if (userAction.get("list").equals(command)) {
                    if (!students.isEmpty()) {
                        System.out.println("Students:");
                        for (Student elem : students) {
                            System.out.println(elem.getId());
                        }
                    } else {
                        System.out.println("No students found");
                    }
                }

                if (userAction.get("addPoint").equals(command)) {
                    boolean newPoints = true;
                    System.out.println("Enter an id and points or 'back' to return:");
                    while (newPoints) {
                        String idData = scanner.next();
                        String lineData = scanner.nextLine().trim();
                        if (!"back".equals(idData)) {
                            addPoints(idData, lineData);
                        } else {
                            newPoints = false;
                        }
                    }
                }

                if (userAction.get("find").equals(command)) {
                    boolean find = true;
                    System.out.println("Enter an id or 'back' to return:");
                    while (find) {
                        String idData = scanner.nextLine().trim();
                        if (!"back".equals(idData)) {
                            viewPoints(idData);
                        } else {
                            find = false;
                        }
                    }
                }
                if (userAction.get("statistics").equals(command)) {
                    boolean stat = true;
                    System.out.println("Type the name of a course to see details or 'back' to quit:");
                    printStatInfo(students);
                    while (stat) {
                        String course = scanner.nextLine().trim();
                        if (!"back".equals(course)) {
                            course = course.toLowerCase(Locale.ROOT);
                            switch (course) {
                                case "java":
                                    System.out.println("Java");
                                    System.out.println("id    points    completed");
                                    showCourse("java", students);
                                    break;
                                case "dsa":
                                    System.out.println("DSA");
                                    System.out.println("id    points    completed");
                                    showCourse("dsa", students);
                                    break;
                                case "databases":
                                    System.out.println("Databases");
                                    System.out.println("id    points    completed");
                                    showCourse("databases", students);
                                    break;
                                case "spring":
                                    System.out.println("Spring");
                                    System.out.println("id    points    completed");
                                    showCourse("spring", students);
                                    break;
                                default:
                                    System.out.println("Unknown course.");
                                    break;
                            }
                        } else {
                            stat = false;
                        }
                    }
                }

                if (userAction.get("notify").equals(command)) {
                    notity(students);
                }
            } else {
                display(command);
            }
            if ("exit".equals(command)) {
                end = false;
            }
        }
        System.out.println("Bye!");
    }

    public static void display(String command) {
        if (command.isEmpty()) {
            System.out.println("No input.");
            return;
        }
        if ("back".equals(command)) {
            System.out.println("Enter 'exit' to exit the program.");
        } else {
            if (!"exit".equals(command)) {
                System.out.println("Unknown command!");
            }
        }
    }

    public static boolean addStudents(String lineData) {
        String[] studentInput = lineData.split(" ");
        if (studentInput.length < 3) {
            System.out.println(errorDescriptions.get("incorCreden"));
            return false;
        }
        String firstName = studentInput[0];
        String lastName = String.join(" ", Arrays.copyOfRange(studentInput, 1, studentInput.length - 1));
        String email = studentInput[studentInput.length - 1];

        if (!isCorrectFirstName(firstName)) {
            System.out.println(errorDescriptions.get("incorFirstName"));
            return false;
        }

        if (!isCorrectLastName(lastName)) {
            System.out.println(errorDescriptions.get("incorLastName"));
            return false;
        }

        if (!isCorrectEmail(email)) {
            System.out.println(errorDescriptions.get("incorEmail"));
            return false;
        }

        if (students.stream()
                .anyMatch(elem -> elem.getEmailAddress().equals(email))) {
            System.out.println(errorDescriptions.get("doubleEmail"));
            return false;
        }

        if (!students.isEmpty()) {
            id = students.getLast().getId() + 1;
        }
        students.add(new Student(id, firstName, lastName, email));
        System.out.println("The student has been added.");
        return true;
    }

    public static boolean isCorrectFirstName(String firstName) {
        return (firstName.matches(nameRegex) && firstName.length() > 1);
    }

    public static boolean isCorrectLastName(String lastName) {
        return (lastName.matches(nameRegex) && lastName.length() > 1);
    }

    public static boolean isCorrectEmail(String email) {
        return email.matches(emailRegex);
    }

    public static void addPoints(String idData, String lineData) {

        try {
            if (students.stream()
                    .noneMatch(elem -> elem.getId() == Integer.parseInt(idData))) {
                System.out.printf("No student is found for id=%s.\n", idData);
                return;
            }
        } catch (NumberFormatException exp) {
            System.out.printf("No student is found for id=%s.\n", idData);
            return;
        }

        try {
            int[] pointsInput = Arrays.stream(lineData.split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            if (pointsInput.length != 4) {
                System.out.println(errorDescriptions.get("incorPoint"));
                return;
            }

            if (Arrays.stream(pointsInput)
                    .anyMatch(elem -> elem < 0)) {
                System.out.println(errorDescriptions.get("incorPoint"));
                return;
            }

            pointsUpdate(idData, pointsInput);

        } catch (NumberFormatException exp) {
            System.out.println(errorDescriptions.get("incorPoint"));
        }

    }

    public static void viewPoints(String idData) {
        try {
            if (students.stream()
                    .noneMatch(elem -> elem.getId() == Integer.parseInt(idData))) {
                System.out.printf("No student is found for id=%s.\n", idData);
                return;
            }
        } catch (NumberFormatException exp) {
            System.out.printf("No student is found for id=%s.\n", idData);
            return;
        }

        Student student = findStudent(Integer.parseInt(idData));

        int id = student.getId();
        int java = student.getJava().stream().mapToInt(el -> el).sum();
        int dsa = student.getDsa().stream().mapToInt(el -> el).sum();
        int database = student.getDatabase().stream().mapToInt(el -> el).sum();
        int spring = student.getSpring().stream().mapToInt(el -> el).sum();

        String view = String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d"
                , id, java, dsa, database, spring);

        System.out.println(view);
    }

    public static void pointsUpdate(String id, int[] pointsInput) {
        Student student = findStudent(Integer.parseInt(id));
        student.getJava().add(pointsInput[0]);
        student.getDsa().add(pointsInput[1]);
        student.getDatabase().add(pointsInput[2]);
        student.getSpring().add(pointsInput[3]);

        System.out.println("Points updated.");
    }

    public static Student findStudent(int id) {
        Student temp = null;
        for (Student elem : students) {
            if (elem.getId() == id) {
                temp = elem;
                break;
            }
        }
        return temp;
    }
}

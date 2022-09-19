package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {


    static void printStatInfo(LinkedList<Student> students) {

        Set<Map.Entry<String, Long>> courseSet = courses(students);

        long numMostPopular = courseSet.iterator().next().getValue();
        if (numMostPopular == 0) {
            showIntro("n/a", "n/a", "n/a", "n/a", "n/a", "n/a");
            return;
        }

        String mostPopular = "";
        for (Map.Entry<String, Long> elem : courseSet) {
            if (elem.getValue() == numMostPopular) {
                mostPopular = mostPopular + elem.getKey() + ", ";
            }
        }
        mostPopular = mostPopular.substring(0, mostPopular.lastIndexOf(", "));

        long numLeastPopular = computeLeastPopular(courseSet);
        String leastPopular = "";
        if (numLeastPopular == numMostPopular) {
            leastPopular = "n/a";
        } else {
            for (Map.Entry<String, Long> elem : courseSet) {
                if (elem.getValue() == numLeastPopular) {
                    leastPopular = leastPopular + elem.getKey() + ", ";
                }
            }
            leastPopular = leastPopular.substring(0, leastPopular.lastIndexOf(", "));
        }

        Set<Map.Entry<String, Long>> tasksSet = tasks(students);

        long numMostTaskPopular = tasksSet.iterator().next().getValue();
        String mostTaskPopular = "";
        for (Map.Entry<String, Long> elem : tasksSet) {
            if (elem.getValue() == numMostTaskPopular) {
                mostTaskPopular = mostTaskPopular + elem.getKey() + ", ";
            }
        }
        mostTaskPopular = mostTaskPopular.substring(0, mostTaskPopular.lastIndexOf(", "));

        long numLeastTaskPopular = computeLeastPopularTask(tasksSet);
        String leastTaskPopular = "";
        if (numLeastTaskPopular == numMostTaskPopular) {
            leastTaskPopular = "n/a";
        } else {
            for (Map.Entry<String, Long> elem : tasksSet) {
                if (elem.getValue() == numLeastTaskPopular) {
                    leastTaskPopular = leastTaskPopular + elem.getKey() + ", ";
                }
            }
            leastTaskPopular = leastTaskPopular.substring(0, leastTaskPopular.lastIndexOf(", "));
        }

        Set<Map.Entry<String, Double>> averageSet = averages(students);

        double numEasiest = averageSet.iterator().next().getValue();
        String easiest = "";
        for (Map.Entry<String, Double> elem : averageSet) {
            if (elem.getValue() == numEasiest) {
                easiest = easiest + elem.getKey() + ", ";
            }
        }
        easiest = easiest.substring(0, easiest.lastIndexOf(", "));

        double numHardest = computeLeastPopularDouble(averageSet);
        String hardest = "";
        if (numHardest == numEasiest) {
            hardest = "n/a";
        } else {
            for (Map.Entry<String, Double> elem : averageSet) {
                if (elem.getValue() == numHardest) {
                    hardest = hardest + elem.getKey() + ", ";
                }
            }
            hardest = hardest.substring(0, hardest.lastIndexOf(", "));
        }

        showIntro(mostPopular, leastPopular, mostTaskPopular, leastTaskPopular, easiest, hardest);

    }

    static Set<Map.Entry<String, Long>> courses(LinkedList<Student> students) {
        Map<String, Long> courseMap = new HashMap<>();

        courseMap.put(Course.JAVA.getName(), students.stream()
                .map(Student::getJava)
                .filter(el -> el.stream().mapToInt(Integer::intValue).sum() > 0)
                .count());
        courseMap.put(Course.DSA.getName(), students.stream()
                .map(Student::getDsa)
                .filter(el -> el.stream().mapToInt(Integer::intValue).sum() > 0)
                .count());
        courseMap.put(Course.DATABASES.getName(), students.stream()
                .map(Student::getDatabase)
                .filter(el -> el.stream().mapToInt(Integer::intValue).sum() > 0)
                .count());
        courseMap.put(Course.SPRING.getName(), students.stream()
                .map(Student::getSpring)
                .filter(el -> el.stream().mapToInt(Integer::intValue).sum() > 0)
                .count());
        return courseMap.entrySet().stream()
                .sorted((Collections.reverseOrder(Map.Entry.comparingByValue())))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static Set<Map.Entry<String, Long>> tasks(LinkedList<Student> students) {
        Map<String, Long> tasksMap = new HashMap<>();

        tasksMap.put(Course.JAVA.getName(), students.stream()
                .map(Student::getJava)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum));
        tasksMap.put(Course.DSA.getName(), students.stream()
                .map(Student::getDsa)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum));
        tasksMap.put(Course.DATABASES.getName(), students.stream()
                .map(Student::getDatabase)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum));
        tasksMap.put(Course.SPRING.getName(), students.stream()
                .map(Student::getSpring)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum));
        return tasksMap.entrySet().stream()
                .sorted((Collections.reverseOrder(Map.Entry.comparingByValue())))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static Set<Map.Entry<String, Double>> averages(LinkedList<Student> students) {
        Map<String, Double> averagesMap = new HashMap<>();

        long sumJava = students.stream()
                .map(Student::getJava)
                .map(el -> el.stream().mapToLong(Integer::longValue).sum())
                .reduce(0L, Long::sum);

        long numJava = students.stream()
                .map(Student::getJava)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum);

        double avgJava = numJava != 0 ? (double) sumJava / numJava : 0;
        averagesMap.put(Course.JAVA.getName(), avgJava);

        long sumDSA = students.stream()
                .map(Student::getDsa)
                .map(el -> el.stream().mapToLong(Integer::longValue).sum())
                .reduce(0L, Long::sum);

        long numDSA = students.stream()
                .map(Student::getDsa)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum);

        double avgDSA = numDSA != 0 ? (double) sumDSA / numDSA : 0;
        averagesMap.put(Course.DSA.getName(), avgDSA);

        long sumDatabases = students.stream()
                .map(Student::getDatabase)
                .map(el -> el.stream().mapToLong(Integer::longValue).sum())
                .reduce(0L, Long::sum);

        long numDatabases = students.stream()
                .map(Student::getDatabase)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum);

        double avgDatabases = numDatabases != 0 ? (double) sumDatabases / numDatabases : 0;
        averagesMap.put(Course.DATABASES.getName(), avgDatabases);

        long sumSpring = students.stream()
                .map(Student::getSpring)
                .map(el -> el.stream().mapToLong(Integer::longValue).sum())
                .reduce(0L, Long::sum);

        long numSpring = students.stream()
                .map(Student::getSpring)
                .map(el -> el.stream().filter(e -> e > 0).count())
                .reduce(0L, Long::sum);

        double avgSpring = numSpring != 0 ? (double) sumSpring / numSpring : 0;
        averagesMap.put(Course.SPRING.getName(), avgSpring);

        return averagesMap.entrySet().stream()
                .sorted((Collections.reverseOrder(Map.Entry.comparingByValue())))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static long computeLeastPopular(Set<Map.Entry<String, Long>> entrySet) {
        Object[] array = entrySet.toArray();
        int idx = array[array.length - 1].toString().indexOf("=");
        return Long.parseLong(array[array.length - 1].toString().substring(idx + 1));
    }


    static long computeLeastPopularTask(Set<Map.Entry<String, Long>> entrySet) {
        Object[] array = entrySet.stream()
                .filter(elem -> elem.getValue() != 0)
                .toArray();
        int idx = array[array.length - 1].toString().indexOf("=");
        return Long.parseLong(array[array.length - 1].toString().substring(idx + 1));
    }

    static double computeLeastPopularDouble(Set<Map.Entry<String, Double>> entrySet) {
        Object[] array = entrySet.stream()
                .filter(elem -> elem.getValue() != 0.0)
                .toArray();
        int idx = array[array.length - 1].toString().indexOf("=");
        return Double.parseDouble(array[array.length - 1].toString().substring(idx + 1));
    }


    static void showIntro(String s1, String s2, String s3, String s4, String s5, String s6) {
        System.out.println("Most popular: " + s1);
        System.out.println("Least popular: " + s2);
        System.out.println("Highest activity: " + s3);
        System.out.println("Lowest activity: " + s4);
        System.out.println("Easiest course: " + s5);
        System.out.println("Hardest course: " + s6);
    }

    static void showCourse(String course, LinkedList<Student> students) {
        Map<Integer, Integer> courseStat = new LinkedHashMap<>();

        switch (course) {
            case "java":
                for (Student elem : students) {
                    int sum = elem.getJava().stream()
                            .mapToInt(Integer::intValue).sum();
                    if (sum > 0) {
                        courseStat.put(elem.getId(), sum);
                    }
                }
                if (courseStat.size() > 0) {
                    Set<Map.Entry<Integer, Integer>> entries = courseStat.entrySet().stream()
                            .sorted(Map.Entry.<Integer, Integer>comparingByValue()
                                    .reversed().thenComparing(Map.Entry.comparingByKey()))
                            .collect(Collectors.toCollection(LinkedHashSet::new));

                    for (Map.Entry<Integer, Integer> elem : entries) {
                        String completed = new BigDecimal(elem.getValue())
                                .divide(new BigDecimal(Course.JAVA.getPoints()), 3, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(100)).setScale(1) + "%";
                        System.out.printf("%-6d%-10d%-9s\n", elem.getKey(), elem.getValue(), completed);
                    }
                }
                break;
            case "dsa":
                for (Student elem : students) {
                    int sum = elem.getDsa().stream()
                            .mapToInt(Integer::intValue).sum();
                    if (sum > 0) {
                        courseStat.put(elem.getId(), sum);
                    }
                }
                if (courseStat.size() > 0) {
                    Set<Map.Entry<Integer, Integer>> entries = courseStat.entrySet().stream()
                            .sorted(Map.Entry.<Integer, Integer>comparingByValue()
                                    .reversed().thenComparing(Map.Entry.comparingByKey()))
                            .collect(Collectors.toCollection(LinkedHashSet::new));

                    for (Map.Entry<Integer, Integer> elem : entries) {
                        String completed = new BigDecimal(elem.getValue())
                                .divide(new BigDecimal(Course.DSA.getPoints()), 3, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(100)).setScale(1) + "%";
                        System.out.printf("%-6d%-10d%-9s\n", elem.getKey(), elem.getValue(), completed);
                    }
                }
                break;
            case "databases":
                for (Student elem : students) {
                    int sum = elem.getDatabase().stream()
                            .mapToInt(Integer::intValue).sum();
                    if (sum > 0) {
                        courseStat.put(elem.getId(), sum);
                    }
                }
                if (courseStat.size() > 0) {
                    Set<Map.Entry<Integer, Integer>> entries = courseStat.entrySet().stream()
                            .sorted(Map.Entry.<Integer, Integer>comparingByValue()
                                    .reversed().thenComparing(Map.Entry.comparingByKey()))
                            .collect(Collectors.toCollection(LinkedHashSet::new));

                    for (Map.Entry<Integer, Integer> elem : entries) {
                        String completed = new BigDecimal(elem.getValue())
                                .divide(new BigDecimal(Course.DATABASES.getPoints()), 3, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(100)).setScale(1) + "%";
                        System.out.printf("%-6d%-10d%-9s\n", elem.getKey(), elem.getValue(), completed);
                    }
                }
                break;
            case "spring":
                for (Student elem : students) {
                    int sum = elem.getSpring().stream()
                            .mapToInt(Integer::intValue).sum();
                    if (sum > 0) {
                        courseStat.put(elem.getId(), sum);
                    }
                }
                if (courseStat.size() > 0) {
                    Set<Map.Entry<Integer, Integer>> entries = courseStat.entrySet().stream()
                            .sorted(Map.Entry.<Integer, Integer>comparingByValue()
                                    .reversed().thenComparing(Map.Entry.comparingByKey()))
                            .collect(Collectors.toCollection(LinkedHashSet::new));

                    for (Map.Entry<Integer, Integer> elem : entries) {
                        String completed = new BigDecimal(elem.getValue())
                                .divide(new BigDecimal(Course.SPRING.getPoints()), 3, RoundingMode.HALF_UP)
                                .multiply(new BigDecimal(100)).setScale(1) + "%";
                        System.out.printf("%-6d%-10d%-9s\n", elem.getKey(), elem.getValue(), completed);
                    }
                }
                break;
            default:
                break;
        }
    }

    static void notity(LinkedList<Student> students) {
        boolean completed;
        int numStudents = 0;
        for (Student elem : students) {
            completed = false;
            if ("no".equals(elem.getCompletedJava())) {
                int javaPoints = elem.getJava().stream()
                        .mapToInt(Integer::intValue)
                        .sum();
                if (javaPoints >= Course.JAVA.getPoints()) {
                    elem.setCompletedJava("yes");
                    send(elem.getEmailAddress(), elem.getFirstName(), elem.getLastName(), Course.JAVA.getName());
                    completed = true;
                }
            }
            if ("no".equals(elem.getCompletedDSA())) {
                int dsaPoints = elem.getDsa().stream()
                        .mapToInt(Integer::intValue)
                        .sum();
                if (dsaPoints >= Course.DSA.getPoints()) {
                    elem.setCompletedDSA("yes");
                    send(elem.getEmailAddress(), elem.getFirstName(), elem.getLastName(), Course.DSA.getName());
                    completed = true;
                }
            }
            if ("no".equals(elem.getCompletedDatabases())) {
                int databasesPoints = elem.getDatabase().stream()
                        .mapToInt(Integer::intValue)
                        .sum();
                if (databasesPoints >= Course.DATABASES.getPoints()) {
                    elem.setCompletedDatabases("yes");
                    send(elem.getEmailAddress(), elem.getFirstName(), elem.getLastName(), Course.DATABASES.getName());
                    completed = true;
                }
            }
            if ("no".equals(elem.getCompletedSpring())) {
                int springPoints = elem.getSpring().stream()
                        .mapToInt(Integer::intValue)
                        .sum();
                if (springPoints >= Course.SPRING.getPoints()) {
                    elem.setCompletedSpring("yes");
                    send(elem.getEmailAddress(), elem.getFirstName(), elem.getLastName(), Course.SPRING.getName());
                    completed = true;
                }
            }
            if (completed) {
                numStudents++;
            }
        }
        System.out.printf("Total %d students have been notified.\n", numStudents);
    }

    static void send(String emailAddress, String firstName, String lastName, String course) {
        System.out.printf("To: %s\n", emailAddress);
        System.out.println("Re: Your Learning Progress");
        System.out.printf("Hello, %s %s! You have accomplished our %s course!\n", firstName, lastName, course);
    }
}

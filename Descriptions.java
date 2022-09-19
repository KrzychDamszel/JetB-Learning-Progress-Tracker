package tracker;

import java.util.Map;

public class Descriptions {

    static Map<String, String> userAction = Map.of(
            "addStudent", "add students",
            "list", "list",
            "addPoint", "add points",
            "find", "find",
            "statistics", "statistics",
            "notify", "notify"

    );

    static Map<String, String> errorDescriptions = Map.of(
            "incorCreden", "Incorrect credentials.",
            "incorFirstName", "Incorrect first name.",
            "incorLastName", "Incorrect last name.",
            "incorEmail", "Incorrect email.",
            "doubleEmail", "This email is already taken.",
            "incorPoint", "Incorrect points format."
    );

}

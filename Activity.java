import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Activity {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private static String activitiesFileName = "activities.txt";

    public Activity(String activityName, LocalTime activityStartTime, LocalTime activityEndTime) {
        this.name = activityName;
        this.startTime = activityStartTime;
        this.endTime = activityEndTime;
    }

    private static void readActivitiesFile() {
        File activityFile = new File(activitiesFileName);
        // to hold file data
        List<String> fileLines = null;

        try {
            // if file doesn't exist, new file with same name is created
            activityFile.createNewFile();

            // read all data from file
            fileLines = Files.readAllLines(Paths.get(activitiesFileName));
        }
        catch (IOException exception) {
            // show an error window
        }
        catch (SecurityException exception) {
            // show an error window
        }

        // for testing
        System.out.println(fileLines);
        String ab = fileLines.get(1);
        System.out.println(ab);
    }

    public static Activity[] getActivities() {
        readActivitiesFile();
        return new Activity[2];
    }
}

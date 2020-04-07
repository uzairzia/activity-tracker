import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private static String activitiesFileName = "activities.txt";
    private static ArrayList<Activity> activitiesList = new ArrayList<>();

    private Activity(String activityName, LocalTime activityStartTime, LocalTime activityEndTime) {
        this.name = activityName;
        this.startTime = activityStartTime;
        this.endTime = activityEndTime;
    }

    private Activity(String[] activityData) {
        // call the constructor with three parameters
        this(activityData[1],
                LocalTime.parse(activityData[0], timeFormat),
                LocalTime.parse(activityData[2], timeFormat));
    }

    public String getName() {
        return this.name;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
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

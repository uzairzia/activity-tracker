import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private final String name;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    private static final String activitiesFileName = "activities.txt";
    // list of activities
    private static final ArrayList<Activity> activitiesList = new ArrayList<>();

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
            // return to avoid NullPointerException
            return;
        }
        catch (SecurityException exception) {
            // show an error window
            // return to avoid NullPointerException
            return;
        }

        for (String line : fileLines) {
            if (line.isEmpty()) {
                continue;
            }
            // individual activity fields are separated by commas in file
            String[] activityData = line.split(",");

            // 3 fields required to create Activity object
            if (activityData.length != 3) {
                continue;
            }
            activitiesList.add(new Activity(activityData));
        }
    }

    public static ArrayList<Activity> getActivities() {
        Activity.readActivitiesFile();
        return Activity.activitiesList;
    }

    public static Activity getNoActivityInstance() {
        return new Activity("No Activity", LocalTime.MIDNIGHT, LocalTime.MIDNIGHT);
    }

    public static boolean isOverLappingStoredActivities(LocalTime newActivityStartTime, LocalTime newActivityEndTime) {
        // midnight in between start and end
        if (newActivityStartTime.isAfter(newActivityEndTime)) {
            // check overlap from start time to midnight - 1 (23:59)
            boolean isOverlappingBeforeMidnight =
                    isOverLappingStoredActivities(newActivityStartTime, LocalTime.MIDNIGHT.minusMinutes(1));
            // check overlap from midnight (00:00) to end time
            boolean isOverlappingAfterMidnight =
                    isOverLappingStoredActivities(LocalTime.MIDNIGHT,newActivityEndTime);

            return isOverlappingBeforeMidnight || isOverlappingAfterMidnight;
        }

        ArrayList<Activity> activitiesList = getActivities();
        // previous stored actvity
        LocalTime storedActivityStartTime = null;
        LocalTime storedActivityEndTime = null;

        for (Activity activity : activitiesList) {
            storedActivityStartTime = activity.getStartTime();
            storedActivityEndTime = activity.getEndTime();

            boolean isNewStartBeforeStoredStart = newActivityStartTime.isBefore(storedActivityStartTime);
            boolean isNewEndAfterStoredEnd = newActivityEndTime.isAfter(storedActivityEndTime);
            if (isNewStartBeforeStoredStart && isNewEndAfterStoredEnd ) {
                return true;
            }
            else if (!isNewStartBeforeStoredStart && !isNewEndAfterStoredEnd) {
                return true;
            }

            boolean isNewEndAfterStoredStart = newActivityEndTime.isAfter(storedActivityStartTime);
            if (isNewStartBeforeStoredStart && isNewEndAfterStoredStart ) {
                return true;
            }

            boolean isNewStartBeforeStoredEnd = newActivityStartTime.isBefore(storedActivityEndTime);
            if (!isNewStartBeforeStoredStart && isNewStartBeforeStoredEnd) {
                return true;
            }
        }

        return false;
    }
}
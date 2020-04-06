import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Activity {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public Activity(String activityName, LocalTime activityStartTime, LocalTime activityEndTime) {
        this.name = activityName;
        this.startTime = activityStartTime;
        this.endTime = activityEndTime;
    }
}

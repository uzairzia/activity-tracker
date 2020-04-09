import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.awt.color.*;
import java.util.ArrayList;

public class Timetable {
    private JFrame mainFrame = new JFrame("Timetable");
    private JPanel mainPanel = new JPanel(new GridBagLayout());
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        Timetable timetable = new Timetable();
    }

    private Timetable() {
        this.setWindowParameters(JFrame.EXIT_ON_CLOSE, 500, 500, true );
        this.displayCurrentTime();
        this.displayCurrentActivity();
        this.mainFrame.add(mainPanel, "Center");
    }

    private void setTime(LocalTime time, JLabel timeLabel) {
        timeLabel.setText(time.format(timeFormat));
    }

    private void setCurrentActivityName(String currentActivityName, JLabel currentActivityLabel) {
        currentActivityLabel.setText(currentActivityName);
    }

    private ArrayList<Activity> getActivities() {
        return Activity.getActivities();
    }

    private LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    private Activity getCurrentActivity() {
        ArrayList<Activity> activitiesList = this.getActivities();
        LocalTime currentTime = getCurrentTime();
        Activity currentActivity = null;
        LocalTime activityStartTime = null;
        LocalTime activityEndTime = null;

        for (Activity activity: activitiesList) {
            activityStartTime = activity.getStartTime();
            activityEndTime = activity.getEndTime();

            // current time is between an activity's start and end time
            if (currentTime.isAfter(activityStartTime)
                    && currentTime.isBefore(activityEndTime)) {
                currentActivity = activity;
                break;
            }
        }

        // if no activity at this time
        if (currentActivity == null) {
            return Activity.getNoActivityInstance();
        }

        return currentActivity;
    }

    private void displayCurrentTime() {
        // add label to panel
        JPanel currentTimePanel = new JPanel();
        JLabel currentTimeLabel = new JLabel();
        currentTimePanel.add(currentTimeLabel);

        // add panel displaying time to main panel
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        mainPanel.add(currentTimePanel, gridConstraints);

        // set current time after every fixed interval
        ActionListener timerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setTime(getCurrentTime(), currentTimeLabel);
            }
        };
        Timer currentTimeTimer = new Timer(1000, timerListener);
        // do not wait initially
        currentTimeTimer.setInitialDelay(0);
        currentTimeTimer.start();
    }

    private JLabel createStartTimeGrid() {
        JPanel startTimePanel = new JPanel();
        // Dummy start time
        String startTimeText = "Started: 06:00";
        JLabel startTimeLabel = new JLabel(startTimeText);
        startTimePanel.add(startTimeLabel);

        // position the time panel in the grid of the main panel
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        mainPanel.add(startTimePanel, gridConstraints);

        return startTimeLabel;
    }

    private JLabel createEndTimeGrid() {
        JPanel endTimePanel = new JPanel();
        // Dummy end time
        String endTimeText = "Ended: 07:00";
        JLabel endTimeLabel = new JLabel(endTimeText);
        endTimePanel.add(endTimeLabel);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        mainPanel.add(endTimePanel, gridConstraints);

        return endTimeLabel;
    }

    private JLabel createNameGrid() {
        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel();
        namePanel.add(nameLabel);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        mainPanel.add(namePanel, gridConstraints);

        return nameLabel;
    }

    private void displayCurrentActivity() {
        // get current activity and display it's data
        Activity currentActivity = this.getCurrentActivity();

        JLabel activityNameLabel = this.createNameGrid();
        JLabel activityStartTimeLabel = this.createStartTimeGrid();
        JLabel activityEndTimeLabel = this.createEndTimeGrid();

        this.setCurrentActivityName(currentActivity.getName(), activityNameLabel);
        this.setTime(currentActivity.getStartTime(), activityStartTimeLabel);
        this.setTime(currentActivity.getEndTime(), activityEndTimeLabel);
    }

    private void setWindowParameters(int onClose, int width, int height, boolean isVisible) {
        // operation to perform when window closed
        mainFrame.setDefaultCloseOperation(onClose);
        mainFrame.setSize(width,height);
        mainFrame.setVisible(isVisible);
    }
}
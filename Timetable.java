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
    private final JFrame mainFrame = new JFrame("Timetable");
    private final JPanel mainPanel = new JPanel(new GridBagLayout());
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) {
        Timetable timetable = new Timetable();
    }

    private Timetable() {
        this.setWindowParameters(JFrame.EXIT_ON_CLOSE, 300, 100, true );

        this.displayCurrentTime();
        this.displayCurrentActivity();
        this.displayNextActivity();
        this.displayMenuBar();

        this.mainFrame.add(mainPanel, "Center");
        this.mainFrame.pack();
    }

    private void setTime(LocalTime time, JLabel timeLabel) {
        timeLabel.setText(time.format(timeFormat));
    }

    private void setActivityName(String activityName, JLabel activityLabel) {
        activityLabel.setText(activityName);
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
        // span two columns
        gridConstraints.gridwidth = 2;

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

    private JLabel createNameGrid(int gridx, int gridy) {
        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel();
        namePanel.add(nameLabel);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = gridx;
        gridConstraints.gridy = gridy;
        mainPanel.add(namePanel, gridConstraints);

        return nameLabel;
    }

    private JLabel createStartTimeGrid(int gridx, int gridy) {
        JPanel startTimePanel = new JPanel();
        JLabel startTimeLabel = new JLabel();
        startTimePanel.add(startTimeLabel);

        // position the time panel in the grid of the main panel
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = gridx;
        gridConstraints.gridy = gridy;
        mainPanel.add(startTimePanel, gridConstraints);

        return startTimeLabel;
    }

    private JLabel createEndTimeGrid(int gridx, int gridy) {
        JPanel endTimePanel = new JPanel();
        JLabel endTimeLabel = new JLabel();
        endTimePanel.add(endTimeLabel);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = gridx;
        gridConstraints.gridy = gridy;
        mainPanel.add(endTimePanel, gridConstraints);

        return endTimeLabel;
    }

    private void displayActivity(Activity activity, String activityType) {
        // row of grid
        int gridy;

        // current or next activity
        if (activityType.equals("current")) {
            gridy = 1;
        }
        else {
            gridy = 2;
        }

        JLabel activityTypeLabel = this.createNameGrid(0, gridy);
        JLabel activityNameLabel = this.createNameGrid(2, gridy);
        JLabel activityStartTimeLabel = this.createStartTimeGrid(1, gridy);
        JLabel activityEndTimeLabel = this.createEndTimeGrid(3, gridy);

        this.setActivityName(activityType, activityTypeLabel);
        this.setActivityName(activity.getName(), activityNameLabel);
        this.setTime(activity.getStartTime(), activityStartTimeLabel);
        this.setTime(activity.getEndTime(), activityEndTimeLabel);
    }

    private void displayCurrentActivity() {
        Activity currentActivity = this.getCurrentActivity();
        this.displayActivity(currentActivity, "current");
    }

    private void displayNextActivity() {
        // dummy test value
        Activity noActivityInstance = Activity.getNoActivityInstance();
        this.displayActivity(noActivityInstance, "next");
    }

    private void displayMenuBar() {
        // create menu bar and its items
        JMenuBar menuBar = new JMenuBar();
        JMenu actionsMenu = new JMenu("Actions");
        JMenuItem addActivityItem = new JMenuItem("Add Activity");
        JMenuItem editActivityItem = new JMenuItem("Edit Activity");
        JMenuItem showAllActivitiesItem = new JMenuItem("Show All Activities");

        actionsMenu.add(addActivityItem);
        actionsMenu.add(editActivityItem);
        actionsMenu.add(showAllActivitiesItem);
        menuBar.add(actionsMenu);

        this.mainFrame.setJMenuBar(menuBar);
    }

    private void setWindowParameters(int onClose, int width, int height, boolean isVisible) {
        // operation to perform when window closed
        mainFrame.setDefaultCloseOperation(onClose);
        mainFrame.setVisible(isVisible);
    }
}
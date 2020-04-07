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
        this.displayStartTime();
        this.displayEndTime();
        this.displayCurrentActivity();
        this.mainFrame.add(mainPanel, "Center");

        // for testing
        ArrayList<Activity> activitiesList = this.getActivities();
        for (Activity act: activitiesList) {
            System.out.println(act.getName());
        }
    }

    private void setCurrentTime(JLabel currentTimeLabel) {
        currentTimeLabel.setText(LocalTime.now().format(timeFormat));
    }

    private ArrayList<Activity> getActivities() {
        return Activity.getActivities();
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
                setCurrentTime(currentTimeLabel);
            }
        };
        Timer currentTimeTimer = new Timer(1000, timerListener);
        // do not wait initially
        currentTimeTimer.setInitialDelay(0);
        currentTimeTimer.start();
    }

    private void displayStartTime() {
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
    }

    private void displayEndTime() {
        JPanel endTimePanel = new JPanel();
        // Dummy end time
        String endTimeText = "Ended: 07:00";
        JLabel endTimeLabel = new JLabel(endTimeText);
        endTimePanel.add(endTimeLabel);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 2;
        gridConstraints.gridy = 1;
        mainPanel.add(endTimePanel, gridConstraints);
    }

    private void displayCurrentActivity() {
        JPanel activityPanel = new JPanel();
        // Dummy activity
        String activityText = "Office";
        JLabel activityLabel = new JLabel(activityText);
        activityPanel.add(activityLabel);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.gridx = 1;
        gridConstraints.gridy = 1;
        mainPanel.add(activityPanel, gridConstraints);
    }

    private void setWindowParameters(int onClose, int width, int height, boolean isVisible) {
        // operation to perform when window closed
        mainFrame.setDefaultCloseOperation(onClose);
        mainFrame.setSize(width,height);
        mainFrame.setVisible(isVisible);
    }
}
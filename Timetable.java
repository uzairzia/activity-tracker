import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.awt.color.*;

public class Timetable {
    private JFrame mainFrame = new JFrame("Timetable");
    private JPanel mainPanel = new JPanel(new GridBagLayout());
    // time format : "HH:mm"
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);

    public static void main(String[] args) {
        Timetable timetable = new Timetable();
    }

    private Timetable() {
        this.setWindowParameters(JFrame.EXIT_ON_CLOSE, 500, 500, true );
        this.displayCurrentTime();
        this.displayStartTime();
        this.displayEndTime();
        this.mainFrame.add(mainPanel, "Center");
    }

    private void setCurrentTime(JLabel currentTimeLabel) {
        currentTimeLabel.setText(LocalTime.now().format(timeFormat));
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
    
    private void setWindowParameters(int onClose, int width, int height, boolean isVisible) {
        // operation to perform when window closed
        mainFrame.setDefaultCloseOperation(onClose);
        mainFrame.setSize(width,height);
        mainFrame.setVisible(isVisible);
    }
}
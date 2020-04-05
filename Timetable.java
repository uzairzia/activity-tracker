import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Timetable {
    private JFrame timetableWindow = new JFrame("Timetable");
    // time format : "HH:mm"
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
    private JLabel currentTimeLabel = new JLabel();


    public static void main(String[] args) {
        Timetable timetable = new Timetable();
        timetable.setWindowParameters(JFrame.EXIT_ON_CLOSE, 500, 500, true );
    }

    private Timetable() {

        this.displayCurrentTime();
    }

    private void setCurrentTime() {
        currentTimeLabel.setText(LocalTime.now().format(timeFormat));
    }

    private void displayCurrentTime() {
        timetableWindow.add(currentTimeLabel);

        // set current time after every fixed interval
        ActionListener timerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setCurrentTime();
            }
        };
        Timer currentTimeTimer = new Timer(1000, timerListener);
        // do not wait initially
        currentTimeTimer.setInitialDelay(0);
        currentTimeTimer.start();
    }

    private void setWindowParameters(int onClose, int width, int height, boolean isVisible) {
        // operation to perform when window closed
        timetableWindow.setDefaultCloseOperation(onClose);
        timetableWindow.setSize(width,height);
        timetableWindow.setVisible(isVisible);
    }
}
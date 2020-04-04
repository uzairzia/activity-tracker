import javax.swing.JFrame;
import java.awt.*;

public class Timetable extends JFrame {
    public static void main(String[] args){
        Timetable timetable = new Timetable();

        // Exit window on "close" operation
        timetable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        timetable.setSize(500,500);
        timetable.setVisible(true);
    }
}

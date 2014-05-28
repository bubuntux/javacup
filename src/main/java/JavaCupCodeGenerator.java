import javax.swing.*;
import java.awt.*;
import org.dsaw.javacup.gui.assistant.AssistantFrame;

public class JavaCupCodeGenerator {

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        } catch (Exception ignored) {
        }
        AssistantFrame a = new AssistantFrame();
        a.setSize(new Dimension(710, 440));
        a.setLocationRelativeTo(null);
    }
}

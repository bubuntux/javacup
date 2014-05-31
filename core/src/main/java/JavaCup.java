import org.dsaw.javacup.gui.principal.PrincipalFrame;

import javax.swing.*;

public class JavaCup {

  public static void main(String[] args) throws Exception {
    try {
      UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
    } catch (Exception ignored) {
    }
    new PrincipalFrame();
  }
}
import javax.swing.*;
public class Main {
	 public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CollatzConjecture ui = new CollatzConjecture();
            ui.setVisible(true);
        });
    }
}
package metrosim.app;
import javax.swing.SwingUtilities; import metrosim.ui.MainWindow;
public final class MetroSimApp {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            var c = Bootstrap.init();
            var win = new MainWindow(c);
            win.setVisible(true);
        });
    }
}

package metrosim.ui;
import metrosim.infrastructure.SimulationFacade; import javax.swing.*;
public final class MainWindowMediator {
    private final SimulationFacade sim; private final JButton startBtn, pauseBtn;
    public MainWindowMediator(SimulationFacade sim, JButton startBtn, JButton pauseBtn){ this.sim=sim; this.startBtn=startBtn; this.pauseBtn=pauseBtn; wire(); }
    private void wire(){
        startBtn.addActionListener(e -> { try { sim.start(); } catch (Exception ex){ JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }});
        pauseBtn.addActionListener(e -> sim.pause());
    }
}

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Cobrinha extends JFrame {

    public Cobrinha() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Tabela());
               
        setResizable(false);
        pack();
        
        setTitle("CobrinhaV1.2");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Cobrinha();
            ex.setVisible(true);
        });
    }
}
package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.mainMVC;

public class View_Connexion {

    private JFrame frame;

    public View_Connexion() {
        frame = new JFrame();
        frame.setTitle("Connexion");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);

        JLabel titre = new JLabel("Entrez votre numéro");
        titre.setBounds(130, 30, 200, 25);
        frame.add(titre);

        JTextField txtNum = new JTextField();
        txtNum.setBounds(100, 80, 200, 30);
        frame.add(txtNum);

        JLabel infos = new JLabel("(A001, A002...)");
        infos.setBounds(155, 115, 100, 20);
        frame.add(infos);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(100, 155, 90, 30);
        frame.add(btnOk);

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.setBounds(210, 155, 90, 30);
        frame.add(btnQuitter);

        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String num = txtNum.getText();
                
                if (num.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Mets ton numéro stp");
                    return;
                }
                
                model.ADHERENT adherent = mainMVC.getM().findAdherent(num);
                
                if (adherent != null) {
                    mainMVC.getM().setAdherentConnecte(adherent);
                    frame.dispose();
                    new View_Accueil();
                } else {
                    JOptionPane.showMessageDialog(frame, "Numéro pas trouvé");
                    txtNum.setText("");
                }
            }
        });

        btnQuitter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.mainMVC;

public class View_Emprunt {

    private JFrame frame;

    public View_Emprunt() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Emprunter un livre");
        frame.setBounds(100, 100, 400, 250);
        frame.getContentPane().setLayout(null);

        JLabel lblTitre = new JLabel("EMPRUNTER UN LIVRE");
        lblTitre.setBounds(120, 20, 200, 30);
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblTitre);

        JLabel lblNumAdherent = new JLabel("Numéro adhérent :");
        lblNumAdherent.setBounds(50, 60, 200, 25);
        frame.getContentPane().add(lblNumAdherent);

        JTextField txtNumAdherent = new JTextField(controller.mainMVC.getM().getAdherentConnecte().getNum());
        txtNumAdherent.setBounds(50, 85, 200, 25);
        txtNumAdherent.setEditable(false);
        frame.getContentPane().add(txtNumAdherent);

        JLabel lblISBN = new JLabel("ISBN du livre :");
        lblISBN.setBounds(50, 120, 200, 25);
        frame.getContentPane().add(lblISBN);

        JTextField txtISBN = new JTextField();
        txtISBN.setBounds(50, 145, 200, 25);
        frame.getContentPane().add(txtISBN);

        JButton btnEmprunter = new JButton("Emprunter");
        btnEmprunter.setBounds(50, 180, 100, 30);
        frame.getContentPane().add(btnEmprunter);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.setBounds(160, 180, 100, 30);
        btnAnnuler.addActionListener(e -> frame.dispose());
        frame.getContentPane().add(btnAnnuler);

        btnEmprunter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numAdherent = txtNumAdherent.getText();
                String isbn = txtISBN.getText();
                
                if (isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer l'ISBN");
                    return;
                }
                
                try {
                    boolean succes = mainMVC.getM().emprunterLivre(isbn, numAdherent);
                    
                    if (succes) {
                        JOptionPane.showMessageDialog(frame, "Livre emprunté");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Erreur");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur de base de données");
                }
            }
        });
    }
}
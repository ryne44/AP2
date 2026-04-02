package view;

import javax.swing.*;
import controller.mainMVC;
import model.ADHERENT;

public class View_Compte {

    private JFrame frame;
    private ADHERENT adherent;

    public View_Compte(ADHERENT a) {
        // Sécurité : si a est null, on n'affiche rien pour éviter le crash
        if (a == null) {
            JOptionPane.showMessageDialog(null, "Erreur : Aucun adhérent connecté.");
            return;
        }
        this.adherent = a;
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Mon Compte");
        frame.setBounds(100, 100, 450, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitre = new JLabel("MON COMPTE");
        lblTitre.setBounds(120, 20, 200, 30);
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblTitre);

        // Numéro (Non modifiable)
        frame.getContentPane().add(new JLabel("Numéro :")).setBounds(50, 60, 100, 25);
        JLabel lblNumVal = new JLabel(adherent.getNum());
        lblNumVal.setBounds(160, 60, 200, 25);
        frame.getContentPane().add(lblNumVal);

        // Nom
        frame.getContentPane().add(new JLabel("Nom :")).setBounds(50, 90, 100, 25);
        JTextField txtNom = new JTextField(adherent.getNom());
        txtNom.setBounds(160, 90, 200, 25);
        frame.getContentPane().add(txtNom);

        // Prénom
        frame.getContentPane().add(new JLabel("Prénom :")).setBounds(50, 120, 100, 25);
        JTextField txtPrenom = new JTextField(adherent.getPrenom());
        txtPrenom.setBounds(160, 120, 200, 25);
        frame.getContentPane().add(txtPrenom);

        // Email
        frame.getContentPane().add(new JLabel("Email :")).setBounds(50, 150, 100, 25);
        JTextField txtEmail = new JTextField(adherent.getEmail());
        txtEmail.setBounds(160, 150, 200, 25);
        frame.getContentPane().add(txtEmail);

        // Bouton Enregistrer
        JButton btnSave = new JButton("Enregistrer");
        btnSave.setBounds(50, 200, 120, 30);
        btnSave.addActionListener(e -> {
            try {
                boolean ok = mainMVC.getM().updateAdherent(adherent.getNum(), txtNom.getText(), txtPrenom.getText(), txtEmail.getText());
                if (ok) JOptionPane.showMessageDialog(frame, "Informations mises à jour !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur SQL : " + ex.getMessage());
            }
        });
        frame.getContentPane().add(btnSave);

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBounds(50, 250, 100, 30);
        btnRetour.addActionListener(e -> frame.dispose());
        frame.getContentPane().add(btnRetour);
    }
}
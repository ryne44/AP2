package view;

import controller.mainMVC;
import model.ADHERENT;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View_Accueil {

    private JFrame frame;

    public View_Accueil() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Accueil - Bibliothèque");
        frame.setBounds(100, 100, 450, 350); // Hauteur augmentée pour loger le nouveau bouton
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 1. Bouton Catalogue
        JButton btnCatalogue = new JButton("CATALOGUE");
        btnCatalogue.setBounds(123, 30, 171, 30);
        btnCatalogue.addActionListener(e -> new View_Catalogue());
        frame.getContentPane().add(btnCatalogue);

        // 2. Bouton Emprunts
        JButton btnEmprunt = new JButton("Emprunter un Livre");
        btnEmprunt.setBounds(123, 75, 171, 30);
        btnEmprunt.addActionListener(e -> new View_Emprunt());
        frame.getContentPane().add(btnEmprunt);

        // 3. NOUVEAU : Bouton Restitution (View_LivresEmpruntes)
        JButton btnMesLivres = new JButton("Mes Emprunts / Retour");
        btnMesLivres.setBounds(123, 120, 171, 30);
        btnMesLivres.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ADHERENT actuel = mainMVC.getM().getAdherentConnecte();
                if (actuel != null) {
                    new View_LivresEmpruntes(actuel);
                }
            }
        });
        frame.getContentPane().add(btnMesLivres);

        // 4. Bouton Voir le Compte
        JButton btnCompte = new JButton("Voir le Compte");
        btnCompte.setBounds(123, 165, 171, 30);
        btnCompte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ADHERENT actuel = mainMVC.getM().getAdherentConnecte();
                new View_Compte(actuel);
            }
        });
        frame.getContentPane().add(btnCompte);

        // 5. Bouton Déconnexion
        JButton btnLogout = new JButton("Déconnexion");
        btnLogout.setBounds(123, 230, 171, 30);
        btnLogout.addActionListener(e -> {
            mainMVC.getM().setAdherentConnecte(null);
            frame.dispose();
            // Assure-toi que View_Connexion existe, sinon crée-la
            // new View_Connexion(); 
        });
        frame.getContentPane().add(btnLogout);
    }
}
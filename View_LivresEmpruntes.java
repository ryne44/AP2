package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.mainMVC;
import model.ADHERENT;
import model.LIVRE;
import java.util.ArrayList;

public class View_LivresEmpruntes {

    private JFrame frame;
    private ADHERENT adherent;
    private DefaultListModel<String> listModel;

    public View_LivresEmpruntes(ADHERENT adherent) {
        this.adherent = adherent;
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Mes livres empruntés");
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTitre = new JLabel("LIVRES EMPRUNTÉS");
        lblTitre.setBounds(150, 20, 200, 30);
        lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblTitre);

        listModel = new DefaultListModel<>();
        JList<String> listLivres = new JList<>(listModel);
        chargerLivresEmpruntes();
        
        JScrollPane scrollPane = new JScrollPane(listLivres);
        scrollPane.setBounds(50, 60, 400, 150);
        frame.getContentPane().add(scrollPane);

        JButton btnRetourner = new JButton("Retourner");
        btnRetourner.setBounds(50, 220, 100, 30);
        frame.getContentPane().add(btnRetourner);

        JButton btnFermer = new JButton("Fermer");
        btnFermer.setBounds(160, 220, 100, 30);
        btnFermer.addActionListener(e -> frame.dispose());
        frame.getContentPane().add(btnFermer);

        btnRetourner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int indexSelectionne = listLivres.getSelectedIndex();
                if (indexSelectionne != -1) {
                    String itemSelectionne = listModel.getElementAt(indexSelectionne);
                    String isbn = itemSelectionne.split(" - ")[0];
                    
                    try {
                        boolean succes = mainMVC.getM().retournerLivre(isbn);
                        
                        if (succes) {
                            JOptionPane.showMessageDialog(frame, "Livre retourné");
                            chargerLivresEmpruntes();
                        } else {
                            JOptionPane.showMessageDialog(frame, "Erreur");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Erreur de base de données");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un livre");
                }
            }
        });
    }

    private void chargerLivresEmpruntes() {
        listModel.clear();
        
        ArrayList<LIVRE> livresEmpruntes = new ArrayList<>();
        
        for (LIVRE livre : mainMVC.getM().getListLivre()) {
            if (livre.getEmprunteur() != null) {
                if (livre.getEmprunteur().getNum().equals(adherent.getNum())) {
                    livresEmpruntes.add(livre);
                }
            }
        }
        
        if (livresEmpruntes.isEmpty()) {
            listModel.addElement("Aucun livre emprunté");
            return;
        }
        
        for (LIVRE livre : livresEmpruntes) {
            String auteur = "Inconnu";
            if (livre.getAuteur() != null) {
                auteur = livre.getAuteur().getPrenom() + " " + livre.getAuteur().getNom();
            }
            
            String item = livre.getISBN() + " - " + livre.getTitre() + 
                         " (" + auteur + ") - " + livre.getPrix() + "€";
            listModel.addElement(item);
        }
    }
}
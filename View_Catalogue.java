package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import controller.mainMVC;
import model.LIVRE;

public class View_Catalogue {

    private JFrame frame;

    public View_Catalogue() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Catalogue des livres");
        frame.setBounds(100, 100, 800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        String[] columnNames = {"ISBN", "Titre", "Auteur", "Prix", "Statut"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        
        for (LIVRE livre : mainMVC.getM().getListLivre()) {
            String auteur = "Inconnu";
            if (livre.getAuteur() != null) {
                auteur = livre.getAuteur().getPrenom() + " " + livre.getAuteur().getNom();
            }
            
            String prix = livre.getPrix() + " €";
            
            String statut;
            if (livre.getEmprunteur() == null) {
                statut = "Disponible";
            } else {
                statut = "Emprunté";
            }
            
            model.addRow(new Object[]{livre.getISBN(), livre.getTitre(), auteur, prix, statut});
        }
        
        JTable table = new JTable(model);
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 770, 300);
        
        JButton btnRetour = new JButton("Retour");
        btnRetour.setBounds(350, 320, 100, 30);
        btnRetour.addActionListener(e -> frame.dispose());
        
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(scrollPane);
        frame.getContentPane().add(btnRetour);
    }
}
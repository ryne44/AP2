package model;

import java.sql.*;
import java.util.ArrayList;

public class model {
    private static final String url = "jdbc:mysql://localhost:3306/ap2_2025";
    private static final String user = "root";
    private static final String passwd = "";
    
    private ArrayList<LIVRE> ListLivre;
    private ArrayList<AUTEUR> ListAuteur;
    private ArrayList<ADHERENT> ListAdherent;
    private ADHERENT adherentConnecte;

    public boolean connexion(String numAdherent) {
        for (ADHERENT a : ListAdherent) {
            if (a.getNum().equalsIgnoreCase(numAdherent)) {
                this.adherentConnecte = a;
                return true;
            }
        }
        return false;
    }

    public void getAll() throws SQLException, ClassNotFoundException {
        ListAdherent = new ArrayList<ADHERENT>();
        ListAuteur = new ArrayList<AUTEUR>();
        ListLivre = new ArrayList<LIVRE>();

        Connection conn = DriverManager.getConnection(url, user, passwd);
        Statement stmt = conn.createStatement();

        // 1. Charger les auteurs
        ResultSet rsAuteur = stmt.executeQuery("SELECT * FROM auteur");
        while (rsAuteur.next()) {
            ListAuteur.add(new AUTEUR(rsAuteur.getString("num"), rsAuteur.getString("nom"), rsAuteur.getString("prenom"), null, null));
        }

        // 2. Charger les adhérents
        ResultSet rsAdh = stmt.executeQuery("SELECT * FROM adherent");
        while (rsAdh.next()) {
            ListAdherent.add(new ADHERENT(rsAdh.getString("num"), rsAdh.getString("nom"), rsAdh.getString("prenom"), rsAdh.getString("email"), ListLivre));
        }

        // 3. Charger les livres
        ResultSet rsLivre = stmt.executeQuery("SELECT * FROM livre");
        while (rsLivre.next()) {
            String isbn = rsLivre.getString("ISBN");
            String titre = rsLivre.getString("titre");
            int prix = rsLivre.getInt("prix");
            String idAuteur = rsLivre.getString("auteur");
            String idAdherent = rsLivre.getString("adherent");

            AUTEUR aut = findAuteur(idAuteur);
            ADHERENT adh = findAdherent(idAdherent);
            ListLivre.add(new LIVRE(isbn, titre, prix, aut, adh));
        }
        conn.close();
    }

    public ADHERENT findAdherent(String num) {
        for (ADHERENT a : ListAdherent) if (a.getNum().equals(num)) return a;
        return null;
    }

    public AUTEUR findAuteur(String num) {
        for (AUTEUR a : ListAuteur) if (a.getNum().equals(num)) return a;
        return null;
    }

    public boolean updateAdherent(String num, String nom, String prenom, String email) throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, passwd);
        String sql = "UPDATE adherent SET nom=?, prenom=?, email=? WHERE num=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, nom);
        pstmt.setString(2, prenom);
        pstmt.setString(3, email);
        pstmt.setString(4, num);
        int res = pstmt.executeUpdate();
        conn.close();
        if (res > 0) {
            ADHERENT a = findAdherent(num);
            if (a != null) { a.setNom(nom); a.setPrenom(prenom); a.setEmail(email); }
        }
        return res > 0;
    }

    // --- MÉTHODE POUR EMPRUNTER ---
    public boolean emprunterLivre(String isbn, String numAdherent) {
        try {
            Connection conn = DriverManager.getConnection(url, user, passwd);
            String sql = "UPDATE livre SET adherent = ? WHERE ISBN = ? AND adherent IS NULL";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, numAdherent);
            pstmt.setString(2, isbn);
            int res = pstmt.executeUpdate();
            conn.close();

            if (res > 0) {
                ADHERENT adh = findAdherent(numAdherent);
                for (LIVRE l : ListLivre) {
                    if (l.getISBN().equals(isbn)) { l.setEmprunteur(adh); break; }
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // --- MÉTHODE POUR RETOURNER ---
    public boolean retournerLivre(String isbn) {
        try {
            Connection conn = DriverManager.getConnection(url, user, passwd);
            String sql = "UPDATE livre SET adherent = NULL WHERE ISBN = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbn);
            int res = pstmt.executeUpdate();
            conn.close();

            if (res > 0) {
                for (LIVRE l : ListLivre) {
                    if (l.getISBN().equals(isbn)) { l.setEmprunteur(null); break; }
                }
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public ADHERENT getAdherentConnecte() { return adherentConnecte; }
    public void setAdherentConnecte(ADHERENT a) { this.adherentConnecte = a; }
    public ArrayList<LIVRE> getListLivre() { return ListLivre; }
}
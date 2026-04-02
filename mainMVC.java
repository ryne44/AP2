package controller;

import java.sql.*;
import model.model;
import view.View_Connexion;

public class mainMVC {

    private static model m;

    public static model getM() {
        return m;
        
    } 

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        m = new model();
        m.getAll();
        new View_Connexion();
        
    }
}
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class InfosJoueurDB {
    
    // Changement de void à Map pour retourner les données
    public Map<String, String> recupererInfosJoueur(int idJoueur){
        Connection conn = connectDb.connect();
        Map<String, String> infos = new HashMap<>();
        
        if (conn == null) return null;

        try {
            // CORRECTION: "where" au lieu de "when"
            String request = "Select * From users where id_user = ?";
            PreparedStatement psmtCheck = conn.prepareStatement(request);
            psmtCheck.setInt(1, idJoueur);
            
            // executeQuery retourne le ResultSet directement
            ResultSet rs = psmtCheck.executeQuery();

            if (rs.next()) {
                infos.put("username", rs.getString("username")); // Adaptez selon vos noms de colonnes
                infos.put("email", rs.getString("email"));
                // infos.put("avatar", rs.getString("avatar"));
            }
            
            rs.close();
            psmtCheck.close();
            conn.close();

            return infos;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Ajoutez ici les méthodes pour récupérer les scores et le classement
    // public List<Score> getScoresJoueur(int idJoueur) { ... }
}

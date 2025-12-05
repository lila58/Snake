package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class requestDB {

    public void saveScore(int idJoueur, int niveau, int scoreActuel) {
        Connection conn = connectDb.connect();
        if (conn == null) return;

        try {
            // 1. Vérifier si une ligne existe déjà pour ce joueur et ce niveau
            String queryCheck = "SELECT meilleur_score FROM score WHERE id_user = ? AND niveau = ?";
            PreparedStatement pstmtCheck = conn.prepareStatement(queryCheck);
            pstmtCheck.setInt(1, idJoueur);
            pstmtCheck.setInt(2, niveau);
            ResultSet rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                // Une entrée existe
                int ancienMeilleur = rs.getInt("meilleur_score");

                if (scoreActuel > ancienMeilleur) {
                    // Nouveau record on met à jour le meilleur ET le dernier
                    String updateBoth = "UPDATE score SET meilleur_score = ?, dernier_score = ? WHERE id_user = ? AND niveau = ?";
                    PreparedStatement pstmtUpdate = conn.prepareStatement(updateBoth);
                    pstmtUpdate.setInt(1, scoreActuel);
                    pstmtUpdate.setInt(2, scoreActuel);
                    pstmtUpdate.setInt(3, idJoueur);
                    pstmtUpdate.setInt(4, niveau);
                    pstmtUpdate.executeUpdate();
                } else {
                    // No record on met à jour SEULEMENT le dernier score
                    String updateLast = "UPDATE score SET dernier_score = ? WHERE id_user = ? AND niveau = ?";
                    PreparedStatement pstmtUpdate = conn.prepareStatement(updateLast);
                    pstmtUpdate.setInt(1, scoreActuel);
                    pstmtUpdate.setInt(2, idJoueur);
                    pstmtUpdate.setInt(3, niveau);
                    pstmtUpdate.executeUpdate();
                }
            } else {
                // Aucune entrée
                String insert = "INSERT INTO score (id_user, niveau, meilleur_score, dernier_score) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmtInsert = conn.prepareStatement(insert);
                pstmtInsert.setInt(1, idJoueur);
                pstmtInsert.setInt(2, niveau);
                pstmtInsert.setInt(3, scoreActuel); // Premier jeu, donc c'est le meilleur
                pstmtInsert.setInt(4, scoreActuel); // Et le dernier
                pstmtInsert.executeUpdate();
            }

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
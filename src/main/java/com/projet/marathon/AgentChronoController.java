package com.projet.marathon;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AgentChronoController implements Initializable {
    int selectedId = 0;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private TextField pseudo_field;
    @FXML
    private TextField password_field;

    @FXML
    private TableView<Utilisateur> agentchrono_table;
    @FXML
    private TableColumn<Utilisateur, Integer> id_column;
    @FXML
    private TableColumn<Utilisateur, String> pseudo_column;
    @FXML
    private TableColumn<Utilisateur, String> password_column;
    @FXML
    private Button ajouter_btn;
    @FXML
    private Button modifier_btn;
    @FXML
    private Button supprimer_btn;

    public ObservableList<Utilisateur> getUtilisateurs() {
        ObservableList<Utilisateur> utilisateurs = FXCollections.observableArrayList();
        String query = "select * from utilisateur where role = ?";
        con = DbConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            st.setString(1, "agent_chrono"); // i don't wanna all the users only agents
            rs = st.executeQuery();
            while (rs.next()){
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setPseudo(rs.getString("pseudo"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return utilisateurs;
    }
    public void listerUtilisateurs () {
        ObservableList<Utilisateur> list = getUtilisateurs();
        System.out.println(list);
        agentchrono_table.setItems(list);
        id_column.setCellValueFactory (new PropertyValueFactory<Utilisateur, Integer>("id"));
        pseudo_column.setCellValueFactory (new PropertyValueFactory<Utilisateur, String>("pseudo"));
        password_column.setCellValueFactory (new PropertyValueFactory<Utilisateur, String>("password"));
    }

    @FXML
    void Ajouter(ActionEvent event) {
        System.out.println("ajouter");
        String insert = "insert into utilisateur (pseudo, password, role) values (?, ?, ?)";
        con = DbConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, pseudo_field.getText());
            st.setString(2, password_field.getText());
            st.setString(3, "agent_chrono"); // i wanna insert user as an agent
            st.executeUpdate();
            listerUtilisateurs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData (MouseEvent event) {
        Utilisateur u = agentchrono_table.getSelectionModel().getSelectedItem();
        selectedId = u.getId();
        pseudo_field.setText(u.getPseudo());
        password_field.setText(u.getPassword());
    }
    @FXML
    void Modifier(ActionEvent event) {
        System.out.println("modifier");
        String update = "update utilisateur set pseudo = ?, password = ? where id = ?";
        con = DbConnexion.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, pseudo_field.getText());
            st.setString(2, password_field.getText());
            st.setInt(3, selectedId);
            st.executeUpdate();
            listerUtilisateurs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Supprimer(ActionEvent event) {
        System.out.println("supprimer");
        String delete = "delete from utilisateur where id = ?";
        con = DbConnexion.getCon();
        try {
            st= con.prepareStatement(delete);
            st.setInt(1, selectedId);
            st.executeUpdate();
            listerUtilisateurs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listerUtilisateurs();
    }
    private void AlertMeWarning(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
        agentchrono_table.refresh();
    }
}

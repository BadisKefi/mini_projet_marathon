package com.projet.marathon.controllers;

import com.projet.marathon.DbConnexion;
import com.projet.marathon.entities.Sponsor;
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

public class SponsorController implements Initializable {
    int selectedId = 0;
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private TextField nom_field;
    @FXML
    private TextField logo_field;
    @FXML
    private TextField montant_field;
    @FXML
    private ChoiceBox marathon_choicebox;

    @FXML
    private TableView<Sponsor> sponsor_table;
    @FXML
    private TableColumn<Sponsor, Integer> id_column;
    @FXML
    private TableColumn<Sponsor, String> nom_column;
    @FXML
    private TableColumn<Sponsor, String> logo_column;
    @FXML
    private TableColumn<Sponsor, Integer> montant_column;
    @FXML
    private TableColumn<Sponsor, String> marathon_column;

    @FXML
    private Button ajouter_btn;
    @FXML
    private Button modifier_btn;
    @FXML
    private Button supprimer_btn;

    public ObservableList<Sponsor> getSponsors() {
        ObservableList<Sponsor> sponsors = FXCollections.observableArrayList();
        String query = "select * from sponsor";
        con = DbConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                Sponsor s = new Sponsor();
                s.setId(rs.getInt("id"));
                s.setNom(rs.getString("nom"));
                s.setLogo(rs.getString("logo"));
                s.setMontant(rs.getInt("montant"));
                s.setMarathon(rs.getString("marathon"));
                sponsors.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sponsors;
    }
    public void listerSponsors () {
        ObservableList<Sponsor> list = getSponsors();
        System.out.println(list);
        sponsor_table.setItems(list);
        id_column.setCellValueFactory (new PropertyValueFactory<Sponsor, Integer>("id"));
        nom_column.setCellValueFactory (new PropertyValueFactory<Sponsor, String>("nom"));
        logo_column.setCellValueFactory (new PropertyValueFactory<Sponsor, String>("logo"));
        montant_column.setCellValueFactory (new PropertyValueFactory<Sponsor, Integer>("montant"));
        marathon_column.setCellValueFactory (new PropertyValueFactory<Sponsor, String>("marathon"));
    }

    @FXML
    void Ajouter(ActionEvent event) {
        System.out.println("ajouter");
        String insert = "insert into sponsor (nom, logo, montant, marathon) values (?, ?, ?, ?)";
        con = DbConnexion.getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, nom_field.getText());
            st.setString(2, logo_field.getText());
            st.setInt(3, Integer.parseInt(montant_field.getText()));
            st.setString(4, (String) marathon_choicebox.getValue());
            st.executeUpdate();
            listerSponsors();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getData (MouseEvent event) {
        Sponsor s = sponsor_table.getSelectionModel().getSelectedItem();
        selectedId = s.getId();
        nom_field.setText(s.getNom());
        logo_field.setText(s.getLogo());
        montant_field.setText(String.valueOf(s.getMontant()));
        marathon_choicebox.setValue(String.valueOf(s.getMarathon()));
    }
    @FXML
    void Modifier(ActionEvent event) {
        System.out.println("modifier");
        String update = "update sponsor set nom = ?, logo = ?, montant = ?, marathon = ? where id = ?";
        con = DbConnexion.getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, nom_field.getText());
            st.setString(2, logo_field.getText());
            st.setInt(3, Integer.parseInt(montant_field.getText()));
            st.setString(4, (String) marathon_choicebox.getValue());
            st.setInt(5, selectedId);
            st.executeUpdate();
            listerSponsors();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Supprimer(ActionEvent event) {
        System.out.println("supprimer");
        String delete = "delete from sponsor where id = ?";
        con = DbConnexion.getCon();
        try {
            st= con.prepareStatement(delete);
            st.setInt(1, selectedId);
            st.executeUpdate();
            listerSponsors();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String[] marathons = {"Light Speed", "Energetic", "Keep Going"};
    private void getMarathons(Event event) {
        String marathon = (String) marathon_choicebox.getValue();
        System.out.println(marathon);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listerSponsors();
        marathon_choicebox.getItems().addAll(marathons);
        marathon_choicebox.setOnAction(this::getMarathons);
    }
    private void AlertMeWarning(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(description);
        alert.showAndWait();
        sponsor_table.refresh();
    }
}

package com.projet.marathon.part2;

import com.projet.marathon.entities.Marathon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private TextField nom;
    @FXML
    private TextField date;
    @FXML
    private TextField lieu_dep;
    @FXML
    private TextField lieu_arv;
    @FXML
    private TextField distance;
    @FXML
    TableView<Marathon> table;
    @FXML
    private TableColumn<Marathon, Integer> id_column;
    @FXML
    private TableColumn<Marathon, String> nom_column;
    @FXML
    private TableColumn<Marathon, String> date_column;
    @FXML
    private TableColumn<Marathon, String> lieu_dep_column;
    @FXML
    private TableColumn<Marathon,String> lieu_arv_column;
    @FXML
    private TableColumn<Marathon,String> distance_column;

    @FXML
    public void ajouter(){

    }
    @FXML
    public void modifier(){

    }
    @FXML
    public void supprimer(){

    }
}

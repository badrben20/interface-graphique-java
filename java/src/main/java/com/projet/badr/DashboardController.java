package com.projet.badr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private Stage stage;
    private Scene scene;
    public void tos1(ActionEvent actionEvent) {
        try {
            Parent fxml =  FXMLLoader.load(getClass().getResource("/FXML/AjouterLivreur.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void toProduit(ActionEvent actionEvent) {
        try {
            Parent fxml =  FXMLLoader.load(getClass().getResource("/FXML/AjouterProduit.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void toCommande(ActionEvent actionEvent) {
        try {
            Parent fxml =  FXMLLoader.load(getClass().getResource("/FXML/AjouterCommande.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Parent fxml;
    @FXML
    private AnchorPane root;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    int id = 0;
    public ObservableList<Livreur> getLivreurs(){
        ObservableList<Livreur> livreurs = FXCollections.observableArrayList();
        String query = "select * from livreur";
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Livreur livreur = new Livreur();
                livreur.setId(resultSet.getInt("id"));
                livreur.setNom(resultSet.getNString("Nom"));
                livreur.setPrenom(resultSet.getNString("prenom"));
                livreur.setTele(resultSet.getNString("tele"));
                livreur.setEmail(resultSet.getNString("Email"));
                livreurs.add(livreur);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livreurs;
    }
    @FXML
    private TableView<Livreur> tbv;
    @FXML
    private TableColumn<Livreur, Integer> col_id;

    @FXML
    private TableColumn<Livreur, String> col_nom;

    @FXML
    private TableColumn<Livreur, String> col_prenom;

    @FXML
    private TableColumn<Livreur, String> col_tele;

    @FXML
    private TableColumn<Livreur, String> col_email;

    @FXML

    public void openOrders(Event event)
    {
        try {
            //add you loading or delays - ;-)
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource( "/Fxml/AjouterCommande.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            System.out.println("y"+ex.getMessage());
        }
    }

    @FXML
    public void back(Event e)
    {
        try {
            //add you loading or delays - ;-)
            Node node = (Node) e.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();

            Parent root = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            System.out.println("y"+ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //showLivreurs();
    }
}

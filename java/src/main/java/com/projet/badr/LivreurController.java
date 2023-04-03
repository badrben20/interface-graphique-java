package com.projet.badr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LivreurController implements Initializable {
    private Parent fxml;
    @FXML
    private AnchorPane root;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;



    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtid;

    @FXML
    private TextField txtprenom;

    @FXML
    private TextField txttele;

    @FXML
    private TextField txtemail;

    @FXML
    private TableView<Livreur> tbv;
    int id = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showlivreurs();
    }
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
    public void showlivreurs(){
        ObservableList<Livreur> list = getLivreurs();
        tbv.setItems(list);
        col_id.setCellValueFactory(new PropertyValueFactory<Livreur,Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Livreur,String>("Nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<Livreur,String>("prenom"));
        col_tele.setCellValueFactory(new PropertyValueFactory<Livreur,String>("tele"));
        col_email.setCellValueFactory(new PropertyValueFactory<Livreur,String>("Email"));

    }

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
    private Button btnsave;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnclear;

    @FXML
    private Button btnupdate;

    @FXML
    void createLivreur(ActionEvent actionEvent){
        String insert = "insert into livreur(Nom, prenom, tele, Email) values(?,?,?,?)";
        connection = DBConnection.getConnection();

            try {
                preparedStatement=connection.prepareStatement(insert);
                preparedStatement.setString(1,txtnom.getText());
                preparedStatement.setString(2,txtprenom.getText());
                preparedStatement.setString(3,txttele.getText());
                preparedStatement.setString(4,txtemail.getText());
                preparedStatement.executeUpdate();
                showlivreurs();
                clear();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


    }

    @FXML
    void getData(MouseEvent mouseEvent) {
        Livreur Livreur = tbv.getSelectionModel().getSelectedItem();
        id = Livreur.getId();
        txtnom.setText(Livreur.getNom());
        txtprenom.setText(Livreur.getPrenom());
        txttele.setText(Livreur.getTele());
        txtemail.setText(Livreur.getEmail());
        showlivreurs();



    }
    void clear(){

        txtnom.setText(null);
        txtprenom.setText(null);
        txttele.setText(null);
        txtemail.setText(null);
    }
    @FXML
    void clearfield(ActionEvent event){

        txtnom.setText(null);
        txtprenom.setText(null);
        txttele.setText(null);
        txtemail.setText(null);
    }
    @FXML
    void deleteLivreur(ActionEvent event){
        String delete = "delete from livreur where id = ?";
        connection = DBConnection.getConnection();
        try {

            preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            showlivreurs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void updateLivreur(ActionEvent event){
        String update = "update livreur set Nom = ?, prenom = ?, tele = ?, Email = ? where id = ?";
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1,txtnom.getText());
            preparedStatement.setString(2,txtprenom.getText());
            preparedStatement.setString(3,txttele.getText());
            preparedStatement.setString(4,txtemail.getText());
            preparedStatement.setInt(5,id);
            preparedStatement.executeUpdate();
            clear();
            showlivreurs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Stage stage;
    private Scene scene;
    @FXML
    public void tos2(ActionEvent actionEvent) {
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
    public void tos1(ActionEvent actionEvent) {
        try {
            Parent fxml =  FXMLLoader.load(getClass().getResource("/FXML/Dashboard.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void toDashboard(ActionEvent actionEvent)
    {
        try {
            Parent fxml =  FXMLLoader.load(getClass().getResource("/FXML/Dashboard.fxml"));
            stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(fxml);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


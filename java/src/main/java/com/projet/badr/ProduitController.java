package com.projet.badr;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private TextField txtproduct;

    @FXML
    private TextField txtprice;

    @FXML
    private TextField txtproductid;

    @FXML
    private Button btnAddProd;
    @FXML
    private TableView<Produit> tbvProduct;

    @FXML
    private TableColumn<Produit, Integer> col_id;

    @FXML
    private TableColumn<Produit, String > col_product_name;

    @FXML
    private TableColumn<Produit, String> col_price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProduit();
    }


    public ObservableList<Produit> getProduits(){
        ObservableList<Produit> Produits = FXCollections.observableArrayList();
        String query = "select * from produit";
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Produit st = new Produit();
                st.setId(resultSet.getInt("id"));
                st.setProductName(resultSet.getNString("ProductName"));
                st.setPrice(resultSet.getNString("Price"));
                Produits.add(st);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Produits;
    }
    public void showProduit(){
        ObservableList<Produit> list = getProduits();
        tbvProduct.setItems(list);
        col_id.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("id"));
        col_product_name.setCellValueFactory(new PropertyValueFactory<Produit,String>("ProductName"));
        col_price.setCellValueFactory(new PropertyValueFactory<Produit,String>("Price"));

    }

    int id = 0;
    private Stage stage;
    private Scene scene;

    @FXML
    void toDashboard(ActionEvent actionEvent) {
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

    @FXML
    void ajouterProduit(ActionEvent actionEvent){
        String insert = "insert into produit( ProductName, Price) values(?,?)";
        connection = DBConnection.getConnection();

        try {
            preparedStatement=connection.prepareStatement(insert);
            preparedStatement.setString(1,txtproduct.getText());
            preparedStatement.setString(2,txtprice.getText());
            preparedStatement.executeUpdate();
            showProduit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    void clear(){
        txtproduct.setText(null);
        txtprice.setText(null);

    }
    @FXML
    void clearfield(ActionEvent actionEvent){
        txtproduct.setText(null);
        txtprice.setText(null);
    }
    @FXML
    void getData(MouseEvent mouseEvent) {
        Produit produit = tbvProduct.getSelectionModel().getSelectedItem();
        id = produit.getId();
        txtproduct.setText(produit.getProductName());
        txtprice.setText(produit.getPrice());
        showProduit();

    }
    @FXML
    void modifierProduit(ActionEvent actionEvent){
        String update = "update produit set ProductName = ?, Price = ? where id = ?";
        connection = DBConnection.getConnection();
        try {
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1,txtproduct.getText());
            preparedStatement.setString(2,txtprice.getText());
            preparedStatement.setInt(3,id);
            preparedStatement.executeUpdate();
            clear();
            showProduit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void supprimerProduit(ActionEvent actionEvent){
        String delete = "delete from produit where id = ?";
        connection = DBConnection.getConnection();
        try {

            preparedStatement = connection.prepareStatement(delete);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            showProduit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
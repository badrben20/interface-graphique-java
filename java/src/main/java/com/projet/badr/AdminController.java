package com.projet.badr;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import com.projet.badr.Admin;
import com.projet.badr.AdminDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private TextField userName;

    @FXML
    private TextField password;
    @FXML
    private Button submit;

    public void login(ActionEvent event) throws SQLException, IOException {

        Window owner = submit.getScene().getWindow();

        System.out.println(userName.getText());
        System.out.println(password.getText());

        if (userName.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your username");
            return;
        }
        if (password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }
        Admin admin = new Admin();

        admin.setPassword(password.getText());
        admin.setUserName(userName.getText());

        AdminDAO adminDao = new AdminDAO();
        boolean flag = adminDao.validate(admin);

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load((getClass().getResource("/FXML/Dashboard.fxml")));
            Scene scene = new Scene(root);
            // code
            stage.setScene(scene);
            stage.show();


        }
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}

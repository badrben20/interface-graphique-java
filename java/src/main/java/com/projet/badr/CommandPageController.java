package com.projet.badr;

import com.projet.badr.Command;
import com.projet.badr.CommandDAO;
import com.projet.badr.Produit;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CommandPageController implements Initializable {
    private Stage stage;
    private  Scene scene;
    private  Parent root;
    @FXML
    private TableView<Command> mytab;

    @FXML
    private TableColumn<Command, Integer> col_idcom;

    @FXML
    private TableColumn<Command, String> col_cli;

    @FXML
    private TableColumn<Command, String> Col_prod;
    @FXML
    private TableColumn<Command, Integer> Col_prix;
    @FXML
    private TableColumn<Command, Integer> Col_qte;
    @FXML
    private TableColumn<Command, String> Col_date;
    @FXML
    private  TextField tfcli;
    @FXML
    private  DatePicker tfdate;
    @FXML
    private  TextField tfprod;
    @FXML
    private  TextField tfqte;
    @FXML
    private  TextField tfprix;
    @FXML
    private  TextField tfidcom;

    @FXML
    protected void onSaveButtonClick(){
        int Qte = Integer.parseInt(tfqte.getText());
        int Prix = Integer.parseInt(tfprix.getText());
        Date date = Date.valueOf(tfdate.getValue());
        Command command = new Command(0,tfcli.getText(),tfprod.getText(),Qte,Prix,date);

        try {
            CommandDAO com = new CommandDAO();
            com.save(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tfcli.setText("");
        tfdate.setId("");
        tfqte.setText("");
        tfidcom.setText("");
        tfprod.setText("");

        tfprix.setText("");
        UpdateTable();
    }
    @FXML
    protected void onUpdatebtnclick(){
        int price = Integer.parseInt(tfprix.getText());
        int qte = Integer.parseInt(tfqte.getText());
        int idcom = Integer.parseInt(tfidcom.getText());
        Date date = Date.valueOf(tfdate.getValue());
        Command command = new Command(idcom , tfcli.getText(),tfprod.getText() ,price ,qte,date);
        try {
            CommandDAO commandDAO= new CommandDAO();
            commandDAO.update(command);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UpdateTable();
    }
    @FXML
    void onbtndeleteclick(Event event) throws SQLException {
        CommandDAO commandDAO=new CommandDAO();
        Command li =mytab.getSelectionModel().getSelectedItem();
        int id_command = Integer.parseInt(tfidcom.getText());
        commandDAO.delete(id_command);


        tfcli.setText("");
        tfqte.setText("");
        tfidcom.setText("");
        tfprod.setText("");
        tfprix.setText("");
        UpdateTable();
    }
    @FXML
    void getData(MouseEvent event) {
        Command product = mytab.getSelectionModel().getSelectedItem();
        tfidcom.setText(String.valueOf(product.getId_command()));
        tfprod.setText(product.getNom_prod());
        tfcli.setText(product.getNom_client());
        tfprix.setText(String.valueOf(product.getPrix()));
        tfqte.setText(String.valueOf(product.getQte()));


    }

    public void UpdateTable(){
        col_idcom.setCellValueFactory(new PropertyValueFactory<Command, Integer>("id_command"));
        col_cli.setCellValueFactory(new PropertyValueFactory<Command,String>("Nom_client"));
        Col_prod.setCellValueFactory(new PropertyValueFactory<Command,String>("Nom_prod"));
        Col_date.setCellValueFactory(new PropertyValueFactory<Command,String >("Date_command"));
        Col_prix.setCellValueFactory(new PropertyValueFactory<Command,Integer>("prix"));
        Col_qte.setCellValueFactory(new PropertyValueFactory<Command,Integer>("Qte"));

        mytab.setItems(getDataCommands());

    }
    public static ObservableList<Command> getDataCommands(){

        CommandDAO com = null;

        ObservableList<Command> listfx = FXCollections.observableArrayList();

        try {
            com = new CommandDAO();
            for(Command ettemp : com.getAll())
                listfx.add(ettemp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listfx ;
    }

    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {UpdateTable();}
    @FXML
    public void toDashboard(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Dashboard.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}


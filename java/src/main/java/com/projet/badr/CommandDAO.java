package com.projet.badr;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandDAO extends BaseDAO<Command> {

    public CommandDAO() throws SQLException {
        super();
    }

    /**
     * @param object
     * @throws SQLException
     */
    @Override
    public void save(Command object) throws SQLException {

        String req = "insert into command (Nom_client , Nom_prod,Qte ,prix,Date_command) values (?,?,?,?,?) ;";

        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setString(1 , object.getNom_client());
        this.preparedStatement.setString(2 , object.getNom_prod());
        this.preparedStatement.setInt(3,object.getQte());
        this.preparedStatement.setInt(4,object.getPrix());
        this.preparedStatement.setDate(5 , object.getDate_command());

        this.preparedStatement.execute();
    }


    @Override
    public void update(Command object) throws SQLException {
        String req = "UPDATE command SET Nom_client = ?, Nom_prod = ?,Qte = ?,prix = ?, Date_command = ?  WHERE  id_command = ?";
        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setString(1 , object.getNom_client());
        this.preparedStatement.setString(2 , object.getNom_prod());
        this.preparedStatement.setInt(3,object.getQte());
        this.preparedStatement.setInt(4,object.getPrix());
        this.preparedStatement.setInt(6, object.getId_command());
        this.preparedStatement.setDate(5 , object.getDate_command());
        this.preparedStatement.execute();
    }

    public void delete(int id_command) throws SQLException {
        String req = "Delete from command where id_command = ?";
        try {
            this.preparedStatement = this.connection.prepareStatement(req);
            this.preparedStatement.setInt(1, id_command);
            this.preparedStatement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public Command getOne(Long id) throws SQLException {
        return null;
    }

    /**
     * @return
     * @throws SQLException
     */
    @Override
    public List<Command> getAll() throws SQLException{

        List<Command> mylist = new ArrayList<Command>();
        String req = "SELECT * FROM command ";



        this.statement = this.connection.createStatement();

        this.resultSet =  this.statement.executeQuery(req);

        while (this.resultSet.next()){

            mylist.add(new Command(this.resultSet.getInt(1) ,
                    this.resultSet.getString(2),
                    this.resultSet.getString(3),
                    this.resultSet.getInt(4),
                    this.resultSet.getInt(5),
                    this.resultSet.getDate(6)
            ));
        }
        return mylist;
    }
    public void delete(Command object) throws SQLException {};

}

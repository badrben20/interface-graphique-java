package com.projet.badr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class AdminDAO extends BaseDAO<Admin> {
    public AdminDAO() throws SQLException {

        super();
    }


    public void save(Admin object) throws SQLException {


    }

    @Override
    public void update(Admin object) throws SQLException {

    }

    @Override
    public void delete(Admin object) throws SQLException {

    }

    @Override
    public List<Admin> getAll()  throws SQLException {

        List<Admin> mylist = new ArrayList<>();




        return mylist;
    }

    @Override
    public Admin getOne(Long id) throws SQLException {
        return null;
    }
// fonction teste la validité

    public boolean validate(Admin object) throws SQLException{


        String request = "SELECT * FROM admins WHERE USERNAME =? AND PASSWORD = ?";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getUserName());

        this.preparedStatement.setString(2 , object.getPassword());

        ResultSet resultSet = this.preparedStatement.executeQuery();

        if (resultSet.next()){
            return true;
        }else {
            return false;
        }
    }
}

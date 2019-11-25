package br.edu.cefsa.ftt.ec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import br.edu.cefsa.ftt.ec.model.People;
import br.edu.cefsa.ftt.ec.util.DbUtil;

public class PeopleDao implements Dao {

    private Connection connection;

    public PeopleDao() {
    	
        try {
			connection = DbUtil.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ArithmeticException("PeopleDao: DB Connect: " + e.getMessage());
		}
    
    } //PeopleDao

    public void addPeople(People people) {
        
    	try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO PEOPLE (NAME, EMAIL, DOB, COLOR, CARDTYPE, GENDER, PERIOD, VALUE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            
            // Parameters start with 1
            preparedStatement.setString(1, people.getName());
            preparedStatement.setString(2, people.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(people.getDob().getTime())); //java.sql.Date não tem time zone...
            preparedStatement.setString(4, people.getColor());
            preparedStatement.setString(5, people.getCardType());
            preparedStatement.setString(6, people.getGender());
            preparedStatement.setString(7, people.getPeriod());
            preparedStatement.setFloat(8, people.getValue());
            
            preparedStatement.executeUpdate();
            

        } catch (SQLException e) {
            e.printStackTrace();
            
            throw new ArithmeticException("PeopleDao: addPeople: " + e.getMessage()); 
        }
    } //addPeople

    public void deletePeople(long id) {
        try {
            
        	PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM PEOPOLE WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } //deletePeople

    public void updatePeople(People people) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE USERS SET NAME=?, " 
                    		                          + "EMAIL=?, " 
                    		                          + "DOB=?, " 
                    		                          + "COLOR=?, " 
                    		                          + "CARDTYPE=?, " 
                    		                          + "GENDER=?, " 
                    		                          + "PERIOD=? " 
                    		                          + "VALUE=? " 
                                               + "WHERE ID=?");
            
            // Parameters start with 1
            preparedStatement.setString(1, people.getName());
            preparedStatement.setString(2, people.getEmail());
            preparedStatement.setDate(3, (java.sql.Date)people.getDob());
            preparedStatement.setString(4, people.getColor());
            preparedStatement.setString(5, people.getCardType());
            preparedStatement.setString(6, people.getGender());
            preparedStatement.setString(7, people.getPeriod());
            preparedStatement.setFloat(8, people.getValue());
            
            preparedStatement.setLong(9, people.getId());
            
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } //updatePeople

    public List<People> getAllPeople() {
        
    	List<People> p = new ArrayList<People>();
        
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PEOPLE");
            while (rs.next()) {
                
            	People people = new People();
                
            	people.setId(rs.getLong("ID"));
                people.setName(rs.getString("NAME"));
                people.setEmail(rs.getString("EMAIL"));
                people.setDob(rs.getDate("DOB"));
                people.setValue(rs.getFloat("VALUE"));
                people.setValue(rs.getString("COLOR"));
                people.setValue(rs.getString("CARDTYPE"));
                people.setValue(rs.getString("GENDER"));
                people.setValue(rs.getString("PERIOD"));

                p.add(people);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    } //getAllPeople

    public People getUserById(long id) {

    	People p = new People();
        
    	try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * from PEOPLE WHERE ID=?");
            
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                p.setId(rs.getLong("ID"));
                p.setName(rs.getString("NAME"));
                p.setEmail(rs.getString("EMAIL"));
                p.setDob(rs.getDate("DOB"));
                p.setValue(rs.getFloat("VALUE"));
                p.setColor(rs.getString("COLOR"));
                p.setColor(rs.getString("CARD"));
                p.setColor(rs.getString("PERIOD"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    } //getUserById
    
} //PeopleDao
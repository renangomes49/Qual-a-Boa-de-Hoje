package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.ConnectionDB;
import pojo.Artista;
import pojo.Local;

public class LocalDAO {
	
	public Local getLocalByName(String  nome) throws SQLException{
		
		Connection connection = ConnectionDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Local local = null;

		try {
			
			statement = connection.prepareStatement("SELECT * FROM localidade WHERE nome = ?");
			statement.setString(1, nome);
			resultSet = statement.executeQuery();
			resultSet.next();
			local = new Local(resultSet.getString("nome"), resultSet.getString("rua"), resultSet.getString("bairro"), 
					resultSet.getString("cidade"));
			
		} catch (SQLException ex) {
			//System.out.println(ex);
		} finally {
			connection.close();
			statement.close();
			resultSet.close();
		}
		
		return local;
		
	}
	
	public int create(Local l) throws SQLException{
		
		Connection connection = ConnectionDB.getConnection();
		PreparedStatement statement = null;
		int codigoLocal = 0;
		
		try {
			
			statement = connection.prepareStatement("INSERT INTO localidade (nome, rua, bairro, cidade)VALUES(?,?,?,?)",  
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, l.getNome());
			statement.setString(2, l.getRua());
			statement.setString(3, l.getBairro());
			statement.setString(4, l.getCidade());
			statement.executeUpdate();
			statement.getGeneratedKeys().next();
			codigoLocal = statement.getGeneratedKeys().getInt("id");
	
			System.out.println("Local cadastrado com sucesso!");
			
		} catch (Exception ex) {
			//System.out.println(ex);
		} finally {
			statement.close();
			connection.close();
		}
		return codigoLocal;
	}

}

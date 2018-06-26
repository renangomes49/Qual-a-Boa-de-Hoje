package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionDB;
import pojo.Artista;
import pojo.Evento;
import pojo.Local;


public class ArtistaDAO {
	
	public int getIdArtistaByName(String  nome) throws SQLException{
		
		Connection connection = ConnectionDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int codigoArtista = 0;

		try {
			
			statement = connection.prepareStatement("SELECT * FROM artista WHERE nome = ?");
			statement.setString(1, nome);
			resultSet = statement.executeQuery();
			resultSet.next();
			codigoArtista = resultSet.getInt("id");
			
		} catch (SQLException ex) {
			//System.out.println(ex);
		} finally {
			connection.close();
			statement.close();
			resultSet.close();
		}
		
		return codigoArtista;
		
	}
	
	public int create(Artista a) throws SQLException{
		
		Connection connection = ConnectionDB.getConnection();
		PreparedStatement statement = null;
		int codigoArtista = 0;
		
		try {	
			statement = connection.prepareStatement("INSERT INTO artista (nome, estilo)VALUES(?,?)",  
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, a.getNome());
			statement.setString(2, a.getEstilo());
			statement.executeUpdate();
			statement.getGeneratedKeys().next();
			codigoArtista = statement.getGeneratedKeys().getInt("id");
			System.out.println("Artista cadastrado com sucesso!");
			
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			statement.close();
			connection.close();
		}
		return codigoArtista;
	}
}

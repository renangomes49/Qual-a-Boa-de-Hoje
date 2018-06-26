package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionDB;
import pojo.Local;
import pojo.Evento;
import pojo.Artista;

public class EventoDAO {
	
	public List<Evento> getAllEventos() throws SQLException {

		Connection connection = ConnectionDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ResultSet resultSet2 = null;
		List<Evento> listaEventos = new ArrayList<>();

		try {
			
			statement = connection.prepareStatement("SELECT * FROM evento");
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				statement = connection.prepareStatement("SELECT * FROM localidade WHERE id ="+resultSet.getInt("local"));
				resultSet2 = statement.executeQuery();
				resultSet2.next();
				listaEventos.add(new Evento(
						resultSet.getString("nome"), 
						resultSet.getString("data"),
						resultSet.getString("descricao"),
						new Local(resultSet2.getString("nome"), resultSet2.getString("rua"), resultSet2.getString("bairro"),
								resultSet2.getString("cidade"))
						));
			}
			
		} catch (SQLException ex) {
			System.out.println(ex);
		}finally {
			connection.close();
			statement.close(); 
			resultSet.close();
			resultSet2.close();
		}
		
		return listaEventos;
	}
	
	public Evento getEventoByName(String  nome) throws SQLException{
		
		Connection connection = ConnectionDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Artista> artistas = new ArrayList<>();
		ResultSet resultSet2 = null;
		ResultSet resultSet3 = null;
		Local local = null;
		Evento evento = null;

		try {
						
			statement = connection.prepareStatement("SELECT * FROM evento WHERE nome = ?");
			statement.setString(1, nome);
			resultSet = statement.executeQuery();
			if(!resultSet.next())
				return null;
			// pegando os artistas
			statement = connection.prepareStatement("SELECT a.nome, a.estilo FROM artista a, grupos_apresentacao ga, evento e "
					+ "WHERE a.id = ga.id_artista AND e.id = ga.id_evento AND e.nome = ?");
			statement.setString(1, nome);
			resultSet2 = statement.executeQuery();
			while(resultSet2.next()) {
				artistas.add(new Artista(resultSet2.getString("nome"), resultSet2.getString("estilo")));
			}
			
			// pegando o local
			statement = connection.prepareStatement("SELECT l.nome, l.rua, l.bairro, l.cidade  FROM evento e, localidade l "
					+ "WHERE e.local = l.id AND e.nome = ?");
			statement.setString(1, nome);
			resultSet3 = statement.executeQuery();
			resultSet3.next();
			local = new Local(resultSet3.getString("nome"),resultSet3.getString("rua") ,resultSet3.getString("bairro"), 
					resultSet3.getString("cidade"));
			
			//resultSet.next();
			evento = new Evento(resultSet.getString("nome"),resultSet.getString("data"), resultSet.getString("descricao"),
					artistas, local);
			
		} catch (SQLException ex) {
			System.out.println(ex);
		} finally {
			connection.close();
			statement.close();
			resultSet.close();
			resultSet2.close();
			resultSet3.close();
		}
		
		return evento;
	}
	
	
	public void create(String nome, String data, String descricao, Local l, List<Integer> listaArtistas) throws SQLException{
		
		Connection connection = ConnectionDB.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
        	statement = connection.prepareStatement("SELECT * FROM localidade WHERE nome = ?");
            statement.setString(1, l.getNome());
            resultSet = statement.executeQuery();
            resultSet.next();
            int idLocal = resultSet.getInt("id");
							
			statement = connection.prepareStatement("INSERT INTO evento (nome, data, descricao, local)VALUES(?,?,?,?)", 
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, nome);
			statement.setString(2, data);
			statement.setString(3, descricao);
			statement.setInt(4,idLocal);
			statement.executeUpdate();
			statement.getGeneratedKeys().next();
			int codigoEvento = statement.getGeneratedKeys().getInt("id");
			
			
			for (int i = 0; i < listaArtistas.size(); i++) {
				statement = connection.prepareStatement("INSERT INTO grupos_apresentacao (id_evento, id_artista)VALUES(?,?)");
				statement.setInt(1, codigoEvento);
				statement.setInt(2, listaArtistas.get(i));
				statement.executeUpdate();
			}
						
			System.out.println("Evento cadastrado com sucesso!");
			
		} catch (Exception ex) {
			//System.out.println(ex);
			
		} finally {
			statement.close();
			connection.close();
			resultSet.close();
		}
	}
	
	public void update(String nomeAntigo, String nome, String data, String descricao, Local l, List<Integer> listaArtistas) throws SQLException {

        Connection connection = ConnectionDB.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        
        try {
        	
        	statement = connection.prepareStatement("SELECT * FROM localidade WHERE nome = ?");
            statement.setString(1, l.getNome());
            resultSet = statement.executeQuery();
            resultSet.next();
            int idLocal = resultSet.getInt("id");

           	statement = connection.prepareStatement("SELECT * FROM evento WHERE nome = ?");
            statement.setString(1, nomeAntigo);
            resultSet2 = statement.executeQuery();
            resultSet2.next();
            int codigoEvento = resultSet2.getInt("id");
            
        	
        	statement = connection.prepareStatement("UPDATE evento SET nome = ? , data = ?, local = ?, descricao = ? WHERE nome = ?");
        	statement.setString(1, nome);
        	statement.setString(2, data);
        	statement.setInt(3, idLocal);
        	statement.setString(4, descricao);
        	statement.setString(5, nomeAntigo);
        	statement.executeUpdate();
        	
        	
        	// Exclui grupo apresentacao - relacao entre evento e artista
        	statement = connection.prepareStatement("DELETE FROM grupos_apresentacao WHERE id_evento = ?");
            statement.setInt(1, codigoEvento);
            statement.executeUpdate();
        	

            
            // recria a rela��o com os novos artistas
			for (int i = 0; i < listaArtistas.size(); i++) {
				statement = connection.prepareStatement("INSERT INTO grupos_apresentacao (id_evento, id_artista)VALUES(?,?)");
				statement.setInt(1, codigoEvento);
				statement.setInt(2, listaArtistas.get(i));
				statement.executeUpdate();
			}
        	

        	System.out.println("Evento Atualizado com s�cesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
        	connection.close();
        	statement.close();
        	resultSet.close();
        	resultSet2.close();
        }
    }
	
	public void delete(String nome) throws SQLException {

        Connection connection = ConnectionDB.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
        	
        	statement = connection.prepareStatement("SELECT * FROM evento WHERE nome = ?");
            statement.setString(1, nome);
            rs = statement.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            
        	statement = connection.prepareStatement("DELETE FROM grupos_apresentacao WHERE id_evento = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            
        	statement = connection.prepareStatement("DELETE FROM evento WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            
           
            System.out.println("Evento excluido com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
        	connection.close();
        	statement.close();
        	rs.close();
        }
    }
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
		
		
	}
	
	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado ) throws Exception {
		
		if(objeto.isNovo()) { // Grava um novo
		
		String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id) VALUES (?, ?, ?, ?,?);";
		PreparedStatement preparaSql = connection.prepareStatement(sql);
		
		preparaSql.setString(1, objeto.getLogin());
		preparaSql.setString(2, objeto.getSenha());
		preparaSql.setString(3, objeto.getNome());
		preparaSql.setString(4, objeto.getEmail());
		preparaSql.setLong(5, userLogado);
		preparaSql.execute();
		connection.commit();
		
		} else { 
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id = "+objeto.getId()+";";
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			
			prepareSql.executeUpdate();
			
			connection.commit();
			
		
			
		}
		
		return this.consultaUsuario(objeto.getLogin(), userLogado);
		
		
	}
	
public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) { // percorrrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("nome"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		
		
		return retorno;
	}
	
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) { // percorrrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("nome"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			
			retorno.add(modelLogin);
		}
		
		
		
		return retorno;
	}
	
public ModelLogin consultaUsuarioLogado(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin(); 
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"')";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {  // se tem resultaado 
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
	
		return modelLogin;
	}
	
	
public ModelLogin consultaUsuario(String login) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin(); 
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false ";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {  // se tem resultaado 
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
	
		return modelLogin;
	}
	
	
	
	public ModelLogin consultaUsuario(String login, Long userLogado) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin(); 
		
		String sql = "select * from model_login where upper(login) = upper('"+login+"') and useradmin is false and usuario_id =  " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {  // se tem resultaado 
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
	
		return modelLogin;
	}
	
public ModelLogin consultaUsuarioID(String id, Long userLogado) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin(); 
		
		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(id));
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {  // se tem resultaado 
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
		}
	
		return modelLogin;
	}
	
	
	public boolean validarLogin(String login) throws Exception{
		String sql = "select count(1) > 0 as existe from model_login where upper(login) =upper('"+login+"');";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next(); // para ele entrar nos resultados do sql
			return resultado.getBoolean("existe");
	
	}
	
	public void deletarUser(String idUser) throws Exception {
		
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false;";
		
		PreparedStatement prepareSql = connection.prepareStatement(sql);
		
		prepareSql.setLong(1, Long.parseLong(idUser));
		
		prepareSql.executeUpdate();
		
		connection.commit();
	}
	
}

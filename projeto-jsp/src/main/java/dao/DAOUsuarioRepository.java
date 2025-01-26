package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import connection.SingleConnectionBanco;
import model.ModelLogin;

public class DAOUsuarioRepository {
	
	private Connection connection;
	
	public DAOUsuarioRepository() {
		connection = SingleConnectionBanco.getConnection();
		
		
	}
	
	public void gravarUsuario(ModelLogin objeto) throws Exception {
		
		
		
		String sql = "INSERT INTO model_login(login, senha, nome, email) VALUES (?, ?, ?, ?)";
		PreparedStatement preparaSql = connection.prepareStatement(sql);
		
		preparaSql.setString(1, objeto.getLogin());
		preparaSql.setString(2, objeto.getSenha());
		preparaSql.setString(3, objeto.getNome());
		preparaSql.setString(4, objeto.getEmail());
		
		preparaSql.execute();
		connection.commit();
		
	}
	
}

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
		
		String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, localidade, uf, numero) VALUES (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?);";
		PreparedStatement preparaSql = connection.prepareStatement(sql);
		
		preparaSql.setString(1, objeto.getLogin());
		preparaSql.setString(2, objeto.getSenha());
		preparaSql.setString(3, objeto.getNome());
		preparaSql.setString(4, objeto.getEmail());
		preparaSql.setLong(5, userLogado);
		preparaSql.setString(6, objeto.getPerfil());
		preparaSql.setString(7, objeto.getSexo());
		
		preparaSql.setString(8, objeto.getCep());
		preparaSql.setString(9, objeto.getLogradouro());
		preparaSql.setString(10, objeto.getBairro());
		preparaSql.setString(11, objeto.getLocalidade());
		preparaSql.setString(12, objeto.getUf());
		preparaSql.setString(13, objeto.getNumero());
		
		
		preparaSql.execute();
		connection.commit();
		
		
		if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
			sql = "update model_login set fotouser =?, extensaofotouser=? where login =?";
			preparaSql = connection.prepareStatement(sql);
			preparaSql.setString(1, objeto.getFotouser());
			preparaSql.setString(2, objeto.getExtensaofotouser());
			preparaSql.setString(3, objeto.getLogin());
		
			preparaSql.execute();
			connection.commit();
			
		}
		
		
		} else { 
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, localidade=?, uf=?, numero=? WHERE id = "+objeto.getId()+";";
			PreparedStatement prepareSql = connection.prepareStatement(sql);
			
			
			prepareSql.setString(1, objeto.getLogin());
			prepareSql.setString(2, objeto.getSenha());
			prepareSql.setString(3, objeto.getNome());
			prepareSql.setString(4, objeto.getEmail());
			prepareSql.setString(5, objeto.getPerfil());
			prepareSql.setString(6, objeto.getSexo());
			
			prepareSql.setString(7, objeto.getCep());
			prepareSql.setString(8, objeto.getLogradouro());
			prepareSql.setString(9, objeto.getBairro());
			prepareSql.setString(10, objeto.getLocalidade());
			prepareSql.setString(11, objeto.getUf());
			prepareSql.setString(12, objeto.getNumero());

			prepareSql.executeUpdate();
			
			connection.commit();
			
			
			if(objeto.getFotouser() != null && !objeto.getFotouser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where id =?";
				prepareSql = connection.prepareStatement(sql);
				prepareSql.setString(1, objeto.getFotouser());
				prepareSql.setString(2, objeto.getExtensaofotouser());
				prepareSql.setLong(3, objeto.getId());
			
				prepareSql.execute();
				connection.commit();
				
			}
			
			
		
			
		}
		
		return this.consultaUsuario(objeto.getLogin(), userLogado);
		
		
	}
	
	
public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + "order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) { // percorrrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("nome"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			
			retorno.add(modelLogin);
		}
		
		
		
		return retorno;
	}
	
public int totalPagina(Long userLogado) throws Exception {
	String sql = "select count(1) as total from model_login where usuario_id = " + userLogado;
	PreparedStatement statement = connection.prepareStatement(sql);
	
	ResultSet resultado = statement.executeQuery();
	
	resultado.next();
	
	Double cadastros = resultado.getDouble("total");
	
	Double porpagina = 5.0;
	
	Double pagina = cadastros / porpagina;
	
	
	Double resto = pagina % 2;
	
	if(resto > 0) {
		pagina ++;
	}
	
	return pagina.intValue();
	
}


	
public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where useradmin is false and usuario_id = " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) { // percorrrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("nome"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));

			
			retorno.add(modelLogin);
		}
		
		
		
		return retorno;
	}

public int consultaUsuarioListTotalPaginacao(String nome, Long userLogado) throws Exception {
	
	
	String sql = "select count(1) as total from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? ";
	
	PreparedStatement statement = connection.prepareStatement(sql);
	statement.setString(1, "%" + nome + "%");
	statement.setLong(2, userLogado);

	ResultSet resultado = statement.executeQuery();
	
			resultado.next();
	
			Double cadastros = resultado.getDouble("total");
	
			Double porpagina = 5.0;
	
			Double pagina = cadastros / porpagina;
	
	
			Double resto = pagina % 2;
	
			if(resto > 0) {
				pagina ++;
			}
	
				return pagina.intValue();
	
	
	
}

public List<ModelLogin> consultaUsuarioListOffSet(String nome, Long userLogado, int offset) throws Exception {
	
	List<ModelLogin> retorno = new ArrayList<ModelLogin>();
	
	String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";
	PreparedStatement statement = connection.prepareStatement(sql);
	statement.setString(1, "%" + nome + "%");
	statement.setLong(2, userLogado);
	


	ResultSet resultado = statement.executeQuery();
	
	while(resultado.next()) { // percorrrer as linhas de resultado do SQL
		ModelLogin modelLogin = new ModelLogin();
		modelLogin.setEmail(resultado.getString("email"));
		modelLogin.setId(resultado.getLong("id"));
		modelLogin.setLogin(resultado.getString("login"));
		modelLogin.setNome(resultado.getString("nome"));
		//modelLogin.setSenha(resultado.getString("senha"));
		modelLogin.setPerfil(resultado.getString("perfil"));
		modelLogin.setSexo(resultado.getString("sexo"));


		
		retorno.add(modelLogin);
	}
	
	
	
	return retorno;
}

	
	
	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();
		
		String sql = "select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) { // percorrrer as linhas de resultado do SQL
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			//modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));


			
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
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));



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
			modelLogin.setUseradmin(resultado.getBoolean("useradmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));

			
			
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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			
		}
	
		return modelLogin;
	}
	
	
public ModelLogin consultaUsuarioID(Long id) throws Exception {
		
		ModelLogin modelLogin = new ModelLogin(); 
		
		String sql = "select * from model_login where id = ? and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);

		ResultSet resultado = statement.executeQuery();
		
		while(resultado.next()) {  // se tem resultaado 
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("Extensaofotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			
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
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotouser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("Extensaofotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setLocalidade(resultado.getString("localidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			
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

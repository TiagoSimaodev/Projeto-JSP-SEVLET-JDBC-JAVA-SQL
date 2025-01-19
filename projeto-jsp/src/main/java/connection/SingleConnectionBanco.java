package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {

	private static String banco = "jdbc:postgresql://localhost:5433/projeto-jsp?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "admin";
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() { // quando tiver uma instancia vai conectar
		conectar();
	}
	
	
	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");// carrega o drive de conexão do banco
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false); //para não efetuar alterações no banco sem nosso comando
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();//mostrar qualquer erro no momento de conectar 
		}
		
	}
}

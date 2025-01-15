package posss_java_jdbc.posss_java_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import conexaojdbc.SingleConnection;
import dao.UserPosDAO;
import junit.framework.TestCase;
import model.BeanUserFone;
import model.Telefone;
import model.Userposjava;

public class TesteBancoJdbc {

	@Test
	public void initBanco() throws SQLException {
		UserPosDAO userPosDAO = new UserPosDAO();
		Userposjava userposjava = new Userposjava();

		userposjava.setNome("Francisco Rodrigues");
		userposjava.setEmail("francisco@gmail.com");

		userPosDAO.salvar(userposjava);

	}

	@Test
	public void initLista() {
		UserPosDAO dao = new UserPosDAO();
		try {
			List<Userposjava> list = dao.listar();
			for (Userposjava userposjava : list) {
				System.out.println(userposjava);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();

		try {
			Userposjava userposjava = dao.buscar(6L);
			System.out.println(userposjava);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initAtualizar() {
		try {

			UserPosDAO dao = new UserPosDAO();

			Userposjava objetoBanco = dao.buscar(5L);

			objetoBanco.setNome("Nome atualizado com o metodo atualizar");

			dao.atualizar(objetoBanco);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void initdeletar () {
		try {
			
			UserPosDAO dao = new UserPosDAO();
			dao.delete(10L);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeInsertTelefone () {
		Telefone telefone = new Telefone();
		telefone.setNumero("(85)9818-6212");
		telefone.setTipo("celular");
		telefone.setUsuario(9L);
		
		UserPosDAO dao = new UserPosDAO();
		dao.salvarTelefone(telefone);
		
		
	}
	
	@Test
	public void testeCarregaFonesUser() {
		UserPosDAO dao = new UserPosDAO();	
		List<BeanUserFone> beanUserFones = dao.listaUserFone(11L);
		for (BeanUserFone beanUserFone : beanUserFones) {
			
			System.out.println(beanUserFone);
			System.out.println("-------------------------------");
		}
		
	}
	
	@Test
	public void testeDeleteUserFone() {
		UserPosDAO dao = new UserPosDAO();
		dao.deleteFonesPorUser(9L);
	}

}

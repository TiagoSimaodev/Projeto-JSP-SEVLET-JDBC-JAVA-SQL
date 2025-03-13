package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;


@WebServlet(urlPatterns = { "/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;
       
    
	private DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();
	
    public ServletUsuarioController() {

    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
		String acao = request.getParameter("acao");
		
			if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
			
				String idUser = request.getParameter("id");
				daoUsuarioRepository.deletarUser(idUser);	
					
				List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
				request.setAttribute("modelLogins", modelLogins);
				
				
					request.setAttribute("msg", "Excluido csom sucesso!");
					request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
					
			
		}else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				
				String idUser = request.getParameter("id");
				
				daoUsuarioRepository.deletarUser(idUser);	
				
				response.getWriter().write("Excluido som sucesso!");
				
		}
		
		else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
			
			String nomeBusca = request.getParameter("nomeBusca");
			System.out.println(nomeBusca);
			
			List<ModelLogin> dadosJsonUser = daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dadosJsonUser);
			response.getWriter().write(json);
			
			
			
			
	}
		else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
			String id = request.getParameter("id");
			
			ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioID(id, super.getUserLogado(request)); 
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);

			
			request.setAttribute("msg", "Usuario em edição");
			request.setAttribute("modolLogin", modelLogin);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
			
			
		}	
			
		else if(acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
			
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("msg", "Usuários carregados");
			request.setAttribute("modelLogins", modelLogins);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			
		}
			
		
			
			
		else {
			List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			request.setAttribute("modelLogins", modelLogins);
			request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		}
		
		
	
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			
		String msg = "Operação realizada com sucesso";	
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
		String sexo = request.getParameter("sexo");

		
		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		modelLogin.setSexo(sexo);
		
		if(daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "Já existe usuário com o mesmo login, informe outro login";
		}else {
			if (modelLogin.isNovo()) {
				msg ="Gravado com sucesso!";
				
			}else {
				msg="Atualizado com sucesso!";
			}
			modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin, super.getUserLogado(request));
		}
		
		
		
		List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
		request.setAttribute("modelLogins", modelLogins);
		request.setAttribute("msg", msg);
		request.setAttribute("modolLogin", modelLogin);
		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
	
		}
	}

}

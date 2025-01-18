package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/principal/*"}) // Interceptas todas as requisições que vierem do projeto ou mapeamento
public class FilterAutenticacao extends HttpFilter implements Filter {
       
    
    public FilterAutenticacao() {
    }
    
    // encerra os processo quando o servidor é parado
    // Mataria os processos de conexão com o banco
	public void destroy() {
	}

	
	//Intercepta as requisições e as resposta no sistema
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		String usuarioLogado = (String) session.getAttribute("usuario");
		
		String urlParaAutenticar = req.getServletPath(); // url que está sendo acessada
		
		// Validar se está logado, se não redireciona a tela de login
		if(usuarioLogado == null &&
				 !urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) { // não está logado
			RequestDispatcher redireciona =request.getRequestDispatcher("/index.jsp?url="+ urlParaAutenticar);
			request.setAttribute("msg", "por favor realize o login!");
			redireciona.forward(request, response);
			return; //para a execução e redireciona para o login
			
		}else {
			chain.doFilter(request, response);
			
		}
		
	}

	
	// Inicia os processos ou recursos quando o servidor sobe o projeto
	//	iniciar a conexão com o banco
	public void init(FilterConfig fConfig) throws ServletException {
	}

}

# 📊 Sistema Web Java com JSP, Servlets e JDBC

Projeto desenvolvido com Java EE utilizando 

JSP (JavaServer Pages), 

Servlets, 

JDBC 

PostgreSQL, 

com foco na construção de um sistema web completo com autenticação, 

CRUD de usuários e telefones, filtros, AJAX, upload de imagem, 

consumo de API ViaCEP, e muito mais.

---

## 🚀 Tecnologias Utilizadas

- Java EE (Servlets + JSP)
- Maven
- JDBC
- PostgreSQL
- HTML5 + CSS3
- Bootstrap 5
- JavaScript + AJAX
- JSTL (Java Standard Tag Library)
- API ViaCEP
- JasperReports
- Chart.js
- Apache Tomcat

---

## 📚 Funcionalidades já implementadas 

- Autenticação com login e senha
- Filtro de acesso por perfil (admin/usuário)
- Cadastro, edição e exclusão de usuários
- Upload de imagem de perfil
- Cadastro e exclusão de telefones associados ao usuário
- Consumo de API ViaCEP para preenchimento automático de endereço
- Validações de formulário com Bootstrap
- Pesquisa dinâmica via AJAX
- Inclusão de calendário para data de nascimento
- Campo monetário com formatação
- Evita duplicidade de telefones
- Templates com JSP Include
- Páginas protegidas por filtro de login
- Estrutura MVC separada (Servlets + DAO + Model + View)

---

## 🧩 Estrutura do Projeto

📁 src
┣ 📁 br.com.projeto.controller # Servlets
┣ 📁 br.com.projeto.dao # DAOs com lógica SQL
┣ 📁 br.com.projeto.model # Models (entidades)
┣ 📁 br.com.projeto.util # Utils (ex: conexão)
┣ 📁 br.com.projeto.filter # Filtros de autenticação
┗ 📁 br.com.projeto.reports # JasperReports (futuramente)

yaml
Copiar
Editar

---

## 💻 Requisitos

- Java 11 ou superior
- Apache Tomcat 9+
- PostgreSQL
- Maven
- JasperReports Studio (opcional)

---

## 🔧 Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-projeto.git
   Importe no Eclipse/IntelliJ como projeto Maven.

Configure o banco de dados PostgreSQL e atualize as credenciais na classe de conexão.

Rode o projeto no Tomcat local.

Acesse no navegador:

bash
Copiar
Editar
http://localhost:8080/seu-projeto
🛠️ Em Desenvolvimento (próximas aulas)
Relatórios em PDF e Excel com JasperReports

Dashboards com gráficos (Chart.js)

Paginação com AJAX

Versionador de banco

Geração de .war para deploy automático

👨‍💻 Desenvolvedor
Tiago Simão

Desenvolvedor Java Web Full Stack
📧 tiagosimaorodri123@gmail.com
🔗 Linkedin: https://www.linkedin.com/in/tiago-simao-685015193/
💻 GitHub: https://github.com/TiagoSimaodev

/**
 * 
 */
package servlets;

import dao.EleitorDAO;
import votacao.Eleitor;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/eleitores")
public class EleitorServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8235548930872504028L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("eleitores", ApplicationContext.eleitorDAO().buscarTodos());
        req.getRequestDispatcher("/jsp/eleitores.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            EleitorDAO dao = ApplicationContext.eleitorDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/eleitores");
                return;
            }

            String idParam = req.getParameter("id");
            Eleitor eleitor;

            if (idParam == null || idParam.isBlank()) {
                eleitor = new Eleitor();
                eleitor.setAtivo(true);
                eleitor.setVotou(false);

                eleitor.setTitulo(req.getParameter("titulo"));
                eleitor.setCpf(req.getParameter("cpf"));
                eleitor.setNome(req.getParameter("nome"));
                eleitor.setZonaId(Long.parseLong(req.getParameter("zonaId")));
                eleitor.setSecaoId(Long.parseLong(req.getParameter("secaoId")));

                dao.inserir(eleitor);
            } else {
                long id = Long.parseLong(idParam);
                eleitor = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Eleitor não encontrado."));

                eleitor.setTitulo(req.getParameter("titulo"));
                eleitor.setCpf(req.getParameter("cpf"));
                eleitor.setNome(req.getParameter("nome"));
                eleitor.setZonaId(Long.parseLong(req.getParameter("zonaId")));
                eleitor.setSecaoId(Long.parseLong(req.getParameter("secaoId")));

                dao.atualizar(eleitor);
            }

            resp.sendRedirect(req.getContextPath() + "/eleitores");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }
}


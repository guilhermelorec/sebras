/**
 * 
 */
package servlets;
import dao.CandidaturaDAO;
import votacao.Candidatura;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/candidaturas")
public class CandidaturaServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7658829610791680880L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("candidaturas", ApplicationContext.candidaturaDAO().buscarTodos());
        req.setAttribute("eleitores", ApplicationContext.eleitorDAO().buscarTodos());
        req.setAttribute("partidos", ApplicationContext.partidoDAO().buscarTodos());
        req.setAttribute("eleicoes", ApplicationContext.eleicaoDAO().buscarTodos());
        req.setAttribute("cargos", ApplicationContext.cargoDAO().buscarTodos());

        req.getRequestDispatcher("/jsp/candidaturas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            CandidaturaDAO dao = ApplicationContext.candidaturaDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/candidaturas");
                return;
            }

            String idParam = req.getParameter("id");
            Candidatura candidatura;

            if (idParam == null || idParam.isBlank()) {
                candidatura = new Candidatura();
            } else {
                long id = Long.parseLong(idParam);
                candidatura = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Candidatura não encontrada."));
            }

            candidatura.setEleitorId(Long.parseLong(req.getParameter("eleitorId")));
            candidatura.setPartidoId(Long.parseLong(req.getParameter("partidoId")));
            candidatura.setEleicaoId(Long.parseLong(req.getParameter("eleicaoId")));
            candidatura.setCargoId(Long.parseLong(req.getParameter("cargoId")));
            candidatura.setNumero(Integer.parseInt(req.getParameter("numero")));
            candidatura.setAtiva(req.getParameter("ativa") != null);

            if (candidatura.getId() == null) {
                dao.inserir(candidatura);
            } else {
                dao.atualizar(candidatura);
            }

            resp.sendRedirect(req.getContextPath() + "/candidaturas");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }
}


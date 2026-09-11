/**
 * 
 */
package servlets;
import dao.PartidoDAO;
import votacao.Partido;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/partidos")
public class PartidoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4728227740377255941L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("partidos", ApplicationContext.partidoDAO().buscarTodos());
        req.getRequestDispatcher("/jsp/partidos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            PartidoDAO dao = ApplicationContext.partidoDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/partidos");
                return;
            }

            String idParam = req.getParameter("id");
            Partido partido;

            if (idParam == null || idParam.isBlank()) {
                partido = new Partido();
            } else {
                long id = Long.parseLong(idParam);
                partido = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Partido não encontrado."));
            }

            partido.setNumero(Integer.parseInt(req.getParameter("numero")));
            partido.setSigla(req.getParameter("sigla"));
            partido.setNome(req.getParameter("nome"));
            partido.setAtivo("on".equals(req.getParameter("ativo")) || "1".equals(req.getParameter("ativo")));

            if (partido.getId() == null) {
                dao.inserir(partido);
            } else {
                dao.atualizar(partido);
            }

            resp.sendRedirect(req.getContextPath() + "/partidos");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }
}

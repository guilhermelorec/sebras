/**
 * 
 */
package servlets;
import dao.SecaoDAO;
import votacao.Secao;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/secoes")
public class SecaoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4397133125183685794L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("secoes", ApplicationContext.secaoDAO().buscarTodos());
        req.setAttribute("zonas", ApplicationContext.zonaDAO().buscarTodos());

        req.getRequestDispatcher("/jsp/secoes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            SecaoDAO dao = ApplicationContext.secaoDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/secoes");
                return;
            }

            String idParam = req.getParameter("id");
            Secao secao;

            if (idParam == null || idParam.isBlank()) {
                secao = new Secao();
            } else {
                long id = Long.parseLong(idParam);
                secao = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Seção não encontrada."));
            }

            secao.setNumero(Integer.parseInt(req.getParameter("numero")));
            secao.setZonaId(Long.parseLong(req.getParameter("zonaId")));
            secao.setLocal(req.getParameter("local"));

            if (secao.getId() == null) {
                dao.inserir(secao);
            } else {
                dao.atualizar(secao);
            }

            resp.sendRedirect(req.getContextPath() + "/secoes");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }
}

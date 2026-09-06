/**
 * 
 */
package servlets;
import dao.ZonaDAO;
import votacao.Zona;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/zonas")
public class ZonaServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2569723581871575763L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("zonas", ApplicationContext.zonaDAO().buscarTodos());
        req.setAttribute("municipios", ApplicationContext.municipioDAO().buscarTodos());

        req.getRequestDispatcher("/jsp/zonas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            ZonaDAO dao = ApplicationContext.zonaDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/zonas");
                return;
            }

            String idParam = req.getParameter("id");
            Zona zona;

            if (idParam == null || idParam.isBlank()) {
                zona = new Zona();
            } else {
                long id = Long.parseLong(idParam);
                zona = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Zona não encontrada."));
            }

            zona.setNumero(Integer.parseInt(req.getParameter("numero")));
            zona.setMunicipioId(Long.parseLong(req.getParameter("municipioId")));

            if (zona.getId() == null) {
                dao.inserir(zona);
            } else {
                dao.atualizar(zona);
            }

            resp.sendRedirect(req.getContextPath() + "/zonas");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }
}


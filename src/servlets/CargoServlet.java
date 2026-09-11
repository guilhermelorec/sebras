/**
 * 
 */
package servlets;

import dao.CargoDAO;
import votacao.Cargo;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/cargos")
public class CargoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -781978258052212985L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("cargos", ApplicationContext.cargoDAO().buscarTodos());
        req.getRequestDispatcher("/jsp/cargos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            CargoDAO dao = ApplicationContext.cargoDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/cargos");
                return;
            }

            String idParam = req.getParameter("id");
            Cargo cargo;

            if (idParam == null || idParam.isBlank()) {
                cargo = new Cargo();
            } else {
                long id = Long.parseLong(idParam);
                cargo = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Cargo não encontrado."));
            }

            cargo.setNome(req.getParameter("nome"));

            String uf = req.getParameter("uf");
            if (uf == null || uf.isBlank()) {
                cargo.setUf(null);
            } else {
                cargo.setUf(uf);
            }

            cargo.setPermiteSegundoTurno(req.getParameter("permiteSegundoTurno") != null);

            if (cargo.getId() == null) {
                dao.inserir(cargo);
            } else {
                dao.atualizar(cargo);
            }

            resp.sendRedirect(req.getContextPath() + "/cargos");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }
}


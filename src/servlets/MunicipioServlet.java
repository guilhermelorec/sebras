package servlets;

import appVoto.ApplicationContext;
import dao.MunicipioDAO;
import votacao.Municipio;
import votacao.UnidadeFederativa;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/municipios")
public class MunicipioServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("municipios", ApplicationContext.municipioDAO().buscarTodos());
        req.setAttribute("ufs", UnidadeFederativa.values());
        req.getRequestDispatcher("/jsp/municipios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            MunicipioDAO dao = ApplicationContext.municipioDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/municipios");
                return;
            }

            String idParam = req.getParameter("id");
            Municipio municipio;

            if (idParam == null || idParam.isBlank()) {
                municipio = new Municipio();
            } else {
                long id = Long.parseLong(idParam);
                municipio = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Município não encontrado."));
            }

            municipio.setNome(req.getParameter("nome"));
            municipio.setUf(UnidadeFederativa.fromSigla(req.getParameter("uf")));

            if (municipio.getId() == null) {
                dao.inserir(municipio);
            } else {
                dao.atualizar(municipio);
            }

            resp.sendRedirect(req.getContextPath() + "/municipios");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }
}

/**
 * 
 */
package servlets;

import  votacao.AbstencaoZona;
import  appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/abstencao")
public class AbstencaoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2555094965200123981L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("eleicoes", ApplicationContext.eleicaoDAO().buscarTodos());

        String eleicaoParam = req.getParameter("eleicaoId");
        String turnoParam = req.getParameter("turno");

        if (eleicaoParam != null && !eleicaoParam.isBlank()
                && turnoParam != null && !turnoParam.isBlank()) {

            long eleicaoId = Long.parseLong(eleicaoParam);
            int turno = Integer.parseInt(turnoParam);

            List<AbstencaoZona> abstencao = ApplicationContext.abstencaoService()
                    .buscarPorEleicaoETurno(eleicaoId, turno);

            req.setAttribute("abstencao", abstencao);
        }

        req.getRequestDispatcher("/jsp/abstencao.jsp").forward(req, resp);
    }
}

/**
 * 
 */
package servlets;

import votacao.ResultadoVotacao;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;



@WebServlet("/resultado")
public class ResultadoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6796898973507895793L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String zonaParam = req.getParameter("zonaId");
        String eleicaoParam = req.getParameter("eleicaoId");
        String turnoParam = req.getParameter("turno");

        if (zonaParam != null && !zonaParam.isBlank()
                && eleicaoParam != null && !eleicaoParam.isBlank()
                && turnoParam != null && !turnoParam.isBlank()) {

            long zonaId = Long.parseLong(zonaParam);
            long eleicaoId = Long.parseLong(eleicaoParam);
            int turno = Integer.parseInt(turnoParam);

            List<ResultadoVotacao> resultado = ApplicationContext.resultadoService()
                    .resultadoPorZona(zonaId, eleicaoId, turno);

            req.setAttribute("resultado", resultado);
        }

        req.getRequestDispatcher("/jsp/resultado.jsp").forward(req, resp);
    }
}


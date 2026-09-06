/**
 * 
 */
package servlets;


import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/votar")
public class VotoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8393697810055903051L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/votar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            long eleitorId = Long.parseLong(req.getParameter("eleitorId"));
            long candidaturaId = Long.parseLong(req.getParameter("candidaturaId"));
            long eleicaoId = Long.parseLong(req.getParameter("eleicaoId"));
            int turno = Integer.parseInt(req.getParameter("turno"));
            long zonaId = Long.parseLong(req.getParameter("zonaId"));
            long secaoId = Long.parseLong(req.getParameter("secaoId"));

            ApplicationContext.votacaoService().registrarVoto(
                    eleitorId,
                    candidaturaId,
                    eleicaoId,
                    turno,
                    zonaId,
                    secaoId
            );

            req.setAttribute("mensagem", "Voto registrado com sucesso.");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
        }

        doGet(req, resp);
    }
}


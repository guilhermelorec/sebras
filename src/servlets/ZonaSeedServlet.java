/**
 * 
 */
package servlets;
import  appVoto.ApplicationContext;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/zonas/seed")
public class ZonaSeedServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2377171753336564922L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String municipioIdParam = req.getParameter("municipioId");
            String minimoParam = req.getParameter("minimo");

            int minimo = minimoParam == null || minimoParam.isBlank()
                    ? 100
                    : Integer.parseInt(minimoParam);

            if (municipioIdParam != null && !municipioIdParam.isBlank()) {
                long municipioId = Long.parseLong(municipioIdParam);
                ApplicationContext.zonaService()
                        .garantirMinimoDeZonasPorMunicipio(municipioId, minimo);
            } else {
                ApplicationContext.zonaService()
                        .garantirMinimoParaTodosMunicipios(minimo);
            }

            resp.sendRedirect(req.getContextPath() + "/");

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}


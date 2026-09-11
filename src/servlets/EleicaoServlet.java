package servlets;

import dao.EleicaoDAO;
import appVoto.Eleicao;
import appVoto.ApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;


@WebServlet("/eleicoes")
public class EleicaoServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6535316457167441576L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("eleicoes", ApplicationContext.eleicaoDAO().buscarTodos());
        req.getRequestDispatcher("/jsp/eleicoes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String acao = req.getParameter("acao");
            EleicaoDAO dao = ApplicationContext.eleicaoDAO();

            if ("remover".equals(acao)) {
                long id = Long.parseLong(req.getParameter("id"));
                dao.remover(id);
                resp.sendRedirect(req.getContextPath() + "/eleicoes");
                return;
            }

            String idParam = req.getParameter("id");
            Eleicao eleicao;

            if (idParam == null || idParam.isBlank()) {
                eleicao = new Eleicao();
            } else {
                long id = Long.parseLong(idParam);
                eleicao = dao.buscarPorId(id)
                        .orElseThrow(() -> new RuntimeException("Eleição não encontrada."));
            }

            eleicao.setNome(req.getParameter("nome"));
            eleicao.setAno(Integer.parseInt(req.getParameter("ano")));
            eleicao.setDataTurno1(parseDate(req.getParameter("dataTurno1")));
            eleicao.setDataTurno2(parseDate(req.getParameter("dataTurno2")));
            eleicao.setTurnoAtual(Integer.parseInt(req.getParameter("turnoAtual")));
            eleicao.setSegundoTurnoHabilitado(req.getParameter("segundoTurnoHabilitado") != null);
            eleicao.setAtiva(req.getParameter("ativa") != null);

            if (eleicao.getId() == null) {
                dao.inserir(eleicao);
            } else {
                dao.atualizar(eleicao);
            }

            resp.sendRedirect(req.getContextPath() + "/eleicoes");

        } catch (Exception e) {
            req.setAttribute("erro", e.getMessage());
            doGet(req, resp);
        }
    }

    private LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDate.parse(value);
    }
}

/**
 * 
 */
package appVoto;
import dao.AbstencaoDAO;
import dao.CandidaturaDAO;
import dao.CargoDAO;
import dao.EleicaoDAO;
import   dao.EleitorDAO;
import   dao.MunicipioDAO;
import   dao.PartidoDAO;
import   dao.SecaoDAO;
import   dao.VotoDAO;
import dao.WildFlyJndiConnectionFactory;
import   dao.ZonaDAO;
import dao.jdbc.AbstencaoJdbcDAO;
import dao.jdbc.CandidaturaJdbcDAO;
import dao.jdbc.CargoJdbcDAO;
import dao.jdbc.EleicaoJdbcDAO;
import   dao.jdbc.EleitorJdbcDAO;
import   dao.jdbc.MunicipioJdbcDAO;
import   dao.jdbc.PartidoJdbcDAO;
import   dao.jdbc.SecaoJdbcDAO;
import   dao.jdbc.VotoJdbcDAO;
import   dao.jdbc.ZonaJdbcDAO;
import service.AbstencaoService;
import   service.ResultadoService;
import   service.VotacaoService;
import   service.ZonaService;

import tankDB.ConnectionFactory;
import tankDB.UCPConnectionFactory;


public final class ApplicationContext {


    private static final ConnectionFactory CONNECTION_FACTORY = createConnectionFactory();

    private ApplicationContext() {
    }

    private static ConnectionFactory createConnectionFactory() {
        String mode = System.getProperty("votacao.connection.mode", "jndi");

        if ("ucp".equalsIgnoreCase(mode)) {
            return UCPConnectionFactory.getInstance();
        }

        try {
            return WildFlyJndiConnectionFactory.getInstance();
        } catch (RuntimeException e) {
            return UCPConnectionFactory.getInstance();
        }
    }

    public static PartidoDAO partidoDAO() {
        return new PartidoJdbcDAO(CONNECTION_FACTORY);
    }

    public static EleitorDAO eleitorDAO() {
        return new EleitorJdbcDAO(CONNECTION_FACTORY);
    }

    public static MunicipioDAO municipioDAO() {
        return new MunicipioJdbcDAO(CONNECTION_FACTORY);
    }

    public static ZonaDAO zonaDAO() {
        return new ZonaJdbcDAO(CONNECTION_FACTORY);
    }

    public static SecaoDAO secaoDAO() {
        return new SecaoJdbcDAO(CONNECTION_FACTORY);
    }

    public static EleicaoDAO eleicaoDAO() {
        return new EleicaoJdbcDAO(CONNECTION_FACTORY);
    }

    public static CargoDAO cargoDAO() {
        return new CargoJdbcDAO(CONNECTION_FACTORY);
    }

    public static CandidaturaDAO candidaturaDAO() {
        return new CandidaturaJdbcDAO(CONNECTION_FACTORY);
    }

    public static VotoDAO votoDAO() {
        return new VotoJdbcDAO(CONNECTION_FACTORY);
    }

    public static AbstencaoDAO abstencaoDAO() {
        return new AbstencaoJdbcDAO(CONNECTION_FACTORY);
    }

    public static ZonaService zonaService() {
        return new ZonaService(CONNECTION_FACTORY);
    }

    public static VotacaoService votacaoService() {
        return new VotacaoService(CONNECTION_FACTORY);
    }

    public static ResultadoService resultadoService() {
        return new ResultadoService(votoDAO());
    }

    public static AbstencaoService abstencaoService() {
        return new AbstencaoService(abstencaoDAO());
    }
}


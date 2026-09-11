/**
 * 
 */
package excecao;

/**
 * 
 */
public class RegraNegocioException extends RuntimeException {

	
	    /**
	 * 
	 */
	private static final long serialVersionUID = -8522331155281880565L;

		public RegraNegocioException(String message) {
	        super(message);
	    }

	    public RegraNegocioException(String message, Throwable cause) {
	        super(message, cause);
	    }
}

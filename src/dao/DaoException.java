/**
 * 
 */
package dao;

/**
 * 
 */
public class DaoException extends RuntimeException {

	
	    /**
	 * 
	 */
	private static final long serialVersionUID = -5391715448257199105L;

		public DaoException(String message) {
	        super(message);
	    }

	    public DaoException(String message, Throwable cause) {
	        super(message, cause);
	    }
}

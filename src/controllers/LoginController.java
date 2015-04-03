package controllers;
import java.security.NoSuchAlgorithmException;
import model.BulletinBoardIO;
import configuration.StringConstants;
import exceptions.*;

public class LoginController {

	/**
	 * Attempts to log-in with a username, password pair. Fails if already logged in,
	 * or login credentials are incorrect. Returns status as String.
	 * @param name
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidUserException
	 * @throws AlreadyLoggedInException
	 */
	public static String attemptLogin(String name, String password) throws NoSuchAlgorithmException, InvalidUserException, AlreadyLoggedInException {
		
		if(ClientState.instance().isLoggedIn())
			throw new AlreadyLoggedInException(ClientState.instance().getName());
		
		else{
			String result = BulletinBoardIO.attemptLogin(name, password);
			
			if(result != ""){
				ClientState.instance().logIn(name, result);
				
				return String.format(StringConstants.LOGGED_IN_MESSAGE, name);
			}
			
			else
				throw new InvalidUserException();
			
		}
		//return StringConstants.LOG_IN_FAILED_MESSAGE;
	}

	/**
	 * Attempts to log out current user, does nothing if not logged in.
	 * Returns status as string.
	 * @return
	 */
	public static String logOut() {
		
		if(!ClientState.instance().isLoggedIn())
			return StringConstants.NOT_LOGGED_IN_MESSAGE;
		
		else{
			ClientState.instance().logOut();
			return StringConstants.LOGGED_OUT_MESSAGE;
		}
	}

	/**
	 * Attempts to log-in was admin. Fails if already logged in,
	 * or login password is incorrect. Returns status as String.
	 * @param name
	 * @param password
	 * @return
	 * @throws InvalidUserException
	 * @throws AlreadyLoggedInException
	 * @throws NoSuchAlgorithmException
	 */
	public static String attemptAdminLogin(String password) throws InvalidUserException, AlreadyLoggedInException {
		
		if(ClientState.instance().isLoggedIn())
			throw new AlreadyLoggedInException(ClientState.instance().getName());
		
		else{
			String result = BulletinBoardIO.attemptAdminLogin(password);
			
			if(result != ""){
				ClientState.instance().logIn(StringConstants.ADMIN_USER, result);
				return String.format(StringConstants.LOGGED_IN_MESSAGE, StringConstants.ADMIN_USER);
			}
			else
				throw new InvalidUserException();
			
		}
	}

}

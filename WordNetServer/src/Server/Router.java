package Server;

import Utils.Request;
//import Views.*;
import controller.FrequencyVector;

/**
 * Server router, routes the requests to the the relevant views
 * 
 * @author aj_pa
 *
 */
public class Router {
	private FrequencyVector FV=new FrequencyVector();

	/**
	 * Router constructor.
	 */
	public Router() {
		
	}

	/**
	 * resolve handles the request and invokes the relevant view.
	 * 
	 * @param request
	 * @return Object
	 */
		public Object resolve(Request request) {
			switch (request.getView()) {
			case "FV":
				return FV.resolve(request);
			}
			return null;
		}
}
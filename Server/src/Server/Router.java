package Server;

import Utils.Request;
import Views.*;

/**
 * Server router, routes the requests to the the relevant views
 * 
 * @author aj_pa
 *
 */
public class Router {
	
	Texts texts=new Texts();
	
	Clusters clusters=new Clusters();

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
		case "texts":
			return texts.resolve(request);
		
		case "clusters":
			return clusters.resolve(request);
		}
		return null;
	}
}
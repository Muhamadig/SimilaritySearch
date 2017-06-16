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

	Global global=new Global();
	
	Search search=new Search();
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
		case "global":
			return global.resolve(request);
		case "search":
			return search.resolve(request);
		}
		return null;
	}
}
package java.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class SingleRoute extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		// create server
		HttpServer server = vertx.createHttpServer();
		
		// create a router
		Router router = Router.router(vertx);
		
		// create a route from the router and mention the path
		Route route = router.route("/simple/pa*");
		
		// handler-1 for the above route (uses lambda)(useful when code block passed as param is used once and is not used anywhere else)
		route.handler(routingContext -> {
			HttpServerResponse response = routingContext.response();

			// enable chunked responses because we will be adding data as
			// we execute over other handlers. This is only required once and
			// only if several handlers do output.
			response.setChunked(true);

			response.write("route1\n");

			routingContext.vertx().setTimer(2000, tid -> routingContext.next());
		});
		
		// handler-2 for the above route (uses handler object) (handler object gets created at instance creation of the class
																// and if the route was never hit then the handler object just sits idle
																// occupying memory without being used)
		Handler<RoutingContext> handler = new Handler<RoutingContext>() {
			@Override
			public void handle(RoutingContext routingContext) {
				HttpServerResponse response = routingContext.response();
				response.write("route2\n");
				routingContext.vertx().setTimer(2000, tid -> routingContext.next());
			}
		};
		route.handler(handler);
		
		// handler-3 for the above route (uses :: operator in java to pass method reference)
		route.handler(this::repeatedLamdaBody);	// This combines the best of the above two approaches. 1. No need to re-write code.
																								// 2. It creates object only when called
		server.requestHandler(router);
		
		server.listen(9999, "localhost");
		
	}
	
	public void repeatedLamdaBody(RoutingContext routingContext) {
		HttpServerResponse response = routingContext.response();
		response.write("route3");

		// Now end the response
		routingContext.response().end();
	}
	
	/**
	 * Browsers might now display the delayed responses as we expect.
	 * So use curl http://localhost:9999/simple/path on terminal.
	 * 
	 * Browsers typically wait for the response to end and then they display the whole thing together and due to that reason 
	 * we don't see the expected delayed response on browsers be it chrome of firefox.
	 */
	public static void main(String[] args) {
		
		Vertx.vertx().deployVerticle(SingleRoute.class.getName());
		
	}

}

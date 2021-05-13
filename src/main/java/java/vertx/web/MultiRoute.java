package java.vertx.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class MultiRoute extends AbstractVerticle {

	@SuppressWarnings("unused")
  @Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		Route route1 = router.route("/some/path/").handler(routingContext -> {

			HttpServerResponse response = routingContext.response();
			// enable chunked responses because we will be adding data as
			// we execute over other handlers. This is only required once and
			// only if several handlers do output.
			response.setChunked(true);

			response.write("route1\n");

			// Now call the next matching route
			routingContext.next();
		});

		Route route2 = router.route("/some/path/").handler(routingContext -> {

			HttpServerResponse response = routingContext.response();
			response.write("route2\n");

			// Now call the next matching route
			routingContext.next();
		});

		Route route3 = router.route("/some/path/").handler(routingContext -> {

			HttpServerResponse response = routingContext.response();
			response.write("route3");

			// Now end the response
			routingContext.response().end();
		});
		
		vertx.createHttpServer().requestHandler(router).listen(9999);
		
	}

	public static void main(String[] args) {

		Vertx.vertx().deployVerticle(MultiRoute.class.getName());
		
	}

}

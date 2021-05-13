package java.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class HTTPClientDemo extends AbstractVerticle {
	
	@Override
	public void start() throws Exception {

		vertx.createHttpServer().requestHandler(request -> {
			request.response().end("Hello from server " + this);
		}).listen(8111);

	}

	@SuppressWarnings("deprecation")
  public static void main(String[] args) {
		
		Vertx.vertx().deployVerticle(HTTPClientDemo.class.getName(), new DeploymentOptions().setInstances(3));
		
		
		Vertx v = Vertx.vertx();
		v.setPeriodic(3000, (l) -> {
			  v.createHttpClient().getNow(8111, "localhost", "/", resp -> {
			    resp.bodyHandler(body -> {
			      System.out.println(body.toString("ISO-8859-1"));
			    });
			  });
			});
		
	}

}

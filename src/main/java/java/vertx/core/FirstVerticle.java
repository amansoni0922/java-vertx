package java.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 * In vertx, a verticle is a deployable unit.
 */
public class FirstVerticle extends AbstractVerticle { // to create your own verticle you need to extend the AbstractVerticle.

	/**
	 * start() is called when this verticle is deployed. 
	 * Similarly, we can override end() which gets called when this verticle is undeployed.
	 */
	@Override
	public void start() throws Exception {
		// By extending AbstractVerticle we get access to its vertx object.
		Vertx myVertx = vertx;

		// Lets use this vertx object to create our server to serve requests.
		HttpServer myServer = myVertx.createHttpServer();

		// The requests will be served by the server but we need to write the logic of how the requests needs to be handled.
		// The request handling logic is written in Handler object. So lets create a handler object and write handling logic.
		
		Handler<HttpServerRequest> myHandler; // just a reference to handler object.
		myHandler = new Handler<HttpServerRequest>() { // a handler object is created.
			@Override
			public void handle(HttpServerRequest event) { // in vertx http requests are nothing but the events to be handled.
				// Handling logic to respond appropriately resides here and for that we create a response object.
				HttpServerResponse response = event.response();
				
				// --- Some back-end work and response logic starts ---
				String jversion = System.getProperty("java.version");
				// --- ------------Response logic ends------------- ---
				
				// Lets respond back with our logic and end the response.
				response.end("FV: This message will be displayed as the end of response: " + jversion);
				
				// Lets also print the response on console
				System.out.println("FV: Responded!!! " + jversion);
			}
		};

		// Lets attach our handler to our server
		myServer = myServer.requestHandler(myHandler);

		// Lets start the server and listen to a particular port for requests aka events in terms of vertx
		myServer.listen(8111);
	}

	/**
	 * We will deploy the verticle using main method. Usually the deployment is done using maven or fat jar.
	 */
	public static void main(String[] args) {
		
		// Lets create a Vertx object.
		Vertx vertx = Vertx.vertx();
		
		// Lets deploy our verticle using this Vertx object.
		vertx.deployVerticle(FirstVerticle.class.getName());
		
		System.out.println(FirstVerticle.class.getName() + " is DEPLOYED!!!");
	}
}





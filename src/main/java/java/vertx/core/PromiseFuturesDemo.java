package java.vertx.core;

import java.util.function.Function;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

public class PromiseFuturesDemo extends AbstractVerticle {
	
	@Override
	public void start(Promise<Void> promise) throws Exception {
		Vertx myVertx = vertx;

		HttpServer myServer = myVertx.createHttpServer();

		Handler<HttpServerRequest> myRequestHandler;
		myRequestHandler = new Handler<HttpServerRequest>() {
			@Override
			public void handle(HttpServerRequest event) {
				HttpServerResponse response = event.response();
				
				// --- Some back-end work and response logic starts ---
				String jversion = System.getProperty("java.version");
				// --- ------------Response logic ends------------- ---
				
				response.end("PFD: This message will be displayed as the end of response: " + jversion);
				System.out.println("PFD: Responded!!! " + jversion);
			}
		};

		myServer = myServer.requestHandler(myRequestHandler);
		
		Handler<AsyncResult<HttpServer>> listenHandler;
		listenHandler = new Handler<AsyncResult<HttpServer>>() {
			@Override
			public void handle(AsyncResult<HttpServer> ar) {
				if(ar.succeeded()) {
					System.out.println("AsyncResult succeeded and so Promise was completed");
					promise.complete();
				} else {
					System.out.println("AsyncResult Failed and so Promise not completed");
					promise.fail(ar.cause());
				}
			}
		};

		myServer.listen(8333, listenHandler);
	}
	

	public static void main(String[] args) {
		
		Promise<String> promise = Promise.promise();
		
		@SuppressWarnings("unused")
    Function<String, String> mapper = new Function<String, String>() {
			@Override
			public String apply(String t) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		//Future<String> future = promise.future().compose();
		//Future<String> future = Promise.promise().future().
		
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(PromiseFuturesDemo.class.getName(), promise);
		
		System.out.println(PromiseFuturesDemo.class.getName() + " is DEPLOYED!!!");
	}
}

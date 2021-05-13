package java.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class AsyncResultDemo extends AbstractVerticle {
	
	@Override
	public void start() throws Exception {
		super.start();
		System.out.println("verticle started");
	}

	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();
		
		Handler<AsyncResult<String>> completionHandler = new Handler<AsyncResult<String>>() {
			@Override
			public void handle(AsyncResult<String> e) {	// handler to handle event e
				if(e.succeeded()) {
					System.out.println("Deployment completed\t" + e);
				}
				else {
					System.out.println(e.cause());
				}
			}
		};
		
		
		// we use a vertx object to deploy verticle
		vertx.deployVerticle(AsyncResultDemo.class.getName(), completionHandler);
		
		/*
		 * when deployVerticle is invoked on vertx it calls the start method of given verticle
		 * and once the start method is completed it returns asyncResult object that is then handled by provided handler.
		 */
		
		// entire above code for deploying verticle can also be written as:
		Vertx.vertx().deployVerticle(AsyncResultDemo.class.getName(), ar -> {	// here "ar" is async result returned if deployment
			if(ar.succeeded()) {										// was successful. Handler for it is passed as lambda expression.
				System.out.println("Deployment completed\t" + ar);
			}
			else {
				System.out.println(ar.cause());
			}					
		});
		
	}
	
}

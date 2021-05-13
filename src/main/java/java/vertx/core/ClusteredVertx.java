package java.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ClusteredVertx extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		
		vertx.eventBus().consumer("shared-space").handler(message -> {
			System.out.println(Thread.currentThread().getName() +this+ ": Yes, I read:\t" + message.body());
		});
	
		vertx.createHttpServer().requestHandler(req -> {
			
			if(req.uri().contains("favicon.ico")) {
				System.out.println("Have not set icon yet");
			} else {
			
			// --- Some back-end work and response logic starts ---
			//String jversion = System.getProperty("java.version");
			// --- ------------Response logic ends------------- ---

			req.response().sendFile("web/my-index.html");
			System.out.println("SV: Responded!!! " + this + "\t" + Thread.currentThread().getName());
			}
		}).listen(8333);
		
	}
	
	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx(new VertxOptions().setEventLoopPoolSize(1));
		vertx.deployVerticle(Unclustered.class.getName(), new DeploymentOptions().setInstances(2));
		
		Handler<AsyncResult<Vertx>> resultHandler = new Handler<AsyncResult<Vertx>>() {
			
			@Override
			public void handle(AsyncResult<Vertx> event) {
				
				if(event.succeeded()) {
					Vertx vertx = event.result();
					//vertx.deployVerticle(Unclustered.class.getName(), new DeploymentOptions().setInstances(2));
					vertx.deployVerticle(SecondVerticle.class.getName(), new DeploymentOptions().setInstances(2));
					vertx.deployVerticle(ClusteredVertx.class.getName(), new DeploymentOptions().setInstances(10));
				}
				else {
					System.out.println("Init cluster failed");
				}
			}
		};
		
		Vertx.clusteredVertx(new VertxOptions().setHAEnabled(true).setHAGroup("mygroup").setEventLoopPoolSize(3), resultHandler);
		
	}

}

package java.vertx.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

/**
 * Exact same logic and code as FirstVerticle but in compact form.
 */
public class Unclustered extends AbstractVerticle {
	
	@Override
	public void start() throws Exception {
		
		vertx.eventBus().publish("shared-space", (Thread.currentThread().getName()+this+": can you read this ?"));
		System.out.println(Thread.currentThread().getName() + this + "\tJust published a message");
		
		vertx
			.createHttpServer()
			.requestHandler(event -> {
				
				if(event.uri().contains("favicon.ico")) {
					System.out.println("Have not set icon yet");
				} else {
				
				// --- Some back-end work and response logic starts ---
				@SuppressWarnings("unused")
        String jversion = System.getProperty("java.version");
				// --- ------------Response logic ends------------- ---

				event.response().sendFile("web/my-index.html");
				//event.response().end("SV: This message will be displayed as the end of response: " + jversion);
				
				System.out.println("SV: Responded!!! " + this + "\t" + Thread.currentThread().getName());
				}
		}).listen(8444);
	}
	
	public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();
		
		vertx.deployVerticle(Unclustered.class.getName(), new DeploymentOptions().setInstances(2));
		vertx.deployVerticle(ClusteredVertx.class.getName(), new DeploymentOptions().setInstances(10));
		
		System.out.println(Unclustered.class.getName() + " is DEPLOYED!!!");
	}
}

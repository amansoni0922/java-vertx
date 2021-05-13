package java.vertx.core;

import java.util.stream.IntStream;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpMethod;

public class PerfTest extends AbstractVerticle {

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
        DeploymentOptions deploymentOptions = new DeploymentOptions();
        deploymentOptions.setInstances(1);
        vertx.deployVerticle(PerfTest.class.getName(), deploymentOptions);
	}

	@Override
	public void start() throws Exception {
		super.start();
		HttpClient httpClient = vertx.createHttpClient();
		IntStream.rangeClosed(1, 100).forEach(delay -> {
			vertx.setPeriodic(1, idx -> {
				HttpClientRequest req = httpClient.request(HttpMethod.GET, 80, "www.amazon.in", "/");
				req.end();
			});
		});
	}
	
}

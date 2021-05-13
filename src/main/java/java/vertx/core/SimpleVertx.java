package java.vertx.core;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;

public class SimpleVertx {

	public static void main(String[] args) {
		
		usageWithExplanation();
		
		//usingLambda();
		
		System.out.println("With very few exceptions (i.e. some file system operations ending in 'Sync'), none of the APIs in Vert.x block the calling thread.");
		
	}

	
	private static void usageWithExplanation() {
		Vertx vertx = Vertx.vertx();
		
		// we create this simple handler for our periodic event
		Handler<Long> handler = new Handler<Long>() {	// functional interface that will use object of type "Long"
			@Override
			public void handle(Long event) {	// we implement the (only one allowed) abstract method of functional interface
				System.out.println("handle() method from handler object called\t" + event);
			}
		};
		
		vertx.setPeriodic(2000, handler);	// setPeriodic internally counts 2000 millis and when reached 
											// an event is said to have occurred and then the handler is called. This loop repeats.
											// Not to be confused with event loop. Each Vertx (not verticle) instance maintains several
											// event loop threads. As nothing in Vert.x or your application blocks, the event loop can merrily
											// run around delivering events to different handlers in succession as they arrive.
		
		vertx.setPeriodic(1000, handler);	// setPeriodic returns Id of the timer which is then passed to the handler provided.
		
		//vertx.cancelTimer(1);	// timer 1 will be cancelled.
	}

	@SuppressWarnings("unused")
  private static void usingLambda() {
		Vertx vertx = Vertx.vertx();
		
		vertx.setPeriodic(2000, event -> {	// passing handler as argument in form of lambda expression
			System.out.println("usingLambda: handle() method from handler object called\t" + event);
		});
		// variable 'event' is just the timer Id. Can be used to cancel the timer.
		
		// Note: timers are not verticle. Above we set timers for vertx object.
		// If you’re creating timers from inside verticles, those timers will be automatically closed when the verticle is undeployed.
		
	}
}


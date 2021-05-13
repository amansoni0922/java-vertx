package java.vertx.core;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * There is a single event bus instance for every Vert.x instance and it is
 * obtained using the method eventBus. The event bus allows different parts of
 * your application to communicate with each other, irrespective of what
 * language they are written in, and whether they’re in the same Vert.x
 * instance, or in a different Vert.x instance.
 */
public class EventBusDemo {

	@SuppressWarnings("unused")
  public static void main(String[] args) {
		
		Vertx vertx = Vertx.vertx();
		
		// There is a single instance of the event bus per Vert.x instance.
		EventBus eb = vertx.eventBus();
		
		// lets have an event listener at address "one-football" on event bus
		MessageConsumer<StringBuffer> consumer = eb.consumer("one-football");
		
		// lets create multiple handlers that will handle messages at above address
		// Handler1
		Handler<Message<StringBuffer>> handler1 = new Handler<Message<StringBuffer>>() {
			@Override
			public void handle(Message<StringBuffer> event) {
				System.out.println("Consumer1: handler1: String message recieved\t" + event.body());
			}
		};
		// Handler2
		Handler<Message<StringBuffer>> handler2 = new Handler<Message<StringBuffer>>() {
			@Override
			public void handle(Message<StringBuffer> event) {
				System.out.println("Consumer1: handler2: Integer message recieved\t" + event.body());
			}
		};

		
		// attach handlers to event listener
		consumer = consumer.handler(handler1);
		consumer = consumer.handler(handler2);
		
		// another consumer to same address
		eb.consumer("one-football").handler(message -> {
			System.out.println("Consumer2: handler1: Message recieved\t" + message.body());
		});
		
		eb.publish("one-football", "1234");
		
		eb.send("one-football", "9876");
		
		eb.publish("one-football", "public Key");
		eb.publish("one-football", "PubKey");
		
		eb.send("one-football", "private key");
		eb.send("one-football", "pri key");
		
		ClusterManager mgr = new HazelcastClusterManager();
		
	}

}

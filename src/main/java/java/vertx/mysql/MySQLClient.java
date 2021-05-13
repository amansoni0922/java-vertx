package java.vertx.mysql;

import java.util.ArrayList;
import java.util.List;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Query;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class MySQLClient extends AbstractVerticle {

	@SuppressWarnings("unused")
  public static void main(String[] args) {
		MySQLConnectOptions connectOptions = new MySQLConnectOptions();
		connectOptions.setHost("localhost");
		connectOptions.setPort(3306);
		connectOptions.setUser("root");
		connectOptions.setPassword("12345678");
		connectOptions.setDatabase("classicmodels");

		// Pool options
		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

		// Create the client pool
		Vertx vertx = Vertx.vertx();
		MySQLPool pool = MySQLPool.pool(vertx, connectOptions, poolOptions);

		// A simple query
		Query<RowSet<Row>> query = pool.query("SELECT productName, buyPrice FROM products");
		
		query.execute(ar -> {
			if (ar.succeeded()) {
				RowSet<Row> result = ar.result();
				System.out.println("Got " + result.size() + " rows ");
			} else {
				System.out.println("Failure: " + ar.cause().getMessage());
			}

			// Now close the pool
			pool.close();
		});
		
		
		
		pool
		  .preparedQuery("CREATE TABLE players(  \n" + 
		  		"    id int NOT NULL AUTO_INCREMENT,\n" + 
		  		"    name varchar(45) NOT NULL,  \n" + 
		  		"    position varchar(35) NOT NULL,  \n" + 
		  		"    jno int NOT NULL,  \n" + 
		  		"    PRIMARY KEY (id)  \n" + 
		  		");  ")
		  .execute(Tuple.of("julien"), ar -> {
		  if (ar.succeeded()) {
		    RowSet<Row> rows = ar.result();
		    System.out.println("Got " + rows.size() + " rows ");
		  } else {
		    System.out.println("Failure: " + ar.cause().getMessage());
		  }
		});
		
		
		
		
		String s = "SELECT * FROM users WHERE id=?";
		Tuple t = Tuple.of("julien");
		pool
		  .preparedQuery("SELECT * FROM users WHERE id=?")
		  .execute(Tuple.of("julien"), ar -> {
		  if (ar.succeeded()) {
		    RowSet<Row> rows = ar.result();
		    System.out.println("Got " + rows.size() + " rows ");
		  } else {
		    System.out.println("Failure: " + ar.cause().getMessage());
		  }
		});
		
		
		
		
		List<Tuple> batch = new ArrayList<Tuple>();
		batch.add(Tuple.of("julien", "Julien Viet"));
		batch.add(Tuple.of("emad", "Emad Alblueshi"));
		
		pool
		  .preparedQuery("INSERT INTO USERS (id, name) VALUES (?, ?)")
		  .executeBatch(batch, res -> {
		  if (res.succeeded()) {

		    // Process rows
		    RowSet<Row> rows = res.result();
		  } else {
		    System.out.println("Batch failed " + res.cause());
		  }
		});
		
		
		
		pool
		  .preparedQuery("UPDATE students SET graduated = ? WHERE id = 0")
		  .execute(Tuple.of(true), ar -> {
		  if (ar.succeeded()) {
		    System.out.println("Updated with the boolean value");
		  } else {
		    System.out.println("Failure: " + ar.cause().getMessage());
		  }
		});
		
	}

}

/** Read */
class Read {
	
}

/** Create */
class Write {
	
}

/** Update */ 
class Modify {
	
}

/** Delete */
class Remove {
	
}


package java.vertx.core;

public class CommonJVM {

	public static void main(String[] args) throws Exception {
		
		/*
		 * This main is called by OS which creates one JVM instance and the following below two verticles
		 * will be deployed in this JVM itself.
		 * 
		 * Run 'jps' command in terminal to see the running JVM instances.
		 */
		
		FirstVerticle.main(null);
		
		SecondVerticle.main(null);
		
		/*
		 * Had we run FirstVerticle and SecondVerticle separately from either eclipse or command line then we would have got two 
		 * instances of JVM one for each main of FirstVerticle and SecondVerticle.
		 * 
		 * Try and run 'jps' command in terminal to see the running JVM instances.
		 */
		
	}

}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Test {
	public static void read(GraphInterface<String> roadMap, Stack<String> path, String p) throws IOException {
		File dosya = new File(p + ".txt");
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new InputStreamReader(new FileInputStream(dosya), "UTF8"));
			String satir;
			while ((satir = inputStream.readLine()) != null) {
				String[] vertex = satir.split(" ");
				roadMap.addVertex(vertex[0]);
				roadMap.addVertex(vertex[1]);
				roadMap.addEdge(vertex[0], vertex[1]);
				roadMap.addEdge(vertex[1], vertex[0]);
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

	}

	public static void main(String[] args) throws IOException {
		GraphInterface<String> karate_club_network = new Graph<>();
		Stack<String> karate_club_network_path = new Stack<String>();
		GraphInterface<String> facebook_social_network = new Graph<>();
		Stack<String> facebook_social_network_path = new Stack<String>();
		System.out.println("\tGRAPH CENTRALITY METRICS (By: Fatih Semirgin, ID: 2019510068)");
		long time_start = System.nanoTime();
		System.out.println("# For Zachary Karate Club Network");
		read(karate_club_network, karate_club_network_path, "karate_club_network");
		karate_club_network.centrality_Metrics(karate_club_network_path);
		System.out.println("+ The process completed.\n# For Facebook Social Network\n+ The process continues...");
		read(facebook_social_network, facebook_social_network_path, "facebook_social_network");
		facebook_social_network.centrality_Metrics(facebook_social_network_path);
		System.out.println("+ The process completed.");
		long computing_time = System.nanoTime() - time_start;
		System.out.println("Elapsed Time: " + computing_time / (Math.pow(10, 9))+ " second.(Usually in the range of 55 and 70)");
	}

}

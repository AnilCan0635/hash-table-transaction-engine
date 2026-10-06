package dataHomework;
import java.util.*;

public class Main {

	
		
		private static final String FILENAME = "movies_dataset.csv";
		private static final String SEARCH_KEY = "tt0266697";
		private static final String SEARCHFILE= "search.txt";
		
		public static void main(String[] args) {
			
			Scanner scanner = new Scanner(System.in);
	        Management mediaManagement = new Management();
	        boolean exit = false;

	        while (!exit) {
	            System.out.println("\n--- Media Program Menu ---");
	            System.out.println("1. Load dataset");
	            System.out.println("2. Run 1000 search test");
	            System.out.println("3. Search for a media item with the ImdbId");
	            System.out.println("4. List the top 10 media according to user votes");
	            System.out.println("5. List all the media streams in a given country");
	            System.out.println("6. List the media items streaming on all 5 platforms");
	            System.out.println("7. Exit");
	            System.out.print("Enter your choice: ");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // Consume newline character
	            
	            switch (choice) {
	                case 1:
	                	mediaManagement.readFile(FILENAME);;
	                    break;
	                case 2:
	                    mediaManagement.Initialization();
	                	mediaManagement.searchFile(SEARCHFILE);
	                	mediaManagement.displayTime();
	                    break;
	                case 3:
	                    System.out.print("Enter ImdbId: ");                
	                    String imdbId = scanner.nextLine();
	                    mediaManagement.Initialization();
	                    mediaManagement.readFile(FILENAME);
	                    mediaManagement.display(imdbId);	                    
	                	mediaManagement.searchFile(SEARCHFILE);
	                	mediaManagement.displayTime();
	                    break;
	                case 4:
	                	//mediaManagement.listTopMediaByVotes();
	                    break;
	                case 5:
	                    System.out.print("Enter country: ");
	                    String country = scanner.nextLine();
	                    mediaManagement.listMediaByCountry(country);
	                    break;
	                case 6:
	                	mediaManagement.listMediaOnAllPlatforms();
	                    break;
	                case 7:
	                    System.out.println("Exiting the program. Goodbye!");
	                    exit = true;
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }
	        }
	        scanner.close();
			
			
		
	}
		
		

}

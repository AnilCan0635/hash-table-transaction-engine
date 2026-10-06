package dataHomework;

import java.io.*;
import java.util.*;

public class Management {
    private HashTable<String, Records> filmRecords;
    private ArrayList<Records> mediaList = new ArrayList<>();
    private Map<String, Records> filmRecordss = new HashMap<>();
    
    
    public Management() {
        this.filmRecords = new HashTable<>();
    }
    
    long difference;
    static double min = 999999999;
    static double max = 0;
    String imdbFilePath = "search.txt";

    static double averageTime;
    
    public void readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int bypass = 0;
            long time = System.nanoTime();
            while ((line = br.readLine()) != null) {
                String[] parts = parseCSVLine(line);
                if (bypass >= 1) {
                    String url = parts[0];
                    String title = parts[1];
                    String type = parts[2].trim();
                    String genres = parts[3];
                    String relaseYear = parts[4];
                    String imdbId = parts[5];
                    String imdbAverage = parts[6];
                    String imdbNum = parts[7];
                    String platform = parts[8];
                    String availableCountries = parts[9].trim();
            

                    Records filmRecord = filmRecords.get(imdbId);
                    if (filmRecord == null) {
                        /// if not create a new Records object and put it in the hash table
                    	filmRecord = new Records(title,type,genres,relaseYear,imdbId,imdbAverage,imdbNum);
                    	filmRecords.put(imdbId, filmRecord);
                    }

                    filmRecord.addTransaction(platform, availableCountries);
                }
                bypass++;
            }
            long off_time = System.nanoTime();
            
            difference = off_time - time;
            
        } catch (IOException e) {
            System.out.println("File not found.");;
        }
    }
    
    private static String[] parseCSVLine(String line) {
        // Bu metot týrnak iþaretlerini ve virgülleri göz önüne alarak satýrý iþler
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        java.util.List<String> fields = new java.util.ArrayList<>();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // Týrnak içindeysek, týrnak iþaretini yönet
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                // Týrnak içinde deðilsek virgüle göre alaný bitir
                fields.add(currentField.toString().trim());
                currentField.setLength(0); // StringBuilder'i sýfýrla
            } else {
                // Karakteri mevcut alana ekle
                currentField.append(c);
            }
        }

        // Son alaný listeye ekle
        fields.add(currentField.toString().trim());

        return fields.toArray(new String[0]);
    }
    
    
	public void Initialization() {
		System.out.println("Choice for load factor: 1-) 0.5  2-) 0.8");
		filmRecords.adjust_load_factor();
		System.out.println("Choice for hash function: 1-) SSF  2-) PAF");
		filmRecords.hashChoice();
		System.out.println("Choice for handling method: 1-) Linear Probing  2-) Double Hashing");
		filmRecords.handlingChoice();
	}
    
    public void display(String imdbId) {
        Records record = filmRecords.get(imdbId);
        if (record != null) {
        	System.out.println();
        	System.out.println();
        	System.out.println(">Search: " + imdbId);
        	System.out.println();
        	System.out.println("Type: " + record.getType());
        	System.out.println("Genre: " + record.getGenre());
        	System.out.println("Relase Year: " + record.getrelaseYear());
        	System.out.println("Imdb ID: " + record.getImdbId());
        	System.out.println("Rating: " + record.getRating());
        	System.out.println("Number of Votes: " + record.getNumberOfVotes());
        	System.out.println();
            System.out.println(record.getTransactionCount() + " Transactions found for " + record.getMoviesName());
        	System.out.println();
            record.displayTransactions();
            //countFoundAndNotFound();// MENU 2 1000Search
        	System.out.println("CollusionCount :" + filmRecords.getCollisionCount());
        	System.out.println("Index time: " + difference);
        	System.out.println();
        	
        	//countFoundAndNotFound();// MENU 2 1000Search
        	//listMediaByCountry("TR"); // MENU 5 List all the media streams in a given country
        	//listMediaOnAllPlatforms(); //  MENU 6  List the media items that are streaming on all 5 platforms 
        	 
        	
        } else {
            System.out.println("Movie not found.");
        }
        
    }
    
    public void listMediaOnAllPlatforms() {
        System.out.println("Media streaming on all 5 platforms:");
        for (String key : filmRecords.keySet()) {
            Records record = filmRecords.get(key);
            if (record != null) {
                HashSet<String> platforms = new HashSet<>();
                for (Transaction transaction : record.getTransactions()) {
                    platforms.add(transaction.getPlatform());
                }
                if (platforms.size() == 5) {
                    System.out.println(record.getMoviesName());
                }
            }
        }
        
    }
    
    
    public void listMediaByCountry(String country) {
        System.out.println("Media available in " + country + ":");
        for (String key : filmRecords.keySet()) {
            Records record = filmRecords.get(key);
            if (record != null) {
                for (Transaction transaction : record.getTransactions()) {
                    if (transaction.toString().contains(country)) {
                        System.out.println(record.getMoviesName());
                        break;
                    }
                }
            }
        }
    }
    
    public void countFoundAndNotFound() {
        int foundCount = 0;
        int notFoundCount = 0;

        try (Scanner scanner = new Scanner(new File(imdbFilePath))) {
            while (scanner.hasNextLine()) {
                String imdbId1 = scanner.nextLine().trim();  // Her satýrda bir IMDb ID'si olduðunu varsayýyoruz

                // IMDb ID'si filmRecords içinde var mý kontrol et
                Records filmRecordss = filmRecords.get(imdbId1);

                if (filmRecordss != null) {
                    foundCount++; // Film bulunduyse
                } else {
                    notFoundCount++; // Film bulunamadýysa
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadý: " + imdbFilePath);
        }

        // Sonuçlarý yazdýrma
        System.out.println("Bulunan Film Sayýsý: " + foundCount);
        System.out.println("Bulunamayan Film Sayýsý: " + notFoundCount);
    }

    

    
    public void displayTime() {
    	//System.out.println("Min time: " + min);
    	//System.out.println("Max time: " + max);
    	System.out.println("Average time: " + averageTime);
    }
 
    public void searchFile(String searchFile) {
        String[] searchedFile = new String[1000];
        try (BufferedReader br = new BufferedReader(new FileReader(searchFile))) {
            String line;
        	int i = 0;
            while ((line = br.readLine()) != null) {
                searchedFile[i] = line;
                i++;
            }
        }      
         catch (IOException e) {
            System.out.println("File not found.");;
        }

        long timer1 = System.nanoTime();
        for (int i = 0; i < searchedFile.length; i++) {
        	String key = searchedFile[i];
        	long timer = System.nanoTime();
        	if (filmRecords.contains(key)) {
        		long timer_off = System.nanoTime();
        		double searchDifference = timer_off - timer;
        		if(searchDifference > max) {
        			max = searchDifference;
        		}
        		else if (searchDifference < min) {
        			min = searchDifference;
        		}
        	}
        }
        long timer1_off = System.nanoTime();
        averageTime = (timer1_off - timer1) / 1000;
    }
   
}

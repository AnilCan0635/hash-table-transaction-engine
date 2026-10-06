package dataHomework;

import java.util.ArrayList;

class Records {
    private String moviesName;
    private String type;
    private String genre;
    private String relaseYear;
    private String imdbId;
    private String rating;
    private String numberOfVotes;
    private ArrayList<Transaction> transactions;
    private ArrayList<String> allPlatforms;

	public Records(String moviesName,String type, String genre, String relaseYear, String imdbId, String rating, String numberOfVotes) {
        this.moviesName = moviesName;
        this.type = type;
        this.genre = genre;
        this.relaseYear = relaseYear;
        this.imdbId = imdbId;
        this.rating = rating;
        this.numberOfVotes = numberOfVotes;
        this.transactions = new ArrayList<>();
        this.allPlatforms = new ArrayList<>();
    }

    public String getMoviesName() {
        return moviesName;
    }
    public String getType() {
        return type;
    }
    public String getGenre() {
        return genre;
    }
    public String getrelaseYear() {
        return relaseYear;
    }
    public String getImdbId() {
        return imdbId;
    }
    public String getRating() {
        return rating;
    }
    public String getNumberOfVotes() {
        return numberOfVotes;
    }
    
    public int getTransactionCount() {
        return transactions.size();
    }

    public void addTransaction(String platform, String language) {
    	
        transactions.add(new Transaction(platform, language));
    }

    private void sortTransactions() {
    	
        int limit = transactions.size();

        for (int i = 0; i < limit - 1; i++) {
            for (int j = 0; j < limit - i - 1; j++) {   //// avoiding out of range
                Transaction transaction1 = transactions.get(j);
                Transaction transaction2 = transactions.get(j + 1);
                
                if (transaction1.getPlatform().compareTo(transaction2.getPlatform()) < 0) {
                    //// swapping transactions if query is true
                    transactions.set(j, transaction2);
                    transactions.set(j + 1, transaction1);
                }
            }
        }
    }
    
    public ArrayList<Transaction> getTransactions() {
    	sortTransactions();
        return transactions;
    }
    
    public void displayTransactions() {
    	sortTransactions();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
        System.out.println();
    }
    
  
    
}


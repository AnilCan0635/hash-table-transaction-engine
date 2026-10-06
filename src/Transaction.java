package dataHomework;
class Transaction {
	private String platform;
	private String language;

	public Transaction(String platform, String language) {
		this.platform = platform;
		this.language = language;
	}

	public String getPlatform() {
		return platform;
	}

	@Override
	public String toString() {
		return platform + ", " + language;
	}
}

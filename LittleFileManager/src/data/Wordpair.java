package data;

public class Wordpair {
	private String wortAlt;
	private String wortNeu;

	public Wordpair(String wort1, String wort2) {
		wortAlt = wort1;
		wortNeu = wort2;
	}

	public String getWortAlt() {
		return wortAlt;
	}

	public String getWortNeu() {
		return wortNeu;
	}
}
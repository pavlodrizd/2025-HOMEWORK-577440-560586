package it.uniroma3.diadia.ambienti;

public enum Direzione {
	NORD, SUD, EST, OVEST;

	public static Direzione fromString(String dir) {
		switch (dir.toLowerCase()) {
		case "nord":
			return NORD;
		case "sud":
			return SUD;
		case "est":
			return EST;
		case "ovest":
			return OVEST;
		default:
			throw new IllegalArgumentException("Direzione sconosciuta: " + dir);
		}
	}
}

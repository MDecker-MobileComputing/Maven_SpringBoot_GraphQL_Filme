package de.eldecker.dhbw.spring.filme.db;


/**
 * Erlaubte Sortierfelder für die Query {@code filme(...)}.
 */
public enum FilmSortByEnum {

	ID( "id" ),
	TITEL( "titel" ),
	ERSCHEINUNGSJAHR( "erscheinungsjahr" ),
	BEWERTUNG( "bewertung" ),
	REGISSEUR( "regisseur" ),
	VERFUEGBAR( "verfuegbar" );

	private final String _feldname;

	FilmSortByEnum( String feldname ) {

		_feldname = feldname;
	}


	public String getFeldname() {

		return _feldname;
	}
}

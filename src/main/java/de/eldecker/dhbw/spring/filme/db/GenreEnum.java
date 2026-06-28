package de.eldecker.dhbw.spring.filme.db;


/**
 * Eigener Aufzählungstyp für Film-Genres. 
 * <br><br>
 * 
 * Muss selbe Einträge wie {@code enum Genre} in  
 * {@code src/main/resources/graphql/filme.graphql} 
 * haben.
 */
public enum GenreEnum {

	ACTION,
	DOKUMENTATION,
	DRAMA,
	FANTASY,
	KOMOEDIE,	
	SCIENCE_FICTION,
	THRILLER	
}
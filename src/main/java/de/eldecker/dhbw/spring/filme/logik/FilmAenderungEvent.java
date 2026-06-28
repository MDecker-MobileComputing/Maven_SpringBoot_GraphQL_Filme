package de.eldecker.dhbw.spring.filme.logik;

import java.time.Instant;

import de.eldecker.dhbw.spring.filme.db.FilmEntity;


/**
 * Ereignis fuer die GraphQL-Subscription zur Beobachtung von Film-Aenderungen.
 */
public class FilmAenderungEvent {

	private final Long filmId;
	private final String art;
	private final String zeitpunkt;
	private final FilmEntity film;


	public FilmAenderungEvent( Long filmId,
							   String art,
							   FilmEntity film ) {

		this.filmId = filmId;
		this.art = art;
		this.film = film;
		this.zeitpunkt = Instant.now().toString();
	}


	public Long getFilmId() {

		return filmId;
	}


	public String getArt() {

		return art;
	}


	public String getZeitpunkt() {

		return zeitpunkt;
	}


	public FilmEntity getFilm() {

		return film;
	}

}
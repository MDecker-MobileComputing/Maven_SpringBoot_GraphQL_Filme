package de.eldecker.dhbw.spring.filme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import de.eldecker.dhbw.spring.filme.db.FilmEntity;
import de.eldecker.dhbw.spring.filme.db.FilmRepository;
import de.eldecker.dhbw.spring.filme.db.FilmSortByEnum;
import de.eldecker.dhbw.spring.filme.db.GenreEnum;
import de.eldecker.dhbw.spring.filme.db.SortDirectionEnum;
import de.eldecker.dhbw.spring.filme.logik.FilmAenderungEvent;
import de.eldecker.dhbw.spring.filme.logik.FilmAenderungPublisher;


/**
 * GraphQL-Controller für Abfragen und Änderungen auf der Film-Datenbank.
 */
@Controller
public class GraphQLController {

	@Autowired
	private FilmRepository _filmRepo;

	@Autowired
	private FilmAenderungPublisher _filmAenderungPublisher;


	@QueryMapping
	public List<FilmEntity> filme( @Argument FilmSortByEnum sortBy,
								   @Argument SortDirectionEnum direction ) {

		final FilmSortByEnum sortByWert = sortBy == null
													? FilmSortByEnum.ID
													: sortBy;

		final SortDirectionEnum directionWert = direction == null
															 ? SortDirectionEnum.ASC
															 : direction;

		final Sort sortierung = Sort.by( directionWert.getSortDirection(),
									     sortByWert.getFeldname() );

		return _filmRepo.findAll( sortierung );
	}


	@QueryMapping
	public FilmEntity filmById( @Argument Long id ) {

		return _filmRepo.findById( id )
						.orElse( null );
	}


	@QueryMapping
	public List<FilmEntity> filmeNachGenre( @Argument GenreEnum genre ) {

		return _filmRepo.findByGenre( genre );
	}


	@QueryMapping
	public List<FilmEntity> verfuegbareFilme() {

		return _filmRepo.findByVerfuegbarTrue();
	}


	@MutationMapping
	public FilmEntity filmAnlegen( @Argument String titel,
								   @Argument GenreEnum genre,
								   @Argument Integer erscheinungsjahr,
								   @Argument Float bewertung,
								   @Argument String regisseur,
								   @Argument Boolean verfuegbar ) {

		pruefeBewertung( bewertung ); // IllegalArgumentException

		final FilmEntity film = new FilmEntity( titel,
										        genre,
										        erscheinungsjahr,
										        bewertung,
										        regisseur,
										        verfuegbar );
		
		final FilmEntity gespeicherterFilm = _filmRepo.save( film );
		_filmAenderungPublisher.filmAngelegt( gespeicherterFilm );
		
		return gespeicherterFilm;
	}


	@MutationMapping
	public FilmEntity filmBewertungAktualisieren( @Argument Long id,
										          @Argument Float bewertung ) {

		pruefeBewertung( bewertung ); // throws IllegalArgumentException
		
		final Optional<FilmEntity> filmOptional = _filmRepo.findById( id ); 
		
		if ( filmOptional.isEmpty() ) {
			
			return null;
		}
		
		final FilmEntity film = filmOptional.get();
		film.setBewertung( bewertung );
		
		final FilmEntity gespeicherterFilm = _filmRepo.save( film );
		_filmAenderungPublisher.filmAktualisiert( gespeicherterFilm );
		
		return gespeicherterFilm;
	}


	@MutationMapping
	public FilmEntity filmVerfuegbarkeitAktualisieren( @Argument Long id,
									                   @Argument Boolean verfuegbar ) {
  
		final Optional<FilmEntity> filmOptional = _filmRepo.findById( id );
		if ( filmOptional.isEmpty() ) {

			return null;
		}

		final FilmEntity film = filmOptional.get();
		film.setVerfuegbar( verfuegbar );

		final FilmEntity gespeicherterFilm = _filmRepo.save( film );
		_filmAenderungPublisher.filmAktualisiert( gespeicherterFilm );
		
		return gespeicherterFilm;
	}


	@MutationMapping
	public Boolean filmLoeschen( @Argument Long id ) {

		final Optional<FilmEntity> filmOptional = _filmRepo.findById( id );
		if ( filmOptional.isEmpty() ) { return false; }

		final FilmEntity film = filmOptional.get();
		_filmRepo.deleteById( id );
		_filmAenderungPublisher.filmGeloescht( film );
		
		return true;
	}


	@SubscriptionMapping
	public Flux<FilmAenderungEvent> filmAenderungen() {

		return _filmAenderungPublisher.filmAenderungen();
	}


	/**
	 * Hilfsmethode um zu überprüfen, ob {@code bewertung} im
	 * Bereich {@code 0.0} und {@code 10.0} (jeweils einschließlich
	 * liegt.
	 * 
	 * @param bewertung Zu überprüfende Bewertung für Film
	 * 
	 * @throws IllegalArgumentException Wenn {@code bewertung} echt-kleiner
	 *                                  0.0 oder echt-größer 10.0
	 */
	private void pruefeBewertung( Float bewertung ) {

		if ( bewertung == null ) { return; }

		if ( bewertung < 0.0f || bewertung > 10.0f ) {
			
			throw new IllegalArgumentException( 
					"Bewertung muss zwischen 0.0 und 10.0 liegen." );
		}
	}

}

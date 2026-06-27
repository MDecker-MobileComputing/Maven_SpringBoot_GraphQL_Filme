package de.eldecker.dhbw.spring.filme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import de.eldecker.dhbw.spring.filme.db.FilmEntity;
import de.eldecker.dhbw.spring.filme.db.FilmRepository;
import de.eldecker.dhbw.spring.filme.db.GenreEnum;


@Controller
public class FilmController {

	@Autowired
	private FilmRepository _filmRepo;


	@QueryMapping
	public List<FilmEntity> filme() {

		return _filmRepo.findAll();
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

		final FilmEntity film = new FilmEntity( titel,
										        genre,
										        erscheinungsjahr,
										        bewertung,
										        regisseur,
										        verfuegbar );
		return _filmRepo.save( film );
	}


	@MutationMapping
	public FilmEntity filmBewertungAktualisieren( @Argument Long id,
										          @Argument Float bewertung ) {
		
		final Optional<FilmEntity> filmOptional = _filmRepo.findById( id ); 
		
		if ( filmOptional.isEmpty() ) {
			
			return null;
		}
		
		final FilmEntity film = filmOptional.get();
		film.setBewertung( bewertung );
		return film;
	}


	@MutationMapping
	public Boolean filmLoeschen( @Argument Long id ) {

		if ( _filmRepo.existsById( id ) == false ) {
			
			return false;
		}

		_filmRepo.deleteById( id );
		return true;
	}

}

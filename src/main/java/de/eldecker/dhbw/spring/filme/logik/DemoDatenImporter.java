package de.eldecker.dhbw.spring.filme.logik;

import static de.eldecker.dhbw.spring.filme.db.GenreEnum.DRAMA;
import static de.eldecker.dhbw.spring.filme.db.GenreEnum.FANTASY;
import static de.eldecker.dhbw.spring.filme.db.GenreEnum.KOMOEDIE;
import static de.eldecker.dhbw.spring.filme.db.GenreEnum.SCIENCE_FICTION;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import de.eldecker.dhbw.spring.filme.db.FilmEntity;
import de.eldecker.dhbw.spring.filme.db.FilmRepository;


/**
 * Diese Service-Bean überprüft unmittelbar nach dem Start der Anwendung,
 * ob sich schon Filme in der Datenbank befinden. Wenn es keinen einzigen
 * Film gibt, dann werden einige Filme als "Demo-Daten" geladen.
 */
@Service
public class DemoDatenImporter implements ApplicationRunner {

	private final static Logger LOG = LoggerFactory.getLogger( DemoDatenImporter.class );
	
	@Autowired
	private FilmRepository _filmRepo;
	
	@Override
	public void run( ApplicationArguments args ) throws Exception {
		
		final long anzahlVorher = _filmRepo.count(); 
		if ( anzahlVorher > 0 ) {
			
			LOG.info( "Schon {} Filme gespeichert, lade keine Demo-Daten.", 
					  anzahlVorher );
			
		} else {
			
			LOG.info( "Datenbank ist leer, lade Demo-Daten ..." );
			
			final FilmEntity film1 = 
					new FilmEntity( "Spaceballs", 
							        KOMOEDIE, 
							        1987,
							        9.5f,
							        "Mel Brooks", 
							        false );
			_filmRepo.save( film1 );								
			
			final FilmEntity film2 = 
					new FilmEntity( "Jurassic Park", 
							        SCIENCE_FICTION, 
							        1993,
							        8.7f,
							        "Steven Spielberg", 
							        true );
			_filmRepo.save( film2 );					
			
			final FilmEntity film3 = 
			        new FilmEntity( "Der Herr der Ringe: Die Gefährten", 
			                        FANTASY, 
			                        2001,
			                        9.0f,
			                        "Peter Jackson", 
			                        false );
			_filmRepo.save( film3 );	
			
	        final FilmEntity film4 = 
	        		new FilmEntity( "Titanic", 
						            DRAMA, 
						            1997,
						            8.8f,
						            "James Cameron", 
						            true );
	        _filmRepo.save( film4 );		 
	        
	        final FilmEntity film5 = 
	                new FilmEntity( "The Matrix", 
	                                SCIENCE_FICTION, 
	                                1999,
	                                9.4f,
	                                "Lana und Lilly Wachowski", 
	                                false );
	        _filmRepo.save( film5 );	        
	        
	        final long anzahlNachher = _filmRepo.count();
	        LOG.info( "Nach Laden Demo-Daten sind jetzt {} Filme in der DB.", 
	        		  anzahlNachher );
		} 		
	}

}

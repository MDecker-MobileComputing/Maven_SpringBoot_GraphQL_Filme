package de.eldecker.dhbw.spring.filme.logik;

import org.springframework.stereotype.Service;

import de.eldecker.dhbw.spring.filme.db.FilmEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


/**
 * Verteilt Film-Aenderungen an alle Abonnenten der Subscription.
 */
@Service
public class FilmAenderungPublisher {

	private final Sinks.Many<FilmAenderungEvent> _sink =
			Sinks.many().replay().limit( 100 );


	public Flux<FilmAenderungEvent> filmAenderungen() {

		return _sink.asFlux();
	}


	public void filmAngelegt( FilmEntity film ) {

		publish( new FilmAenderungEvent( film.getId(), "ANGELEGT", film ) );
	}


	public void filmAktualisiert( FilmEntity film ) {

		publish( new FilmAenderungEvent( film.getId(), "AKTUALISIERT", film ) );
	}


	public void filmGeloescht( FilmEntity film ) {

		publish( new FilmAenderungEvent( film.getId(), "GELOESCHT", film ) );
	}


	private void publish( FilmAenderungEvent event ) {

		_sink.emitNext( event, Sinks.EmitFailureHandler.FAIL_FAST );
	}

}
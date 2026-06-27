package de.eldecker.dhbw.spring.filme.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface FilmRepository extends JpaRepository<FilmEntity, Long> {

	List<FilmEntity> findByGenre( GenreEnum genre );

	List<FilmEntity> findByVerfuegbarTrue();
}

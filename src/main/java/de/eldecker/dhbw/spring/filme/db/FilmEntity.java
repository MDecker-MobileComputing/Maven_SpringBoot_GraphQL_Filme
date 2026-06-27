package de.eldecker.dhbw.spring.filme.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table( name = "filme" )
public class FilmEntity {

	@Id
	@GeneratedValue( strategy = IDENTITY )
	private Long id;

	@Column( nullable = false )
	private String titel;

	@Enumerated( EnumType.STRING )
	@Column( nullable = false )
	private GenreEnum genre;

	@Column( nullable = false )
	private Integer erscheinungsjahr;

	private Float bewertung;

	private String regisseur;

	@Column( nullable = false )
	private Boolean verfuegbar;


	/** Default-Konstruktor, obligatorisch für Entity-Klasse. */
	protected FilmEntity() {}		
	

	public FilmEntity( String titel,
					   GenreEnum genre,
					   Integer erscheinungsjahr,
					   Float bewertung,
					   String regisseur,
					   Boolean verfuegbar ) {

		this.titel            = titel;
		this.genre            = genre;
		this.erscheinungsjahr = erscheinungsjahr;
		this.bewertung        = bewertung;
		this.regisseur        = regisseur;
		this.verfuegbar       = verfuegbar;
	}


	public Long getId() {

		return id;
	}


	public String getTitel() {

		return titel;
	}


	public void setTitel( String titel ) {

		this.titel = titel;
	}


	public GenreEnum getGenre() {

		return genre;
	}


	public void setGenre( GenreEnum genre ) {

		this.genre = genre;
	}


	public Integer getErscheinungsjahr() {

		return erscheinungsjahr;
	}


	public void setErscheinungsjahr( Integer erscheinungsjahr ) {

		this.erscheinungsjahr = erscheinungsjahr;
	}


	public Float getBewertung() {

		return bewertung;
	}


	public void setBewertung( Float bewertung ) {

		this.bewertung = bewertung;
	}


	public String getRegisseur() {

		return regisseur;
	}


	public void setRegisseur( String regisseur ) {

		this.regisseur = regisseur;
	}


	public Boolean getVerfuegbar() {

		return verfuegbar;
	}


	public void setVerfuegbar( Boolean verfuegbar ) {

		this.verfuegbar = verfuegbar;
	}
}
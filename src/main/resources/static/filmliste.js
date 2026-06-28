"use strict";


let divTabelleFilm = null;


/**
 * Funktion wird ausgeführt, sobald die HTML-Seite vollständig geladen ist.
 */
document.addEventListener( "DOMContentLoaded", function() {

  divTabelleFilm = document.getElementById( "tabelleFilm" );
  if ( !divTabelleFilm ) {

    console.error( "Fehler: Konnte <div id=\"tabelleFilm\"> nicht finden!" );
    return;
  }

  ladeUndZeigeFilme();
});


/**
 * Lädt alle Filme von der GraphQL-API.
 *
 * @returns {Promise<Array<object>>} Liste von Film-Objekten.
 */
async function ladeFilme() {

  const graphqlQuery = `
    query {
      filme( sortBy: ERSCHEINUNGSJAHR, direction: ASC ) {
        id
        titel
        genre
        erscheinungsjahr
        bewertung
        regisseur
        verfuegbar
      }
    }
  `;

  const antwort = await fetch( "/graphql", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Accept"      : "application/json"
    },
    body: JSON.stringify( { query: graphqlQuery } )
  } );

  if ( !antwort.ok ) {

    throw new Error( `HTTP-Fehler beim Laden der Filme: ${antwort.status}` );
  }

  const json = await antwort.json();

  if ( json.errors && json.errors.length > 0 ) {

    const meldung = json.errors.map( function(err) { return err.message; } ).join( "; " );
    throw new Error( `GraphQL-Fehler: ${meldung}` );
  }

  if ( !json.data || !Array.isArray( json.data.filme ) ) {

    throw new Error( "Unerwartete API-Antwort: Feld data.filme fehlt oder ist ungültig." );
  }

  return json.data.filme;
}


/**
 * Erstellt eine HTML-Tabelle aus der Liste der Filme.
 *
 * @param {Array<object>} filme Liste der Filme.
 * 
 * @returns {HTMLTableElement} Fertige Tabelle.
 */
function erstelleFilmtabelle( filme ) {

  const tabelle = document.createElement( "table" );

  const kopf = document.createElement( "thead" );
  kopf.innerHTML = `
    <tr>
      <th>ID</th>
      <th>Titel</th>
      <th>Genre</th>
      <th>Erscheinungsjahr</th>
      <th>Bewertung</th>
      <th>Regisseur</th>
      <th>Verfügbar</th>
    </tr>
  `;
  tabelle.appendChild( kopf );

  const rumpf = document.createElement( "tbody" );

  filme.forEach( function( film ) {
    const zeile = document.createElement( "tr" );

    const bewertungText = ( film.bewertung === null || film.bewertung === undefined )
      ? "-"
      : film.bewertung.toFixed( 1 );

    const regisseurText  = film.regisseur  ? film.regisseur : "-";
    const verfuegbarText = film.verfuegbar ? "Ja"           : "Nein";

    zeile.innerHTML = `
      <td>${film.id}</td>
      <td>${film.titel}</td>
      <td>${film.genre}</td>
      <td>${film.erscheinungsjahr}</td>
      <td>${bewertungText}</td>
      <td>${regisseurText}</td>
      <td>${verfuegbarText}</td>
    `;

    rumpf.appendChild( zeile );
  } );

  tabelle.appendChild( rumpf );
  return tabelle;
}


/**
 * Lädt die Filme und rendert die Tabelle unterhalb des Ziel-DIVs.
 */
async function ladeUndZeigeFilme() {

  divTabelleFilm.textContent = "Filmliste wird geladen...";

  try {

    const filme = await ladeFilme();

    divTabelleFilm.innerHTML = "";

    if ( filme.length === 0 ) {

      divTabelleFilm.textContent = "Keine Filme gefunden.";
      return;
    }

    const tabelle = erstelleFilmtabelle( filme );
    divTabelleFilm.appendChild( tabelle );

  } catch ( fehler ) {

    console.error( fehler );
    divTabelleFilm.textContent = `Fehler beim Laden der Filmliste: ${fehler.message}`;
  }
}

# Spring Boot: Filmdatenbank mit GraphQL #

<br>

Diese Repo enthält ein Maven-Projekt mit einer Spring-Boot-Anwendung, die eine Tabelle mit Filmen
über eine GraphQL-API bereitstellt.

<br>

----

## GraphQL-Queries ##

<br>

Wenn die Anwendung lokal ausgeführt wird, dann ist unter der folgenden URL der Web-Client GraphiQL verfügbar:
http://localhost:8080/graphiql

<br>

Liste aller Filme mit allen Attributen:
```
query {
  filme {
    id
    titel
    genre
    erscheinungsjahr
    bewertung
    regisseur
    verfuegbar
  }
}
```

<br>

Einige Attribute für Film mit ID=1 abfragen:
```
query {
  filmById(id: "1") {
    id
    titel
    genre
  }
}
```

<br>

----

## License ##

<br>

See the [LICENSE file](LICENSE.md) for license rights and limitations (BSD 3-Clause License).

<br>

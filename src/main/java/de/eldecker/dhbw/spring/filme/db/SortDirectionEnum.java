package de.eldecker.dhbw.spring.filme.db;

import org.springframework.data.domain.Sort;


/**
 * Erlaubte Sortierrichtung für die Query {@code filme(...)}.
 */
public enum SortDirectionEnum {

	ASC(  Sort.Direction.ASC  ),
	DESC( Sort.Direction.DESC );

	
	private final Sort.Direction _sortDirection;

	
	SortDirectionEnum( Sort.Direction sortDirection ) {

		_sortDirection = sortDirection;
	}


	public Sort.Direction getSortDirection() {

		return _sortDirection;
	}
}

package com.service.pokemon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.service.pokemon.models.Pokemon;

@Repository
public interface PokemonRepository extends PagingAndSortingRepository<Pokemon, Long> {

	@Query("select p from Pokemon p where p.userId is null")
	public Page<Pokemon> findAllPublicPokemons(Pageable page);
	
	@Query("select p from Pokemon p where p.userId = ?1")
	public Page<Pokemon> findAllPokemonsByUserId(Long userId, Pageable page);
	

	@Query(value = "select p from Pokemon p join p.userPokemonLike up join up.user u where u.id = ?1")
	public Page<Pokemon> findLikesItemsByUserId(Long userId, Pageable page);
}

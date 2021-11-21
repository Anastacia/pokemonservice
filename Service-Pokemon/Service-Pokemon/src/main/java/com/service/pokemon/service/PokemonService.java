package com.service.pokemon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.service.pokemon.models.Pokemon;
import com.service.pokemon.models.UserPokemonLike;

public interface PokemonService {

	public Pokemon savePokemon(Pokemon pokemon); 
	
	public Page<Pokemon> findAllPublicPokemons(Pageable page);
	
	public Page<Pokemon> findAllPokemonsByUserId(Long userId, Pageable page);
	
	public Optional<Pokemon> findById(Long id);
	
	public void deleteById(Long id);
	
	public void deleteAllById(List<Long> id);
	
	public UserPokemonLike saveLikePokemon(UserPokemonLike userPokemonLike);

	public Page<Pokemon> findPokemonsByLike(Long userId, Pageable page);
}

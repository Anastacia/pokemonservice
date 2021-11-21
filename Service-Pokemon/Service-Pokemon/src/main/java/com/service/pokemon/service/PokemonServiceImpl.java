package com.service.pokemon.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.service.pokemon.models.Pokemon;
import com.service.pokemon.models.UserPokemonLike;
import com.service.pokemon.repository.PokemonRepository;
import com.service.pokemon.repository.UserPokemonLikeRepository;

@Service
public class PokemonServiceImpl implements PokemonService {
	
	@Autowired
	PokemonRepository repository;
	
	@Autowired
	UserPokemonLikeRepository userPokemonLikeRepository;

	@Override
	@Transactional
	public Pokemon savePokemon(Pokemon pokemon) {
		return repository.save(pokemon);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pokemon> findAllPublicPokemons(Pageable page) {
		return repository.findAllPublicPokemons(page);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pokemon> findAllPokemonsByUserId(Long userId, Pageable page) {
		return repository.findAllPokemonsByUserId(userId, page);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pokemon> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteAllById(List<Long> id) {
		repository.deleteAllById(id);
	}

	@Override
	@Transactional
	public UserPokemonLike saveLikePokemon(UserPokemonLike userPokemonLike) {
		return userPokemonLikeRepository.save(userPokemonLike);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Pokemon> findPokemonsByLike(Long userId, Pageable page) {
		return repository.findLikesItemsByUserId(userId, page);
	}

}

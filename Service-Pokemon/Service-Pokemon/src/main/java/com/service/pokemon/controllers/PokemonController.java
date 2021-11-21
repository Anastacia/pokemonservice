package com.service.pokemon.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.pokemon.models.Pokemon;
import com.service.pokemon.models.UserPokemonLike;
import com.service.pokemon.service.PokemonService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/pokemon")
public class PokemonController {

	@Autowired
	PokemonService service;

	@PostMapping
	public ResponseEntity<?> createPokemon(@RequestBody Pokemon pokemon) throws Exception {
		try {
			Pokemon pokemonDb = service.savePokemon(pokemon);

			return ResponseEntity.status(HttpStatus.CREATED).body(pokemonDb);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("/public")
	public ResponseEntity<?> findAllPublicPokemons(Pageable page) {
		Page<Pokemon> listPokemon = service.findAllPublicPokemons(page);

		return ResponseEntity.ok(listPokemon);
	}

	@GetMapping
	public ResponseEntity<?> findAllPublicPokemons(@RequestParam Long userId, Pageable page) {
		Page<Pokemon> listPokemon = service.findAllPokemonsByUserId(userId, page);

		return ResponseEntity.ok(listPokemon);
	}

	@PutMapping("/{id}/{userId}")
	public ResponseEntity<?> editPokemon(@Valid @RequestBody Pokemon pokemon, BindingResult result,
			@PathVariable Long id, @PathVariable Long userId) {
		if (result.hasErrors()) {
			return this.validar(result);
		}

		Optional<Pokemon> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Pokemon pokemonDb = o.get();

		if (pokemonDb.getUserId() != userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		pokemonDb.setName(pokemon.getName());
		pokemonDb.setType(pokemon.getType());
		pokemonDb.setPower(pokemon.getPower());
		pokemon.setHp(pokemon.getHp());

		return ResponseEntity.status(HttpStatus.CREATED).body(service.savePokemon(pokemonDb));
	}

	@DeleteMapping("/{id}/{userId}")
	public ResponseEntity<?> deletePokemon(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
		Optional<Pokemon> o = service.findById(id);

		if (o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Pokemon pokemonDb = o.get();

		if (pokemonDb.getUserId() != userId) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		service.deleteById(id);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/delete-pokemons/{userId}")
	public ResponseEntity<?> deleteAllPokemons(@PathVariable Long userId, @RequestParam List<Long> ids) {

		List<Long> pokemonIds = ids.stream().map(id -> {
			Optional<Pokemon> o = service.findById(id);
			if (!o.isEmpty()) {
				Pokemon pokemon = o.get();
				if (pokemon.getUserId() == userId) {
					return pokemon.getId();
				}
			}

			return null;
		}).collect(Collectors.toList());

		if (pokemonIds.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		service.deleteAllById(pokemonIds);

		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/like-pokemon")
	public ResponseEntity<?> likePokemon(@RequestBody UserPokemonLike userLike){
		try {
			UserPokemonLike userLikeDb = service.saveLikePokemon(userLike);

			return ResponseEntity.status(HttpStatus.CREATED).body(userLikeDb);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
	
	@GetMapping("/like-pokemon")
	public ResponseEntity<?> getPokemonLikes(@RequestParam Long userId, Pageable page){
		Page<Pokemon> pokemons = service.findPokemonsByLike(userId, page);
		
		return ResponseEntity.ok(pokemons);
	}

	private ResponseEntity<?> validar(BindingResult result) {
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "Field " + err.getField() + " " + err.getDefaultMessage());
		});

		return ResponseEntity.badRequest().body(errores);
	}

}

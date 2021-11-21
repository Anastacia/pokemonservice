package com.service.pokemon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.service.pokemon.models.Pokemon;
import com.service.pokemon.models.UserPokemonLike;

@Repository
public interface UserPokemonLikeRepository extends PagingAndSortingRepository<UserPokemonLike, Long> {

}

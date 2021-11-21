package com.service.pokemon.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pokemons")
public class Pokemon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	private String type;
	
	private Integer power;
	
	private Integer hp;
	
	private Long userId;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="pokemon", cascade= CascadeType.ALL, orphanRemoval= true)
	@JsonIgnoreProperties(value = {"pokemon"}, allowSetters = true)
	private List<UserPokemonLike> userPokemonLike;
	
	public Pokemon() {
	}

	public Pokemon(Long id, String name, String type, Integer power, Integer hp, Long userId) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.power = power;
		this.hp = hp;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}

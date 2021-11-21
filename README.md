# pokemonservice
##57 blocks test.

## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Collaboration](#collaboration)
5. [FAQs](#faqs)

### General Info
***
This HTTP server API is called Pokemons
It consist of a registration service, that save an user in the database, a login service with email and password to authenticate user,usign an username, password and a token, this one expire in 20 minutes, after this time, the user must login again to communicate with the server. If an user is authorizade to login it can access to the resources.

Once logged in users can view a list of pokemons, there are some private, meaning the ones a user creates, and public, pokemons that already exist, create a new register of a pokemon, edit at least one field of this pokemon (name, type, power etc), there are two ways to delete private registers, all at once o by id. There is also the option to like public pokemons and retrieve a list of their liked pokemons!

## Technologies
***
* Spring Tool Suite 4 Version 4.12.1.RELEASE
* MySql Version 8.0
* Maven-4.0.0
* JSON Web Token (JWT)




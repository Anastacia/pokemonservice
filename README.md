# pokemonservice
##57 blocks test.

### General Info
This HTTP server API is called Pokemons
It consist of a registration service, that save an user in the database, a login service with email and password to authenticate user,usign an username, password and a token, this one expire in 20 minutes, after this time, the user must login again to communicate with the server. If an user is authorizade to login it can access to the resources.

Once logged in users can view a list of pokemons, there are some private, meaning the ones a user creates, and public, pokemons that already exist, create a new register of a pokemon, edit at least one field of this pokemon (name, type, power etc), there are two ways to delete private registers, all at once o by id. There is also the option to like public pokemons and retrieve a list of their liked pokemons!





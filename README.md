This application utilises the pokeapi.co Pokemon API, recyclerview, cardview, SQLite databasing and volley
It features a recyclerview on the main page for Pokemon and a recyclerview on the detailed page to look at moves
Features logic to determine if DB is empty and will make a JSON call. If it is not, it will load from the DB
Refresh buttons refresh the Pokedex and Pokemon list and also allow the user to switch the type of icons displayed in the Pokedex (shiny, back, front)
A search function is also included which changes the recyclerview on the fly depending on the user's input

Functional features:
- Full generation 1 Pokedex
- Has a detailed view for each Pokemon with the base stats, basic information and the moves for the Pokemon
- Loads from both an online API and a SQLite database
- Has a refresh button which refreshes the application with shiny, back and default icons
- Has a search feature that filters the recyclerview on the fly

Non functional features:
- Background colours for the Pokemon types
- Loads from a database or Volley cache if loading the second time
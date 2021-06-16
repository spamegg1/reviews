import React from 'react'
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'
import { useApi } from './useApi'
import LoadingSpinner from './LoadingSpinner'
import ErrorMessage from './ErrorMessage'
import PokemonPage from './PokemonPage'
import PokemonList from './PokemonList'

const mapResults = (({ results }) => results.map(({ url, name }) => ({
  url,
  name,
  id: parseInt(url.match(/\/(\d+)\//)[1])
})))

const App = () => {
  const { data: pokemonList, error, isLoading } = useApi('https://pokeapi.co/api/v2/pokemon/?limit=784', mapResults)
  if (isLoading) {
    return <LoadingSpinner />
  }
  if (error) {
    return <ErrorMessage error={error} />
  }

  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <PokemonList pokemonList={pokemonList} />
        </Route>
        <Route path="/pokemon/:name" render={(routeParams) => {
          const pokemonId = pokemonList.find(({ name }) => name === routeParams.match.params.name).id
          const previous = pokemonList.find(({ id }) => id === pokemonId - 1)
          const next = pokemonList.find(({ id }) => id === pokemonId + 1)
          return <PokemonPage pokemonList={pokemonList} previous={previous} next={next} />
        }} />
      </Switch>
    </Router>
  )
}

export default App
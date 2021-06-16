import React from 'react'
import { render, screen } from '@testing-library/react'
import { Router } from 'react-router-dom'
import { createMemoryHistory } from 'history'
import axiosMock from 'axios'
import { act } from 'react-dom/test-utils'
import '@testing-library/jest-dom/extend-expect'
import PokemonPage from '../src/PokemonPage'

jest.mock('axios')

const previous = {
  url: 'https://pokeapi.co/api/v2/pokemon/132/',
  name: 'ditto',
  id: 132
}
const next = {
  url: 'https://pokeapi.co/api/v2/pokemon/134/',
  name: 'vaporeon',
  id: 134
}

const pokemonList = {
  id: 133,
  abilities: [
    {
      ability: {
        name: 'anticipation',
        url: 'https://pokeapi.co/api/v2/ability/107/'
      },
      is_hidden: true,
      slot: 3
    },
    {
      ability: {
        name: 'adaptability',
        url: 'https://pokeapi.co/api/v2/ability/91/'
      },
      is_hidden: false,
      slot: 2
    }
  ],
  name: 'eevee',
  stats: [
    {
      base_stat: 55,
      effort: 0,
      stat: {
        name: 'attack',
        url: 'https://pokeapi.co/api/v2/stat/2/'
      }
    },
    {
      base_stat: 55,
      effort: 0,
      stat: {
        name: 'hp',
        url: 'https://pokeapi.co/api/v2/stat/1/'
      }
    }
  ],
  types: [
    {
      slot: 1,
      type: {
        name: 'normal',
        url: 'https://pokeapi.co/api/v2/type/1/'
      }
    }
  ],
  sprites: { front_default: 'URL' }
}

const history = createMemoryHistory()

describe('<PokemonPage />', () => {
  beforeEach(() => {
    history.push('/pokemon/eevee')
  })

  it('should render abilities', async () => {
    axiosMock.get.mockResolvedValueOnce({ data: pokemonList })

    await act(async () => {
      render(
        <Router history={history}>
          <PokemonPage />
        </Router>
      )
    })

    expect(screen.getByText('adaptability')).toBeVisible()
    expect(screen.getByText('anticipation')).toBeVisible()
  })

  it('should render stats', async () => {
    axiosMock.get.mockResolvedValueOnce({ data: pokemonList })

    await act(async () => {
      render(
        <Router history={history}>
          <PokemonPage />
        </Router>
      )
    })

    expect(screen.getByTestId('stats')).toHaveTextContent('hp55attack55')
  })

  it('should render previous and next urls if they exist', async () => {
    axiosMock.get.mockResolvedValueOnce({ data: pokemonList })

    await act(async () => {
      render(
        <Router history={history}>
          <PokemonPage previous={previous} next={next} />
        </Router>
      )
    })

    expect(screen.getByText('Previous')).toHaveAttribute('href', '/pokemon/ditto')
    expect(screen.getByText('Next')).toHaveAttribute('href', '/pokemon/vaporeon')
  })

  it('should not render previous and next urls if none exist', async () => {
    axiosMock.get.mockResolvedValueOnce({ data: pokemonList })

    await act(async () => {
      render(
        <Router history={history}>
          <PokemonPage />
        </Router>
      )
    })

    expect(screen.queryByText('Previous')).toBeNull()
    expect(screen.queryByText('Next')).toBeNull()
  })
})
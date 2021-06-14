import React, { useState, useEffect } from 'react'
import axios from 'axios'
import Countries from './components/Countries'

function App() {
  // STATES
  const [query, setQuery] = useState('')
  const [countries, setCountries] = useState([])
  const url = 'https://restcountries.eu/rest/v2/all'

  // EFFECTS
  useEffect(() => {                          // this gets countries from the API
    console.log('effect')
    axios
      .get(url)
      .then(response => {
        console.log('promise fulfilled')
        setCountries(response.data)
      })
  }, [])                     // only runs once, just upon first load of the page

  // EVENT HANDLERS
  const queryChange = (event) => {      // this handles typing in the search bar
    setQuery(event.target.value)
  }

  // filter countries by search result
  const filtered = countries.filter(c =>
    c.name.toLowerCase().includes(query.toLowerCase())
  )

  // RETURN
  return (
    <>
      find countries
      <input value={query} onChange={queryChange}/>
      <p></p>
      <Countries countries={filtered}/>
    </>
  )
}

export default App

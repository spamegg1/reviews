import React, { useState } from 'react'
import Country from './Country'
import axios from 'axios'


const Countries = ({ countries }) => {
  // STATES
  // this is the name of the country being viewed "in detail" by clicking "show"
  // when there are less than 10 results
  // (violating the principle: "keep all state in main App, not in components!")
  const [viewed, setViewed] = useState('')

  // using only one weather state for all countries, instead of one per country,
  // in order to reduce number of API usages (limited to 1000 per month!)
  const [weather, setWeather] = useState(null)
  const key = process.env.REACT_APP_API_KEY      // this is inside the .env file


  // toggles the country being shown "in detail",
  // or switches it to another country (depends on the state of 'viewed')
  // also queries the weather API for the capital of the country
  const toggle = (country) => {
    if (viewed === country.name) {                          // "hide" is pressed
      setViewed('')
    }
    else {                                                  // "show" is pressed
      setViewed(country.name)
      axios
      .get(`http://api.weatherstack.com/current?access_key=${key}&query=${country.capital}`)
      .then(response => {
        console.log(response)
        setWeather(response.data.current)
      })
    }
  }

  // this depends on the state of 'viewed'
  const ToggleButton = ({country}) => {
    return (
      <button onClick={() => toggle(country)}>
        {country.name === viewed ? 'hide' : 'show'}
      </button>
    )
  }

  // RETURN
  const results = countries.length
  if (results === 0) {
    return (
      <>
        no matches
      </>
    )
  }

  // only one country matches the search filter. 'detailed' view by default
  // (with population, languages, weather...)
  if (results === 1) {
    return (
      <Country key={countries[0].alpha2Code} country={countries[0]}
              type='detailed' weather={weather}/>
    )
  }

  // < 10 countries matches the search filter.
  // 'detailed' view only for the country whose "show" button is pressed
  // other countries only display the country name.
  if (results < 10) {
    return (
      <li>
        {countries.map(c =>
          <div key={c.alpha2Code}>
            <ToggleButton country={c}/>
            <Country country={c}
                     type={c.name === viewed ? 'detailed' : ''}
                     weather={weather}/>
          </div>)
        }
      </li>
    )
  }
  else {
    return (
      <>
        too many matches, specify another filter
      </>
    )
  }
}

export default Countries

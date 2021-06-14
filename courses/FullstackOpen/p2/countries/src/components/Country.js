import React from 'react'
import Weather from './Weather'


const Country = ({ country, type, weather }) => {

  if (type === 'detailed') {
    return (
      <div>
        <h2>{country.name}</h2>
        capital {country.capital}
        <p></p>
        population {country.population}
        <h3>languages</h3>
        <ul>
        {country.languages.map(lang =>
          <li key={lang.iso639_2}>
            {lang.name}
          </li>
        )}
        </ul>
        <img alt='flag' height="80px" src={country.flag}/>
        <Weather weather={weather} city={country.capital}/>
      </div>
    )
  }
  return (
    <>{country.name}</>
  )
}

export default Country

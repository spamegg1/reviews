import React, {useState, useEffect } from 'react'
import ReactDOM from 'react-dom'
import axios from 'axios'

const Weather = ({ weather, city }) => {
  console.log(weather)
  if (!weather) {
    return null
  }

  return (
    <div>
      <h3>Weather in {city}</h3>
      <div>
        <strong>temperature:</strong> {weather.temperature} Celcius
      </div>
      <img src={weather.weather_icons[0]} alt={weather.weather_descriptions[0]} />
      <div>
        <strong>wind:</strong> {weather.wind_speed} mph direction {weather.wind_dir}
      </div>

    </div>
  )
}

const Country = ({ country }) => {
  const [weather, setWeather] = useState(null)
  const api_key = process.env.REACT_APP_API_KEY
  const url = `http://api.weatherstack.com/current?access_key=${api_key}&query=${country.capital}`

  useEffect(() => {
    axios.get(url).then(response => {
      setWeather(response.data.current)
    })
  }, [])

  return (
    <div>
      <h2>{country.name}</h2>

      <div>capital {country.capital}</div>
      <div>population {country.population}</div>

      <h3>Spoken languages</h3>
      <ul>
        {country.languages.map(lang =>
          <li key={lang.iso639_2}>
            {lang.name}
          </li>
        )}
      </ul>
      <div>
        <img src={country.flag} height="80px" alt="flag"/>
      </div>
      <Weather weather={weather} city={country.capital} />
    </div>
  )
}

const Countries = ({ countries, setValue }) => {
  if (countries.length === 0) {
    return (
      <div>
        no matches
      </div>
    )
  }

  if (countries.length === 1) {
    return (
      <Country country={countries[0]} />
    )
  }

  if (countries.length < 10) {
      return(
        <div>
          {countries.map(c =>
            <div key={c.alpha2Code}>
              {c.name}
              <button onClick={() => setValue(c.name)}>
                show
              </button>
            </div>
          )}
        </div>
      )
  }

  return (
    <div>
      Too many matches, specify another filter
    </div>
  )
}

const Serach = ({ value, setValue }) => {
  const handleChange = (event) => {
    setValue(event.target.value)
  }

  return (
    <div>
      find countries
      <input value={value} onChange={handleChange} />
    </div>
  )
}

const App = () => {
  const [countries, setCountries] = useState([])
  const [filter, setFilter] = useState('')

  useEffect(() => {
    axios.get('https://restcountries.eu/rest/v2/all').then((result) => {
      setCountries(result.data)
    })
  }, [])

  const filteredCountries = filter.length === 1 ?
    countries :
    countries.filter(c => c.name.toLowerCase().indexOf(filter.toLowerCase())>-1 )

  return (
    <div>
      <Serach
        value={filter}
        setValue={setFilter}
      />
      <Countries
        countries={filteredCountries}
        setValue={setFilter}
      />
    </div>
  )
}

ReactDOM.render(<App />, document.getElementById('root'))

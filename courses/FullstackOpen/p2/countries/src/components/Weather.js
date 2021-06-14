import React from 'react'


// 'weather' comes from the weather state in Countries.js, passed to Country.js,
// which is then passed to here.
// 'city' comes from country.capital in Country.js
const Weather = ({ weather, city }) => {
  if (!weather) {
    return null
  }

  return (
    <div>
      <h3>Weather in {city}</h3>
      <div>
        <strong>temperature:</strong> {weather.temperature} Celcius
      </div>
      <img src={weather.weather_icons[0]}
            alt={weather.weather_descriptions[0]} />
      <div>
        <strong>wind:</strong>
        {weather.wind_speed} mph direction {weather.wind_dir}
      </div>
    </div>
  )
}

export default Weather

import React from 'react'
import ReactDOM from 'react-dom'
import useField from './hooks/field'

const App = () => {
  const name = useField('text')
  const born = useField('text')
  const height = useField('text')

  return (
    <div>
      <form>
        name:
        <input
          type={name.type}
          value={name.value}
          onChange={name.onChange}
        />
        <br/>
        birthdate:
        <input
          type={born.type}
          value={born.value}
          onChange={born.onChange}
        />
        <br />
        height:
        <input
          type={height.type}
          value={height.value}
          onChange={height.onChange}
        />
      </form>
      <div>
        {name} {born} {height}
      </div>
    </div>
  )
}

ReactDOM.render(<App />, document.getElementById('root'))

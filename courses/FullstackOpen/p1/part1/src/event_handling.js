import React, {useState} from 'react'
import ReactDOM from 'react-dom'

const App = () => {
  const [value, setValue] = useState(10)

  return (
    <div>
      {value}
      <button onClick={() => setValue(0)}>
        reset to zero
      </button>
    </div>
  )
}

ReactDOM.render(<App />, document.getElementById('root'))

import React, {useState} from 'react'
import ReactDOM from 'react-dom'

const App = () => {
  const [counter, setCounter] = useState(0)

  const handleClick = () => {
    console.log("clicked")
    setCounter(counter + 1)
  }

  return (
    <div>
      <div>{counter}</div>
      <button onClick={handleClick}>
        plus
      </button>
    </div>
  )
}

ReactDOM.render(
  <App/>,
  document.getElementById('root')
)
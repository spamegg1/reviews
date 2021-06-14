import React, {useState} from 'react'
import ReactDOM from 'react-dom'

const Display = ({counter}) => {
  return (
    <div>{counter}</div>
  )
}

const Button = ({handleClick, text}) => {
  return (
    <button onClick={handleClick}>
      {text}
    </button>
  )
}

const App = () => {
  const [counter, setCounter] = useState(0)

  const increaseByOne = () => setCounter(counter + 1)
  const decreaseByOne = () => setCounter(counter - 1)
  const setToZero = () => setCounter(0)

  return (
    <div>
      <Display counter={counter}/>
      <Button text="plus" handleClick={increaseByOne}/>
      <Button text="minus" handleClick={decreaseByOne}/>
      <Button text="zero" handleClick={setToZero}/>
    </div>
  )
}

ReactDOM.render(
  <App/>,
  document.getElementById('root')
)
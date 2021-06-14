import React, {useState} from 'react'
import ReactDOM from 'react-dom'

const Button = (props) => {
  console.log('props value is', props)
  return (
    <button onClick={props.onClick}>
      {props.text}
    </button>
  )
}

const History = (props) => {
  if (props.allClicks.length === 0) {
    return (
      <div>
        the app is used by pressing the buttons
      </div>
    )
  }

  return (
    <div>
      button press history: {props.allClicks.join(' ')}
    </div>
  )
}

const App = () => {
  const [left, setLeft] = useState(0)
  const [right, setRight] = useState(0)
  const [allClicks, setAllClicks] = useState([])

  const handleLeftClick = () => {
    setAllClicks(allClicks.concat('L')) // concat does not directly mutate state
    setLeft(left + 1)
  }
  const handleRightClick = () => {
    setAllClicks(allClicks.concat('R')) // concat does not directly mutate state
    setRight(right + 1)
  }

  debugger;

  return (
    <div>
      {left}
      <Button onClick={handleLeftClick} text={left}/>
      <Button onClick={handleRightClick} text={right}/>
      {right}
      <History allClicks={allClicks}/>
    </div>
  )
}

ReactDOM.render(<App/>, document.getElementById('root'))
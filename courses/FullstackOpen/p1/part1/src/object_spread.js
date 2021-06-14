import React, {useState} from 'react'
import ReactDOM from 'react-dom'

const App = () => {
  const [clicks, setClicks] = useState({
    left: 0, right: 0
  })

  // DO NOT MUTATE STATE DIRECTLY! These create new objects instead
  const handleLeftClick = () => setClicks({...clicks, left: clicks.left + 1})
  const handleRightClick = () => setClicks({...clicks, right: clicks.right + 1})

  return (
    <div>
      {clicks.left}
      <button onClick={handleLeftClick}>left</button>
      <button onClick={handleRightClick}>right</button>
      {clicks.right}
    </div>
  )
}

ReactDOM.render(<App/>, document.getElementById('root'))

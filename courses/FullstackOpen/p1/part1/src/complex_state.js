import React, {useState} from 'react'
import ReactDOM from 'react-dom'

const App = () => {
  const [left, setLeft] = useState(0)
  const [right, setRight] = useState(0)

  return (
    <div>
      <button onClick={() => setLeft(left + 1)}>left</button>
      <button onClick={() => setRight(right + 1)}>right</button>
    </div>
  )
}

ReactDOM.render(<App/>, document.getElementById('root'))

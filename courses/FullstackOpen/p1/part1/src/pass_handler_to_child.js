import React, {useState} from 'react'
import ReactDOM from 'react-dom'

const Button = ({text, handleClick}) => (
  <button onClick={handleClick}>text</button>
)

const App = () => {
  const [value, setValue] = useState(10)
  const setToValue = (newValue) => {setValue(newValue)}

  return (
    <div>
      {value}
      <Button handleClick={() => setToValue(1000)} text={'thousand'}/>
      <Button handleClick={() => setToValue(0)} text={'reset'}/>
      <Button handleClick={() => setToValue(value + 1)} text={'increment'}/>
    </div>
  )
}

ReactDOM.render(<App />, document.getElementById('root'))

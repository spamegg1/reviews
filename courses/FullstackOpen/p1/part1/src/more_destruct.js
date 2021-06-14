import React from 'react'
import ReactDOM from 'react-dom'

// DESTRUCTURING EVEN MORE: EQUIVALENT TO const {name, age} = props
const Hello = ({name, age}) => {

  // COMPACT ARROW FUNCTION WITHOUT CURLY BRACKETS
  const bornYear = () => new Date().getFullYear() - age

  return (
    <div>
      <p>
        Hello {name}, you are {age} years old
      </p>
      <p>So you were probably born in {bornYear()}</p>
    </div>
  )
}

const App = () => {
  const name = 'Peter'
  const age = 10

  return (
    <div>
    <h1>Greetings</h1>
    <Hello name="Maya" age={26 + 10} />
    <Hello name={name} age={age} />
    </div>
  )
}

ReactDOM.render(<App />, document.getElementById("root"))

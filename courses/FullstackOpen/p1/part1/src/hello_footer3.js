import React from 'react'
import ReactDOM from 'react-dom'

const Hello = (props) => {
  return (
    <div>
      <p>Hello {props.name}, you are {props.age} years old</p>
    </div>
  )
}

const Footer = () => {
  return (
    <div>
      greeting app created by <a href="https://github.com/mluukkai">mluukkai</a>
    </div>
  )
}

const App = () => {
  const a = 10
  const b = 20
  const name = "Peter"
  const age = 10
  const now = new Date()

  console.log('Hello from component')
  return (
    <>
      <h1>Greetings</h1>
      <Hello name="Maya" age={26 + 10}/>
      <Hello name={name} age={age}/>
      <p>It is {now.toString()}</p>
      <p>{a} plus {b} is {a + b}</p>
      <Footer />
    </>
  )
}

ReactDOM.render(<App />, document.getElementById("root"))

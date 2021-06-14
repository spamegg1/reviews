import React from 'react'

const PersonForm = ({name, number, nameChange, numberChange, personChange}) => {
  return (
    <form>
      <div>name: <input value={name} onChange={nameChange}/></div>
      <div>number: <input value={number} onChange={numberChange}/></div>
      <div><button onClick={personChange}>add</button></div>
    </form>
  )
}

export default PersonForm

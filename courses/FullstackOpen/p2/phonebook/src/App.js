import React, { useEffect, useState } from 'react'
import Filter from './components/Filter'
import PersonForm from './components/PersonForm'
import Persons from './components/Persons'
import personService from './services/persons'
import Notification from './components/Notification'

const App = () => {

  /** State hooks */
  const [ persons, setPersons ] = useState([])
  const [ newName, setNewName ] = useState('')
  const [ newNumber, setNewNumber ] = useState('')
  const [ newSearch, setnewSearch ] = useState('')
  const [ notification, setNotification ] = useState(null)

  /** Effect hooks */
  useEffect(() => {
    personService
      .getAll()
      .then(initialPersons => {
        setPersons(initialPersons)
      })
    }, [])  // only on first render!

  /** Event handlers */
  const notifyWith = (message, type='success') => {
    setNotification({ message, type })
    setTimeout(() => {
      setNotification(null)
    }, 5000)
  }

  const handleNameChange = (event) => {
    setNewName(event.target.value)
  }

  const handleNumberChange = (event) => {
    setNewNumber(event.target.value)
  }

  const handleSearchChange = (event) => {
    setnewSearch(event.target.value)
  }

  const handlePersonsChange = (event) => {
    event.preventDefault()             // normally submit button refreshes page!

    const personObject = {                                  // create new person
      name: newName,
      number: newNumber
    }

    // if we are adding a user that is already in the phonebook, update user
    const existingPerson = persons.find(p => p.name === newName)
    if (existingPerson) {
      const result = window.confirm(
        `${newName} is already added to phonebook, replace the old number with a new one?`
      )
      if (result) {                                 // if user clicked "confirm"
        personService
          .update(existingPerson.id, personObject)
          .then(returnedPerson => {
            setPersons(persons.map(p =>                         // update states
              p.id === returnedPerson.id ? returnedPerson : p))
            setNewName('')
            setNewNumber('')
            notifyWith(`Updated ${returnedPerson.name}`)
          })
          .catch(error => {
            console.log(error.response.data.error)
            notifyWith(`Information of '${newName}' was already removed from server`, 'error')
          })
      }
    }
    else {                                          // add new user to phonebook
      personService                                 // add new person to backend
        .create(personObject)
        .then(returnedPerson => {
          setPersons(persons.concat(returnedPerson))            // update states
          setNewName('')
          setNewNumber('')
          notifyWith(`Added ${returnedPerson.name}`)
        })
        .catch(error => {             // catch validation error, display message
          console.log(error.response.data.error)
          notifyWith(`${error.response.data.error}`, 'error')
        })
    }
  }

  const handleDelete = (id) => {
    const toDelete = persons.find(p => p.id === id)
    const ok = window.confirm(`Delete ${toDelete.name}?`)
    if (ok) {
      personService.remove(id)
        .then(response => {
          setPersons(persons.filter(p => p.id !== id))
          notifyWith(`Deleted ${toDelete.name}`)
        })
        .catch(() => {
          setPersons(persons.filter(p => p.id !== id))
          notifyWith(`${toDelete.name} has already been removed`, 'error')
        })
    }
  }


  return (
    <div>
      <h2>Phonebook</h2>

      <Notification notification={notification}/>

      <Filter search={newSearch} onSearch={handleSearchChange}/>

      <h3>Add a new</h3>

      <PersonForm
        name={newName} number={newNumber} nameChange={handleNameChange}
        numberChange={handleNumberChange} personChange={handlePersonsChange}
      />

      <h3>Numbers</h3>

      <Persons persons={persons} search={newSearch} handleDelete={handleDelete}/>

    </div>
  )
}

export default App

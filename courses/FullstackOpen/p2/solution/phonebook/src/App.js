import React, { useState, useEffect } from 'react'
import Persons from './components/Persons'
import PersonForm from './components/PersonForm'
import Notification from './components/Notification'
import Filter from './components/Filter'
import personService from './services/persons'

const App = () => {
  const [persons, setPersons] = useState([])
  const [ newName, setNewName ] = useState('')
  const [ newNumber, setNewNumber ] = useState('')
  const [ filterString, setStringFilter ] = useState('')
  const [ notification, setNotification ] = useState(null)

  useEffect(() => {
    personService
      .getAll()
      .then((data) => {
        setPersons(data)
      })

  }, [])

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

  const handleFilterStringChange = (event) => {
    setStringFilter(event.target.value)
  }

  const deletePerson = (id) => {
    const toDelete = persons.find(p => p.id === id)
    const ok = window.confirm(`Delete ${toDelete.name}`)
    if (ok) {
      personService.remove(id)
        .then(response => {
          setPersons(persons.filter(p => p.id !== id))
          notifyWith(`Deleted ${toDelete.name}`)
        }).catch(() => {
          setPersons(persons.filter(p => p.id !== id))
          notifyWith(`${toDelete.name} had already been removed`, 'error')
        })
    }
  }

  const addPerson = (event) => {
    event.preventDefault()

    const existing = persons.find(p => p.name === newName)
    if (existing) {
      const ok = window.confirm(`${existing.name} already in phonebook, replace the old number with new one?`)
      if (ok) {
        personService.update(existing.id, {
          name: existing.name,
          number:newNumber
        }).then(retunedPerson => {
          setPersons(persons.map(person => person.id !== existing.id ? person : retunedPerson))
          notifyWith(`Changed number of  ${existing.name}`)
          setNewName('')
          setNewNumber('')
        })
      }

    } else {
      personService.create({
        name: newName,
        number: newNumber
      }).then(addedPerson => {
        setPersons(persons.concat(addedPerson))
        notifyWith(`Added ${newName}`)
        setNewName('')
        setNewNumber('')
      }).catch(error => {
        // pääset käsiksi palvelimen palauttamaan virheilmoitusolioon näin
        console.log(error.response.data.error)
        notifyWith(`${error.response.data.error} `, 'error')
      })
    }
  }

  const personsToShow = filterString.length === 0 ?
    persons :
    persons.filter(p => p.name.toLowerCase().indexOf(filterString.toLowerCase()) > 0 )

  return (
    <div>
      <h2>Phonebook</h2>

      <Notification notification={notification} />

      filter shown with:
      <Filter
        value={filterString}
        onChange={handleFilterStringChange}
      />

      <h3>add a new</h3>
      <PersonForm
        handleNameChange={handleNameChange}
        handleNumberChange={handleNumberChange}
        newNumber={newNumber}
        newName={newName}
        addPerson={addPerson}
      />

      <h3>Numbers</h3>
      <Persons persons={personsToShow} deletePerson={deletePerson}/>
    </div>
  )

}

export default App
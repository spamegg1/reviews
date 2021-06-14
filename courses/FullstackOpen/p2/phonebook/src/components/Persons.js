import React from 'react'

const Persons = ({persons, search, handleDelete}) => {

  return (
    <div>
      {persons
          .filter(person =>
            person.name.toLowerCase().includes(search.toLowerCase()))
          .map(person =>
          <div key={person.name}>
            {person.name}  {person.number}
            <button onClick={() => handleDelete(person.id)}>delete</button>
          </div>
          )
        }
    </div>
  )
}

export default Persons

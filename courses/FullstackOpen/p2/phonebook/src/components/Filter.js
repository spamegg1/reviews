import React from 'react'

const Filter = ({search, onSearch}) => {
  return (
    <form>
      <div>
        filter shown with
        <input value={search} onChange={onSearch}></input>
      </div>
    </form>
  )
}

export default Filter
import { useState } from 'react'

const useField = (type) => {
  const [value, setValue] = useState('')

  const onChange = (event) => {
    setValue(event.target.value)
  }

  const reset = () => {
    setValue('')
  }

  return {
    form: {  // Exercise 7.6
      type,
      value,
      onChange
    },
    reset // Exercise 7.5
  }
}

export default useField

import React from 'react'

const Comments = ({ comments, handleComment }) => {             // Exercise 7.18
  const addComment = (event) => {
    event.preventDefault()
    const content = event.target.comment.value
    event.target.comment.value = ''
    handleComment(content)
  }

  if ( comments.length === 0) {
    return (
      <div>
        <form onSubmit={addComment}>
          <input name="comment" />
          <button type="submit">add comment</button>
        </form>
      </div>
    )
  }

  return (
    <div>
      <h3>comments</h3>
      <form onSubmit={addComment}>
        <input name="comment" />
        <button type="submit">add comment</button>
      </form>
      {comments.map((c, i) =>
        <p key={i}>{c}</p>
      )}
    </div>
  )
}

export default Comments

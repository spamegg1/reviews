package todo

import todo.data.Id

/**
 * This class encapsulates an Id that will be incremented every time it is accessed.
 *
 * You should NOT modify this file
 */
class IdGenerator(private var id: Id):
  /**
   *  Get the next Id value to use, and increment the Id stored in the IdGenerator.
   */
  def nextId(): Id =
    val currentId = id
    id = currentId.next
    currentId

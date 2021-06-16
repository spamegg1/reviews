const { UserInputError, AuthenticationError, PubSub } = require('apollo-server')
const jwt = require('jsonwebtoken')
const JWT_SECRET = 'NEED_HERE_A_SECRET_KEY'

const Author = require('./models/author')
const Book = require('./models/book')
const User = require('./models/user')

const pubsub = new PubSub()

const findOrCreate = async (name) => {
  let author = await Author.findOne({ name })

  if ( !author ) {
    author = new Author({ name })
    await author.save()
  }

  return author
}

let books

module.exports = {
  Query: {
    bookCount: () =>
      Book.countDocuments(),
    authorCount: () =>
      Author.countDocuments(),
    allBooks: async (root, { author, genre }) => {
      const query = {}

      if (author) {
        const theAuthor = await Author.findOne({ name: author })
        query.author = theAuthor.id
      }

      if (genre) {
        query.genres = { $in: [genre] }
      }

      return Book.find(query).populate('author')
    },
    allAuthors: async () => {
      books = await Book.find()
      return Author.find({})
    },
    me: (root, args, context) => {
      return context.currentUser
    }
  },
  Author: {
    bookCount: async (root) =>
      books.filter(b => String(b.author) === String(root.id)).length
  },
  Mutation: {
    addBook: async (root, { title, author, published, genres }, { currentUser }) => {
      if (!currentUser) {
        throw new AuthenticationError("not authenticated")
      }

      let book = new Book({ title, published, genres })

      try {
        book.author = await findOrCreate(author)
        await book.save()

      } catch (error) {
        throw new UserInputError(error.message, {
          invalidArgs: { title, author, published, genres },
        })
      }

      book = await Book.findById(book.id).populate('author')
      pubsub.publish('BOOK_ADDED', { bookAdded: book })

      return book
    },
    editAuthor: async (root, { name, setBornTo }, { currentUser }) => {
      if (!currentUser) {
        throw new AuthenticationError("not authenticated")
      }

      const author = await Author.findOne({ name })

      author.born = setBornTo
      return author.save()
    },
    createUser: (root, { username, favoriteGenre }) => {
      const user = new User({ username, favoriteGenre })

      return user.save()
        .catch(error => {
          throw new UserInputError(error.message, {
            invalidArgs: args,
          })
        })
    },
    login: async (root, args) => {
      const user = await User.findOne({ username: args.username })

      if ( !user || args.password !== 'secret' ) {
        throw new UserInputError("wrong credentials")
      }

      const userForToken = {
        username: user.username,
        id: user._id,
      }

      return { value: jwt.sign(userForToken, JWT_SECRET) }
    }
  },
  Subscription: {
    bookAdded: {
      subscribe: () => pubsub.asyncIterator(['BOOK_ADDED'])
    }
  }
}
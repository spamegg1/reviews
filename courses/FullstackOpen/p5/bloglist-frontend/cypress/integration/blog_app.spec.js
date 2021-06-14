// // to pass tests on Firefox
// Cypress.on('uncaught:exception', () => {
//   // returning false here prevents Cypress from
//   // failing the test
//   return false
// })

// Exercises 5.17
describe('Blog app', () => {
  beforeEach(() => {
    cy.request('POST', 'http://localhost:3001/api/testing/reset')
    cy.createUser({
      name: 'Matti Luukkainen',
      username: 'mluukkai',
      password: 'salainen'
    })
    cy.createUser({
      name: 'Spam Egg',
      username: 'spamegg',
      password: 'password'
    })
  })

  // Exercise 5.18
  it('login form can be opened', () => {
    cy.contains('login').click()
  })

  // Exercise 5.18
  describe('Login', () => {
    it('fails with wrong credentials', () => {
      cy.contains('login').click()
      cy.get('#username').type('mluukkai')
      cy.get('#password').type('wrong')
      cy.get('#login-button').click()

      cy.get('.error').should('contain', 'wrong username or password')
        .and('have.css', 'color', 'rgb(255, 0, 0)')
        .and('have.css', 'font-size', '20px')
        .and('have.css', 'margin-bottom', '10px')

      cy.get('html').should('not.contain', 'Matti Luukkainen logged in')
    })

    // Exercise 5.18
    it('succeeds with correct credentials', () => {
      cy.contains('login').click()
      cy.get('#username').type('mluukkai')
      cy.get('#password').type('salainen')
      cy.get('#login-button').click()
      cy.contains('Matti Luukkainen logged in')
      cy.contains('Blogs')
    })
  })

  describe('When a user is logged in', () => {
    beforeEach(() => {
      cy.login({ username: 'mluukkai', password: 'salainen' })
    })

    // Exercise 5.19
    it('a new blog can be created', () => {
      cy.contains('new blog').click()
      cy.get('#title').type('a blog created by cypress')
      cy.get('#author').type('Spam Egg')
      cy.get('#url').type('https://fullstackopen.com/en/part5')
      cy.get('#create').click()
      cy.contains('a blog created by cypress')
    })

    describe('When several blogs created by different users exist', () => {
      beforeEach(() => {
        cy.login({ username: 'mluukkai', password: 'salainen' })
        cy.createBlog({ author: 'John Doe', title: 'test1', url: 'http://example.com/test1' })
        cy.createBlog({ author: 'John Doe', title: 'test2', url: 'http://example.com/test2' })
        cy.contains('logout').click()
        cy.login({ username: 'spamegg', password: 'password' })
        cy.createBlog({ author: 'Jane Doe', title: 'test3', url: 'http://example.com/test3' })

        // for the rest of the tests mluukkai is logged out and spamegg is logged in
        cy.contains('test1').as('blog1')
        cy.contains('test2').as('blog2')
        cy.contains('test3').as('blog3')
        cy.contains('test1').parent().as('blogs')
      })

      it('one of them can be viewed and hidden', () => {
        cy.get('@blog2')
          .contains('view')
          .click()

        cy.get('@blog2')
          .should('contain', 'test2')
          .should('contain', 'John Doe')
          .should('contain', 'http://example.com/test2')
          .should('contain', 'likes')

        cy.get('@blog2')
          .contains('hide')
          .click()

        cy.get('@blog2')
          .should('contain', 'test2')
          .should('contain', 'John Doe')
          .should('not.contain', 'http://example.com/test2')
          .should('not.contain', 'likes')
      })

      // Exercise 5.20
      it('one of them can be liked', () => {
        cy.get('@blog3')
          .contains('view')
          .click()

        cy.get('@blog3')
          .contains('like')
          .click()

        cy.get('@blog3').contains('likes 1')
      })

      // Exercise 5.21
      it('a note can be deleted by the user who posted it', () => {
        cy.get('@blog3')
          .contains('view')
          .click()

        cy.get('@blog3')
          .contains('remove')
          .click()

        cy.get('@blogs')
          .should('not.contain', 'test3')
      })

      // Exercise 5.21 (Optional)
      it('but it cannot be deleted by other users', () => {
        cy.get('@blog1')
          .contains('view')
          .click()

        cy.get('@blog1')
          .should('not.contain', 'remove')
      })

      // Exercise 5.22
      it('blogs are ordered according to likes', () => {
        cy.get('@blog1').contains('view').click()
        cy.get('@blog2').contains('view').click()
        cy.get('@blog3').contains('view').click()
        cy.get('@blog1').contains('like').as('like1')
        cy.get('@blog2').contains('like').as('like2')
        cy.get('@blog3').contains('like').as('like3')

        cy.get('@like2').click()  // only 1 like for blog2
        cy.wait(500)
        cy.get('@like1').click()
        cy.wait(500)
        cy.get('@like1').click()  // only 2 likes for blog1
        cy.wait(500)
        cy.get('@like3').click()
        cy.wait(500)
        cy.get('@like3').click()
        cy.wait(500)
        cy.get('@like3').click()  // only 3 likes for blog3
        cy.wait(500)

        cy.get('.blog').then(blogs => {
          cy.wrap(blogs[0]).contains('likes 3')
          cy.wrap(blogs[1]).contains('likes 2')
          cy.wrap(blogs[2]).contains('likes 1')
        })
      })
    })
  })
})
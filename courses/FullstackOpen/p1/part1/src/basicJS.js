// CONST vs LET
const x = 1
let y = 5

console.log(x, y) // 1, 5 are printed
y += 10
console.log(x, y) // 1, 15 are printed
y = 'sometext'
console.log(x, y) // 1, sometext are printed
//x = 4 // causes error

// PUSH
const t = [1, -1, 3]
t.push(5)
console.log(t.length) // 4 is printed
console.log(t[1]) // -1 is printed

// CONCAT
const t1 = t.concat(6)
console.log(t) // [1, -1, 3, 5]
console.log(t1) // [1, -1, 3, 5, 6]

// FOREACH
t.forEach(value => {
  console.log(value)  // 1, -1, 3, 5 are printed on separate lines
})

// MAP
const t2 = [1, 2, 3]
const m1 = t2.map(value => value * 2)
console.log(m1) // [2, 4, 6] is printed
const m2 = t2.map(value => '<li>' + value + '</li>')
console.log(m2) // [ '<li>1</li>', '<li>2</li>', '<li>3</li>' ] is printed

// DESTRUCTURING
const t3 = [1, 2, 3, 4, 5]
const [first, second, ...rest] = t3
console.log(first, second)  // 1, 2 is printed
console.log(rest)  // [3, 4, 5] is printed

// OBJECT LITERALS
const object1 = {
  name: 'Arto Hellas',
  age: 35,
  education: 'PhD',
}

const object2 = {
  name: 'Full Stack web application development',
  level: 'intermediate studies',
  size: 5,
}

const object3 = {
  name: {
    first: 'Dan',
    last: 'Abramov',
  },
  grades: [2, 3, 5, 3],
  department: 'Stanford University'
}

console.log(object1.name) // Arto Hellas is printed
const fieldName = 'age'
console.log(object1[fieldName]) // 35 is printed

object1.address = 'Helsinki'
object1['secret number'] = 12341

// FUNCTIONS
const sum = (p1, p2) => {
  console.log(p1)
  console.log(p2)
  return p1 + p2
}
const result1 = sum(1, 5)
console.log(result1)

const square = p => {
  console.log(p)
  return p * p
}

const square2 = p => p * p
const t4 = [1, 2, 3]
const tSquared = t4.map(p => p * p) // tSquared is now [1, 4, 9]

function product(a, b) {
  return a * b
}
const result = product(2, 6) // result is now 12

const average = function(a, b) {
  return (a + b) / 2
}
const result2 = average(2, 5) // result is now 3.5



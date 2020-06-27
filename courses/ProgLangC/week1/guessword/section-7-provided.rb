## Provided code for Guess The Word practice problem (section 7)

class GuessTheWordGame
  def initialize secret_word_class
    @secret_word_class = secret_word_class
    @game_over = false
    @secret_word_entered = false
    @mistakes_allowed = 9
  end

  def play
    puts "Welcome to Guess The Word!"
    while !@game_over
      tick!
    end
    if @secret_word.is_solved?
      puts "Congratulations, you won."
    else
      puts "Sorry, but you failed to guess the word."
      puts "It was:"
      puts @secret_word.word
    end
    puts "Thank you for playing."
  end

  private

  def tick!
    if @secret_word_entered
      ask_for_guessed_letter
    else
      ask_for_secret_word
    end
  end

  def ask_for_secret_word
    puts "Enter the secret word of phrase:"
    word = gets.chomp
    if is_valid_secret_word? word
      @secret_word_entered = true
    end
  end

  def ask_for_guessed_letter
    puts "Secret word:"
    puts @secret_word.pattern
    puts @mistakes_allowed.to_s + " incorrect guess(es) left."
    puts "Enter the letter you want uncovered:"
    letter = gets.chomp
    if @secret_word.valid_guess? letter
      if !@secret_word.guess_letter! letter
        @mistakes_allowed -= 1
        @game_over = @mistakes_allowed == 0
      else
        @game_over = @secret_word.is_solved?
      end
    else
      puts "I'm sorry, but that's not a valid letter."
    end
  end

  def is_valid_secret_word? word
    @secret_word = @secret_word_class.new word
    !@secret_word.is_solved?
  end
end

class SecretWord
  attr_accessor :word, :pattern

  def initialize word
    self.word = word
    self.pattern = '-' * self.word.length
  end

  def is_solved?
    self.word == self.pattern
  end

  def valid_guess? guess
    guess.length == 1
  end

  def guess_letter! letter
    found = self.word.index letter
    if found
      start = 0
      while ix = self.word.index(letter, start)
        self.pattern[ix] = self.word[ix]
        start = ix + 1
      end
    end
    found
  end
end

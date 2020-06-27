# University of Washington, Programming Languages, Homework 6, hw6runner.rb

# This is the only file you turn in, so do not modify the other files as
# part of your solution.

class MyPiece < Piece
  # The constant All_My_Pieces should be declared here
  All_My_Pieces = All_Pieces + 
  [
	rotations([[0, 0], [1, 0], [0, -1], [-1, -1], [-1, 0]]), # 5-sq
    [[[0, 0], [-1, 0], [1, 0], [-2, 0], [2, 0]], # 5-long (only needs two)
     [[0, 0], [0, -1], [0, 1], [0, -2], [0, 2]]],
    rotations([[0, 0], [0, 1], [1, 1]]) # short-L
  ]

  # your enhancements here
  # class method to choose the next piece
  def self.next_piece (board)
    MyPiece.new(All_My_Pieces.sample, board)
  end
end

class MyBoard < Board
  # your enhancements here
  def initialize (game)
    super(game)
    @current_block = MyPiece.next_piece(self)
    @cheated = false
  end

  # rotates the current piece 180 degrees
  def rotate_180_degrees
    if !game_over? and @game.is_running?
      @current_block.move(0, 0, 2)
    end
    draw
  end

  # cheats
  def cheat
    if @score >= 100 and not @cheated
      @cheated = true
      @score -= 100
    end
  end

  # gets the next piece
  def next_piece
    if @cheated
      @current_block = MyPiece.new([[[0, 0]]], self)
      @cheated = false
    else
      @current_block = MyPiece.next_piece(self)
    end
    @current_pos = nil
  end

  def store_current
    locations = @current_block.current_rotation
    displacement = @current_block.position
    (0..locations.size-1).each{|index| 
      current = locations[index];
      @grid[current[1]+displacement[1]][current[0]+displacement[0]] = 
      @current_pos[index]
    }
    remove_filled
    @delay = [@delay - 2, 80].max
  end
end

class MyTetris < Tetris
  # your enhancements here
  # creates a canvas and the board that interacts with it
  def set_board
    @canvas = TetrisCanvas.new
    @board = MyBoard.new(self)
    @canvas.place(@board.block_size * @board.num_rows + 3,
                  @board.block_size * @board.num_columns + 6, 24, 80)
    @board.draw
  end

  def key_bindings
    super
    @root.bind('c', proc {@board.cheat})
    @root.bind('u', proc {@board.rotate_180_degrees})
  end

  def buttons
    super
    rotate_180 = TetrisButton.new('180', 'lightgreen'){@board.rotate_180_degrees}
    rotate_180.place(35, 50, 127, 501)
	
    cheat_button = TetrisButton.new('cheat', 'lightgreen'){@board.cheat}
    cheat_button.place(35, 50, 27, 501)
  end
end



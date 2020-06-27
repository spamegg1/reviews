# University of Washington: Programming Languages
# Homework 6: A working Tetris game you will extend without breaking.

require_relative './hw6graphics'

# class responsible for the pieces and their movements
class Piece

  # creates a new Piece from the given point array, holding the board for
  # determining if movement is possible for the piece, and gives the piece a
  # color, rotation, and starting position.
  def initialize (point_array, board)
    @all_rotations = point_array
    @rotation_index = (0..(@all_rotations.size-1)).to_a.sample
    @color = All_Colors.sample
    @base_position = [5, 0] # [column, row]
    @board = board
    @moved = true
  end

  def current_rotation
    @all_rotations[@rotation_index]
  end

  def moved
    @moved
  end

  def position
    @base_position
  end

  def color
    @color
  end

  def drop_by_one
    @moved = move(0, 1, 0)
  end

  # takes the intended movement in x, y and rotation and checks to see if the
  # movement is possible.  If it is, makes this movement and returns true.
  # Otherwise returns false.
  def move (delta_x, delta_y, delta_rotation)
    # Ensures that the rotation will always be a possible formation (as opposed
    # to nil) by altering the intended rotation so that it stays
    # within the bounds of the rotation array
    moved = true
    potential = @all_rotations[(@rotation_index + delta_rotation) % @all_rotations.size]
    # for each individual block in the piece, checks if the intended move
    # will put this block in an occupied space
    potential.each{|posns|
      if !(@board.empty_at([posns[0] + delta_x + @base_position[0],
                            posns[1] + delta_y + @base_position[1]]));
        moved = false;
      end
    }
    if moved
      @base_position[0] += delta_x
      @base_position[1] += delta_y
      @rotation_index = (@rotation_index + delta_rotation) % @all_rotations.size
    end
    moved
  end

  # class method to figure out the different rotations of the provided piece
  def self.rotations (point_array)
    rotate1 = point_array.map {|x,y| [-y,x]}
    rotate2 = point_array.map {|x,y| [-x,-y]}
    rotate3 = point_array.map {|x,y| [y,-x]}
    [point_array, rotate1, rotate2, rotate3]
  end

  # class method to choose the next piece
  def self.next_piece (board)
    Piece.new(All_Pieces.sample, board)
  end

  # class array holding all the pieces and their rotations
  All_Pieces = [[[[0, 0], [1, 0], [0, 1], [1, 1]]],  # square (only needs one)
                rotations([[0, 0], [-1, 0], [1, 0], [0, -1]]), # T
                [[[0, 0], [-1, 0], [1, 0], [2, 0]], # long (only needs two)
                 [[0, 0], [0, -1], [0, 1], [0, 2]]],
                rotations([[0, 0], [0, -1], [0, 1], [1, 1]]), # L
                rotations([[0, 0], [0, -1], [0, 1], [-1, 1]]), # inverted L
                rotations([[0, 0], [-1, 0], [0, -1], [1, -1]]), # S
                rotations([[0, 0], [1, 0], [0, -1], [-1, -1]]), # Z
                rotations([[0, 0], [1, 0], [0, -1], [-1, -1], [-1, 0]]), # 5-sq
                [[[0, 0], [-1, 0], [1, 0], [-2, 0], [2, 0]], # 5-long (only needs two)
                 [[0, 0], [0, -1], [0, 1], [0, -2], [0, 2]]],
                rotations([[0, 0], [0, 1], [1, 1]]) # 3-L
  ]

  # class array
  All_Colors = ['DarkGreen', 'dark blue', 'dark red', 'gold2', 'Purple3',
               'OrangeRed2', 'LightSkyBlue']
end


# Class responsible for the interaction between the pieces and the game itself
class Board

  def initialize (game)
    @grid = Array.new(num_rows) {Array.new(num_columns)}
    @current_block = Piece.next_piece(self)
    @score = 0
    @game = game
    @delay = 500
    @cheated = false
  end

  # both the length and the width of a block, since it is a square
  def block_size
    15
  end

  def num_columns
    10
  end

  def num_rows
    27
  end

  # the current score
  def score
    @score
  end

  # the current delay
  def delay
    @delay
  end

  # the game is over when there is a piece extending into the second row
  # from the top
  def game_over?
    @grid[1].any?
  end

  # moves the current piece down by one, if this is not possible stores the
  # current piece and replaces it with a new one.
  def run
    ran = @current_block.drop_by_one
    if !ran
      store_current
      if !game_over?
        next_piece
      end
    end
    @game.update_score
    draw
  end

  # moves the current piece left if possible
  def move_left
    if !game_over? and @game.is_running?
      @current_block.move(-1, 0, 0)
    end
    draw
  end

  # moves the current piece right if possible
  def move_right
    if !game_over? and @game.is_running?
      @current_block.move(1, 0, 0)
    end
    draw
  end

  # rotates the current piece clockwise
  def rotate_clockwise
    if !game_over? and @game.is_running?
      @current_block.move(0, 0, 1)
    end
    draw
  end

  # rotates the current piece counterclockwise
  def rotate_counter_clockwise
    if !game_over? and @game.is_running?
      @current_block.move(0, 0, -1)
    end
    draw
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

  # drops the piece to the lowest location in the currently occupied columns.
  # Then replaces it with a new piece
  # Change the score to reflect the distance dropped.
  def drop_all_the_way
    if @game.is_running?
      ran = @current_block.drop_by_one
      @current_pos.each{|block| block.remove}
      while ran
        @score += 1
        ran = @current_block.drop_by_one
      end
      draw
      store_current
      if !game_over?
        next_piece
      end
      @game.update_score
      draw
    end
  end

  # gets the next piece
  def next_piece
    if @cheated
      @current_block = Piece.new([[0, 0]], self)
      @cheated = false
    else
      @current_block = Piece.next_piece(self)
    end
    @current_pos = nil
  end

  # gets the information from the current piece about where it is and uses this
  # to store the piece on the board itself.  Then calls remove_filled.
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

  # Takes a point and checks to see if it is in the bounds of the board and
  # currently empty.
  def empty_at (point)
    if !(point[0] >= 0 and point[0] < num_columns)
      return false
    elsif point[1] < 1
      return true
    elsif point[1] >= num_rows
      return false
    end
    @grid[point[1]][point[0]] == nil
  end

  # removes all filled rows and replaces them with empty ones, dropping all rows
  # above them down each time a row is removed and increasing the score.
  def remove_filled
    (2..(@grid.size-1)).each{|num| row = @grid.slice(num);
      # see if this row is full (has no nil)
      if @grid[num].all?
        # remove from canvas blocks in full row
        (0..(num_columns-1)).each{|index|
          @grid[num][index].remove;
          @grid[num][index] = nil
        }
        # move down all rows above and move their blocks on the canvas
        ((@grid.size - num + 1)..(@grid.size)).each{|num2|
          @grid[@grid.size - num2].each{|rect| rect && rect.move(0, block_size)};
          @grid[@grid.size-num2+1] = Array.new(@grid[@grid.size - num2])
        }
        # insert new blank row at top
        @grid[0] = Array.new(num_columns);
        # adjust score for full flow
        @score += 10;
      end}
    self
  end

  # current_pos holds the intermediate blocks of a piece before they are placed
  # in the grid.  If there were any before, they are sent to the piece drawing
  # method to be removed and replaced with that of the new position
  def draw
    @current_pos = @game.draw_piece(@current_block, @current_pos)
  end
end

class Tetris

  # creates the window and starts the game
  def initialize
    @root = TetrisRoot.new
    @timer = TetrisTimer.new
    set_board
    @running = true
    key_bindings
    buttons
    run_game
  end

  # creates a canvas and the board that interacts with it
  def set_board
    @canvas = TetrisCanvas.new
    @board = Board.new(self)
    @canvas.place(@board.block_size * @board.num_rows + 3,
                  @board.block_size * @board.num_columns + 6, 24, 80)
    @board.draw
  end

  def key_bindings
    @root.bind('c', proc {@board.cheat})
    @root.bind('u', proc {@board.rotate_180_degrees})
    @root.bind('n', proc {self.new_game})

    @root.bind('p', proc {self.pause})

    @root.bind('q', proc {exitProgram})

    @root.bind('a', proc {@board.move_left})
    @root.bind('Left', proc {@board.move_left})

    @root.bind('d', proc {@board.move_right})
    @root.bind('Right', proc {@board.move_right})

    @root.bind('s', proc {@board.rotate_clockwise})
    @root.bind('Down', proc {@board.rotate_clockwise})

    @root.bind('w', proc {@board.rotate_counter_clockwise})
    @root.bind('Up', proc {@board.rotate_counter_clockwise})

    @root.bind('space' , proc {@board.drop_all_the_way})
  end

  def buttons
    pause = TetrisButton.new('pause', 'lightcoral'){self.pause}
    pause.place(35, 50, 90, 7)

    new_game = TetrisButton.new('new game', 'lightcoral'){self.new_game}
    new_game.place(35, 75, 15, 7)

    quit = TetrisButton.new('quit', 'lightcoral'){exitProgram}
    quit.place(35, 50, 140, 7)

    move_left = TetrisButton.new('left', 'lightgreen'){@board.move_left}
    move_left.place(35, 50, 27, 536)

    move_right = TetrisButton.new('right', 'lightgreen'){@board.move_right}
    move_right.place(35, 50, 127, 536)

    rotate_clock = TetrisButton.new('^_)', 'lightgreen'){@board.rotate_clockwise}
    rotate_clock.place(35, 50, 77, 501)

    rotate_counter = TetrisButton.new('(_^', 'lightgreen'){
      @board.rotate_counter_clockwise}
    rotate_counter.place(35, 50, 77, 571)

    rotate_180 = TetrisButton.new('180', 'lightgreen'){@board.rotate_180_degrees}
    rotate_180.place(35, 50, 127, 501)

  cheat_button = TetrisButton.new('cheat', 'lightgreen'){@board.cheat}
    cheat_button.place(35, 50, 27, 501)

    drop = TetrisButton.new('drop', 'lightgreen'){@board.drop_all_the_way}
    drop.place(35, 50, 77, 536)

    label = TetrisLabel.new(@root) do
      text 'Current Score: '
      background 'lightblue'
    end
    label.place(35, 100, 26, 45)
    @score = TetrisLabel.new(@root) do
      background 'lightblue'
    end
    @score.text(@board.score)
    @score.place(35, 50, 126, 45)
  end

  # starts the game over, replacing the old board and score
  def new_game
    @canvas.unplace
    @canvas.delete
    set_board
    @score.text(@board.score)
    @running = true
    run_game
  end

  # pauses the game or resumes it
  def pause
    if @running
      @running = false
      @timer.stop
    else
      @running = true
      self.run_game
    end
  end

  # alters the displayed score to reflect what is currently stored in the board
  def update_score
    @score.text(@board.score)
  end

  # repeatedly calls itself so that the process is fully automated.  Checks if
  # the game is over and if it isn't, calls the board's run method which moves
  # a piece down and replaces it with a new one when the old one can't move any
  # more
  def run_game
    if !@board.game_over? and @running
      @timer.stop
      @timer.start(@board.delay, (proc{@board.run; run_game}))
    end
  end

  # whether the game is running
  def is_running?
    @running
  end

  # takes a piece and optionally the list of old TetrisRects corresponding
  # to it and returns a new set of TetrisRects which are how the piece is
  # visible to the user.
  def draw_piece (piece, old=nil)
    if old != nil and piece.moved
      old.each{|block| block.remove}
    end
    size = @board.block_size
    blocks = piece.current_rotation
    start = piece.position
    blocks.map{|block|
    TetrisRect.new(@canvas, start[0]*size + block[0]*size + 3,
                            start[1]*size + block[1]*size,
                            start[0]*size + size + block[0]*size + 3,
                            start[1]*size + size + block[1]*size,
                            piece.color)}
  end
end

# To help each game of Tetris be unique.
srand

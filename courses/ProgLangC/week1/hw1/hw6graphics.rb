# University of Washington, Programming Languages, Homework 6, hw6graphics.rb

# This file provides an interface to a wrapped Tk library. The auto-grader will
# swap it out to use a different, non-Tk backend.

require 'tk'

class TetrisRoot
  def initialize
    @root = TkRoot.new('height' => 615, 'width' => 205,
             'background' => 'lightblue') {title "Tetris"}
  end

  def bind(char, callback)
    @root.bind(char, callback)
  end

  # Necessary so we can unwrap before passing to Tk in some instances.
  # Student code MUST NOT CALL THIS.
  attr_reader :root
end

class TetrisTimer
  def initialize
    @timer = TkTimer.new
  end

  def stop
    @timer.stop
  end

  def start(delay, callback)
    @timer.start(delay, callback)
  end
end

class TetrisCanvas
  def initialize
    @canvas = TkCanvas.new('background' => 'grey')
  end

  def place(height, width, x, y)
    @canvas.place('height' => height, 'width' => width, 'x' => x, 'y' => y)
  end

  def unplace
    @canvas.unplace
  end

  def delete
    @canvas.delete
  end

  # Necessary so we can unwrap before passing to Tk in some instances.
  # Student code MUST NOT CALL THIS.
  attr_reader :canvas
end

class TetrisLabel
  def initialize(wrapped_root, &options)
    unwrapped_root = wrapped_root.root
    @label = TkLabel.new(unwrapped_root, &options)
  end

  def place(height, width, x, y)
    @label.place('height' => height, 'width' => width, 'x' => x, 'y' => y)
  end

  def text(str)
    @label.text(str)
  end
end

class TetrisButton
  def initialize(label, color)
    @button = TkButton.new do
      text label
      background color
      command (proc {yield})
    end
  end

  def place(height, width, x, y)
    @button.place('height' => height, 'width' => width, 'x' => x, 'y' => y)
  end
end

class TetrisRect
  def initialize(wrapped_canvas, a, b, c, d, color)
    unwrapped_canvas = wrapped_canvas.canvas
    @rect = TkcRectangle.new(unwrapped_canvas, a, b, c, d,
                             'outline' => 'black', 'fill' => color)
  end

  def remove
    @rect.remove
  end

  def move(dx, dy)
    @rect.move(dx, dy)
  end

end

def mainLoop
  Tk.mainloop
end

def exitProgram
  Tk.exit
end

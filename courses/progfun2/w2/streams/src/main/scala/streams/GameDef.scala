package streams

/**
 * This trait represents the layout and building blocks of the game
 */
trait GameDef:

  /**
   * The case class `Pos` encodes positions in the terrain.
   *
   * IMPORTANT NOTE
   *  - The `row` coordinate denotes the position on the vertical axis
   *  - The `col` coordinate is used for the horizontal axis
   *  - The coordinates increase when moving down and right
   *
   * Illustration:
   *
   *     0 1 2 3   <- col axis
   *   0 o o o o
   *   1 o o o o
   *   2 o # o o    # is at position Pos(2, 1)
   *   3 o o o o
   *
   *   ^
   *   |
   *
   *   row axis
   */
  case class Pos(row: Int, col: Int):
    /** The position obtained by changing the `row` coordinate by `d` */
    def deltaRow(d: Int): Pos = copy(row = row + d)

    /** The position obtained by changing the `col` coordinate by `d` */
    def deltaCol(d: Int): Pos = copy(col = col + d)

  /**
   * The position where the block is located initially.
   *
   * This value is left abstract, it will be defined in concrete
   * instances of the game.
   */
  def startPos: Pos

  /**
   * The target position where the block has to go.
   * This value is left abstract.
   */
  def goal: Pos

  /**
   * The terrain is represented as a function from positions to
   * booleans. The function returns `true` for every position that
   * is inside the terrain.
   *
   * As explained in the documentation of class `Pos`, the `row` axis
   * is the vertical one and increases from top to bottom.
   */
  type Terrain = Pos => Boolean


  /**
   * The terrain of this game. This value is left abstract.
   */
  def terrain: Terrain


  /**
   * In Bloxorz, we can move left, right, Up or down.
   * These moves are encoded as case objects.
   */
  enum Move:
    case Left, Right, Up, Down

  /**
   * This function returns the block at the start position of
   * the game.
   */
  def startBlock: Block = Block(startPos, startPos)                      // TODO


  /**
   * A block is represented by the position of the two cubes that
   * it consists of. We make sure that `b1` is lexicographically
   * smaller than `b2`.
   */
  case class Block(b1: Pos, b2: Pos):

    // checks the requirement mentioned above
    require(b1.row <= b2.row && b1.col <= b2.col,
            "Invalid block position: b1=" + b1 + ", b2=" + b2)

    /**
     * Returns a block where the `row` coordinates of `b1` and `b2` are
     * changed by `d1` and `d2`, respectively.
     */
    def deltaRow(d1: Int, d2: Int) = Block(b1.deltaRow(d1), b2.deltaRow(d2))

    /**
     * Returns a block where the `col` coordinates of `b1` and `b2` are
     * changed by `d1` and `d2`, respectively.
     */
    def deltaCol(d1: Int, d2: Int) = Block(b1.deltaCol(d1), b2.deltaCol(d2))


    /** The block obtained by moving left */
    def left = if      isStanding       then  deltaCol(-2, -1)
               else if b1.row == b2.row then  deltaCol(-1, -2)
               else                           deltaCol(-1, -1)

    /** The block obtained by moving right */
    def right = if      isStanding       then deltaCol(1, 2)
                else if b1.row == b2.row then deltaCol(2, 1)
                else                          deltaCol(1, 1)

    /** The block obtained by moving up */
    def up = if      isStanding       then    deltaRow(-2, -1)
             else if b1.row == b2.row then    deltaRow(-1, -1)
             else                             deltaRow(-1, -2)

    /** The block obtained by moving down */
    def down = if      isStanding       then  deltaRow(1, 2)
               else if b1.row == b2.row then  deltaRow(1, 1)
               else                           deltaRow(2, 1)


    /**
     * Returns the list of blocks that can be obtained by moving
     * the current block, together with the corresponding move.
     */
    def neighbors: List[(Block, Move)] =                                 // TODO
      List((left, Move.Left), (right, Move.Right),
           (up  , Move.Up  ), (down , Move.Down ))

    /**
     * Returns the list of positions reachable from the current block
     * which are inside the terrain.
     */
    def legalNeighbors: List[(Block, Move)] =                            // TODO
      neighbors filter ((block, _) => block.isLegal)

    /**
     * Returns `true` if the block is standing.
     */
    def isStanding: Boolean = b1 == b2                                   // TODO

    /**
     * Returns `true` if the block is entirely inside the terrain.
     */
    def isLegal: Boolean = terrain(b1) && terrain(b2)                    // TODO

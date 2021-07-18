package barneshut

import conctrees.ConcBuffer

// Interfaces used by the grading infrastructure. Do not change signatures
// or your submission will fail with a NoSuchMethodError.

trait SectorMatrixInterface:
  def +=(b: Body): SectorMatrix
  def combine(that: SectorMatrix): SectorMatrix
  def apply(x: Int, y: Int): ConcBuffer[Body]

trait QuadInterface:
  def massX: Float
  def massY: Float
  def mass: Float
  def centerX: Float
  def centerY: Float
  def size: Float
  def total: Int
  def insert(b: Body): Quad

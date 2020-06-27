# Programming Languages, Dan Grossman
# Section 8: Multiple Inheritance

class Pt
  attr_accessor :x, :y
  def distToOrigin
    Math.sqrt(x * x  + y * y)
  end
end

class ColorPt < Pt
  attr_accessor :color
  def darken # error if @color not already set
    self.color = "dark " + self.color
  end
end

class Pt3D < Pt
  attr_accessor :z
  def distToOrigin
    Math.sqrt(x * x  + y * y + z * z)
  end
end


# This does not exist in Ruby (or Java/C#, it does in C++)

# class ColorPt3D_3 < ColorPt, Pt3D
# end

# two ways we could actually make 3D Color Points:

class ColorPt3D_1 < ColorPt
  attr_accessor :z
  def distToOrigin
    Math.sqrt(x * x  + y * y + z * z)
  end
end

class ColorPt3D_2 < Pt3D
  attr_accessor :color
  def darken # error if @color not already set
    self.color = "dark " + self.color
  end
end

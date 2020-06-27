class Encounter
end

class Output
end

class Stdout < Output
  def print str
    puts str
  end
end

class Null < Output
  def print str
  end
end

class Adventure
  def initialize(out, character, dungeon)
    @out = out
    @init_character = character
    @dungeon = dungeon
  end

  def play_out
    reset

    @dungeon.each do |encounter|
      if @character.is_dead?
        break
      end
      @out.print @character.to_s
      @out.print encounter.to_s
      @character.resolve_encounter encounter
    end

    if !@character.is_dead?
      @out.print @character.to_s
      @out.print "The hero emerges victorious!\nTheir adventures are over...\nFOR NOW."
    else
      @out.print "Alas, the hero is dead.\nThe adventure ends here."
    end

    @character
  end

  private

  def reset
    @character = @init_character
  end
end

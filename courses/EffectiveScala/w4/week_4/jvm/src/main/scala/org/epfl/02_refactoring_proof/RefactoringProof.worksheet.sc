def windowSide = util.Random.between(1, 4)

def windowArea = windowSide * windowSide

println(windowArea)

util.Random.between(1, 4) + util.Random.between(1, 4) == 2 * util.Random.between(1, 4)
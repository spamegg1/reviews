def hand_to_dict(hand):
    already_seen = False

    keys = ['' for i in range(len(hand))]
    values = [0 for j in range(len(hand))]

    i = 0
    char_count = 0
    while i < len(hand):
        j = 0
        while j < len(hand):
            if keys[j] == hand[i]:
                values[j] += 1
                already_seen = True
                j = len(hand)
            j += 1
        if not already_seen:
            keys[char_count] = hand[i]
            values[char_count] = 1
            char_count += 1
        i += 1
        already_seen = False

    if char_count == len(hand):
        return dict(zip(keys, values))

    newkeys = []
    newvalues = []
    for i in range(char_count):
        newkeys.append(keys[i])
        newvalues.append(values[i])
    return dict(zip(newkeys, newvalues))


print(hand_to_dict('rarecarebear'))

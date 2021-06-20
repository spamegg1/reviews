def dfs_loop(nodes: list, edges: dict, reversed_edges: dict):
    nodes.sort(reverse=True)
    is_explored = {i: False for i in nodes}
    finish_time = dict()  # keys are the finish time and values are the nodes
    t = 1

    # first DFS on reversed edges
    for node in nodes:
        if is_explored[node]:
            continue

        is_explored[node] = True

        stack = [node]
        another_stack = []

        while len(stack) > 0:
            last_item = stack.pop()
            another_stack.append(last_item)

            for to_node in reversed_edges.get(last_item, ()):
                if is_explored[to_node]:
                    continue
                is_explored[to_node] = True
                stack.append(to_node)

        another_stack.reverse()
        for rev_node in another_stack:
            finish_time[t] = rev_node
            t += 1

    is_explored = {i: False for i in nodes}
    scc = []
    leaders = []
    finish_time_keys = list(finish_time.keys())
    finish_time_keys.sort(reverse=True)

    # second DFS on actual edges
    for i in finish_time_keys:
        node = finish_time[i]

        if is_explored[node]:
            continue

        is_explored[node] = True

        stack = [node]
        scc.append([node])
        leaders.append(node)

        while len(stack) > 0:
            last_item = stack.pop()

            for to_node in edges.get(last_item, ()):
                if is_explored[to_node]:
                    continue
                is_explored[to_node] = True
                stack.append(to_node)
                scc[-1].append(to_node)

    return scc


def handle_file(file_name):
    nodes = set()  # using set to not have duplicates
    edges = dict()  # key is the from_node and value is the list of to_nodes
    reversed_edges = dict()  # key is from_node, value is list of to_node

    with open(file_name) as file:
        for line in file.readlines():
            from_node, to_node = map(int, line.split())
            nodes.add(from_node)
            nodes.add(to_node)

            try:
                edges[from_node].append(to_node)
            except KeyError:
                edges[from_node] = [to_node]

            try:
                reversed_edges[to_node].append(from_node)
            except KeyError:
                reversed_edges[to_node] = [from_node]

    return list(nodes), edges, reversed_edges


def solve(nodes, edges, reversed_edges):
    scc = dfs_loop(nodes, edges, reversed_edges)
    scc_lengths = [len(component) for component in scc]
    scc_lengths.sort(reverse=True)
    print(scc_lengths[:5])


if __name__ == "__main__":
    filename = "SCC.txt"  # 434821 968 459 313 211
    # filename = 'problem8.10test1.txt'  # 3, 3, 3
    # filename = 'problem8.10test2.txt'  # 3, 3, 2
    # filename = 'problem8.10test3.txt'  # 3, 3, 1, 1
    # filename = 'problem8.10test4.txt'  # 7, 1
    # filename = "problem8.10test5.txt"  # 6, 3, 2, 1
    NODES, EDGES, REVERSED = handle_file(filename)

    # print("nodes:", NODES)
    # print("edges:", EDGES)
    # print("reversed_edges:", REVERSED)

    solve(NODES, EDGES, REVERSED)

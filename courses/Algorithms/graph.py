# -*- coding: utf-8 -*-
"""
Created on Wed Sep 11 17:07:03 2019.

@author: spamegg1
"""


class Ugraph:
    """Undirected Graph object class, using adjacency list representation.

    Represents an undirected graph without self loops (parallel edges OK).
    Uses two "dictionary of sets" structures, one for nodes, one for edges.

    For the nodes dictionary:
    Keys of the dictionary represent nodes.
    Values of the dictionary are sets of edges to which that node is
    incident.
    If a node is incident to no edges, it must have an empty set {}.
    For example, consider key:value pair in nodes dictionary:
        's': {'e', 'f', 'g'}.
    This means that node 's' is incident to edges 'e', 'f' and 'g'.

    For the edges dictionary:
    Keys of the dictionary represent edges.
    Values of the dictionary are sets of TWO nodes that are adjacent to
    that edge. These sets must each have exactly two members.
    An edge MUST be incident to two nodes. No self loops allowed.
    For example, consider key:value pair in edge dictionary:
        'e': {'u', 's'}.
    This means that edge 'e' is incident to nodes 'u' and 's'.

    So an example of an undirected graph is:
        {'s': {'e', 'f'},
         'u': {'e', 'g', 'i'},
         'v': {'f', 'i', 'h'},
         'w': {'g', 'h'},
         'z': {}},
        {'e': {'u', 's'},
         'f': {'v', 's'},
         'g': {'u', 'w'},
         'h': {'w', 'v'},
         'i': {'u', 'v'}}

    There are alternate representations that can be given as text files.
    For those, I provide conversion functions below.

    One alternate representation is a text file in which each line contains
    exactly two integers separated by space.
    These two integers denote adjacent nodes (an edge between). For example:
        1 4
        7 2
        12 72
        etc.
    This means node 1 is adjacent to node 4 (there is an edge between them),
    node 7 is adjacent to node 2, node 12 is adjacent to node 72, etc.
    This representation can be converted by the edge_to_graph function below.

    Another alternate representation is a text file in which each line
    contains multiple integers separated by space.
    On each line, the first integer is adjacent to all the rest. For example:
        1   37  79  164 155
        2   123 134 10  141 13
        etc.
    This means node 1 is adjacent to nodes 37, 79, 164, 155;
    node 2 is adjacent to nodes 123, 134, 10, 141, 13; etc.
    This representation can be converted by the node_to_graph function below.
    """

    def __init__(self, graph=None):
        """Create empty graph object, or one with given graph dictionaries.

        The format for the input graph is pair of two dictionaries of sets:
        (
            {v0: {e1, e2, ...},
             v3: {e3, e4, ...},
             ...},
            {e0: {v1, v2},
             e3: {v3, v4},
             ...}
        )
        """
        if graph is None:
            self.nodes = {}  # create empty graph
            self.edges = {}
        else:
            nodes, edges = graph
            self.nodes = nodes
            self.edges = edges

    def __str__(self):
        """Friendlier printing of a graph."""
        result = ''
        for node in self.nodes:
            result += str(node) + ': ' + str(self.nodes[node]) + '\n'
        for edge in self.edges:
            result += str(edge) + ': ' + str(self.edges[edge]) + '\n'
        return result

    def get_node(self, node):
        """Get given node's incident edges.

        ex: G.get_node('s') -> {'e', 'f'}  # undirected
        G.get_node('s') -> [{'e'}, {'f'}]  # directed
        """
        return self.nodes[node]

    def add_node(self, node):
        """Add given node to graph (with no edges to it)."""
        if node not in self.nodes:
            self.nodes[node] = set()

    def rem_node(self, node):
        """Remove given node and all its edges."""
        if node in self.nodes:
            edges = list(self.get_node(node))
            for edge in edges:  # delete all of node's edges
                self.rem_edge(edge)
            del self.nodes[node]  # delete node itself

    def has_node(self, node):
        """Return True if given node is on graph."""
        return node in self.nodes

    def get_edge(self, edge):
        """Get given edge's two endpoints.

        ex: G.get_edge('e') -> {'s', 'u'}  # undirected
            G.get_edge('e') -> ('s', 'u')  # directed
        """
        return self.edges[edge]

    def add_edge(self, edge, node1, node2):
        """Add edge between two given nodes.

        example: G.set_edge('e', 's', 'u') ->
        before: 's': {'f'},
                'u': {'g', 'i'}
               ('e' has no entry in self.edges)
         after: 's': {'f', 'e'},
                'u': {'g', 'e', 'i'}
                'e': {'u', 's'} (in self.edges)
        """
        self.nodes[node1].add(edge)  # add edge to node1's set
        self.nodes[node2].add(edge)  # add edge to node2's set
        self.edges[edge] = {node1, node2}  # add edge to edge dict

    def rem_edge(self, edge):
        """Remove edge between two nodes.

        example: G.rem_edge('e') ->
        before: 's': {'f', 'e'},
                'u': {'g', 'e', 'i'}
                'e': {'s', 'u'} (in self.edges)
         after: 's': {'f'},
                'u': {'g', 'i'}
                (entry removed from self.edges)
        """
        # example: deleting 'e': {'s', 'u'}
        node1, node2 = self.edges[edge]  # node1 = 's', node2 = 'u'
        self.nodes[node1].remove(edge)  # remove 'e' from 's': {'f', 'e'}
        self.nodes[node2].remove(edge)  # remove 'e' from 'u': {'g', 'e', 'i'}
        del self.edges[edge]  # delete 'e': {'s', 'u'} from edge dict

    def has_edge(self, edge):
        """Return True if given edge is on graph."""
        return edge in self.edges


class Uwgraph(Ugraph):
    """Undirected Weighted Graph object class, adjacency list representation.

    Represents an undirected weighted graph.
    Uses two dictionaries, one for nodes, one for edges.

    For the nodes dictionary:
    Keys of the dictionary represent nodes.
    Values of the dictionary are sets of edges to which that node is
    incident.
    If a node is incident to no edges, it must have an empty set {}.
    For example, consider key:value pair in nodes dictionary:
        's': {'e', 'f', 'g'}.
    This means that node 's' is incident to edges 'e', 'f' and 'g'.

    For the edges dictionary:
    Keys of the dictionary represent edges.
    Values of the dictionary are pairs of one set (these sets must each have
    exactly two members) and an integer (the weight).
    An edge MUST be incident to TWO distinct nodes. No self loops allowed.
    For example, consider key:value pair in edge dictionary:
        'e': ({'u', 's'}, 5).
    This means that edge 'e' is incident to nodes 'u' and 's' with weight 5.

    So an example of an undirected weighted graph is:
        {'s': {'e', 'f'},
         'u': {'e', 'g', 'i'},
         'v': {'f', 'i', 'h'},
         'w': {'g', 'h'},
         'z': {}},
        {'e': ({'u', 's'}, 5),
         'f': ({'v', 's'}, -2),
         'g': ({'u', 'w'}, 13),
         'h': ({'w', 'v'}, 92),
         'i': ({'u', 'v'}, -14)}

    One representation of a Uwgraph is a text file in which each line
    contains multiple integers separated by spaces and commas.
    On each line, the first integer is adjacent to all the rest. For example:
        1   37,5    79,23   164,62  155,12
        2   123,1   134,3   10,4    141,892
        etc.
    This means node 1 is adjacent to nodes 37, 79, 164, 155 with corresponding
    weights 5, 23, 62, 12;
    node 2 is adjacent to nodes 123, 134, 10, 141, 13 with corresponding
    weights 1, 3, 4, 892; etc.
    This representation can be converted by the node_to_uwgraph function below.
    """

    def __init__(self, graph=None):
        """Create empty graph object, or one with given graph dictionary."""
        Ugraph.__init__(self, graph)

    def add_edge(self, edge, node1, node2, weight):
        """Add edge between two given nodes with given weight.

        example: G.set_edge('e', 's', 'u', 5) ->
        before: 's': {'f'},
                'u': {'g', 'i'}
               ('e' has no entry in self.edges)
         after: 's': {'f', 'e'},
                'u': {'g', 'e', 'i'}
                'e': ({'u', 's'}, 5) (in self.edges)
        """
        self.nodes[node1].add(edge)  # add edge to node1's set
        self.nodes[node2].add(edge)  # add edge to node2's set
        self.edges[edge] = ({node1, node2}, weight)  # add edge to edge dict


class Dgraph(Ugraph):
    """
    Directed Graph object class, using adjacency list representation.

    Represents a directed graph.
    Very similar to undirected graph object class Ugraph above.
    Each node has a pair of sets, one for outgoing edges, one for incoming.
    For example, consider key:value pair in nodes dictionary:
        'u': [{'i'}, {'e', 'g'}]
    This means node 'u' has one outgoing edge 'i' and two incoming edges 'e'
    and 'g'.
    Each edge has an ordered pair of nodes, instead of a set of two nodes.
    For example, consider key:value pair in edges dictionary:
        'e': ('s', 'u').
    This means edge ('e') goes FROM 's' TO 'u' (but not the reverse).

    So an example of a directed graph is:
        {'s': [{'e', 'f'}, {}],
         'u': [{'i'}, {'e', 'g'}],
         'v': [{}, {'f', 'i', 'h'}],
         'w': [{'g', 'h'}, {}],
         'z': [{}, {}]},
        {'e': ('s', 'u'),
         'f': ('s', 'v'),
         'g': ('w', 'u'),
         'h': ('w', 'v'),
         'i': ('u', 'v')}
    """

    def __init__(self, graph=None):
        """Create empty Dgraph object, or one with given graph dictionary.

        The format for the input graph is a pair of dictionaries,
        a dict of a list of two sets, and a dict of ordered pairs.
        (
            {v0: [{e1, ...}, {e2, e3, ...}],
             v3: [{e4, e5, ...}, {e6, e7, e8, ...}],
             ...},
            {e0: (v1, v2),
             e3: (v3, v4),
             ...}
        )
        """
        Ugraph.__init__(self, graph)

    def add_node(self, node):
        """Add given node to graph (with no edges to it)."""
        if node not in self.nodes:
            self.nodes[node] = [set(), set()]

    def rem_node(self, node):
        """Remove given node and all its outgoing and incoming edges."""
        if node in self.nodes:
            outgoing, incoming = self.get_node(node)
            for edge in list(outgoing):
                self.rem_edge(edge)  # delete all of node's outgoing edges
            for edge in list(incoming):
                self.rem_edge(edge)  # delete all of node's incoming edges
            del self.nodes[node]  # delete node itself

    def add_edge(self, edge, node1, node2):
        """Add edge FROM node1 TO node2 (but not in reverse).

        example: G.add_edge('e', 's', 'u') ->
        before: 's': [{'f'}, {}],
                'u': [{'i'}, {'g'}],
               ('e' has no entry in self.edges)
         after: 's': [{'f', 'e'}, {}],     # 's' has new outgoing edge
                'u': [{'i'}, {'g', 'e'}],  # 'u' has new incoming edge
                'e': ('s', 'u')            # (in self.edges)
        """
        self.nodes[node1][0].add(edge)  # add edge to outgoing of node1
        self.nodes[node2][1].add(edge)  # add edge to incoming of node2
        self.edges[edge] = (node1, node2)  # ordered pair, instead of set

    def rem_edge(self, edge):
        """Remove edge between two nodes.

        example: G.rem_edge('e') ->
        before: 's': [{'e'}, {'f'}],
                'u': [{'i'}, {'g', 'e'}],
                'e': ('s', 'u') (in self.edges)
         after: 's': [{}, {'f'}],       # outgoing 'e' deleted from 's'
                'u': [{'i'}, {'g'}],    # incoming 'e' deleted from 'u'
                (entry removed from self.edges)
        """
        # example: deleting 'e': ('s', 'u')
        # node1 = 's', node2 = 'u'
        node1, node2 = self.edges[edge][0], self.edges[edge][1]
        self.nodes[node1][0].remove(edge)  # remove outgoing 'e' from 's'
        self.nodes[node2][1].remove(edge)  # remove incoming 'e' from 'u'
        del self.edges[edge]  # delete 'e': ('s', 'u') from edge dict


class Wgraph(Dgraph):
    """Weighted directed graph object class, in adjacency list representation.

    Represents a weighted directed graph.
    Very similar to directed graph object class Dgraph above.
    Nodes are the same as in Dgraph.
    Each edge has an ordered triple of two nodes AND an integer (the weight).

    So an example of a weighted graph is:
        {'s': [{'e', 'f'}, {}],
         'u': [{'i'}, {'e', 'g'}],
         'v': [{}, {'f', 'i', 'h'}],
         'w': [{'g', 'h'}, {}],
         'z': [{}, {}]},
        {'e': ('s', 'u', -2),
         'f': ('s', 'v', 5),
         'g': ('w', 'u', 1),
         'h': ('w', 'v', 0),
         'i': ('u', 'v', -3)}
    """

    def __init__(self, graph=None):
        """Create empty Wgraph object, or one with given graph dictionary.

        The format for the input graph is a pair of dictionaries:
        a dict of a list of two sets, and
        a dict of ordered triples: two nodes and an integer (weight).
        (
            {v0: [{e1, ...}, {e2, e3, ...}],
             v3: [{e4, e5, ...}, {e6, e7, e8, ...}],
             ...},
            {e0: (v1, v2, w1),
             e3: (v3, v4, w2),
             ...}
        )
        """
        Dgraph.__init__(self, graph)

    def isedge(self, node1, node2):
        """Take weighted directed graph and two nodes on it as input.

        :return: Edge cost if an edge between given nodes exists, else False.
        """
        node1out, node1in = self.get_node(node1)
        for edge in node1out:
            _, othernode, cost = self.get_edge(edge)
            if node2 == othernode:
                return cost
        return False

    def add_edge(self, edge, node1, node2, weight):
        """Add edge with weight, FROM node1 TO node2 (but not in reverse).

        example: G.add_edge('e', 's', 'u', 5) ->
        before: 's': [{'f'}, {}],
                'u': [{'i'}, {'g'}],
               ('e' has no entry in self.edges)
         after: 's': [{'f', 'e'}, {}],     # 's' has new outgoing edge
                'u': [{'i'}, {'g', 'e'}],  # 'u' has new incoming edge
                'e': ('s', 'u', 5)         # (in self.edges)
        """
        self.nodes[node1][0].add(edge)  # add edge to outgoing of node1
        self.nodes[node2][1].add(edge)  # add edge to incoming of node2
        self.edges[edge] = (node1, node2, weight)  # ordered triple


class Uwpgraph(Ugraph):
    """Undirected Weighted Path Graph class, adjacency list representation.

    Represents an undirected PATH graph where VERTICES are weighted.
    The graph is a PATH GRAPH, so the first vertex is connected to the second,
    the second to the third, and so on, with no other edges.
    Uses two dictionaries, one for nodes, one for edges.

    For the nodes dictionary:
    Keys of the dictionary represent nodes.
    Values of the dictionary are pairs of one set of (at most two) edges,
    to which that node is incident, and a nonnegative integer (the weight).
    For example, consider key:value pair in nodes dictionary:
        's': ({'e', 'f'}, 5).
    This means that node 's' has weight 5 and is incident to edges 'e' and 'f'.
    There are exactly two nodes incident to only one edge, and the rest are
    all incident to exactly two edges.

    For the edges dictionary:
    Keys of the dictionary represent edges.
    Values of the dictionary are sets of nodes (these sets must each have
    exactly two members).
    An edge MUST be incident to TWO distinct nodes. No self loops allowed.
    For example, consider key:value pair in edge dictionary:
        'e': {'u', 's'}.
    This means that edge 'e' is incident to nodes 'u' and 's'.

    So an example of an undirected weighted path graph is:
        {'s': ({'e'     }, 5),
         'u': ({'e', 'f'}, 2),
         'v': ({'f', 'g'}, 1),
         'w': ({'g', 'h'}, 3),
         'z': ({'h'     }, 0)
        },
        {'e': {'u', 's'},
         'f': {'v', 's'},
         'g': {'v', 'w'},
         'h': {'w', 'z'},
        }

    One representation of a Uwpgraph is a text file in which each line
    corresponds to a vertex and contains an integer (weight of the vertex).
    This representation can be converted by node_to_uwpgraph below.
    """
    def __init__(self, graph=None):
        """Create empty graph object, or one with given graph dictionary."""
        Ugraph.__init__(self, graph)

    def add_node(self, node, weight):
        """Add given node to graph with given weight (with no edges to it)."""
        if node not in self.nodes:
            self.nodes[node] = (set(), weight)


def edge_to_graph(filename, directed=False):
    """Take filename string as input. Return graph generated from file.

    Can generate undirected or directed graph (undirected by default).
    To generate directed graph, pass in directed=True.
    The file must have, on each line, two positive integers
    with one space between them, and nothing else. Example:
        12 72
        8 1
        3 2 etc.
    Each line of the file indicates two vertices and an edge between them:
        vertex 12 is connected to vertex 72, etc.
    The text file must NOT repeat undirected edges; i.e. having two lines like
        1 2
        2 1 etc. is not allowed for undirected graphs.
    For a directed graph each line indicates a directed edge from the first
    (left) vertex number to the second (right):
        there is an outgoing edge from vertex 12 to vertex 72, etc.
    """
    # we must build the node and edge dictionaries for the graph
    nodes = {}  # then use the constructor with the graph optional argument
    edges = {}

    with open(filename, 'r') as text:  # open file
        for line in text:  # read each line
            node1, node2 = line.split()  # split two integers by space
            edgelabel = str(node1) + '_' + str(node2)  # create edge label

            if directed:  # we are making a directed graph
                if node1 not in nodes:  # if we see node1 for the first time
                    nodes[node1] = [set(), set()]  # create node1 dict entry
                nodes[node1][0].add(edgelabel)  # add OUTGOING edge from node1
                if node2 not in nodes:  # if we see node2 for the first time
                    nodes[node2] = [set(), set()]  # create node2 dict entry
                nodes[node2][1].add(edgelabel)  # add INCOMING edge to node2
                edges[edgelabel] = (node1, node2)  # add edge to edge dict
            else:
                if node1 not in nodes:  # if we see node1 for the first time
                    nodes[node1] = set()  # create node1 dict entry
                nodes[node1].add(edgelabel)  # add edge to node1
                if node2 not in nodes:  # if we see node2 for the first time
                    nodes[node2] = set()  # create node2 dict entry
                nodes[node2].add(edgelabel)  # add edge to node2
                edges[edgelabel] = {node1, node2}  # add edge to edge dict

    graph = (nodes, edges)
    return Dgraph(graph=graph) if directed else Ugraph(graph=graph)


def edge_to_uwgraph(filename):
    """Take text file as input.

    Each line in textfile contains 3 integers:
        node1 node2 cost

    Return undirected weighted graph represented by textfile.
    """
    nodes = {}
    edges = {}
    with open(filename, 'r') as file:
        for line in file:
            node1, node2, cost = line.split()
            edgelabel = node1 + '_' + node2

            if node1 not in nodes:
                nodes[node1] = set()
            nodes[node1].add(edgelabel)
            if node2 not in nodes:
                nodes[node2] = set()
            nodes[node2].add(edgelabel)

            edges[edgelabel] = ({node1, node2}, int(cost))

    graph = (nodes, edges)
    return Uwgraph(graph=graph)


def edge_to_dwgraph(filename):
    """Take text file as input.

    Each line in textfile contains 3 integers:
        node1 node2 cost

    Return directed weighted graph represented by textfile.
    """
    nodes = {}
    edges = {}
    with open(filename, 'r') as file:
        for line in file:
            node1, node2, cost = line.split()
            edgelabel = node1 + '_' + node2

            if node1 not in nodes:
                nodes[node1] = [set(), set()]
            if node2 not in nodes:
                nodes[node2] = [set(), set()]

            nodes[node1][0].add(edgelabel)
            nodes[node2][1].add(edgelabel)
            edges[edgelabel] = (node1, node2, int(cost))

    graph = (nodes, edges)
    return Wgraph(graph=graph)


def node_to_graph(filename, directed=False):
    """Take filename string as input. Return graph generated from file.

    Can generate undirected or directed graph (undirected by default).
    To generate directed graph, pass in directed=True.
    The file must have, on each line, (at least 2) positive integers. Example:
        1   37  79  164 155 32  87  39  113
        2   123 134 10  141 13  12  43  47  3   177 101 179 77
        etc.
    Each line of the file indicates that the vertex represented by the first
    number has edges with vertices represented by the rest of the numbers:
        there are edges between vertex 1 and 37, 1 and 79, 1 and 164 etc.
    Parallel edges are not allowed.
    Note that for an undirected graph, edges will be doubly listed; parallel
    edges are not the intention here. For example if text file has the lines:
        1 2 ...
        2 1 ...
    this is intended to mean there is ONLY ONE edge between nodes 1 and 2.

    For a directed graph each line indicates a directed edge from the first
    (leftmost) vertex number to all the others:
        there are outgoing edges from vertex 1 to vertices 37, 79, 164 etc.
    Similarly parallel edges in the same direction are not allowed, the lines
    1 2 ...
    2 1 ...
    would indicate TWO edges between nodes 1 and 2 in OPPOSITE directions.
    """
    # we must build the node and edge dictionaries for the graph
    nodes = {}  # then use the constructor with the graph optional argument
    edges = {}

    with open(filename, 'r') as text:  # open file
        for line in text:  # read each line
            nodelist = line.split()  # split integers by space
            node1 = nodelist.pop(0)  # get leftmost node

            for node2 in nodelist:
                edgelabel = node1 + '_' + node2  # create edge label
                reverselabel = node2 + '_' + node1

                if directed:  # we are making a directed graph
                    if node1 not in nodes:  # if we see node1 for first time
                        nodes[node1] = [set(), set()]  # create node1 entry
                    nodes[node1][0].add(edgelabel)  # OUTGOING edge from node1
                    if node2 not in nodes:  # if we see node2 for first time
                        nodes[node2] = [set(), set()]  # create node2 entry
                    nodes[node2][1].add(edgelabel)  # INCOMING edge to node2
                    edges[edgelabel] = (node1, node2)  # add edge to edge dict
                else:  # we are making an undirected graph
                    if node1 not in nodes:  # if we see node1 for first time
                        nodes[node1] = set()  # create node1 dict entry
                    if node2 not in nodes:  # if we see node2 for first time
                        nodes[node2] = set()  # create node2 dict entry
                    if edgelabel not in edges and reverselabel not in edges:
                        nodes[node1].add(edgelabel)  # add edge to node1
                        nodes[node2].add(edgelabel)  # add edge to node2
                        edges[edgelabel] = {node1, node2}  # add edge to dict

    graph = (nodes, edges)
    return Dgraph(graph=graph) if directed else Ugraph(graph=graph)


def node_to_uwgraph(filename):
    """Take filename string as input. Return graph generated from file.

    Generate undirected weighted graph.
    The file must have, on each line, one integer (node), followed by pairs
    of integers, comma separated. Example:
        1    80,982    163,8164    170,2620    145,648
        2    42,1689    127,9365    5,8026    170,9342    131,7005 etc.
    Each line of the file indicates that the vertex represented by the first
    number has edges with vertices represented by the rest of the numbers with
    given weights:
        there is an edge between vertices
        1 and 80 with weight 982,
        1 and 163 with weight 8164, etc.
    Parallel edges are not allowed.
    Note that for an undirected graph, edges will be doubly listed; parallel
    edges are not the intention here. For example if text file has the lines:
        1 2,57 ...
        2 1,57 ...
    this is intended to mean there is ONLY ONE edge between nodes 1 and 2 with
    weight 57.
    """
    # we must build the node and edge dictionaries for the graph
    # then use the constructor with the graph optional argument
    nodes, edges = {}, {}

    with open(filename, 'r') as text:                               # open file
        for line in text:                                      # read each line
            nodelist = line.split()               # split two integers by space
            node1 = nodelist.pop(0)                         # get leftmost node

            for entry in nodelist:             # each entry is: integer,integer
                node2, weight = entry.split(',')        # split node and weight
                edgelabel = node1 + '_' + node2             # create edge label
                reverselabel = node2 + '_' + node1              # reverse label

                # we are making an undirected graph
                if node1 not in nodes:         # if we see node1 for first time
                    nodes[node1] = set()              # create node1 dict entry
                if node2 not in nodes:         # if we see node2 for first time
                    nodes[node2] = set()              # create node2 dict entry
                if edgelabel not in edges and reverselabel not in edges:
                    nodes[node1].add(edgelabel)             # add edge to node1
                    nodes[node2].add(edgelabel)             # add edge to node2
                    edges[edgelabel] = ({node1, node2}, int(weight))

    graph = (nodes, edges)
    return Uwgraph(graph=graph)


def node_to_uwpgraph(filename):
    """Take filename string as input. Return graph generated from file.

    Generate undirected weighted path graph.
    The text file must have, on each line, one integer. Example:
        145
        1689 etc.
    For each i, the ith line of the file indicates that the ith node on path
    has weight represented by the number on that line.
        Node 1 has weight 145 (and is connected to node 2),
        Node 2 has weight 1689 (and is connected to nodes 1 and 3), etc.
    """
    nodes, edges = {}, {}

    with open(filename, 'r') as text:
        node_counter = 0
        for line in text:
            weight = int(line)
            node_label = str(node_counter)
            edge_label1 = str(node_counter - 1) + '_' + str(node_counter)
            edge_label2 = str(node_counter) + '_' + str(node_counter + 1)
            nodes[node_label] = ({edge_label1, edge_label2}, weight)
            if edge_label1 not in edges:
                edges[edge_label1] = set()
            if edge_label2 not in edges:
                edges[edge_label2] = set()
            edges[edge_label1].add(node_label)
            edges[edge_label2].add(node_label)
            node_counter += 1

    # delete the two extraneous edges from first and final nodes of path
    first_edges, first_weight = nodes['0']
    first_edges.remove('-1_0')
    nodes['0'] = (first_edges, first_weight)
    del edges['-1_0']

    last_edges, last_weight = nodes[str(node_counter - 1)]
    extraneous = str(node_counter - 1) + '_' + str(node_counter)
    last_edges.remove(extraneous)
    nodes[str(node_counter - 1)] = (last_edges, last_weight)
    del edges[extraneous]

    graph = (nodes, edges)
    return Uwpgraph(graph=graph)


# TESTING
if __name__ == "__main__":
    UNODES = {'s': {'e', 'f'},
              'u': {'e', 'g', 'i'},
              'v': {'f', 'i', 'h'},
              'w': {'g', 'h'}}
    UEDGES = {'e': {'u', 's'},
              'f': {'v', 's'},
              'g': {'u', 'w'},
              'h': {'w', 'v'},
              'i': {'u', 'v'}}
    UTEST = Ugraph(graph=(UNODES, UEDGES))

    assert UTEST.nodes == UNODES
    assert UTEST.edges == UEDGES
    for nod in UNODES:
        assert UTEST.has_node(nod)
        assert UTEST.get_node(nod) == UNODES[nod]
    for edg in UEDGES:
        assert UTEST.has_edge(edg)
        assert UTEST.get_edge(edg) == UEDGES[edg]

    DNODES = {'s': [{'e', 'f'}, {}],
              'u': [{'i'}, {'e', 'g'}],
              'v': [{}, {'f', 'i', 'h'}],
              'w': [{'g', 'h'}, {}]}
    DEDGES = {'e': ('s', 'u'),
              'f': ('s', 'v'),
              'g': ('w', 'u'),
              'h': ('w', 'v'),
              'i': ('u', 'v')}
    DTEST = Dgraph(graph=(DNODES, DEDGES))

    assert DTEST.nodes == DNODES
    assert DTEST.edges == DEDGES
    for nod in DNODES:
        assert DTEST.has_node(nod)
        assert DTEST.get_node(nod) == DNODES[nod]

    for edg in DEDGES:
        assert DTEST.has_edge(edg)
        assert DTEST.get_edge(edg) == DEDGES[edg]

    # testing isedge
    WDNODES = {'s': [{'e', 'f'}, {}],
               'u': [{'i'}, {'e', 'g'}],
               'v': [{}, {'f', 'i', 'h'}],
               'w': [{'g', 'h'}, {}]}
    WDEDGES = {'e': ('s', 'u', 1),
               'f': ('s', 'v', -2),
               'g': ('w', 'u', 3),
               'h': ('w', 'v', -4),
               'i': ('u', 'v', 5)}
    WDTEST = Wgraph(graph=(WDNODES, WDEDGES))
    assert WDTEST.isedge('s', 'u') == 1
    assert WDTEST.isedge('s', 'v') == -2
    assert WDTEST.isedge('s', 'w') is False
    assert WDTEST.isedge('u', 'v') == 5
    assert WDTEST.isedge('u', 'w') is False
    assert WDTEST.isedge('v', 'w') is False
    assert WDTEST.isedge('u', 's') is False
    assert WDTEST.isedge('v', 's') is False
    assert WDTEST.isedge('w', 's') is False
    assert WDTEST.isedge('v', 'u') is False
    assert WDTEST.isedge('w', 'u') == 3
    assert WDTEST.isedge('w', 'v') == -4

    FILES = ['problem8.10test1.txt',
             'problem8.10test2.txt',
             'problem8.10test3.txt',
             'problem8.10test4.txt',
             'problem8.10test5.txt']

    GRAPHS = []
    for file in FILES:
        GRAPHS.append(edge_to_graph(file))
        GRAPHS.append(edge_to_graph(file, directed=True))

    FILES = ['kargertest.txt',
             'kargertest2.txt',
             'kargertest3.txt',
             'kargertest4.txt']
    for file in FILES:
        GRAPHS.append(node_to_graph(file))
        GRAPHS.append(node_to_graph(file, directed=True))

    # testing uwpgraph
    UWPGRAPH = node_to_uwpgraph('problem16.6test.txt')
    # print(UWPGRAPH)

    print("Tests pass.")

# Writes symbol table for Jack source code
# spamegg 3/8/2019
'Writes symbol tables for Jack source code'


CLASSKINDS = ['static', 'field']
SUBKINDS = ['argument', 'local']


class SymbolTable():
    'Symbol table object for Jack code.'

    def __init__(self):
        'Creates symbol table object for Jack code.'
        self.class_table = {}
        self.sub_table = {}
        self.class_index = {'static': 0, 'field': 0}
        self.sub_index = {'argument': 0, 'local': 0}

    def start_subroutine(self):
        'Resets subroutine table and index in order to start a new subroutine.'
        self.sub_table = {}
        self.sub_index = {'argument': 0, 'local': 0}

    def define(self, name, datatype, kind):
        """Adds new entry to symbol table, assings a running index to it.
        'static' and 'field' identifiers have a class scope, while
        'argument' and 'var' (local) identifiers have subroutine scope.
        datatype can be 'int', 'char', 'boolean' or a class name.
        kind can be 'static', 'field', 'argument', 'local'."""
        if kind in CLASSKINDS:
            self.class_table[name] = (datatype, kind, self.class_index[kind])
            self.class_index[kind] += 1
        elif kind in SUBKINDS:
            self.sub_table[name] = (datatype, kind, self.sub_index[kind])
            self.sub_index[kind] += 1

    def var_count(self, kind):
        'Returns number of variables of given kind within current scope.'
        count = 0
        table = {}
        if kind in CLASSKINDS:
            table = self.class_table
        elif kind in SUBKINDS:
            table = self.sub_table
        for value in table.values():
            if kind in value:
                count += 1
        return count

    def type_of(self, name):
        'Returns type of variable with given name within current scope.'
        # must look up in subroutine table FIRST
        if name in self.sub_table:
            return self.sub_table[name][0]
        # if not there, then look up in class table
        if name in self.class_table:
            return self.class_table[name][0]
        return None

    def kind_of(self, name):
        'Returns kind of variable with given name within current scope.'
        # must look up in subroutine table FIRST
        if name in self.sub_table:
            return self.sub_table[name][1]
        # if not there, then look up in class table
        if name in self.class_table:
            return self.class_table[name][1]
        return None

    def index_of(self, name):
        'Returns index of variable with given name within current scope.'
        # must look up in subroutine table FIRST
        if name in self.sub_table:
            return self.sub_table[name][2]
        # if not there, then look up in class table
        if name in self.class_table:
            return self.class_table[name][2]
        return None

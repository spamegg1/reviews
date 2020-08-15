# Class that writes VM code from a Jack tokenizer
# spamegg 3/8/2019
'Writes VM code from Jack code.'


class VmWriter():
    'Creates a VmWriter object for Jack code.'

    def __init__(self, outfile):
        """Takes an input and an output filename as input. Creates a VmWriter
        object and gets ready to write to output .vm file. """
        # ready outfile for writing
        self.outfile = open(outfile, 'w')

        # set up counters to create unique labels
        self.if_false = 0
        self.if_true = 0
        self.if_end = 0
        self.while_exp = 0
        self.while_end = 0

    def write_push(self, segment, index):
        """Writes a VM push command. Segment can be any one of the 8 segments:
        constant, argument, local, static, this, that, pointer, temp. """
        self.outfile.write('push ' + segment + ' ' + str(index) + '\n')

    def write_pop(self, segment, index):
        """Writes a VM pop command. Segment can be 7 of the 8 segments:
        argument, local, static, this, that, pointer, temp. """
        self.outfile.write('pop ' + segment + ' ' + str(index) + '\n')

    def write_arithmetic(self, command):
        """Writes VM arithmetic or logic command.
        Command can be one of: +, -, =, >, <, &, |, ~, *, /.
        '*' is handled by Math.multiply, '/' by Math.divide.
        When '-' is unary (neg instead of sub), it's handled separately."""
        if command == '+':
            self.outfile.write('add\n')
        elif command == '-':
            self.outfile.write('sub\n')
        elif command == '=':
            self.outfile.write('eq\n')
        elif command == '>':
            self.outfile.write('gt\n')
        elif command == '<':
            self.outfile.write('lt\n')
        elif command == '&':
            self.outfile.write('and\n')
        elif command == '|':
            self.outfile.write('or\n')
        elif command == '~':
            self.outfile.write('not\n')
        elif command == '*':
            self.outfile.write('call Math.multiply 2\n')
        elif command == '/':
            self.outfile.write('call Math.divide 2\n')

    def write_label(self, label):
        """Writes VM label."""
        self.outfile.write('label ' + label + '\n')

    def write_goto(self, label):
        """Writes VM goto command."""
        self.outfile.write('goto ' + label + '\n')

    def write_if(self, label):
        """Writes VM if-goto command."""
        self.outfile.write('if-goto ' + label + '\n')

    def write_call(self, name, nargs):
        """Writes VM call command."""
        self.outfile.write('call ' + name + ' ' + str(nargs) + '\n')

    def write_function(self, name, nlocals):
        """Writes VM function command."""
        self.outfile.write('function ' + name + ' ' + str(nlocals) + '\n')

    def write_return(self):
        """Writes VM return command."""
        self.outfile.write('return\n')

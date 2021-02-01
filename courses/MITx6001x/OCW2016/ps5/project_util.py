#
# Utility functions for 6.00
#

# A HTML escape code -> text decoding table
HTML_ESCAPE_DECODE_TABLE = { "#39"   : "\'",
                             "quot"  : "\"",
                             "#34"   : "\"",
                             "amp"   : "&",
                             "#38"   : "&",
                             "lt"    : "<",
                             "#60"   : "<",
                             "gt"    : ">",
                             "#62"   : ">",
                             "nbsp"  : " ",
                             "#160"  : " "   }

def translate_html(html_fragment):
    """
    Translates a HTML fragment to plain text.

    html_fragment: string (ascii or unicode)
    returns: string (ascii)
    """
    txt = ""                 # translated string
    parser_reg=""            # parser register
    parser_state = "TEXT"    # parser state: TEXT, ESCAPE or TAG
    
    for x in html_fragment:  # process each character in html fragment
        parser_reg += x     
        if parser_state == "TEXT":   # in TEXT mode.
            if x == '<':             # does this char start a tag?
                parser_state = "TAG"
            elif x == '&':           # does this char start an escape code?
                parser_state = "ESCAPE"
            else:                    # otherwise, this is normal text
                txt += x             # copy the character as-is to output
                parser_reg = ""      # character handled, erase register
        elif parser_state == "TAG":  # inside an html TAG.
            if x == '>':             # does this char end the tag?
                parser_state = "TEXT"# return to TEXT mode for next character

                tag = parser_reg     # the complete tag is in the register           

                # translate some tags, ignore all others
                if tag[1:-1] == "br" or tag[1:4] == "br ":
                    txt += "\n"
                elif tag == "</table>":
                    txt += "\n"
                elif tag == "<p>":
                    txt += "\n\n"

                parser_reg = ""      # tag handled, erase register
                
        elif parser_state == "ESCAPE": # inside an ESCAPE code
            if x == ';':               # does this char end an escape code?
                parser_state = "TEXT"  # return to TEXT mode for next character

                esc = parser_reg[1:-1] # complete escape code is in register 
                
                if esc in HTML_ESCAPE_DECODE_TABLE:  # try to decode escape code
                    txt += HTML_ESCAPE_DECODE_TABLE[esc]
                else:
                    txt += " "         # unknown escape code -> space
                    
                parser_reg = ""      # code handled, erase register

    if type(txt) is str:
        txt = unicode_to_ascii(txt)
        
    return txt

def unicode_to_ascii(s):
    """
    converts s to an ascii string.
    
    s: unicode string
    """
    ret = ""
    for ch in s:
        try:
            ach = str(ch)
            ret += ach
        except UnicodeEncodeError:
            ret += "?"
    return ret


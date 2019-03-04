#!/opt/local/bin/python

# File: formatter.py 
# Student Name  : Zimeng Ming
# Student Number: V00844078
# SENG 265 - Assignment 3


# The basic for this assignment using the re, argparse, and sys module. which is need for to some functions in the file. 


import sys
import re
import calendar

class CommandException(Exception):
    def __init__(self, msg):
        self.msg = msg

class Formatter:
    """This is the definition for the class"""

    def __init__(self, filename=None, inputlines=None):
        if filename is not None:
            self.inputlines = []
            with open(sys.argv[1]) as f:
                for line in f:
                    self.inputlines.append(line.strip('\n'))
        elif inputlines is not None:
            self.inputlines = inputlines
        else:
            return
        self.cmd_prefix = ['?mrgn ', '?maxwidth ', '?fmt ', '?cap ', '?replace ', '?monthabbr ']
        self.UNSET = 10000
        self.margin = 0
        self.maxwidth = self.UNSET
        self.fmt = True
        self.cap = False
        self.words = []
        self.outputlines = []
        
    def is_command(self, line):
        for t in self.cmd_prefix:
            if line.startswith(t):
                return True
        return False
	
    def do_command(self, cmd, linenum):
        try:
            if cmd.startswith('?mrgn '):
                parts = cmd.split(' ')
                if len(parts) != 2:
                    raise CommandException("line {}: {}, should be 1 parameters".format(linenum, cmd))
                num = parts[1]
                try:
                    if num.startswith('+'):
                        self.margin += int(num[1:])
                        if self.margin > self.maxwidth - 20:
                            self.margin = self.maxwidth - 20
                    elif num.startswith('-'):
                        self.margin -= int(num[1:])
                        if self.margin < 0:
                            self.margin = 0
                    else:
                        self.margin = int(num)
                except:
                    raise CommandException("line {}: {}, parameter 1 should be an integer".format(linenum, cmd))
            elif cmd.startswith('?maxwidth '):
                parts = cmd.split(' ')
                if len(parts) != 2:
                    raise CommandException("line {}: {}, should have 1 parameters".format(linenum, cmd))
                try:
                    self.maxwidth = int(parts[1])
                except:
                    raise CommandException("line {}: {}, parameter 1 should be an integer".format(linenum, cmd))
            elif cmd.startswith('?fmt '):
                if cmd.split(' ')[1] == 'on':
                    self.fmt = True
                elif cmd.split(' ')[1] == 'off':
                    self.fmt = False
                else:
                    raise CommandException("line {}: {}, parameter 1 shoud be on/off".format(linenum, cmd))
            elif cmd.startswith('?cap '):
                if cmd.split(' ')[1] == 'on':
                    self.cap = True
                elif cmd.split(' ')[1] == 'off':
                    self.cap = False
                else:
                    raise CommandException("line {}: {}, parameter 1 shoud be on/off".format(linenum, cmd))
            elif cmd.startswith('?replace '):
                parts = cmd.strip().split(' ')
                if len(parts) != 3:
                    raise CommandException("line {}: {}, should have 2 parameters".format(linenum, cmd))
                pat1, pat2 = cmd.split(' ')[1:3]
                self.replace_until_next(pat1, pat2, linenum)
            elif cmd.startswith('?monthabbr '):
                if cmd.split(' ')[1] == 'on':
                    self.monthabbr_until_next(linenum)
        except CommandException:
            raise
        except:
            raise CommandException("An error occurs in line {}: {}".format(linenum, cmd))
    
    def replace_until_next(self, pat1, pat2, linenum):
        while linenum < len(self.inputlines):
            line = self.inputlines[linenum]
            linenum += 1
            if self.is_command(line):
                if line.startswith('?replace '):
                    break
                continue
            self.inputlines[linenum-1] = re.sub(pat1, pat2, line)

    def monthabbr_until_next(self, linenum):
        def replace(match):
                return '{}. {}, {}'.format(calendar.month_abbr[int(match.group(1))], match.group(2), match.group(3))

        p = re.compile(r'(\d{2})[./-](\d{2})[./-](\d{4})')
        while linenum < len(self.inputlines):
            line = self.inputlines[linenum]
            linenum += 1
            if line.startswith('?monthabbr off'):
                break
            self.inputlines[linenum-1] = p.sub(replace, line)

    def do(self, line, linenum):
        print_blank_line = False
        new_command = False
        if self.is_command(line):
            new_command = True
        else:
            self.words.extend([w for w in line.split(' ') if len(w) > 0])
            while linenum < len(self.inputlines):
                line = self.inputlines[linenum]
                linenum += 1
                # a blank line
                if len(line) == 0:
                    print_blank_line = True
                    break
                # a new command
                if self.is_command(line):
                    new_command = True
                    break
                # simple text
                self.words.extend([w for w in line.split(' ') if len(w) > 0])
        flag = True
        while flag:
            l = -1
            #decide which words should output in this line
            words_in_this_line = []
            for i in range(0, len(self.words)):
                if l + len(self.words[i]) + 1 > self.maxwidth - self.margin:
                    self.words = self.words[i:]
                    break
                l += (len(self.words[i]) + 1)
                words_in_this_line.append(self.words[i])
            # this is the last line to be output in this batch
            else:
                self.words = []
                flag = False
                # if there is a new format after this line. we should fill up this line with old format.
                if new_command:
                    flag2 = True
                    while flag2:
                        newline = self.inputlines[linenum]
                        linenum += 1
                        self.words.extend([w for w in newline.split(' ') if len(w) > 0])
                        for i in range(0, len(self.words)):
                            if l + len(self.words[i]) + 1 > self.maxwidth - self.margin:
                                flag2 = False
                                self.words = self.words[i:]
                                break
                            l += (len(self.words[i]) + 1)
                            words_in_this_line.append(self.words[i])
                        else:
                            self.words = []
                            break
            # fill spaces between words
            outline = words_in_this_line[0]
            if len(words_in_this_line) > 1:
                space_cnt = self.maxwidth - self.margin - l
                space_each = int(space_cnt / (len(words_in_this_line) - 1))
                space_left = space_cnt % (len(words_in_this_line) - 1)
                
                for i in range(0, space_left):
                    outline += ' '*(space_each+2) + words_in_this_line[i+1]
                for i in range(space_left, len(words_in_this_line) - 1):
                    outline += ' '*(space_each+1) + words_in_this_line[i+1]
            if self.cap:
                outline = outline.upper()
            self.outputlines.append(" "*self.margin + outline)
        if print_blank_line:
            self.outputlines.append("")
        if new_command:
            self.do_command(line, linenum)
        return linenum

    def get_lines(self):
        self.outputlines = []
        linenum = 0
        while linenum < len(self.inputlines):
            line = self.inputlines[linenum]
            linenum += 1
            if self.is_command(line) and len(self.words) == 0:
                self.do_command(line, linenum)
            else:
                if len(line) == 0:
                    self.outputlines.append("")
                elif self.fmt:
                    if self.maxwidth == self.UNSET:
                        if self.cap:
                            line = line.upper()
                        self.outputlines.append(" "*self.margin+line)
                    else:
                        linenum = self.do(line, linenum)
                else:
                    self.outputlines.append(line)
        return self.outputlines

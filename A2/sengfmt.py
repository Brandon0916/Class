#!/usr/bin/env python3

# File: sengfmt.py 
# Student Name  : Zimeng Ming
# Student Number: V00844078
# SENG 265 - Assignment 2


# The basic for this assignment using the re, argparse, and sys module. which is need for to some functions in the file. 

import argparse
import sys
import re

#set the variables. margin maxwidth fmt cap is the command.
UNSET= 10000000
margin = 0
maxwidth = UNSET
fmt = True
cap = False

#create the word list.
words = []

# judge wether the line of text is a command
def is_command(line):
   return line.startswith('?mrgn ') | line.startswith('?maxwidth ') | line.startswith('?fmt ') | line.startswith('?cap')
   
# execute the command
def do_command(cmd):
    global margin, maxwidth, fmt, cap
    #check the margin command 
    if cmd.startswith('?mrgn '):
        num = cmd.split(' ')[1]

        #the command starts with "+"
        if num.startswith('+'):
            margin += int(num[1:])
            #the the value is not greater than maxwidth -20
            if margin > maxwidth - 20:
                margin = maxwidth - 20

        #the command starts with"-"
        elif num.startswith('-'):
            margin -= int(num[1:])
            #for the margin value is less than 0
            if margin < 0:
                margin = 0
        
        else:
            margin = int(num)
    
    #check the command maxwidth
    elif cmd.startswith('?maxwidth '):
        num = cmd.split(' ')[1]
        maxwidth = int(num)
    #check the comman fmt
    elif cmd.startswith('?fmt '):
        fmt = cmd.split(' ')[1] == 'on'
    #check the command cap
    elif cmd.startswith('?cap'):
        cap = cmd.split(' ')[1] == 'on'

def do_the_process(line, f):
    global margin, maxwidth, fmt, cap, words
    print_blank_line = False
    new_command = False
    if is_command(line):
        new_command = True
    else:
        words.extend([w for w in line.split(' ') if len(w) > 0])
        while True:
            line = f.readline()
            # end of input
            if line == "":
                break

            #omit the space.
            line = line.strip('\n')
            
            # a blank line
            if len(line) == 0:
                print_blank_line = True
                break
            
            # if there is a new command appear
            if is_command(line):
                new_command = True
                break


            # simple text
            words.extend([w for w in line.split(' ') if len(w) > 0])
   
    #then we define a new command flag
    flag = True
    while flag:
        l = -1
        #decide which words should output in this line
        words_in_this_line = []
        for i in range(0, len(words)):
            if l + len(words[i]) + 1 > maxwidth - margin:
                words = words[i:]
                break
            l += (len(words[i]) + 1)
            words_in_this_line.append(words[i])
        # this is the last line to be output in this batch
        else:
            words = []
            flag = False
            # if there is a new format after this line. we should fill up this line with old format.
            if new_command:
                flag2 = True
                while flag2:
                    newline = f.readline().strip('\n')
                    words.extend([w for w in newline.split(' ') if len(w) > 0])
                    for i in range(0, len(words)):
                        if l + len(words[i]) + 1 > maxwidth - margin:
                            flag2 = False
                            words = words[i:]
                            break
                        l += (len(words[i]) + 1)
                        words_in_this_line.append(words[i])
                    else:
                        words = []
       
        #test for the margin and maxwidth is right or not                  
        #print(margin)
        #print(maxwidth)
       
        # fill spaces between words
        outline = outline = words_in_this_line[0]
        if len(words_in_this_line) > 1:
            space_count = maxwidth - margin - l
            space_each = int(space_count / (len(words_in_this_line) - 1))
            space_left = space_count % (len(words_in_this_line) - 1)
            
            for i in range(0, space_left):
                outline += ' '*(space_each+2) + words_in_this_line[i+1]
            for i in range(space_left, len(words_in_this_line) - 1):
                outline += ' '*(space_each+1) + words_in_this_line[i+1]
        #for captiliaztion
        if cap:
            outline = outline.upper()
        print(" "*margin + outline)
    #print the blank line
    if print_blank_line:
        print()

    #if there is a new command appear, we do another time.
    if new_command:
        do_command(line)



def main():
    global margin, maxwidth, fmt, cap
    f = None
    #open the file and read the lines
    if len(sys.argv) == 2:
        f = open(sys.argv[1])
    elif len(sys.argv) == 1:
        f = sys.stdin
    else:
        return
    #doing the process for the lines.
    for line in f:
        line = line.strip('\n')

        if is_command(line) and len(words) == 0:
            do_command(line)
        
        else:
            if len(line) == 0:
                print()
            elif fmt:
                if maxwidth == UNSET:
                    if cap:
                        line = line.upper()
                    print(" "*margin+line)
                else:
                    do_the_process(line, f)
            else:
                if cap:
                    line = line.upper()
                print(line)


# Detect if we're running via ./ or if we're called.
if __name__ == "__main__": 
    main() 

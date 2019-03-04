#!/opt/local/bin/python
# File: sengfmt2.py
# Student Name  : Zimeng Ming
# Student Number: V00844078
# SENG 265 - Assignment 3


# The basic for this assignment using the re, argparse, and sys module. which is need for to some functions in the file.
import sys
import fileinput
from formatter import Formatter, CommandException

def main():
    """
        Creates an instance of the formatter and process the lines
        """
    f = None
    if len(sys.argv) == 2:
        f = Formatter(filename=sys.argv[1])
    elif len(sys.argv) == 1:
        lines = []
        for line in sys.stdin:
            lines.append(line.strip('\n'))
            f = Formatter(inputlines=lines)
    else:
        return
    try:
        lines = f.get_lines()
        for l in lines:
            print (l)
    except CommandException as e:
        print(e.msg)

if __name__ == "__main__":
    main()

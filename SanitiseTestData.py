import os, re

DATA_DIR = "G:/Scott/Documents/Projects/BookingBot/"
DATA_FILE = "debugData"

removeList = [
            "Engine warning:",
            "Maximum number (2) of time-outs reached",
            "Output from your bot:"
            ]


file = open(DATA_DIR + DATA_FILE,"r+")
data = file.readlines()
file.seek(0)
for line in data:

    lineContainsItem = False;

    for item in removeList:
        if item in line:
            lineContainsItem = True;

    if not lineContainsItem:
        file.write(line)

file.truncate()
file.close()
import os, zipfile, shutil

SOURCE_DIR = "W:/BookingBot/BookingBot/src/uk/tlscott/BookingBot/"
TEMP_DIR = "G:/Scott/Documents/Projects/BookingBot/updatesFiles/"
ZIP_DIR = "G:/Scott/Documents/Projects/BookingBot/"

OLD_NAME = "package uk.tlscott.BookingBot;"
NEW_NAME = "package BookingBot;"

DEBUG_ON = "static final boolean DEBUG = true;"
DEBUG_OFF = "static final boolean DEBUG = false;"

IGNORE_LIST = ["StringBoard.java"]

# Create Tempory dir if it doesn't exist
if not os.path.exists(TEMP_DIR):
    os.mkdir(TEMP_DIR)

# Copy all files from source dir to temp dir
for filename in os.listdir(SOURCE_DIR):
    if filename in IGNORE_LIST:
        continue
    # turn debug off and rename packages
    try:
        with open(SOURCE_DIR + filename, 'r') as infile:
            data = infile.read().replace(OLD_NAME, NEW_NAME).replace(DEBUG_ON, DEBUG_OFF)
    except OSError as e:
        print "Could not read file " + filename
        raw_input();
    else:
        with open(TEMP_DIR + filename, "w") as outfile:
            outfile.write(data)

# Create zip file and add contents of temp dir
zf = zipfile.ZipFile(ZIP_DIR + "BookingBot.zip", "w")
for dirname, subdirs, files in os.walk(TEMP_DIR):
    # zf.write("BookingBot")
    for filename in files:
        zf.write(os.path.join(dirname, filename), arcname=os.path.join("BookingBot", filename))
zf.close()

# Delete temp dir
shutil.rmtree(TEMP_DIR)
# To get credit for this assignment, perform the instructions below and upload
# your SQLite3 database here:
# (Must have a .sqlite suffix)

# You do not need to export or convert the database - simply upload the .sqlite
# file that your program creates. See the example code for the use of the
# connect() statement.

# Musical Track Database

# This application will read an iTunes export file in XML and produce a properly
# normalized database with this structure:

# CREATE TABLE Artist (
#     id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
#     name    TEXT UNIQUE
# );

# CREATE TABLE Genre (
#     id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
#     name    TEXT UNIQUE
# );

# CREATE TABLE Album (
#     id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
#     artist_id  INTEGER,
#     title   TEXT UNIQUE
# );

# CREATE TABLE Track (
#     id  INTEGER NOT NULL PRIMARY KEY
#         AUTOINCREMENT UNIQUE,
#     title TEXT  UNIQUE,
#     album_id  INTEGER,
#     genre_id  INTEGER,
#     len INTEGER, rating INTEGER, count INTEGER
# );

# If you run the program multiple times in testing or with different files, make
# sure to empty out the data before each run.

# You can use this code as a starting point for your application:
# http://www.py4e.com/code3/tracks.zip. The ZIP file contains the Library.xml
# file to be used for this assignment. You can export your own tracks from
# iTunes and create a database, but for the database that you turn in for this
# assignment, only use the Library.xml data that is provided.

# To grade this assignment, the program will run a query like this on your
# uploaded database and look for the data it expects to see:

# SELECT Track.title, Artist.name, Album.title, Genre.name
#     FROM Track JOIN Genre JOIN Album JOIN Artist
#     ON Track.genre_id = Genre.ID and Track.album_id = Album.id
#         AND Album.artist_id = Artist.id
#     ORDER BY Artist.name LIMIT 3

# The expected result of the modified query on your database is: (shown here as
# a simple HTML table with titles)

# Track	Artist	Album	Genre
# Chase the Ace	AC/DC	Who Made Who	Rock
# D.T.	AC/DC	Who Made Who	Rock
# For Those About To Rock (We Salute You)	AC/DC	Who Made Who	Rock


import xml.etree.ElementTree as ET
import sqlite3

conn = sqlite3.connect('trackdb.sqlite')
cur = conn.cursor()

# Make some fresh tables using executescript()
# cur.executescript('''
# DROP TABLE IF EXISTS Artist;
# DROP TABLE IF EXISTS Album;
# DROP TABLE IF EXISTS Track;

# CREATE TABLE Artist (
#     id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
#     name    TEXT UNIQUE
# );

# CREATE TABLE Album (
#     id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
#     artist_id  INTEGER,
#     title   TEXT UNIQUE
# );

# CREATE TABLE Track (
#     id  INTEGER NOT NULL PRIMARY KEY
#         AUTOINCREMENT UNIQUE,
#     title TEXT  UNIQUE,
#     album_id  INTEGER,
#     len INTEGER, rating INTEGER, count INTEGER
# );
# ''')

# NEW
cur.executescript('''
DROP TABLE IF EXISTS Artist;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Album;
DROP TABLE IF EXISTS Track;
CREATE TABLE Artist (
    id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    name    TEXT UNIQUE
);
CREATE TABLE Genre (
    id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    name    TEXT UNIQUE
);
CREATE TABLE Album (
    id  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
    artist_id  INTEGER,
    title   TEXT UNIQUE
);
CREATE TABLE Track (
    id  INTEGER NOT NULL PRIMARY KEY
        AUTOINCREMENT UNIQUE,
    title TEXT  UNIQUE,
    album_id  INTEGER,
    genre_id INTEGER,
    len INTEGER, rating INTEGER, count INTEGER
);
''')


fname = input('Enter file name: ')
if len(fname) < 1:
    fname = 'Library.xml'

# <key>Track ID</key><integer>369</integer>
# <key>Name</key><string>Another One Bites The Dust</string>
# <key>Artist</key><string>Queen</string>
def lookup(d, key):
    found = False
    for child in d:
        if found:
            return child.text
        if child.tag == 'key' and child.text == key:
            found = True
    return None

stuff = ET.parse(fname)
all = stuff.findall('dict/dict/dict')
print('Dict count:', len(all))
for entry in all:
    if lookup(entry, 'Track ID') is None:
        continue

    name = lookup(entry, 'Name')
    artist = lookup(entry, 'Artist')
    genre = lookup(entry, 'Genre')  # NEW
    album = lookup(entry, 'Album')
    count = lookup(entry, 'Play Count')
    rating = lookup(entry, 'Rating')
    length = lookup(entry, 'Total Time')

    # if name is None or artist is None or album is None:
    if name is None or genre is None or artist is None or album is None:
        continue

    # print(name, artist, album, count, rating, length)
    print(name, artist, genre, album, count, rating, length)

    cur.execute('''INSERT OR IGNORE INTO Artist (name)
        VALUES ( ? )''', ( artist, ) )
    cur.execute('SELECT id FROM Artist WHERE name = ? ', (artist, ))
    artist_id = cur.fetchone()[0]

    # NEW
    cur.execute('''INSERT OR IGNORE INTO Genre (name)
        VALUES ( ? )''', ( genre, ))
    cur.execute('SELECT id FROM Genre WHERE name = ? ', (genre, ))
    genre_id = cur.fetchone()[0]

    cur.execute('''INSERT OR IGNORE INTO Album (title, artist_id)
        VALUES ( ?, ? )''', ( album, artist_id ) )
    cur.execute('SELECT id FROM Album WHERE title = ? ', (album, ))
    album_id = cur.fetchone()[0]

    # cur.execute('''INSERT OR REPLACE INTO Track
    #     (title, album_id, len, rating, count)
    #     VALUES ( ?, ?, ?, ?, ? )''',
    #     ( name, album_id, length, rating, count ) )
    cur.execute('''INSERT OR REPLACE INTO Track
        (title, album_id, genre_id, len, rating, count)
        VALUES ( ?, ?, ?, ?, ?, ? )''',
        ( name, album_id, genre_id, length, rating, count ) )

    conn.commit()

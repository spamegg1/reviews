import cs50
import csv

def main():
    min_tempo = cs50.get_int("Minimum tempo: ")
    max_tempo = cs50.get_int("Maximum tempo: ")

    playlist = []
    with open("2018_top100.csv", "r") as song_file:
        reader = csv.DictReader(song_file)
        for song in reader:
            if float(song["tempo"]) >= min_tempo and float(song["tempo"]) <= max_tempo:
                playlist.append(song)

    for song in playlist:
        print(f"{song['name']}")

main()
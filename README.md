ChordCommand DSL
================

ChordCommand is a domain specific language for manipulating chords using standard
chord symbols (e.g. CM7, Dm7, Gdom7, Am7, F, Gdom9, CM7, etc.). Currently implemented
by means of a command line interpreter whereby the user may manipulate chords
interactively.

Language Overview
-----------------

### Primitive Data

The only primitive data type in ChordCommand is a chord.

### Primitive Procedures

Primitive procedures include:

* chordsByFilter(filter)
* chordsContaining(noteList)
* create(identifier, chordList)
* display(chordList)
* load(fileName)
* play(chordList)
* save(fileName, chordList)
* voice(chordList)

### Means of Combination

TBD.

### Means of Abstraction

The primitive procedure create(identifier, chordList) is used to assign names to
lists of chords.

Examples
--------

### Create a list of chords and play it:

    create(list1, CM7, Dm7, Gdom7, DbM7, CM7)
    play(list1)

### Play a list of chords voiced using a closest-voicing-is-best strategy:

    play(voice(FM7, Gdom7, C))

### Display chords containing the notes Bb and F:

    display(chordsContaining(Bb, F))

### Display chords where the third of the chord is C#:

    display(chordsByFilter(third=C#))

### Save a compound list of chords in LilyPond format:

    save("example.ly", CM, voice(Dm7, Gdom7), Adom7, voice(Ddom7, GM7))

### Play a list of chords loaded from a file:

    play(load("examples\chords.txt")) 
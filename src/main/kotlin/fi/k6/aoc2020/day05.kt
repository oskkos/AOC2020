package fi.k6.aoc2020

import kotlin.math.ceil
import kotlin.math.floor

fun day05() {
    println("--- Day 5: Binary Boarding ---")
    println("Part 1: " + day0501(seatCodes()))
    println("Part 2: " + day0502(seatCodes()))

}

/*
-- Day 5: Binary Boarding ---
You board your plane only to discover a new problem: you dropped your boarding pass! You aren't sure which seat is yours, and all of the flight attendants are busy with the flood of people that suddenly made it through passport control.

You write a quick program to use your phone's camera to scan all of the nearby boarding passes (your puzzle input); perhaps you can find your seat through process of elimination.

Instead of zones or groups, this airline uses binary space partitioning to seat people. A seat might be specified like FBFBBFFRLR, where F means "front", B means "back", L means "left", and R means "right".

The first 7 characters will either be F or B; these specify exactly one of the 128 rows on the plane (numbered 0 through 127). Each letter tells you which half of a region the given seat is in. Start with the whole list of rows; the first letter indicates whether the seat is in the front (0 through 63) or the back (64 through 127). The next letter indicates which half of that region the seat is in, and so on until you're left with exactly one row.

For example, consider just the first seven characters of FBFBBFFRLR:

Start by considering the whole range, rows 0 through 127.
F means to take the lower half, keeping rows 0 through 63.
B means to take the upper half, keeping rows 32 through 63.
F means to take the lower half, keeping rows 32 through 47.
B means to take the upper half, keeping rows 40 through 47.
B keeps rows 44 through 47.
F keeps rows 44 through 45.
The final F keeps the lower of the two, row 44.
The last three characters will be either L or R; these specify exactly one of the 8 columns of seats on the plane (numbered 0 through 7). The same process as above proceeds again, this time with only three steps. L means to keep the lower half, while R means to keep the upper half.

For example, consider just the last 3 characters of FBFBBFFRLR:

Start by considering the whole range, columns 0 through 7.
R means to take the upper half, keeping columns 4 through 7.
L means to take the lower half, keeping columns 4 through 5.
The final R keeps the upper of the two, column 5.
So, decoding FBFBBFFRLR reveals that it is the seat at row 44, column 5.

Every seat also has a unique seat ID: multiply the row by 8, then add the column. In this example, the seat has ID 44 * 8 + 5 = 357.

Here are some other boarding passes:

BFFFBBFRRR: row 70, column 7, seat ID 567.
FFFBBBFRRR: row 14, column 7, seat ID 119.
BBFFBBFRLL: row 102, column 4, seat ID 820.
As a sanity check, look through your list of boarding passes. What is the highest seat ID on a boarding pass?
 */
private fun day0501(seatCodes: List<String>): Int =
    seatCodes.map { seatCode -> resolveSeatId(seatCode).third }.maxOrNull() ?: 0

/*
--- Part Two ---
Ding! The "fasten seat belt" signs have turned on. Time to find your seat.

It's a completely full flight, so your seat should be the only missing boarding pass in your list. However, there's a catch: some of the seats at the very front and back of the plane don't exist on this aircraft, so they'll be missing from your list as well.

Your seat wasn't at the very front or back, though; the seats with IDs +1 and -1 from yours will be in your list.

What is the ID of your seat?
 */
private fun day0502(seatCodes: List<String>): Int {
    val seatIds = seatCodes.map { seatCode -> resolveSeatId(seatCode).third }.sorted()
    var previous: Int? = null
    for (seatId in seatIds) {
        if (previous != null && (previous + 1) < seatId ) {
            return previous + 1
        }
        previous = seatId
    }
    throw error("Unable to resolve")
}

private fun resolveSeatId(str: String): Triple<Int, Int, Int> {
    val chars = str.toCharArray()
    val row = resolveRow(chars.sliceArray(0..6), 'F', 127)
    val column = resolveRow(chars.sliceArray(7..9), 'L', 7)
    val seatId = row * 8 + column
    return Triple(row, column, seatId)
}
private fun resolveRow(rowInstructions: CharArray, lowerChar: Char, max: Int): Int {
    var x_high = max
    var x_low = 0
    for (instruction in rowInstructions) {
        if (instruction == lowerChar) {
            x_high = floor(((x_high + x_low) / 2).toDouble()).toInt()
        } else {
            x_low = x_low + ceil(((x_high - x_low) / 2).toDouble()).toInt() + 1
        }
    }
    if (x_high != x_low) throw error("Something went wrong in here?")
    return x_high
}

private fun main() {
    day05()
}

private fun seatCodes(): List<String> {
    return """FFBBFFFLRL
        FFBBFBBRRL
        FBBBFFBLRL
        BBFBFFBLRR
        BFBBBFFLLL
        BFBBBBBLLR
        FBFBFBFLLR
        BFBFBBFLLR
        FBBFBFBLLL
        BBBFBFBRLL
        BFBBBFBLRL
        FBBFFFBLLR
        BFFBFBFLRL
        FBBBBFBRLR
        FFBBBBBRRL
        FBBFBFFLRL
        FBBBBBBLLR
        FBBFBBBRRL
        FBBFFBFRLL
        FBFBBBBRRR
        BFFBFBFRRL
        BFBFBBBLLR
        FFFFBFBLRL
        FBFFBFFLRL
        FFBFBBBRRR
        FFBFFFFLRR
        FBBBFFFLLR
        FFFFFBFLLL
        BFFFFFFLRL
        FBBBBBFRLR
        BBBFFBBRRR
        FFFFBBBRLL
        FBFFBBBRLR
        BFBBBFFRRL
        BBFBBFBLRL
        BFBFBFFRRR
        BBFBFFFLLL
        FBFBFBFRRR
        BFBFBFFRRL
        BBFBBFBRRL
        FFBBFBFLRL
        FBFFFFFRLR
        FBBFFFBLLL
        FBBBBFFRLL
        FFBBBBBLLL
        BFFBBFFRLR
        FFFBBFBRRR
        FBBBFFFRLL
        FFFBFBFLRL
        BFFBBFFLRL
        BFBBBBBLRL
        BFFFFFBRRR
        FFFBBFBLRL
        FBBBFBFRLL
        FBBFBFBLRR
        BBFBBFFLRR
        FBFFBBFLLL
        FFFFFBBRLL
        FFBBFFBRLR
        BBFFFFFLLL
        BFFFFBFLRR
        FBFBBFFRRL
        BFBFFBBLLR
        FBBBFBBRLR
        BBBFBFFRLL
        BBFFBBFRLR
        FFBFFBBRLR
        FFBBBFBRLR
        BBBFFFBLRR
        BBFBFBFLRR
        BFBBBBBRLR
        BFFFFFBLLR
        FBFFBFFRLL
        BBBFFBBRLR
        FFBBBFBRRR
        BFBBBFBRLR
        FBFFFBBLRL
        FFFBFFFRLL
        BBFFBFFLLL
        BFFBBFFRRR
        FFBFBFBRRL
        FFBBBFBRRL
        FBBFFBBRLR
        FBFFBBFRLR
        BFBFFBBRRR
        BBFBFBBRLR
        FFBFFFFRRL
        BBFFBFFLLR
        FBBFBFBRRR
        BFBFBBBRRL
        FBFBFFBRRL
        FFFBBFFRLL
        BFBFFFFRRR
        FBBFFBFLLR
        FFBFBFFRLL
        FBFFBFBLLL
        FBFBFFFLRL
        BBFFFFFRRR
        FBFFBBFLRR
        FBFFFBFLRL
        FBBBFBBRRL
        BFFBFFFRLR
        FFBBBBFLLR
        BFFFFBFLRL
        BFFBFFBRRR
        FFBFBFBLRL
        BBFFFBBRLL
        FFBFBBFRRR
        FFFBFBFRRL
        FBFFBFBRRR
        FBBBBFBRRL
        FBFFFFFLLR
        FFBFFFFLLR
        FBBBBFFRLR
        BBBFFBBLRR
        BBBFFBFLRR
        FBBFBBBLRR
        FBBFBBFRRL
        FFFBFFBLLL
        FBFFFFBLLL
        BBFFFFBLLL
        FBFFFBFLRR
        FFBFBFFLRR
        BBFBFFFRLL
        FFFBBBFRLR
        FFBFFBBRLL
        BBBFBFFRRL
        BFFBBBBLRR
        BFFFFFFLLL
        BBBFFFBLLL
        FBBBBBFRLL
        FBBFBBBLLR
        BBFBFFFRRR
        BBFFFBBLLL
        FBBFFBFLRL
        BBFBFFFLRR
        BFBFFBFRLL
        BFFFBFBLLR
        BBBFBFBLLL
        BBBFFBFRLR
        FBFFBFBRLR
        BFBBFFFLLL
        BBFFFFBLRR
        BBFFFBBRRL
        BBBFBFFLLL
        BFBFFBFLLL
        FBBBBFFLLR
        FFBFFFFRLR
        FFBBBFBRLL
        BFFFFFFRRL
        BFBFFBBLRL
        BBFBBBFRLL
        BFFFBFFLLR
        BBFBFFBRLL
        BFBBFFFRLL
        BBFBBFBRLR
        FFFBBFBRLR
        BFFFBBFLRL
        FFBFFBFRLL
        FFFBFFBLRL
        BFBBBFFRLR
        BBBFFBBLRL
        BFFBBBBRRL
        FFBBFFFRLL
        BFBFFBFLRL
        BFFBBBBRRR
        BBFBFBFRRL
        BBBFFBFRRL
        BFFFBFBLRL
        FFFBFBFRLR
        FBFFBBBLRR
        FFFFBBBLLL
        BBBFBBFLLR
        BFFBFBFRRR
        BFBFFFBRRR
        BBFBBBBLLR
        FFBBBBBLRL
        FFBFFBFLRR
        FFBBBFBLRR
        BBFBBFBRRR
        FFFFFFBRRR
        BFBFBFBRRL
        BBFFBBBLRR
        BBFBFFFRLR
        FBBFBFBLLR
        BFBBFFBRLL
        FFFFBFBLLR
        BFBFFBBRLR
        FBFFFBFRRR
        BFFBFFFRLL
        FBBBFFFRRR
        FFFBBBFLLL
        FBBBBBBRRL
        FBBFFBBLLR
        BBBFBFFLRR
        BFBBFBFLRR
        FFFBBBBRRR
        BFFFBBFRLR
        FBFFBBFLLR
        FBFFFFFRRL
        BBBFBBFLLL
        BBBFFFFRLR
        FFFFBBBRRR
        FBBBFBBLLL
        BFFBFFBLLL
        FBFBBFBRLR
        FFBFFBBLLR
        FFFFBFFLRR
        FBBFFBFLLL
        FBFBBFBRRR
        BBBFBFBLLR
        BBFFBFBLLL
        FFBFFBFRLR
        FBBBFBBLRR
        BBFBBFBLLL
        FFBFFBFRRR
        FBBBBFFLRL
        BFBBFBFLLR
        FBFBBBFRLR
        BFBBFFBLLR
        FFFBBBFLLR
        FFFBBBFRRL
        BFFFBFBRLR
        BBBFBBFLRR
        BBFBBFFLLR
        BBFBBBBRLR
        FBBFFFFLRR
        FBFBBFFRLL
        FFBFFFFRRR
        FFFBBFFRRL
        FFFBBFBRRL
        FFFBFBFLLR
        FFBBBBFRRR
        BFFFFBBRRL
        BFFFFBFRRR
        BFBBBFBLRR
        FFBBFFFRRL
        FBBBBBBRRR
        BBFBFFBLLR
        FBFFFFBRLL
        BFBBFBFLLL
        FBFFFBBRLL
        FBBBFFBLLR
        BBFBBBFRLR
        FBFBBFBLRR
        FFFFFBFLLR
        FBFBBBFLRL
        FFBFFFBRRR
        FFFBBFFLRL
        BBBFFFFLLR
        BFBFFFFRRL
        FBFBFBBLLR
        FBBBFBFRRL
        BBFBBBBRRL
        BFBBFBFLRL
        BFFBBFFLLR
        BFBFFBBRLL
        FBFFBFFRLR
        FBFBFBFLRR
        BFBFBBFRRR
        FBBBBFBLRL
        FFBBBFFRRR
        FFBBFFBRLL
        FFBBBBBRLL
        FBFFFBFRRL
        FFFBBBFLRL
        BBBFBFFRRR
        FFFBFFFRRR
        FBFFBFFLLL
        FFBBFFBRRL
        BFFBFFBRRL
        FFBBBFFRLR
        BBBFFFFRRR
        BBFBFBBRRR
        FBFBBBBLRR
        BFBFBFBLLL
        FFBBFFBLLR
        BFFFBBBRLR
        BFBBFBBRLR
        BFFFFBBLLR
        FBFBFBBRLR
        BFFBBBFLLR
        FBFFBFBLRL
        BFBFBBBRLL
        BFBBBFFLRL
        FFFBFFFLRR
        BFFBBFBRLL
        FFBBBBFRLL
        BBFFFBBLLR
        FBFBFBFRLR
        BBBFFFBRLR
        BFFFFFBRLR
        BBBFFFFLLL
        FFFBBFBRLL
        BFFFFFBRRL
        FBFFBBBRRL
        FBFBFBBLLL
        FBFBBFBRRL
        FBBFBBBRRR
        BFBFFBBRRL
        FBBBBFBLLL
        FBBBBBBRLR
        BBFFFBFRRL
        FFFFBFBLLL
        FFFFBBFLRR
        FFFFBFFLRL
        BFFFBBFRLL
        BBFFFFBRRR
        BFBFFFFRLL
        BFFFFBFLLR
        BBBFFBBRRL
        BBFBBFBLLR
        BFFFFBFRLR
        FBBBFBFLLR
        FFFFFBBLRR
        BFBFFFFLRR
        FBFBBFFLRL
        FBFFFBBRRR
        BBFBBBFLRL
        BFBBBFFLLR
        BFBFBBFRRL
        FBFFFFBLRL
        FBBFBFFRLR
        BFBFBBBLRR
        BFBFBBFLRL
        BBFFBFFRLL
        FBFFBBBLRL
        BFBFBFFLLR
        FFFBFFBLLR
        FFFBFBBRRR
        BBFBFFFLLR
        FBBBFBFRLR
        FBFFBBBLLL
        FFBFBBFLRR
        FBFBFFFLLL
        BBFFFFFLRL
        FBFBBFBLRL
        BBFFFBFLLR
        BBFBBBBRRR
        BFBBBFFRRR
        FFFBFFBRLL
        FBBBFFFRLR
        BBBFBFBLRL
        FBFFFFBRLR
        BFFFFFBLLL
        FBFFFFBLLR
        BFBBFFBLRR
        BFBFBBBRRR
        BBBFBFBRRR
        FFFBBFFRRR
        FBBBFFFLRR
        BBFBFFFRRL
        BFBFBBFRLR
        BFFFBFBRLL
        FBFFFFFLLL
        FFFBBBBLRL
        FFFBBFFLLL
        FBFFFFBRRR
        FFBFBFBRLR
        BFBFFFBLRR
        BFFFBFBLLL
        FBBFBFFRLL
        FBFBFFFRLL
        FBBBFFBRRR
        FBBBFFBRLL
        FFBBFBBLLL
        FBBBBFBRRR
        BFBBBFBLLR
        FBBBBBFLLR
        FFBBFBFLRR
        BFFBBFBLLR
        FBFFFFBLRR
        BFFBFFFRRR
        BFFFBBBRLL
        FBFBBBFRLL
        BFBBBFBRRL
        FFBFFFBRRL
        FBBFFFFRLR
        BFBFBFFLLL
        FFBFBFBLLL
        FBFBBFBRLL
        FBFBFFBRLR
        BFFFFFBLRR
        FBBBFFBRLR
        BFBBFBBLLL
        BBFFBFBRRR
        FFFBFBBRRL
        FFBBBFFRLL
        BFFFFFFRLL
        BFBFFFBRLL
        FFFFFBFRLL
        BBBFFFBRRL
        FFBBBBFRRL
        BFFFBBBLRL
        FFFFFBBLLR
        BFFFFBBLRR
        BFBFFFBLLL
        FFFFFBBRLR
        BBFBFBFLLL
        FBFBBBBRLR
        FBBFBFBRRL
        FFBFBFBRRR
        FBBBBBFLRL
        BBFBFFBLLL
        BBFFFFBRLL
        BBBFBFBRLR
        BBFBFBBLLL
        BFBBBBBLRR
        BBFFFBFRRR
        FFFBFBFRLL
        BFFBBFBLLL
        FBFFBBBRRR
        FFFBFBFLLL
        BBFFBBFLRL
        BBFFFFBRLR
        BFBFBBBRLR
        BBBFBFFLLR
        FFBBBBFRLR
        BBFFBFFRRL
        BBFBFFBRRR
        BFBFFBFLLR
        FBFFBBFRLL
        BBFFFFBLLR
        FBFBFFFRLR
        FFBFBFBLLR
        BFBBFFBRRL
        FBBFFBBRRL
        BBFFBFBRLR
        FFFFBFBLRR
        FFFFBBBLRR
        FFBFBFFLLL
        FFBBBFFRRL
        BBFFFBFRLL
        FBFBBBFLRR
        FBBFBFFRRR
        FBBFBFFLLR
        BFFBBBFLRR
        FBBFBBFRLR
        BFFBBBFLLL
        BBFBBFBRLL
        BBBFFBBLLR
        BBBFBFBRRL
        FBFBBBFRRL
        FBBFFFBRLR
        BFBBFBBLRL
        FFBBFFFRRR
        BFBFFFFRLR
        BBFFFFFLLR
        BBFBFFBRLR
        BBFBFBFLRL
        FBFFFFFLRL
        FFBBBFFLRL
        FBFBFBBLRR
        FFBFBBBRLL
        FFBFBFFRLR
        FBBBFBFLRR
        FBFFBFFLRR
        BBFFBFBRRL
        FBBFFFFLRL
        FFFFBBFLRL
        FFBBFFBLRR
        BFBFFFBLLR
        BFBFBBFLRR
        BFBFBFBLLR
        BFBBFBFRLL
        BFBBFFBRLR
        FBFFFBBLLL
        FFFBBBBRRL
        FBBFBFFLLL
        FBFFBFBRLL
        BFFBFFFRRL
        FBFBFFBLLR
        BBFFBBFLLL
        FFBBBFBLLR
        BFBBFFFLLR
        FBBBFBFRRR
        FBBBFFBRRL
        FFFFBBFRRR
        FFBFFFFLRL
        FBBFBBFLRR
        FFBBBFBLLL
        FBFFFBFRLR
        FFBFFBBRRL
        FFFFBBFRRL
        BBBFFFFLRL
        FBBFFFBLRL
        FFBFBFBLRR
        FFFFBFFRRR
        FFFBFBFRRR
        BBFBBBBLLL
        FFBBFFBRRR
        FBBFFBBLRR
        FBBFBBFLLL
        BBFBBBFRRL
        FFFBBBBLLR
        FFBFFBBLRR
        BBFBBFFRLL
        BFBBBBFRLL
        BBFBFFBRRL
        BFFFBBFRRL
        FBBBBBBLLL
        FFBBFFBLLL
        FFBBFBBLRR
        BFBFBFBLRR
        FBBBFBBLLR
        BBFBFBFRLL
        FBBFBFFLRR
        FBBBFBFLLL
        FFBBFFFLRR
        BFFBBBFRLL
        FBBBBFBLLR
        BFBFFFFLRL
        FFBFBBBLLL
        FFBFFFBLRR
        BFBFBFBRLL
        FBFBFBFRLL
        BFBBBBBLLL
        FFBFBFBRLL
        BFBFFFFLLR
        BFFBBBBLLL
        FFBBFBBRLL
        FFFFBFBRLR
        FBFBBFFLLR
        BFBFBBFRLL
        FFBFFFFLLL
        FBBFFBFLRR
        FFBFFFBRLL
        FFFFBBBLLR
        BFBBBBBRLL
        FBBFBBBLRL
        FBFFBBBRLL
        FBFFFBBLLR
        FFBBBFBLRL
        FFFFBFFRLL
        BBFFFBBLRL
        BFFFBFFRRL
        BFFBFBFLLL
        BBFFFBFLLL
        FFFFFBFLRL
        FBBFFFFRRR
        FBBBBBFLRR
        FFBFFFBRLR
        FBBBFFFLLL
        FFBFBBBLRL
        FFBFFFBLLL
        FBFFBBBLLR
        BBBFBFFRLR
        FFFBFFFRLR
        BBFBBBFLLR
        FFBBFFFRLR
        FFFFFBFRLR
        FBBFFFBRRL
        BFFBBBBRLR
        BFFBFFBRLL
        BFBBBFBRLL
        FBFFBBFRRR
        FBFBBFFLLL
        BBFBFBBLRL
        BBBFBBFLRL
        FBFBFFBLRR
        FBFFFFFRLL
        BFBBFBFRRR
        BFBFBBBLLL
        FFBBBFFLRR
        BFBBFBBLLR
        FBBBBFFRRR
        BFFFBFFLRL
        FFFFBFBRLL
        BFFFFBBLLL
        BFFBFBBLLL
        BFFBFBBRRL
        BBBFFFBLRL
        BBBFFFBRRR
        BFFFBBFLRR
        FFBBFBFRLL
        BFFBBFBRLR
        FFBFFBFLLR
        FBFBBBBLRL
        BFFBBFFRRL
        BBFFFBBRRR
        FBBBFBBRRR
        BFFFFBFRRL
        FFBFBBBRRL
        BFFFBBBRRR
        BFBBBFBRRR
        BFBFBFFRLR
        FFFFFBBRRR
        FBFBFBFLRL
        FFBBBBBLRR
        BBFFFFBLRL
        FFFBFFFLLR
        FFFBFFFRRL
        FBBBFBBLRL
        BBFFBBFLLR
        BBFFBFFLRR
        BFFFFFFRLR
        FBBFBBFRLL
        BFBBBBFRRR
        FBFBBBBLLR
        FFFBBFFRLR
        FBBFBBBRLR
        FBBFBFBRLR
        FBBBFFFLRL
        FFBBFBBRLR
        BFFFBBBLLL
        BBFBBFFRRR
        FBFBFFFLRR
        BBFFBBFRRR
        FBBFBFFRRL
        BFFFFBFLLL
        BBBFFBBLLL
        BFBBBBFRRL
        FBBFFFBRRR
        BFBBBBFLLL
        FFFBFFBRRL
        BFFBFFBLLR
        FFBBFBFRRL
        FBFFFBBRLR
        BFFFBBBLRR
        FFBFBFFRRR
        FBFBFFBRLL
        BFBBBBFLRL
        BFBBFFFRRR
        FBFBFBFRRL
        BBFBBFFLRL
        FBBFFFFLLL
        BBBFFBBRLL
        FBFFFBBRRL
        FFBFBFFLRL
        FBFFBBFLRL
        BFFBFBFLRR
        BFFFFBBLRL
        BBFBBBBLRL
        FFBFBBFRLL
        FBFFFBFLLL
        BFFBFFBLRL
        FBBFFFBLRR
        FFFFBFFRRL
        FBBFFBFRLR
        BFFFBFFRRR
        FFFFBFBRRL
        BBFFBFBLRL
        FBBFBFBRLL
        BFBBFFFLRL
        FFFBBBFRRR
        BFFFFFFLLR
        FBFBBFFRRR
        BFFFFFBRLL
        FFFFBBBRRL
        BFFBFBFLLR
        FFFFBFFRLR
        BFFBBBFRRL
        BFBBFBBRLL
        FFFFFBFRRL
        FFBBBBFLRL
        FFBBFBFLLR
        BBFFBBBRLL
        BFFBFFFLLR
        BBFBBBBRLL
        BFFBFBBRLR
        FFBFBFFRRL
        FBBFBBFLRL
        FFBFFBBLRL
        BFBFFFBRLR
        BFFBFFFLRR
        BFFBBBBLRL
        FFFBFBBRLR
        FBBBBBFRRR
        BFBFBFFLRL
        BFBFFFBLRL
        FBBBFBBRLL
        FBFBFBFLLL
        FBBFFBFRRR
        FFFBFBBLRR
        FFFBFFFLRL
        BFBFBFBRRR
        BFFBBFFLLL
        BFFBFBBRRR
        FFBFFBFRRL
        FBFFFBFRLL
        FFBFBBFLLL
        FFBBFFBLRL
        FBFBBBBRRL
        BFFBBFBRRL
        FBFBFBBLRL
        BBFBFBFRLR
        FBFFFFFLRR
        BBBFFBFRLL
        BFBFBFFRLL
        FBBFFBBLRL
        FBFBFBBRRL
        FFBBBBBRLR
        FBBFBBFLLR
        FFFBFBBRLL
        BBFFFFBRRL
        BFBFBFBLRL
        FBFBBFBLLR
        BBBFBFBLRR
        FFFFBBFRLL
        FFBBBBBRRR
        BFBBFFBRRR
        BFFFBBBLLR
        FBBBBBBRLL
        FFBFBBFRLR
        BBFFBBFRLL
        BFFBFFFLRL
        FFBBFFFLLL
        BFFBFBBLRL
        BBBFFFFRLL
        BBFBBBFRRR
        FBFFBFFRRR
        FBBFFFFRLL
        BFFFBBBRRL
        FBFBBBFLLL
        FFFFBBFLLR
        FBBFBBFRRR
        BBFFBBBRLR
        BBFBBFBLRR
        BFFFBFFLLL
        BBFBFBFRRR
        FFBFFFBLRL
        BBFFFBBRLR
        FBFBFBBRRR
        BFBBFFBLRL
        BFFFFBFRLL
        BBFBFBBRLL
        BFFFFBBRLL
        FFFFBFFLLR
        FFBBBBFLRR
        FBFBFFFRRR
        FFFBBFBLLR
        FFBBFBFRRR
        BFBBFBBRRR
        FFBFBBBLLR
        FFBFFBFLLL
        FFFFBBBLRL
        FFBFBFFLLR
        BFBBBFFLRR
        FBBFFFFLLR
        FBFFFFBRRL
        BFFBBFFLRR
        FFBBFBBRRR
        FFFFBFBRRR
        BFBBBBFLLR
        FBBFFBBRRR
        BBFBFBBLRR
        FFFBBBBLRR
        FBFFFBBLRR
        BFFBFBFRLR
        BFBFFBFRLR
        BFBBFFFRLR
        FFBBFFFLLR
        FFFFFBFLRR
        BFFBBFBLRR
        FFFFBBFRLR
        BFFFBBFLLR
        FBFBFFBRRR
        BBFBFBBRRL
        FBFBBFFLRR
        BFBFFBFLRR
        BBFFFBBLRR
        FBFFBBFRRL
        FBBBBBBLRL
        FBBFFBBLLL
        BFBFBBBLRL
        BBBFFFBLLR
        BFFFBFFLRR
        BBFFBBFRRL
        BFBFBBFLLL
        FFBBBFFLLR
        BFBBFBFRRL
        FBFFBFFRRL
        BFFFBFBLRR
        BFBBFBBRRL
        BBFBFFBLRL
        BFFBBBFRRR
        FBFBBFBLLL
        BBFFBFFRRR
        BBFFBFBRLL
        BBBFFBFLLR
        FFFBBFBLLL
        FBBBFFFRRL
        BFFBFFBLRR
        BBFBFBBLLR
        FFBBFBFRLR
        BBFFFFFRLR
        FBFFBFBLRR
        BFFFFFFRRR
        FFBBFBFLLL
        BFBBFBBLRR
        BBFFFBFRLR
        BBBFFBFRRR
        FFFFBBFLLL
        BBFFBFBLRR
        BFFBFBBRLL
        FBFFFBFLLR
        BFFFBBFRRR
        FFBFBBFRRL
        BFBFFBBLRR
        FBBFFBFRRL
        FFBBBBBLLR
        BFBFFBBLLL
        BFBBBBFLRR
        FFFFBBBRLR
        BFFBBBBLLR
        BFFFFFFLRR
        BFFFFBBRLR
        FFFBBBBRLL
        BFBBFFFLRR
        BFFBBBFRLR
        BFFBBFFRLL
        FBFBBBBLLL
        BFBFBFBRLR
        BBFBBFFLLL
        BBFBBBBLRR
        FFBBFBBLLR
        FBBFFFBRLL
        FBBBBFFRRL
        FFFBBFBLRR
        FFFBFBBLLR
        FFFFFBBRRL
        FBFBBBFRRR
        BBFFFBFLRL
        FBFBFFBLLL
        FBFBFFFRRL
        BFBFFFBRRL
        BBFFBFFRLR
        BBFFBFBLLR
        FFFBFBFLRR
        BBFBBBFLLL
        BFBBBFFRLL
        BFFFBFBRRL
        FFFFBFFLLL
        FBBBBBFRRL
        FBFBFFBLRL
        FBBBBBBLRR
        FFFBBBBRLR
        BFFBFBBLLR
        BBFFBBFLRR
        BFFFFFBLRL
        FFBBBBFLLL
        BFFBFBBLRR
        FFFBBBFLRR
        FFBFBBFLRL
        FBBFBFBLRL
        BBBFFFFRRL
        BBFBFBFLLR
        BFBBBFBLLL
        FFBFBBBLRR
        BFFBFFBRLR
        FFFBBBBLLL
        BFFFBFFRLR
        BFFFBBFLLL
        FBBBBFBLRR
        FBBFBBBRLL
        BFBFFBFRRL
        BFFFFBBRRR
        BBFFBBBLRL
        FBBBBFBRLL
        BFBBFFFRRL
        BBBFFBFLLL
        BFBFFBFRRR
        FFBFFBBRRR
        FFBFBBBRLR
        FBBBBFFLRR
        BFBFBFFLRR
        BFBBBBFRLR
        FFFFFBFRRR
        BFFBBFBRRR
        BFBBBBBRRL
        BFFBBFBLRL
        FFFBFBBLRL
        FFFBFBBLLL
        FFBBFBBLRL
        BBBFFFFLRR
        FFFFFBBLRL
        FBFFBFBLLR
        FBFBBBBRLL
        BFFBFBFRLL
        BFFFBFFRLL
        BFFFBFBRRR
        BBBFBFFLRL
        BFFBFFFLLL
        BBFFFFFRRL
        FBFBFFFLLR
        FFBFFBBLLL
        BBFFBFFLRL
        BBFFBBBLLL
        FFFFFBBLLL
        BFBBFFBLLL
        FFFBFFBRLR
        FBBFFBBRLL
        FBFBFBBRLL
        BFFBBBFLRL
        FFFBFFBLRR
        FBFBBFFRLR
        FFFBBFFLLR
        FBFBBBFLLR
        FBBFFFFRRL
        BBFBBBFLRR
        BFBBFBFRLR
        FFBFFFBLLR
        FFBFFBFLRL
        BBFFBBBRRR
        BBFFFFFLRR
        FBFFBFBRRL
        FBFFBFFLLR
        FBBBFFBLLL
        FBBBFFBLRR
        BFBBBBBRRR
        BBBFFFBRLL
        FBFFFFFRRR
        BBFBBFFRLR
        BBFFBBBLLR
        BBBFFBFLRL
        BBFFFBFLRR
        FFFBFFFLLL
        FFBFBBFLLR
        BBFFFFFRLL
        BBFBBFFRRL
        FBBFBBBLLL
        FFFBFFBRRR
        FBBBBFFLLL
        FFFBBFFLRR
        BBFFBBBRRL
        FFBBBFFLLL
        FBBBFBFLRL
        BBFBFFFLRL
        FFBFFFFRLL
        FFFBBBFRLL
        FBBBBBFLLL
        BFBFFFFLLL""".lines().map { it.trim() }
}

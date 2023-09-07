package io.github.maple8192.ndmaze

sealed class Maze {
    data class Array(val array: List<Maze>) : Maze() {
        override fun toString(): String {
            if (array.isEmpty()) {
                return ""
            }

            if (array[0] is Cell) {
                return array.joinToString("") { it.toString() }
            }

            return array.joinToString("\n", postfix = "\n") { it.toString() }
        }
    }
    data class Cell(var value: Int) : Maze() {
        override fun toString(): String {
            return if (value == 1) "@@" else "  "
        }
    }

    fun get(idx: List<Int>): Int {
        return recGet(idx, 0)
    }

    private fun recGet(idx: List<Int>, depth: Int): Int {
        if (depth == idx.size) {
            return (this as Cell).value
        }

        return (this as Array).array[idx[depth]].recGet(idx, depth + 1)
    }

    fun set(idx: List<Int>, value: Int) {
        return recSet(idx, 0, value)
    }

    private fun recSet(idx: List<Int>, depth: Int, value: Int) {
        if (depth == idx.size) {
            (this as Cell).value = value
            return
        }

        (this as Array).array[idx[depth]].recSet(idx, depth + 1, value)
    }

    companion object {
        fun create(size: List<Int>): Maze {
            return recCreate(size, 0)
        }

        private fun recCreate(size: List<Int>, depth: Int): Maze {
            if (depth == size.size) {
                return Cell(1)
            }

            return Array(List(2 * size[depth] + 1) { recCreate(size, depth + 1) })
        }
    }
}
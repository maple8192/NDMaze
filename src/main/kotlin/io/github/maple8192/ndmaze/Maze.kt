package io.github.maple8192.ndmaze

sealed class Maze {
    data class Array(val array: List<Maze>) : Maze() {
        override fun toString(): String {
            if (array.isEmpty()) {
                return ""
            }

            if (array[0] is Cell) {
                return array.joinToString(" ") { it.toString() }
            }

            return array.joinToString("\n", postfix = "\n") { it.toString() }
        }
    }
    data class Cell(val value: Int) : Maze() {
        override fun toString(): String {
            return value.toString()
        }
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
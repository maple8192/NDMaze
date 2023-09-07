package io.github.maple8192.ndmaze

import kotlin.random.Random

class MazeBuilder(private val size: List<Int>) {
    fun build(): Maze {
        return dig(Maze.create(size))
    }

    private fun dig(maze: Maze): Maze {
        val start = List(size.size) { i -> Random.nextInt(0, size[i]) * 2 + 1 }
        return recDig(maze.also { it.set(start, 0) }, start, mutableListOf())
    }

    private fun recDig(maze: Maze, idx: List<Int>, path: MutableList<List<Int>>): Maze {
        val loc = idx.toMutableList()
        while (true) {
            val dirs = mutableListOf<List<Int>>()
            for (i in 0..size.lastIndex) {
                for (j in -1..1) {
                    if (j == 0) continue

                    val vec = List(size.size) { k -> if (k == i) j else 0 }
                    if (isDiggable(maze, loc, vec)) {
                        dirs.add(vec)
                    }
                }
            }

            if (dirs.isEmpty()) break

            val randomIdx = Random.nextInt(0, dirs.size)
            val a = dirs[randomIdx].mapIndexed { i, v -> v + loc[i] }
            val b = dirs[randomIdx].mapIndexed { i, v -> v * 2 + loc[i] }
            maze.set(a, 0)
            maze.set(b, 0)
            path.add(b)
            loc.forEachIndexed { i, v -> loc[i] = v + dirs[randomIdx][i] + dirs[randomIdx][i] }
        }

        val next = nextStartPoint(path) ?: return maze
        return recDig(maze, next, path)
    }

    private fun nextStartPoint(points: MutableList<List<Int>>): List<Int>? {
        if (points.isEmpty()) return null

        val i = Random.nextInt(0, points.size)
        val next = points[i]
        points.removeAt(i)
        return next
    }

    private fun isDiggable(maze: Maze, idx: List<Int>, vec: List<Int>): Boolean {
        val a = idx.mapIndexed { i, v -> v + vec[i] }
        val b = idx.mapIndexed { i, v -> v + vec[i] * 2 }

        return isWall(maze, a) && isWall(maze, b)
    }

    private fun isWall(maze: Maze, idx: List<Int>): Boolean {
        if (idx.withIndex().any { e -> e.value < 0 || e.value >= size[e.index] * 2 + 1 }) {
            return false
        }

        return maze.get(idx) == 1
    }
}
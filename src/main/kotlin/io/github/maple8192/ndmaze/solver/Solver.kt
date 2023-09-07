package io.github.maple8192.ndmaze.solver

import io.github.maple8192.ndmaze.Maze

class Solver(private val maze: Maze) {
    fun solve(start: List<Int>, goal: List<Int>): Maze? {
        val stack = ArrayDeque(listOf(start))
        val set = mutableSetOf<List<Int>>()

        while (!stack.isEmpty()) {
            val loc = stack.last()
            set.add(loc)

            if (loc == goal) {
                return markPath(stack)
            }

            var e = false
            loop@ for (i in 0..start.lastIndex) {
                for (j in -1..1) {
                    if (j == 0) continue

                    val l = loc.mapIndexed { k, v -> if (k == i) v + j else v }
                    if (!set.contains(l) && maze.get(l) != 1) {
                        set.add(l)
                        stack.addLast(l)
                        e = true
                        break@loop
                    }
                }
            }
            if (!e) {
                stack.removeLast()
            }
        }

        return null
    }

    private fun markPath(path: ArrayDeque<List<Int>>): Maze {
        val copy = (maze as Maze.Array).copy()
        while (!path.isEmpty()) {
            copy.set(path.removeLast(), 2)
        }
        return copy
    }
}
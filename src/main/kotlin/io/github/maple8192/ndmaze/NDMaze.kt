package io.github.maple8192.ndmaze

import io.github.maple8192.ndmaze.solver.Solver

fun main() {
    val maze = MazeBuilder(listOf(5, 5, 5, 5)).build()
    println(maze)

    val solver = Solver(maze)
    println(solver.solve(listOf(1, 1, 1, 1), listOf(9, 9, 9, 9)))
}
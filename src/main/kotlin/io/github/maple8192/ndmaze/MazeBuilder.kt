package io.github.maple8192.ndmaze

class MazeBuilder(private val size: List<Int>) {
    fun build(): Maze {
        return Maze.create(size)
    }
}
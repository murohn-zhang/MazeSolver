// Maze Solver by Murohn
/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;



public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // Create new arraylist and stack
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        Stack<MazeCell> flipped = new Stack<MazeCell>();

        // Get start and end cells
        MazeCell end = maze.getEndCell();

        // Keep pushing the parent of the cells into stack, until it's start
        while (end != maze.getStartCell()) {
            flipped.push(end);
            end = end.getParent();
        }
        solution.add(maze.getStartCell());
        // Reverse it so its start to end by adding from the top of the stack
        while (!(flipped.empty())) {
            solution.add(flipped.pop());
        }
        return solution;
    }

    // Performs DFS to complete the maze by setting the parent cell for all cells needed
    // And calling getSolution() at the end
    // Visits the last cell seen next (use of stack!)
    public ArrayList<MazeCell> solveMazeDFS() {
        // Create a variable to hold the current cell, starting at the start cell
        MazeCell current = maze.getStartCell();
        // Set the start cell to explored, so we don't accidentally visit it again and go in circles
        current.setExplored(true);
        // Stack to hold cells to visit
        Stack<MazeCell> toVisit = new Stack<MazeCell>();


        // While we haven't reached the end cell, check each direction
        // For each direction, if it's valid, add it to the stack, set it to explored so it's not visited multiple times
        // And set parent to current so getSolution() knows where to go
        while (current != maze.getEndCell()) {
            int row = current.getRow();
            int col = current.getCol();
            // Check North
            if (maze.isValidCell(row - 1, col)){
                maze.getCell(row - 1, col).setExplored(true);
                maze.getCell(row - 1, col).setParent(current);
                toVisit.push(maze.getCell(row - 1, col));
            }

            // Check East
            if (maze.isValidCell(row, col + 1)){
                maze.getCell(row, col + 1).setExplored(true);
                maze.getCell(row, col + 1).setParent(current);
                toVisit.push(maze.getCell(row , col + 1));
            }

            // Check South
            if (maze.isValidCell(row + 1, col)){
                maze.getCell(row + 1, col).setExplored(true);
                maze.getCell(row + 1, col).setParent(current);
                toVisit.push(maze.getCell(row + 1, col));
            }

            // Check West
            if (maze.isValidCell(row, col - 1)){
                maze.getCell(row, col - 1).setExplored(true);
                maze.getCell(row, col - 1).setParent(current);
                toVisit.push(maze.getCell(row, col - 1));
            }

            // Otherwise, go to the last possible option
            current = toVisit.pop();
        }

        return getSolution();
    }

    // Performs BFS to complete the maze by setting the parent cell for all cells needed
    // And calling getSolution() at the end
    // Visits the first cell seen next (use of queue!)
    public ArrayList<MazeCell> solveMazeBFS() {
        // Create a variable to hold the current cell, starting at the start cell
        MazeCell current = maze.getStartCell();
        // Set the start cell to explored, so we don't accidentally visit it again and go in circles
        current.setExplored(true);
        // Queue to hold cells to visit
        Queue<MazeCell> toVisit = new LinkedList<MazeCell>();

        // While we haven't reached the end cell, check each direction
        // For each direction, if it's valid, add it to the queue, set it to explored so it's not visited multiple times
        // And set parent to current so getSolution() knows where to go
        while (current != maze.getEndCell()) {
            int row = current.getRow();
            int col = current.getCol();
            // Check North
            if (maze.isValidCell(row - 1, col)){
                maze.getCell(row - 1, col).setExplored(true);
                maze.getCell(row - 1, col).setParent(current);
                toVisit.add(maze.getCell(row - 1, col));
            }

            // Check East
            if (maze.isValidCell(row, col + 1)){
                maze.getCell(row, col + 1).setExplored(true);
                maze.getCell(row, col + 1).setParent(current);
                toVisit.add(maze.getCell(row , col + 1));
            }

            // Check South
            if (maze.isValidCell(row + 1, col)){
                maze.getCell(row + 1, col).setExplored(true);
                maze.getCell(row + 1, col).setParent(current);
                toVisit.add(maze.getCell(row + 1, col));
            }

            // Check West
            if (maze.isValidCell(row, col - 1)){
                maze.getCell(row, col - 1).setExplored(true);
                maze.getCell(row, col - 1).setParent(current);
                toVisit.add(maze.getCell(row, col - 1));
            }

            // Otherwise, go to the first possible option
            current = toVisit.remove();
        }

        return getSolution();
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}

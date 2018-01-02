package recursivePuzzleSolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Maze {

	/***
	 * 
	 * @param grid - takes an NxN integer array as input to navigate all potential paths
	 * @param row - represents each row in the 2d array
	 * @param column - represents each column in the 2d array
	 * @param paths - stores the exit as a positive value (1), all sensors as 0's and all open paths as -1
	 * This method traverses the paths array and every time a possible path that ends in a 1 (exit) is found it is summed together recursively to give the total number of unique paths 
	 * @return
	 */
	public static int findPaths(int[][] grid, int row, int column, int[][] paths) {
		if (row < 0 || row >= grid.length || column < 0 || column >= grid[0].length)
			return 0; // if the iterators for row or column go out of bounds, return.
		if (paths[row][column] == -1) {
			paths[row][column] = 
					findPaths(grid, row + 1, column, paths) + findPaths(grid, row, column + 1, paths);
		}
		return paths[row][column];
	}

	public static void main(String[] args) {
		/*int[][] Grid = new int[][] { 
			{ 0, 0, 1, 1 },
			{ 0, 0, 0, 0 }, 
			{ 1, 0, 1, 0 },
			{ 1, 0, 0, 0 } };*/

		String inputString = "";
		int lineNumber = 0;
		int gridSize = 0;
		ArrayList<String> myList = new ArrayList();
		try {
			BufferedReader input = new BufferedReader(new FileReader("resources/in1.txt"));
			while ((inputString = input.readLine()) != null) {
				switch (lineNumber) {
				case 0: 
					gridSize = Integer.parseInt(inputString.trim());
					lineNumber++;
					break;
				default:
					myList.add(lineNumber - 1, inputString); // adds 1's and 0's to the arraylist
					lineNumber++;
					break;
				}
			}
		} catch (IOException e) {
			System.out.println(e + " file could not be read into the program ");
		}
		
		String[] sList;
		int[][] Grid = new int[gridSize][gridSize];
		boolean isUnsolveable = false;
		
		for(int i = 0; i < gridSize; i++) {
			sList = myList.get(i).split(" ");// splits each line into individual strings
			for(int j = 0; j< sList.length; j++) {
				Grid[i][j] = Integer.parseInt(sList[j]);// converts each string into an integer and stores it in its place on the grid
				if(Grid[0][0] == 1 || Grid[gridSize-1][gridSize-1] == 1)
					isUnsolveable = true; // checks to see if there is a sensor at 0,0 or N-1,N-1. 
				System.out.print(Grid[i][j]+ " ");
			}
			System.out.println();
		}
		
		if(isUnsolveable == true) {
			System.out.println("This grid is unsolveable!");
		}else {
		int[][] paths = Grid;
		for (int i = 0; i < Grid.length; i++) { // converts the grid into a paths array that can be used to sum all possible paths that end in 1 (which is the exit)
			for (int j = 0; j < Grid[0].length; j++)
				if (Grid[i][j] == 1)
					paths[i][j] = 0;
				else
					paths[i][j] = -1;
		}
		paths[Grid.length - 1][Grid[0].length - 1] = 1; // exit is set to 1.
		System.out.println(findPaths(Grid, 0, 0, paths));
		}

	}

}

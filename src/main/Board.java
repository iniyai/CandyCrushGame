package main;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
	private Pair[][] matrix;
	private byte dimension;
	byte maxCount = 0;
	String finalChoice;
	Queue<Byte> visited = new LinkedList<Byte>();

	// public Board(byte dimension) {
	// this.dimension = dimension;
	// matrix = new Pair[dimension][dimension];
	// }

	public Board(byte dimension, char[][] matrix) {
		this.dimension = dimension;
		this.matrix = new Pair[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				this.matrix[i][j] = new Pair();
				this.matrix[i][j].setValue(matrix[i][j]);
				this.matrix[i][j].setCount((byte) 0);
			}
		}
	}

	public void setCounts(byte count) {
		while (!visited.isEmpty()) {
			byte position = visited.remove();
			byte r = (byte) (position / dimension);
			byte c = (byte) (position % dimension);
			matrix[r][c].setCount(count);
		}
	}

	public void findCounts() {
		for (byte i = 0; i < dimension; i++) {
			for (byte j = 0; j < dimension; j++) {
				if ((matrix[i][j].getValue() != '*') && (matrix[i][j].getCount() == 0)) {
					byte count = (byte) DFS(i, j, matrix[i][j].getValue());
					matrix[i][j].setCount(count);
					setCounts(count);
					if (count > maxCount) {
						maxCount = count;
					}
				}
			}
		}
	}

	public int DFS(int i, int j, char value) {
		if (i < 0 || j < 0 || i >= dimension || j >= dimension || matrix[i][j].getValue() != value
				|| visited.contains((byte) (i * dimension + j))) {
			return 0;
		} else {
			// System.out.println(i + "$$" + j);
			visited.add((byte) (i * dimension + j));
			int c1 = DFS(i - 1, j, value);
			int c2 = DFS(i, (byte) j - 1, value);
			int c3 = DFS((byte) i + 1, j, value);
			int c4 = DFS(i, j + 1, value);
			return 1 + c1 + c2 + c3 + c4;
		}
	}

	public void choose() {
		boolean flag = true;
		for (byte i = 0; i < dimension; i++) {
			for (byte j = 0; j < dimension; j++) {
				if ((matrix[i][j].getValue() != '*') && (matrix[i][j].getCount() == maxCount)) {
					finalChoice = (char) (j + 'A') + new Byte((byte) (i + 1)).toString();
					DFSReplace(i, j, matrix[i][j].getValue());
					flag = false;
					break;
				}
			}
			if (!flag) {
				break;
			}
		}
	}

	public void DFSReplace(int i, int j, char value) {
		if (i < 0 || j < 0 || i >= dimension || j >= dimension || matrix[i][j].getValue() != value) {
			return;
		} else {
			// System.out.println(i + "$$" + j);
			matrix[i][j].setValue('*');
			DFSReplace(i - 1, j, value);
			DFSReplace(i, (byte) j - 1, value);
			DFSReplace((byte) i + 1, j, value);
			DFSReplace(i, j + 1, value);
		}
	}

	public void gravity() {
		for (int c = 0; c < dimension; c++) {
			int start = -1;
			int end = -1;
			for (int r = dimension - 1; r >= 0; r--) {
				if (matrix[r][c].getValue() == '*') {
					start = r;
					break;
				}
			}
			for (int r = start - 1; r >= 0; r--) {
				if (matrix[r][c].getValue() != '*') {
					end = r + 1;
					break;
				}
			}
			for (int r = end - 1; r >= 0; r--) {
				matrix[start--][c].setValue(matrix[r][c].getValue());
			}
			while (start >= 0) {
				matrix[start--][c].setValue('*');
			}
		}
	}

	public void solve() {
		findCounts();
		choose();
		gravity();
	}

	public void print(PrintWriter out) {
		printConsole();
		printToFile(out);
	}

	public void printConsole() {
		System.out.println(finalChoice);
		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				System.out.print(matrix[r][c].getValue());
			}
			System.out.println("");
		}
	}

	public void printToFile(PrintWriter out) {
		out.println(finalChoice);
		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				out.print(matrix[r][c].getValue());
			}
			out.println("");
		}
	}

	public void printCounts() {
		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				System.out.print(matrix[r][c].getCount());
			}
			System.out.println("");
		}
	}

	public char[][] getMatrix() {
		char[][] ret = new char[dimension][dimension];
		for (int r = 0; r < dimension; r++) {
			for (int c = 0; c < dimension; c++) {
				ret[r][c] = matrix[r][c].getValue();
			}
		}
		return ret;
	}
}

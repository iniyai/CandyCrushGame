package homework2;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Board implements Comparable<Board> {
	private char[][] matrix;
	private int points = 0; // pick returns a new board with the number of points that are collected.

	public Board(char[][] matrix) {
		this.matrix = new char[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				this.matrix[i][j] = matrix[i][j];
			}
		}
	}

	public Board makeMove(int r, int c) {
		Board ret = pick(r, c);
		ret.gravity();
		return ret;
	}

	public double ratioEmpty() {
		int length = matrix.length;
		int count = 0;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (matrix[i][j] == '*') {
					count++;
				}
			}
		}
		if (count == 0) {
			return 0.05;
		}
		return (count / (double) (length * length));
	}

	public List<Pair> uniqueMoves() {
		List<Pair> retMoves = new ArrayList<Pair>();
		char[][] matrix = new char[this.matrix.length][this.matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				matrix[i][j] = this.matrix[i][j];
			}
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != '*') {
					retMoves.add(new Pair(i, j));
					DFSAdd(i, j, matrix[i][j], matrix);
				}
			}
		}
		return retMoves;
	}

	public static void DFSAdd(int i, int j, char value, char[][] matrix) {
		if (i < 0 || j < 0 || i >= matrix.length || j >= matrix.length || matrix[i][j] != value) {
		} else {
			matrix[i][j] = '*';
			DFSAdd(i - 1, j, value, matrix);
			DFSAdd(i, j - 1, value, matrix);
			DFSAdd(i + 1, j, value, matrix);
			DFSAdd(i, j + 1, value, matrix);
		}
	}

	public Board pick(int r, int c) {
		if (matrix[r][c] != '*') {
			Board ret = new Board(this.matrix);
			ret.DFSReplace(r, c, ret.matrix[r][c]);
			return ret;
		}
		return this;
	}

	public void DFSReplace(int i, int j, char value) {
		if (i < 0 || j < 0 || i >= matrix.length || j >= matrix.length || matrix[i][j] != value) {
			return;
		} else {
			points++;
			matrix[i][j] = '*';
			DFSReplace(i - 1, j, value);
			DFSReplace(i, j - 1, value);
			DFSReplace(i + 1, j, value);
			DFSReplace(i, j + 1, value);
		}
	}

	public void gravity() {
		for (int c = 0; c < matrix.length; c++) {
			gravity(matrix, c);
		}
	}

	public static void gravity(char[][] matrix, int c) {
		int dst = -1;
		int src = -1;
		for (int i = matrix.length - 1; i >= 0; i--) {
			if (matrix[i][c] == '*') {
				dst = i;
				break;
			}
		}
		src = dst - 1;
		while (dst >= 0) {
			for (; src >= 0; src--) {
				if (matrix[src][c] != '*') {
					break;
				}
			}
			if (src >= 0) {
				for (; src >= 0 && matrix[src][c] != '*'; src--, dst--) {
					matrix[dst][c] = matrix[src][c];
				}
			} else {
				for (; dst >= 0; dst--) {
					matrix[dst][c] = '*';
				}
			}
		}
	}

	public int getPoints() {
		return points;
	}

	public char[][] getMatrix() {
		char[][] ret = new char[matrix.length][matrix.length];
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix.length; c++) {
				ret[r][c] = matrix[r][c];
			}
		}
		return ret;
	}

	public void print(PrintWriter out) {
		printConsole();
		printToFile(out);
	}

	public void printConsole() {
		// System.out.println("Number of points: " + points);
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix.length; c++) {
				System.out.print(matrix[r][c]);
			}
			System.out.println("");
		}
	}

	public void printToFile(PrintWriter out) {
		// out.println(finalChoice);
		for (int r = 0; r < matrix.length; r++) {
			for (int c = 0; c < matrix.length; c++) {
				out.print(matrix[r][c]);
			}
			out.println("");
		}
	}

	@Override
	public int compareTo(Board o) {
		return points - o.points;
	}
}

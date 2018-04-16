package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import homework2.Board;
import homework2.Pair;

public class BoardTest {

	@Test
	public void uniqueMoves() {
		List<Pair> pairs = new ArrayList<Pair>();
		pairs.add(new Pair(0, 0));
		// pairs.add(new Pair(0, 1));
		pairs.add(new Pair(0, 2));
		pairs.add(new Pair(1, 3));
		pairs.add(new Pair(2, 1));
		pairs.add(new Pair(3, 3));
		matrixUniqueMove(
				new String[] {
						"1122",
						"1221",
						"1022",
						"1110" },
				pairs);
	}

	@Test
	public void makeMove() {
		matrixMakeMove(
				new String[] {
						"1011",
						"0111",
						"1101",
						"0220" },
				new String[] {
						"****",
						"1***",
						"000*",
						"0220" },
				2, 0);
	}

	@Test
	public void countEmpty() {
		matrixCountEmpty(
				new String[] {
						"****",
						"1***",
						"000*",
						"0220" },
				0.5);
	}

	@Test
	public void pick() {
		matrixPick(
				new String[] {
						"1011",
						"0111",
						"1101",
						"0220" },
				new String[] {
						"10**",
						"0***",
						"**0*",
						"0220" },
				2, 0, 8);
		matrixPick(
				new String[] {
						"1011",
						"0111",
						"1101",
						"0220" },
				new String[] {
						"*011",
						"0111",
						"1101",
						"0220" },
				0, 0, 1);
		matrixPick(
				new String[] {
						"1011",
						"0111",
						"1101",
						"0220" },
				new String[] {
						"1011",
						"0111",
						"1101",
						"0**0" },
				3, 1, 2);
	}

	@Test
	public void gravity() {
		matrixGravity(
				new String[] {
						"1011",
						"011*",
						"**01",
						"0**0" },
				new String[] {
						"****",
						"1*11",
						"0011",
						"0100" });
	}

	@Test
	public void columngravity() {
		testColumn("", "");
		testColumn("1", "1");
		testColumn("*", "*");
		testColumn("1*", "*1");
		testColumn("*1", "*1");
		testColumn("1*1", "*11");
		testColumn("1*2", "*12");
		testColumn("1*3*", "**13");
		testColumn("1*23", "*123");
		testColumn("1*12", "*112");
		testColumn("112*", "*112");
		testColumn("1***", "***1");
		testColumn("****", "****");
	}

	private void matrixCountEmpty(String[] str, double i) {
		char[][] matrix = createMatrix(str);
		Board b = new Board(matrix);
		double percent = b.ratioEmpty();
		Assert.assertEquals(i, percent, 0.01);
	}

	public void matrixUniqueMove(String[] str, List<Pair> pairs) {
		char[][] matrix = createMatrix(str);
		Board b = new Board(matrix);
		// b.printConsole();
		List<Pair> actual = b.uniqueMoves();
		for (int i = 0; i < actual.size(); i++) {
			// actual.get(i).print();
			Assert.assertEquals(true, actual.get(i).isEqual(pairs.get(i)));
		}
	}

	public void matrixMakeMove(String[] str, String[] expected, int x, int y) {
		char[][] matrix = createMatrix(str);
		Board b = new Board(matrix);
		Board ret = b.makeMove(x, y);
		checkMatrix(expected, ret);
	}

	public void matrixPick(String[] str, String[] expected, int x, int y, int points) {
		char[][] matrix = createMatrix(str);
		Board b = new Board(matrix);
		Board ret = b.pick(x, y);
		Assert.assertEquals(ret.getPoints(), points);
		checkMatrix(expected, ret);
	}

	public void matrixGravity(String[] str, String[] expected) {
		char[][] matrix = createMatrix(str);
		Board b = new Board(matrix);
		b.gravity();
		checkMatrix(expected, b);
	}

	public void testColumn(String str, String expected) {
		char[] ca = str.toCharArray();
		char[][] matrix = new char[ca.length][];
		for (int i = 0; i < ca.length; i++) {
			matrix[i] = new char[] { ca[i] };
		}
		Board.gravity(matrix, 0);
		for (int i = 0; i < ca.length; i++) {
			ca[i] = matrix[i][0];
		}
		Assert.assertArrayEquals(ca, expected.toCharArray());
	}

	private void checkMatrix(String[] expected, Board b) {
		char[][] out = b.getMatrix();
		for (int i = 0; i < out.length; i++) {
			Assert.assertArrayEquals(out[i], expected[i].toCharArray());
		}
	}

	private char[][] createMatrix(String[] str) {
		char[][] matrix = new char[str.length][];
		for (int i = 0; i < str.length; i++) {
			matrix[i] = str[i].toCharArray();
		}
		return matrix;
	}
}

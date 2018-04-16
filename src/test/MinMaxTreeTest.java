package test;

import org.junit.Assert;
import org.junit.Test;

import homework2.Board;
import homework2.MinMaxTree;

public class MinMaxTreeTest {

	//	@Test
	//	public void getChildren() {
	//		matrixChildren(
	//				new String[] {
	//						"111",
	//						"113",
	//						"222" },
	//				new int[][] { { 0, 0 }, { 1, 2 }, { 2, 0 } });
	//	}

	@Test
	public void findPrunedValue() {
		matrixPlay(
				new String[] {
						"111",
						"113",
						"222" },
				17, 0, 0);
		matrixPlay(
				new String[] {
						"1011",
						"0111",
						"1101",
						"0220" },
				52, 0, 2);
		matrixPlay(
				new String[] {
						"4341",
						"1110",
						"2410",
						"4000" },
				18, 1, 3);
		// matrixPlay(
		// new String[] {
		// "10110",
		// "01111",
		// "11010",
		// "02000",
		// "11010" },
		// 28);
	}

	public void matrixPlay(String[] str, int value, int xPos, int yPos) {
		char[][] mat = createMatrix(str);
		Board board = new Board(mat);
		MinMaxTree tree = new MinMaxTree(true, board);
		tree.cutOffTime = (long) (System.currentTimeMillis() + 0.0323);
		int[] v = tree.findValuePrune(Integer.MIN_VALUE, Integer.MAX_VALUE, 3, 0);
		Assert.assertEquals(value, v[0]);
		Assert.assertEquals(xPos, v[1]);
		Assert.assertEquals(yPos, v[2]);
	}

	//	public void matrixChildren(String[] str, String[][] matArray) {
	//		char[][] mat = createMatrix(str);
	//		char[][] chMat = createMatrix(matArray);
	//		Board board = new Board(mat);
	//		MinMaxTree tree = new MinMaxTree(true, board);
	//		List<MinMaxTree> children = tree.getChildren();
	//		
	//	}

	private char[][] createMatrix(String[] str) {
		char[][] matrix = new char[str.length][];
		for (int i = 0; i < str.length; i++) {
			matrix[i] = str[i].toCharArray();
		}
		return matrix;
	}
}

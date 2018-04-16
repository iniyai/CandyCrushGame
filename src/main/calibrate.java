package homework2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class calibrate {

	private static final String OUTPUT_FILENAME = "calibration.txt";
	private static PrintWriter out = null;
	private static final int n = 5;
	private static final int p = 5;
	// private static final int depth = 5;
	private static double remainingTime = 4 * 60 * 1000;

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		char[][] boardVal = null;

		try {
			out = new PrintWriter(new FileWriter(OUTPUT_FILENAME));

		} catch (IOException e) {
			e.printStackTrace();

		}

		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 40; j++) {
				boardVal = createMatrix(i, p);
				solve(i, p, remainingTime, boardVal, 3);
			}
		}
		double totalTimePerNode = 0;
		double totalNodeCount = 0;
		for (int bSize = 3; bSize <= 6; bSize++) {
			for (int dep = 2; dep <= 5; dep++) {
				double sum = 0;
				int nodes = 0;
				for (int j = 1; j <= 40; j++) {
					boardVal = createMatrix(bSize, p);
					long[] ans = solve(bSize, p, remainingTime, boardVal, dep);
					sum += (ans[1] / (double) ans[0]); // time per node.
					nodes += (ans[0]);
				}
				double timePerNode = (sum / (double) 40) / bSize / bSize;
				double nodeCount = Math.pow(bSize, dep + 1.25) / ((double) nodes / 40);
				totalTimePerNode += timePerNode;
				totalNodeCount += nodeCount;
				//				System.out.println(
				//						"bsize, depth, timePerNode: " + bSize + " " + dep + " " + timePerNode + " " + nodeCount);
			}
		}
		totalTimePerNode = totalTimePerNode / (4 * 4);
		totalNodeCount = totalNodeCount / (4 * 4);
		out.println(totalTimePerNode);
		out.println(totalNodeCount);
		System.out.println("Calibrate: " + (System.currentTimeMillis() - startTime));
		out.close();
	}

	private static char[][] createMatrix(int n, int p) {
		char[][] ret = new char[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int randNo = (int) (Math.random() * p);
				ret[i][j] = (char) (randNo + '0');
			}
		}
		return ret;
	}

	private static long[] solve(int n, int p, double remainingTime, char[][] boardVal, int depth) {
		Board board = new Board(boardVal);
		MinMaxTree tree = new MinMaxTree(true, board);
		tree.count = 0;
		int[] v = { -1, -1, -1 };
		long startTime = System.currentTimeMillis();
		v = tree.findValuePrune(Integer.MIN_VALUE, Integer.MAX_VALUE, depth, 0);
		int nodesVisited = tree.count;
		StringBuilder outString = new StringBuilder();
		outString.append((char) (v[2] + 'A'));
		outString.append(v[1] + 1);
		// System.out.println(n + " , " + outString.toString());
		long endTime = System.currentTimeMillis();
		return new long[] { nodesVisited, endTime - startTime };

	}

}

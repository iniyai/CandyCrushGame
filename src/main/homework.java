package homework2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class homework {
	private static final String INPUT_FILENAME = "input.txt";
	private static final String CALIBRATION_FILENAME = "calibration.txt";
	private static final String OUTPUT_FILENAME = "output.txt";
	private static PrintWriter out = null;
	private static BufferedReader br = null;
	private static FileReader fr = null;
	private static BufferedReader br2 = null;
	private static FileReader fr2 = null;

	public static void main(String[] args) {

		int n = 0;
		int p = 0;
		double k1 = 0;
		double k2 = 0;
		double remainingTime = 0;
		char[][] boardVal = null;

		try {
			out = new PrintWriter(new FileWriter(OUTPUT_FILENAME));
			fr = new FileReader(INPUT_FILENAME);
			br = new BufferedReader(fr);
			fr2 = new FileReader(CALIBRATION_FILENAME);
			br2 = new BufferedReader(fr2);

			n = Integer.valueOf(br.readLine());
			p = Integer.valueOf(br.readLine());
			remainingTime = Double.valueOf(br.readLine());
			boardVal = new char[n][n];

			k1 = Double.valueOf(br2.readLine());
			k2 = Double.valueOf(br2.readLine());

			for (int i = 0; i < n; i++) {
				String boardLine = br.readLine();
				for (int j = 0; j < n; j++) {
					boardVal[i][j] = boardLine.charAt(j);
				}
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

				if (br2 != null)
					br2.close();

				if (fr2 != null)
					fr2.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
		solve(n, p, remainingTime * 1000, boardVal, k1, k2); // Passing remaining time in milliseconds.
		out.close();
	}

	private static void solve(int n, int p, double budgetTime, char[][] boardVal, double k1, double k2) {
		long startTime = System.currentTimeMillis();
		Board board = new Board(boardVal);
		MinMaxTree tree = new MinMaxTree(true, board);
		// long startTime = System.currentTimeMillis();
		//double dep = Math.log10(remainingTime * 1000 / k1 / n / n) / (k2 * n);

		double dep = (Math.log10(k2 * budgetTime * board.ratioEmpty() / k1) / Math.log10(n)) - 3.25;
		//System.out.println("calc Dep: " + dep);
		//System.out.println("rem Time: " + budgetTime);
		int[] v = { -1, -1, -1 };
		int depth = (int) Math.min(4, Math.floor(dep));
		tree.cutOffTime = (long) (System.currentTimeMillis() + budgetTime / n / p);
		depth = 3;
		//System.out.println("depth: " + depth);
		//System.out.println("time " + (k1 * (Math.pow(n, depth + 3.25)) / k2));
		v = tree.findValuePrune(Integer.MIN_VALUE, Integer.MAX_VALUE, depth, 0);
		// depth++;
		// }
		StringBuilder outString = new StringBuilder();
		outString.append((char) (v[2] + 'A'));
		outString.append(v[1] + 1);
		// System.out.println(outString.toString());
		out.println(outString.toString());
		// System.out.println(v[1] + " " + v[2]);
		Board ret = tree.board.makeMove(v[1], v[2]);
		// System.out.println("************");
		ret.printToFile(out);
		System.out.println(System.currentTimeMillis() - startTime);
	}

}

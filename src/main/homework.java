package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class homework {
	private static final String INPUT_FILENAME = "input.txt";
	private static final String OUTPUT_FILENAME = "output.txt";
	private static PrintWriter out = null;
	private static BufferedReader br = null;
	private static FileReader fr = null;

	public static void main(String[] args) {

		byte n = 0;
		byte p = 0;
		double remainingTime = 0;
		char[][] boardVal = null;

		try {
			out = new PrintWriter(new FileWriter(OUTPUT_FILENAME));
			fr = new FileReader(INPUT_FILENAME);
			br = new BufferedReader(fr);

			n = Byte.valueOf(br.readLine());
			p = Byte.valueOf(br.readLine());
			remainingTime = Double.valueOf(br.readLine());
			boardVal = new char[n][n];

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

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
		solve(n, p, remainingTime, boardVal);
		out.close();
	}

	private static void solve(byte n, byte p, double remainingTime, char[][] boardVal) {
		Board board = new Board(n, boardVal);
		board.solve();
		board.print(out);
	}

}

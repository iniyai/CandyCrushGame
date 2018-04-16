package homework2;

public class Pair {
	public int row;
	public int column;

	public Pair(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public boolean isEqual(int r, int c) {
		return ((r == row) && (c == column));
	}

	public boolean isEqual(Pair p) {
		return ((p.row == row) && (p.column == column));
	}

	public void print() {
		System.out.println(row + " $ " + column);
	}
}

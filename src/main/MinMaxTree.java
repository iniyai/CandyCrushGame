package homework2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinMaxTree implements Comparable<MinMaxTree> {

	private boolean isMaxNode = true;
	Board board;
	private Pair parent;
	public static int count = 0;
	public static long cutOffTime = 0;

	public MinMaxTree(boolean isMaxNode, Board board, Pair parent) {
		this.isMaxNode = isMaxNode;
		this.board = board;
		this.parent = parent;
	}

	public MinMaxTree(boolean isMaxNode, Board board) {
		this(isMaxNode, board, null);
	}

	public List<MinMaxTree> getChildren() {
		List<Pair> pairs = board.uniqueMoves();
		List<MinMaxTree> ret = new ArrayList<MinMaxTree>();
		for (Pair pair : pairs) {
			Board b = board.makeMove(pair.getRow(), pair.getColumn());
			ret.add(new MinMaxTree(!isMaxNode, b, pair));
		}
		return ret;
	}

	public List<MinMaxTree> getChildrenSort() {
		List<Pair> pairs = board.uniqueMoves();
		List<MinMaxTree> ret = new ArrayList<MinMaxTree>();
		for (Pair pair : pairs) {
			Board b = board.makeMove(pair.getRow(), pair.getColumn());
			ret.add(new MinMaxTree(!isMaxNode, b, pair));
		}
		Collections.sort(ret);
		if (isMaxNode) {
			Collections.reverse(ret);
		}
		return ret;
	}

	public int[] findValuePrune(int alpha, int beta, int depth, int score) {
		count++;
		List<MinMaxTree> children = getChildrenSort();
		// System.out.println(children.size());
		int xPos = -1, yPos = -1;
		int value = board.getPoints();
		int result = 0;
		if (children != null && !children.isEmpty() && (depth > 0 || System.currentTimeMillis() < cutOffTime)) {
			if (isMaxNode) {
				int v = Integer.MIN_VALUE;
				for (MinMaxTree child : children) {
					int newVal = child.findValuePrune(alpha, beta, depth - 1, score - value * value)[0];
					// v = Math.max(v, newVal);
					if (newVal > v) {
						v = newVal;
						xPos = child.parent.getRow();
						yPos = child.parent.getColumn();
					}
					if (v >= beta) {
						break;
					}
					alpha = Math.max(alpha, v);
				}
				result = v;
			} else {
				int v = Integer.MAX_VALUE;
				for (MinMaxTree child : children) {
					int newVal = child.findValuePrune(alpha, beta, depth - 1, score + value * value)[0];
					// v = Math.min(v, newVal);
					if (newVal < v) {
						v = newVal;
						xPos = child.parent.getRow();
						yPos = child.parent.getColumn();
					}
					if (v <= alpha) {
						break;
					}
					beta = Math.min(beta, v);
				}
				result = v;
			}
		} else {
			result = score + (isMaxNode ? -(value * value) : value * value);
		}
		// System.out.println("********************");
		// System.out.println("Alpha: " + alpha + " Beta: " + beta);
		// System.out.println("Max ? " + isMaxNode);
		// System.out.println("Depth " + depth);
		// board.printConsole();
		// System.out.println("value at node " + (!isMaxNode ? value * value :
		// -(value * value)));
		// System.out.println("result at node " + result);
		// System.out.println("********************");
		return new int[] { result, xPos, yPos };
	}

	@Override
	public int compareTo(MinMaxTree o) {
		return 0;
	}

	// public static void main(String[] args) {
	// MinMaxTree tree = new MinMaxTree(true, new Board);
	// }

}

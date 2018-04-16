package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.Board;

public class BoardTest {

	Board board;

	@Before
	public void setup() {
		char[][] matrix = { { '1', '0', '1', '1' }, { '0', '1', '1', '0' }, { '1', '0', '1', '1' },
				{ '0', '1', '1', '0' } };
		board = new Board((byte) 4, matrix);
	}

	@Test
	public void gravityTest() {
		char[][] testMatrix = { { '1', '0', '1', '1' }, { '0', '1', '1', '0' }, { '1', '0', '1', '1' },
				{ '0', '1', '1', '0' } };
		board.gravity();
		Assert.assertEquals(board.getMatrix(), testMatrix);
	}
}

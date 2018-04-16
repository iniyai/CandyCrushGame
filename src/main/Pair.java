package main;

public class Pair {
	private char value;
	private byte count;

	public Pair(char value, byte count) {
		this.value = value;
		this.count = count;
	}

	public Pair() {
		this.value = 0;
		this.count = 0;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public byte getCount() {
		return count;
	}

	public void setCount(byte count) {
		this.count = count;
	}

}

package com.comScore.ByteParserUtil;
// NEED

/**
 * Class to parse a row into columns.<br>
 * Row can be represented as String or UTF-8/UTF-16 bytes. A row represented as
 * bytes can only have a character delimiter as regex cannot be applied on
 * bytes.
 */
public abstract class RowParser {

	protected static final String TOKEN_GET_EXCP_MSG = "Token requested is out of bounds.";
	protected static final String PARSING_EXCP_MSG = "Number of columns parsed in current row are less than expected.";
	protected static final String EMPTY_COLUMN_EXCP_MSG = "Expecting data but the column is empty.";
	protected static final int DEFAULT_COLUMNS_SIZE = 20;

	protected int[] idxArray;
	protected int tokensCount;
	protected int columnsExpected;

	public void parse(String row) {
		// to be over-ridden by child class.
	}

	public void parse(byte[] row, int length) {
		// to be over-ridden by child class.
	}

	public int getTokensCount() {
		return tokensCount;
	}

	public abstract char getCharToken(int index);

	public abstract String getStringToken(int index);

	public abstract int getIntToken(int index);

	public abstract long getLongToken(int index);

	public abstract float getFloatToken(int index);

	public abstract double getDoubleToken(int index);

	public abstract byte[] getBytesToken(int index);

	protected boolean isTokenInaccessible(int index) {
		return index > tokensCount - 1 || index < 0; // index is zero based
	}

	protected boolean isContentEmpty(int index) {
		return idxArray[index + 1] - idxArray[index] <= 1;
	}
}

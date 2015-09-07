package com.comScore.ByteParserUtil;
// NEED

import java.util.Arrays;

/**
 * Class to support bytes parsing. The Class handles '\n' byte.
 */
public class ByteParser extends RowParser {

	private static final byte ENDLINE = '\n';
	private static final String EMPTY_STRING = "";

	private byte delim;
	private byte[] byteRow;

	ByteParser(RowParserBuilder builder) {
		delim = builder.byteDelim;
		idxArray = new int[builder.columns + 1];
		columnsExpected = builder.columns;
	}

	@Override
	public void parse(byte[] row, int length) {
		tokensCount = 1;
		byteRow = row;
		int j = 0;
		idxArray[j++] = 0;
		for (int i = 0; tokensCount < columnsExpected; i++) {
			try {
				if (byteRow[i] == delim) {
					idxArray[j++] = i + 1;
					tokensCount++;
				}
			} catch (IndexOutOfBoundsException ioobe) {
				ioobe.printStackTrace();
				throw new RuntimeException(PARSING_EXCP_MSG + " Expected : "
						+ (columnsExpected) + ". Parsed : " + tokensCount + ".");
			}
		}
		idxArray[j] = row[length - 1] == ENDLINE ? length : length + 1;
	}

	/**
	 * Method to return char column value at the given index.
	 * 
	 * @param index
	 *            Column index(zero based).
	 * @return Column value as char.
	 */
	public char getCharToken(int index) {
		if (isTokenInaccessible(index))
			throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
		if (isContentEmpty(index))
			throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
		return (char) byteRow[idxArray[index]];
	}

	/**
	 * Method to return string column value at the given index.
	 * 
	 * @param index
	 *            Column index(zero based).
	 * @return Column value as string.
	 */
	public String getStringToken(int index) {
		if (isTokenInaccessible(index))
			throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
		if (isContentEmpty(index))
			return EMPTY_STRING;
		int offset = idxArray[index];
		int len = idxArray[index + 1] - 1 - offset;
		char[] tempAr = new char[len];
		for (int i = offset; i < offset + len; i++)
			tempAr[i - offset] = (char) byteRow[i];
		return new String(tempAr);
	}

	/**
	 * Method to return int column value at the given index.
	 * 
	 * @param index
	 *            Column index(zero based).
	 * @return Column value as int.
	 */
	public int getIntToken(int index) {
		if (isTokenInaccessible(index))
			throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
		if (isContentEmpty(index))
			throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
		return AlphaToNumeric.atoi(byteRow, idxArray[index],
				idxArray[index + 1] - 1);
	}

	/**
	 * Method to return long column value at the given index.
	 * 
	 * @param index
	 *            Column index(zero based).
	 * @return Column value as long.
	 */
	public long getLongToken(int index) {
		if (isTokenInaccessible(index))
			throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
		if (isContentEmpty(index))
			throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
		return AlphaToNumeric.atol(byteRow, idxArray[index],
				idxArray[index + 1] - 1);
	}

	/**
	 * Method to return float column value at the given index.
	 * 
	 * @param index
	 *            Column index(zero based).
	 * @return Column value as float.
	 */
	public float getFloatToken(int index) {
		if (isTokenInaccessible(index))
			throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
		if (isContentEmpty(index))
			throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
		return AlphaToNumeric.atof(byteRow, idxArray[index],
				idxArray[index + 1] - 1);
	}

	/**
	 * Method to return double column value at the given index.
	 * 
	 * @param index
	 *            Column index(zero based).
	 * @return Column value as double.
	 */
	public double getDoubleToken(int index) {
		if (isTokenInaccessible(index))
			throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
		if (isContentEmpty(index))
			throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
		return AlphaToNumeric.atod(byteRow, idxArray[index],
				idxArray[index + 1] - 1);
	}

	public byte[] getBytesToken(int index) {
		if (isTokenInaccessible(index))
			throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
		if (isContentEmpty(index))
			throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
		return Arrays.copyOfRange(byteRow, idxArray[index],
				idxArray[index + 1] - 1);
	}
}

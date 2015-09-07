package com.comScore.ByteParserUtil;
/**
 * Class to handle char delimited strings parsing.
 */
public class CharDelimStringParser extends RowParser {

    private char delim;
    private String stringRow;

    CharDelimStringParser(RowParserBuilder builder) {
        this.delim = builder.charDelim;
        this.idxArray = new int[builder.columns + 1];
        this.columnsExpected = builder.columns;
    }

    public void parse(String row) {
        tokensCount = 1;
        stringRow = row;

        int j = 0;
        idxArray[j++] = 0;
        for(int i = 0; tokensCount < columnsExpected; i++) {
            try {
                if (stringRow.charAt(i) == delim) {
                    idxArray[j++] = i + 1;
                    tokensCount++;
                }
            } catch (IndexOutOfBoundsException ioobe) {
                System.err.println(ioobe.getMessage());
                ioobe.printStackTrace();
                throw new RuntimeException(PARSING_EXCP_MSG + " Expected : " + (columnsExpected) + ". Parsed : " + tokensCount + ".");
            }
        }

        idxArray[j] = stringRow.length() + 1;
    }

    /**
     * Method to return char column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as char.
     */
    public char getCharToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return stringRow.charAt(idxArray[index]);
    }

    /**
     * Method to return string column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as string.
     */
    public String getStringToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        return stringRow.substring(idxArray[index], idxArray[index + 1] - 1);
    }

    /**
     * Method to return int column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as int.
     */
    public int getIntToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atoi(stringRow, idxArray[index], idxArray[index + 1] - 1);
    }

    /**
     * Method to return long column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as long.
     */
    public long getLongToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atol(stringRow, idxArray[index], idxArray[index + 1] - 1);
    }

    /**
     * Method to return float column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as float.
     */
    public float getFloatToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atof(stringRow, idxArray[index], idxArray[index + 1] - 1);
    }

    /**
     * Method to return double column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as double.
     */
    public double getDoubleToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atod(stringRow, idxArray[index], idxArray[index + 1] - 1);
    }

    public byte[] getBytesToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        int currIdx = idxArray[index];
        int nextIdx = idxArray[index + 1];
        byte[] bytes = new byte[nextIdx - currIdx - 1];
        for (int i = 0; i < bytes.length; i++)
            bytes[i] = (byte)stringRow.charAt(currIdx + i);
        return bytes;
    }


}

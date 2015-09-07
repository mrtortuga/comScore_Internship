package com.comScore.ByteParserUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to handle regex pattern delimited string parsing.
 */
public class RegexDelimStringParser extends RowParser {

    private Pattern delim;
    private String stringRow;

    RegexDelimStringParser(RowParserBuilder builder) {
        this.delim = Pattern.compile(builder.strDelim);
        this.idxArray = new int[builder.columns * 2]; //because the list stores both start and end indexes.
        columnsExpected = builder.columns;
    }

    public void parse(String row) {
        tokensCount = 1;
        stringRow = row;

        int j = 0;
        idxArray[j++] = 0;
        Matcher matcher = delim.matcher(stringRow);
        while(tokensCount < columnsExpected && matcher.find()) {
            idxArray[j++] = matcher.start();
            idxArray[j++] = matcher.end();
            tokensCount++;
        }

        if (tokensCount < idxArray.length / 2)
            throw new RuntimeException(PARSING_EXCP_MSG + " Expected : " + columnsExpected + ". Parsed : " + tokensCount + ".");

        idxArray[j] = stringRow.length();
    }

    /**
     * Method to return char column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as char.
     */
    public char getCharToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        index = index * 2;
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
        index = index * 2;
        return stringRow.substring(idxArray[index], idxArray[index + 1]);
    }

    /**
     * Method to return int column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as int.
     */
    public int getIntToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        index = index * 2;
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atoi(stringRow, idxArray[index], idxArray[index + 1]);
    }

    /**
     * Method to return long column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as long.
     */
    public long getLongToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        index = index * 2;
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atol(stringRow, idxArray[index], idxArray[index + 1]);
    }

    /**
     * Method to return float column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as float.
     */
    public float getFloatToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        index = index * 2;
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atof(stringRow, idxArray[index], idxArray[index + 1]);
    }

    /**
     * Method to return double column value at the given index.
     * @param index Column index(zero based).
     * @return Column value as double.
     */
    public double getDoubleToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        index = index * 2;
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return AlphaToNumeric.atod(stringRow, idxArray[index], idxArray[index + 1]);
    }

    @Override
    public byte[] getBytesToken(int index) {
        if (isTokenInaccessible(index))
            throw new IndexOutOfBoundsException(TOKEN_GET_EXCP_MSG);
        index = index * 2;
        if (isContentEmpty(index))
            throw new RuntimeException(EMPTY_COLUMN_EXCP_MSG);
        return stringRow.substring(idxArray[index], idxArray[index + 1]).getBytes();
    }

    protected boolean isContentEmpty(int index) {
        return idxArray[index] == idxArray[index + 1];
    }
}

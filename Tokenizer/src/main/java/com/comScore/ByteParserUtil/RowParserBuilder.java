package com.comScore.ByteParserUtil;
/**
 * Builder to help build a RowParser instance.
 */
public class RowParserBuilder {

    int columns;
    ParsingData parsingData;
    char charDelim;
    String strDelim;
    byte byteDelim;

    public static RowParserBuilder newBuilder() {
        return new RowParserBuilder();
    }

    private RowParserBuilder() {
        columns = RowParser.DEFAULT_COLUMNS_SIZE;
    }

    public RowParserBuilder forParsing(ParsingData parsingData) {
        this.parsingData = parsingData;
        return this;
    }

    public RowParserBuilder withColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public RowParserBuilder withDelim(char charDelim) {
        if (parsingData == ParsingData.BYTES)
            byteDelim = (byte)charDelim;
        else
            this.charDelim = charDelim;
        return this;
    }

    public RowParserBuilder withDelim(byte byteDelim) {
        this.byteDelim = byteDelim;
        return this;
    }

    public RowParserBuilder withDelim(String strDelim) {
        if (parsingData == ParsingData.BYTES)
            throw new RuntimeException("Building parser for bytes parsing but the delimiter is of string type");
        this.strDelim = strDelim;
        return this;
    }

    public RowParser build() {
        return parsingData == ParsingData.BYTES ?
                new ByteParser(this)
                :
                strDelim == null ?
                        new CharDelimStringParser(this)
                        :
                        new RegexDelimStringParser(this);
    }
}

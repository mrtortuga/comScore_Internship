package com.comScore.ByteParserUtil;

/**
 * Class with static routines to help parse numerics from strings/byte arrays.
 * <br>Byte arrays should have UTF-16/UTF-8 representation.
 */
public class AlphaToNumeric {

    private static final int INT_DIGIT_LENGTH = 10;
    private static final int MINUS = '-';
    private static final int PLUS = '+';
    private static final int ZERO = '0';
    private static final int ONE = '1';
    private static final int TWO = '2';
    private static final int THREE = '3';
    private static final int FOUR = '4';
    private static final int FIVE = '5';
    private static final int SIX = '6';
    private static final int SEVEN = '7';
    private static final int EIGHT = '8';
    private static final int NINE = '9';
    private static final int DOT = '.';
    private static final int NEGATIVE_SIGN = -1;
    private static final int POSITIVE_SIGN = 1;
    private static final int BASE_DECIMAL = 10;

    /**
     * Parse long from ascii bytes
     * @param bytes byte array that contains the ascii representation
     * @param start start position of the long in the array
     * @param end end position of the long in the array
     * @return parsed long from the byte array
     */
    public static long atol(byte[] bytes, int start, int end) {
        int length = end - start;
        int limit = start + length;
        int character = bytes[start];
        int sign = character == MINUS ? NEGATIVE_SIGN : POSITIVE_SIGN;

        if (sign == NEGATIVE_SIGN || character == PLUS) {
            start++;
        }

        long value;
        character = bytes[start];
        if (character >= ZERO && character <= NINE) {
            value = character - ZERO;
            start++;
        } else
            throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");

        while (start < limit) {
            character = bytes[start];
            switch(character) {
                case ZERO:
                case ONE:
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                case SEVEN:
                case EIGHT:
                case NINE:
                    value = value * BASE_DECIMAL + (character - ZERO);
                    break;
                default:
                    throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");
            }
            start++;
        }

        return value * sign;
    }


    /**
     * Parse integer from ascii bytes
     * @param bytes byte array that contains the ascii representation
     * @param start start position of the integer in the array
     * @param end end position of the integer in the array
     * @return parsed integer from the byte array
     */
    public static int atoi(byte[] bytes, int start, int end) {
        return (int)atol(bytes, start, end);
    }


    /**
     * Parse double from ascii bytes
     * @param bytes byte array that contains the ascii representation
     * @param start start position of the double in the array
     * @param end end position of the double in the array
     * @return parsed double from the byte array
     */
    public static double atod(byte[] bytes, int start, int end) {
        double divider = POSITIVE_SIGN;
        int length = end - start;
        int limit = start + length;
        int character = bytes[start];
        int sign = character == MINUS ? NEGATIVE_SIGN : POSITIVE_SIGN;
        double significand = 0;

        if (sign == NEGATIVE_SIGN || character == PLUS) {
            start++;
        }

        if (character != DOT) { //start char is not '.'
            character = bytes[start];
            if (character >= ZERO && character <= NINE) {
                significand = character - ZERO;
                start++;
            } else
                throw new NumberFormatException("Non numeric character '" + (char) character + "' cannot be parsed.");
        }

        int multiplier = POSITIVE_SIGN;
        while (start < limit) {
            character = bytes[start];
            switch(character) {
                case DOT:
                    multiplier = BASE_DECIMAL;
                    break;
                case DOT + POSITIVE_SIGN:
                    //empty case for switch optimization
                    throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");
                case ZERO:
                case ONE:
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                case SEVEN:
                case EIGHT:
                case NINE:
                    significand = significand * BASE_DECIMAL + (character - ZERO);
                    divider *= multiplier;
                    break;
                default:
                    throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");
            }
            start++;
        }

        return (significand * sign) / divider;
    }

    /**
     * Parse floats from ascii bytes
     * @param bytes byte array that contains the ascii representation
     * @param start start position of the float in the array
     * @param end end position of the float in the array
     * @return parsed float from the byte array
     */
    public static float atof(byte[] bytes, int start, int end) {
        return (float)atod(bytes, start, end);
    }

    /**
     * Parse long from string
     * @param numericStr string containing the long to be parsed
     * @param start start position of the long in the string
     * @param end end position of the long in the string
     * @return parsed long from the string
     */
    public static long atol(String numericStr, int start, int end) {
        int length = end - start;
        int limit = start + length;
        int character = numericStr.charAt(start);
        int sign = character == MINUS ? NEGATIVE_SIGN : POSITIVE_SIGN;

        if (sign == NEGATIVE_SIGN || character == PLUS) {
            start++;
        }

        long value;
        character = numericStr.charAt(start);
        if (character >= ZERO && character <= NINE) {
            value = character - ZERO;
            start++;
        } else
            throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");

        while (start < limit) {
            character = numericStr.charAt(start);
            switch(character) {
                case ZERO:
                case ONE:
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                case SEVEN:
                case EIGHT:
                case NINE:
                    value = value * BASE_DECIMAL + (character - ZERO);
                    break;
                default:
                    throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");
            }
            start++;
        }

        return value * sign;
    }

    /**
     * Parse integer from string
     * @param numericStr string containing the integer to be parsed
     * @param start start position of the integer in the string
     * @param end end position of the integer in the string
     * @return parsed integer from the string
     */
    public static int atoi(String numericStr, int start, int end) {
        if (end - start > INT_DIGIT_LENGTH)
            throw new NumberFormatException("Too Long to be parsed as an integer : " + numericStr);
        long integer = atol(numericStr, start, end);
        if (integer > Integer.MAX_VALUE || integer < Integer.MIN_VALUE)
            throw new NumberFormatException("Cannot be parsed as an integer : " + numericStr);
        return (int)integer;
    }

    /**
     * Parse double from string
     * @param numericStr string that contains the double
     * @param start start position of the double in the string
     * @param end end position of the double in the string
     * @return parsed double from the byte array
     */
    public static double atod(String numericStr, int start, int end) {
        double divider = POSITIVE_SIGN;
        int length = end - start;
        int limit = start + length;
        int character = numericStr.charAt(start);
        int sign = character == MINUS ? NEGATIVE_SIGN : POSITIVE_SIGN;
        double significand = 0;

        if (sign == NEGATIVE_SIGN || character == PLUS) {
            start++;
        }

        if (character != DOT) { //start char is not '.'
            character = numericStr.charAt(start);
            if (character >= ZERO && character <= NINE) {
                significand = character - ZERO;
                start++;
            } else
                throw new NumberFormatException("Non numeric character '" + (char) character + "' cannot be parsed.");
        }

        int multiplier = POSITIVE_SIGN;
        while (start < limit) {
            character = numericStr.charAt(start);
            switch(character) {
                case DOT:
                    multiplier = BASE_DECIMAL;
                    break;
                case DOT + POSITIVE_SIGN:
                    throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");
                case ZERO:
                case ONE:
                case TWO:
                case THREE:
                case FOUR:
                case FIVE:
                case SIX:
                case SEVEN:
                case EIGHT:
                case NINE:
                    significand = significand * BASE_DECIMAL + (character - ZERO);
                    divider *= multiplier;
                    break;
                default:
                    throw new NumberFormatException("Non numeric character '" + (char)character + "' cannot be parsed.");
            }
            start++;
        }

        return (significand * sign) / divider;
    }

    /**
     * Parse float from string
     * @param numericStr string that contains the float
     * @param start start position of the float in the string
     * @param end end position of the float in the string
     * @return parsed float from the byte array
     */
    public static float atof(String numericStr, int start, int end) {
        return (float)atod(numericStr, start, end);
    }
}

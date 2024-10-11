package ru.corvinella;

import ru.corvinella.parser.Parser;
import ru.corvinella.parser.ParserIllegalTokenValueException;
import ru.corvinella.parser.ParserUnknownEntityException;

public class App {
    /**
     * Main method for testing.
     * @param args
     * @throws ParserIllegalTokenValueException 
     * @throws ParserUnknownEntityException 
     */
    public static void main(String[] args) throws ParserUnknownEntityException, ParserIllegalTokenValueException {
        // nothing here...
        Parser parser = new Parser("log(2,4)");
        parser.parse();
    }
}

package gov.va.transpiler.jinja.printing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specifies a part of a file on the file system.
 */
public class Locator {

    private final int startLine;
    private final int startCharacter;
    private final int endLine;
    private final int endCharacter;

    /**
     * Creates a {@link Locator} from the locator string of a node in the CQL AST.
     * 
     * @param locatorString Original locator string.
     * @return Created {@link Locator}.
     */
    public static Locator fromString(String locatorString) {
        // locator strings come in the format firstline:firstcharacter-lastline:lastcharacter
        Pattern pattern = Pattern.compile("(\\d+):(\\d+)-(\\d+):(\\d+)");
        Matcher matcher = pattern.matcher(locatorString);
        if (matcher.find()) {
          return new Locator(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        } else {
          return null;
        }

    }

    /**
     * @param startLine Line where the specified section of a file begins.
     * @param startCharacter Character where the specified section of a file begins.
     * @param endLine Line where the specified section of a file ends.
     * @param endCharacter Character where the specified section of a file ends.
     */
    public Locator(int startLine, int startCharacter, int endLine, int endCharacter) {
        this.startLine = startLine;
        this.startCharacter = startCharacter;
        this.endLine = endLine;
        this.endCharacter = endCharacter;
    }

    /**
     * @return Line where the specified section of a file begins.
     */
    public int getStartLine() {
        return startLine;
    }

    /**
     * @return Character where the specified section of a file begins.
     */
    public int getStartCharacter() {
        return startCharacter;
    }

    /**
     * @return Line where the specified section of a file ends.
     */
    public int getEndLine() {
        return endLine;
    }

    /**
     * @return Character where the specified section of a file ends.
     */
    public int getEndCharacter() {
        return endCharacter;
    }
}

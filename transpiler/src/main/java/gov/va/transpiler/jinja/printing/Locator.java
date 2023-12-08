package gov.va.transpiler.jinja.printing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Locator {

    private final int startLine;
    private final int startCharacter;
    private final int endLine;
    private final int endCharacter;

    public static Locator fromString(String locatorString) {
        Pattern pattern = Pattern.compile("(\\d+):(\\d+)-(\\d+):(\\d+)");
        Matcher matcher = pattern.matcher(locatorString);
        if (matcher.find()) {
          return new Locator(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
        } else {
          return null;
        }

    }

    public Locator(int startLine, int startCharacter, int endLine, int endCharacter) {
        this.startLine = startLine;
        this.startCharacter = startCharacter;
        this.endLine = endLine;
        this.endCharacter = endCharacter;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getStartCharacter() {
        return startCharacter;
    }

    public int getEndLine() {
        return endLine;
    }

    public int getEndCharacter() {
        return endCharacter;
    }

    public List<String> getLinesFromFile(String absoluteFilePath) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(absoluteFilePath))) {
            return reader.lines().skip(startLine - 1).limit(endLine - startLine + 1).collect(Collectors.toList());
        }
    }
}

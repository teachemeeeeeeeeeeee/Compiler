package Compiler_TPL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lexicalAnalysis {
    private String code;
    public lexicalAnalysis(String code) {
        this.code = code;
    }
    public String analyze() {
        String dataTypeRegex = "\\b(int|double|char|String)\\b";
        String assignmentOperatorRegex = "=";
        String delimiterRegex = ";";
        String valueRegex = "(\"[^\"]*\"|'[^']*'|\\d+(\\.\\d+)?)";
        String identifierRegex = "\\b[a-zA-Z_]\\w*\\b";

        String combinedRegex = String.format("(%s)|(%s)|(%s)|(%s)|(%s)", dataTypeRegex, assignmentOperatorRegex,
                delimiterRegex, valueRegex, identifierRegex);
        Pattern pattern = Pattern.compile(combinedRegex);

        StringBuilder result = new StringBuilder();

        Matcher matcher = pattern.matcher(code);
        int lastMatchEnd = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            String unmatchedText = code.substring(lastMatchEnd, start).trim();
            if (!unmatchedText.isEmpty()) {
                result.append("<error> ");
            }

            String match = matcher.group();

            if (match.matches(dataTypeRegex)) {
                result.append("<data_type> ");
            } else if (match.equals("=")) {
                result.append("<assignment_operator> ");
            } else if (match.equals(";")) {
                result.append("<delimiter> ");
            } else if (match.matches(valueRegex)) {
                result.append("<value> ");
            } else if (match.matches(identifierRegex)) {
                result.append("<identifier> ");
            }

            lastMatchEnd = end;
        }

        String remainingText = code.substring(lastMatchEnd).trim();
        if (!remainingText.isEmpty()) {
            result.append("<error> ");
        }

        return result.toString().trim();

    }


}

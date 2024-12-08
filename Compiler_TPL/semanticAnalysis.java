package Compiler_TPL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class semanticAnalysis {
    private String code;

    public semanticAnalysis(String code) {
        this.code = code;
    }

    public boolean semantic() {
        String intRegex = "\\bint\\b\\s+\\w+\\s*=\\s*\\d+\\s*;";
        String doubleRegex = "\\bdouble\\b\\s+\\w+\\s*=\\s*\\d+(\\.\\d+)?\\s*;";
        String charRegex = "\\bchar\\b\\s+\\w+\\s*=\\s*'[^']'\\s*;";
        String stringRegex = "\\bString\\b\\s+\\w+\\s*=\\s*\"[^\"]*\"\\s*;";

        String combinedRegex = String.format("(%s|%s|%s|%s)", intRegex, doubleRegex, charRegex, stringRegex);

        Pattern pattern = Pattern.compile(combinedRegex);
        Matcher matcher = pattern.matcher(code);

        while (matcher.find()) {
            return true;
        }

        return false;
    }

}

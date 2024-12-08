package Compiler_TPL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class syntaxAnalysis {
    public static boolean syntaxAnalysis(String lexicalResult) {
        if (lexicalResult.contains("<error>")) {
            return false;
        }

        String variableDeclarationWithAssignment = "<data_type> <identifier> <assignment_operator> <value> <delimiter>";
        String variableDeclaration = "<data_type> <identifier> <delimiter>";
        String combinedRegex = String.format("(%s|%s)", variableDeclarationWithAssignment, variableDeclaration);

        Pattern pattern = Pattern.compile(combinedRegex);
        Matcher matcher = pattern.matcher(lexicalResult);

        return matcher.find();
    }
}

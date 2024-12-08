package Compiler_TPL;

import javax.swing.*;


public class compilerApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Compiler");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel codeLabel = new JLabel("Code:");
        codeLabel.setBounds(50, 20, 100, 30);
        frame.add(codeLabel);

        JTextArea codeArea = new JTextArea();
        codeArea.setBounds(50, 50, 700, 100);
        frame.add(codeArea);

        JButton lexicalButton = new JButton("Lexical Analysis");
        lexicalButton.setBounds(50, 170, 150, 30);
        frame.add(lexicalButton);

        JButton syntaxButton = new JButton("Syntax Analysis");
        syntaxButton.setBounds(220, 170, 150, 30);
        frame.add(syntaxButton);

        JButton semanticButton = new JButton("Semantic Analysis");
        semanticButton.setBounds(390, 170, 150, 30);
        frame.add(semanticButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(560, 170, 150, 30);
        frame.add(clearButton);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(50, 220, 700, 100);
        resultArea.setEditable(false);
        frame.add(resultArea);

        lexicalButton.addActionListener(e -> {
            String code = codeArea.getText();
            Compiler_TPL.lexicalAnalysis lexical = new Compiler_TPL.lexicalAnalysis(code);
            String result = lexical.analyze();
            resultArea.setText("Lexical Analysis Result:\n" + result);
        });

        syntaxButton.addActionListener(e -> {
            String code = codeArea.getText();
            Compiler_TPL.lexicalAnalysis lexical = new Compiler_TPL.lexicalAnalysis(code);
            String lexicalResult = lexical.analyze();
            boolean isValidSyntax = Compiler_TPL.syntaxAnalysis.syntaxAnalysis(lexicalResult);
            resultArea.setText("Syntax Analysis Result:\n" + (isValidSyntax ? "Valid Syntax" : "Syntax Error"));
        });

        semanticButton.addActionListener(e -> {
            String code = codeArea.getText();

            Compiler_TPL.lexicalAnalysis lexical = new Compiler_TPL.lexicalAnalysis(code);
            String lexicalResult = lexical.analyze();
            if (lexicalResult.contains("<error>")) {
                resultArea.setText("Semantic Analysis Result:\nLexical Errors Found - Cannot Analyze Semantics.");
                return;
            }

            boolean isValidSyntax = Compiler_TPL.syntaxAnalysis.syntaxAnalysis(lexicalResult);
            if (!isValidSyntax) {
                resultArea.setText("Semantic Analysis Result:\nSyntax Errors Found - Cannot Analyze Semantics.");
                return;
            }

            Compiler_TPL.semanticAnalysis semantic = new Compiler_TPL.semanticAnalysis(code);
            boolean isValidSemantic = semantic.semantic();
            resultArea.setText("Semantic Analysis Result:\n" + (isValidSemantic ? "Valid Semantics" : "Semantic Error"));
        });


        clearButton.addActionListener(e -> {
            codeArea.setText("");
            resultArea.setText("");
        });

        frame.setVisible(true);
    }
}

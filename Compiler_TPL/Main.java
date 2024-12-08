package Compiler_TPL;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static Compiler_TPL.syntaxAnalysis.syntaxAnalysis;

public class Main {
    static lexicalAnalysis lexicalAnalyzer;
    static String lexicalResult;
    public static void main(String[] args) {

        JFrame frame = new JFrame("Final Project");
        frame.setSize(800, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.PINK);
        frame.setLocationRelativeTo(null);


        JButton openFile = new JButton("Open File");
        openFile.setBounds(50, 50, 150, 30);
        frame.add(openFile);
        openFile.setBackground(new Color(203, 175, 225));
        frame.add(openFile);

        JButton lexical = new JButton("Lexical Analysis");
        lexical.setBounds(50, 100, 150, 30);
        frame.add(lexical);
        lexical.setBackground(new Color(203, 175, 225));
        frame.add(lexical);

        JButton syntax = new JButton("Syntax Analysis");
        syntax.setBounds(50, 150, 150, 30);
        frame.add(syntax);
        syntax.setBackground(new Color(203, 175, 225));
        frame.add(syntax);

        JButton semantic = new JButton("Semantic Analysis");
        semantic.setBounds(50, 200, 150, 30);
        frame.add(semantic);
        semantic.setBackground(new Color(203, 175, 225));
        frame.add(semantic);

        JButton clear = new JButton("Clear");
        clear.setBounds(50, 250, 150, 30);
        frame.add(clear);
        clear.setBackground(new Color(203, 175, 225));
        frame.add(clear);

        JTextField result = new JTextField("Result: ");
        result.setBounds(250, 50, 500, 30);
        frame.add(result);


        JTextArea code = new JTextArea();
        code.setBounds(250, 120, 500, 200);
        frame.add(code);

        lexical.setEnabled(false);
        syntax.setEnabled(false);
        semantic.setEnabled(false);


        openFile.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            int file = fileChooser.showOpenDialog(null);
            if (file == JFileChooser.APPROVE_OPTION) {
                try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                    StringBuilder content = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                        lexical.setEnabled(true);
                    }
                    code.setText(content.toString());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        lexical.addActionListener(e -> {
            String codeText = code.getText();
            lexicalAnalyzer = new lexicalAnalysis(codeText);
            lexicalResult = lexicalAnalyzer.analyze();
            result.setText(lexicalResult);

            if (lexicalResult.contains("<error>")) {
                result.setText("Lexical analysis failed. Unexpected token. ");
                JOptionPane.showMessageDialog(frame, "Lexical analysis failed! Syntax button is disabled.", "Analysis Result", JOptionPane.INFORMATION_MESSAGE);

            } else {
                result.setText(lexicalResult);
                syntax.setEnabled(true);
            }
        });



        syntax.addActionListener(e -> {
            String codeText = code.getText();
            lexicalAnalyzer = new lexicalAnalysis(codeText);
            lexicalResult = lexicalAnalyzer.analyze();

            if (syntaxAnalysis(lexicalResult)) {
                result.setText("Syntax analysis passed.");
                semantic.setEnabled(true);

            } else {
                result.setText("Syntax analysis failed. Invalid syntax.");
                JOptionPane.showMessageDialog(frame, "Syntax analysis failed! Semantic button is disabled.", "Analysis Result", JOptionPane.INFORMATION_MESSAGE);

            }
        });




        semantic.addActionListener(e -> {
            String codeText = code.getText();
            lexicalAnalyzer = new lexicalAnalysis(codeText);
            lexicalResult = lexicalAnalyzer.analyze();

            if (syntaxAnalysis(lexicalResult)) {
                semanticAnalysis semanticAnalyzer = new semanticAnalysis(codeText);
                boolean isSemanticCorrect = semanticAnalyzer.semantic();

                if (isSemanticCorrect) {
                    result.setText("Semantic Analysis Passed");
                } else {
                    result.setText("Semantic Analysis Failed");
                }
            }
        });


        clear.addActionListener(e -> {
            lexical.setEnabled(false);
            syntax.setEnabled(false);
            semantic.setEnabled(false);
            result.setText("");
            code.setText("");
        });

    }
}
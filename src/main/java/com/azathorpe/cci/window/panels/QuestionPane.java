package com.azathorpe.cci.window.panels;

import com.azathorpe.cci.model.TestCase;
import com.azathorpe.cci.utils.Infos;
import com.azathorpe.cci.utils.PersistentStorage;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import com.azathorpe.cci.runner.Message;

import javax.swing.*;
import java.awt.*;

public class QuestionPane extends JPanel {
    private final JLabel inputLabel = new JLabel("Input:");
    private final JLabel outputLabel = new JLabel("Answer:");
    private final JLabel myAnswerLabel = new JLabel("My Answer:");
    private final JLabel statusLabel = new JLabel();
    private final JTextArea inputArea = new JTextArea();
    private final JTextArea outputArea = new JTextArea();
    private final JTextArea myOutputArea = new JTextArea();
    private final JButton run = new JButton("Run");
    private final JButton delete = new JButton("Delete");

    public static QuestionPane createPane() {
        QuestionPane qp = new QuestionPane();
        qp.init();
        return qp;
    }

    public static QuestionPane createPane(TestCase testCases) {
        QuestionPane pane = createPane();
        pane.inputArea.setText(testCases.getInput());
        pane.outputArea.setText(testCases.getOutput());
        return pane;
    }

    public void init() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: buttons
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0;
        run.setForeground(JBColor.GREEN);
        this.add(run, gbc);

        gbc.gridx = 1;
        delete.setForeground(JBColor.RED);
        this.add(delete, gbc);

        // Row 1: input label
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        this.add(inputLabel, gbc);

        // Row 2: input field
        gbc.gridy = 2;
        this.add(inputArea, gbc);

        // Row 3: output label
        gbc.gridy = 3;
        this.add(outputLabel, gbc);

        // Row 4: output field
        gbc.gridy = 4;
        this.add(outputArea, gbc);

        // Row 5: status label (hidden until run)
        gbc.gridy = 5;
        statusLabel.setVisible(false);
        this.add(statusLabel, gbc);

        // Row 6: my answer label (hidden until run)
        gbc.gridy = 6;
        myAnswerLabel.setVisible(false);
        this.add(myAnswerLabel, gbc);

        // Row 7: my output area wrapped in scroll pane (hidden until run)
        gbc.gridy = 7;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JBScrollPane outputScrollPane = new JBScrollPane(myOutputArea);
        outputScrollPane.setVisible(false);
        myOutputArea.setEditable(false);
        myOutputArea.setRows(4);
        this.add(outputScrollPane, gbc);

        // Row 8: vertical glue
        gbc.gridy = 8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(Box.createVerticalGlue(), gbc);

        // Wire Run button
        run.addActionListener((e) -> {
            run.setEnabled(false);
            run.setText("Running...");
            statusLabel.setVisible(false);
            myAnswerLabel.setVisible(false);
            outputScrollPane.setVisible(false);

            new Thread(() -> {
                Message result = Infos.compiler.start(
                        PersistentStorage.getLastChangedFilePath(),
                        new TestCase(inputArea.getText(), outputArea.getText()));
                SwingUtilities.invokeLater(() -> {
                    String error = result.getError();
                    String output = result.getOutput();

                    if (error != null && !error.isEmpty()) {
                        // 编译错误 或 运行时错误 / 超时
                        myOutputArea.setText(error);
                        myOutputArea.setForeground(JBColor.RED);
                        statusLabel.setText("  ERROR");
                        statusLabel.setForeground(JBColor.RED);
                        myAnswerLabel.setText("Error:");
                    } else {
                        // 正常输出，判断 PASS / FAIL
                        myOutputArea.setText(output);
                        if (result.isStatus()) {
                            myOutputArea.setForeground(JBColor.GREEN);
                            statusLabel.setText("  PASS");
                            statusLabel.setForeground(JBColor.GREEN);
                        } else {
                            myOutputArea.setForeground(JBColor.RED);
                            statusLabel.setText("  FAIL");
                            statusLabel.setForeground(JBColor.RED);
                        }
                        myAnswerLabel.setText("My Answer:");
                    }

                    statusLabel.setVisible(true);
                    myAnswerLabel.setVisible(true);
                    outputScrollPane.setVisible(true);
                    run.setText("Run");
                    run.setEnabled(true);
                    QuestionPane.this.revalidate();
                });
            }).start();
        });
    }
}

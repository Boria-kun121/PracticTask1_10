package com.mycompany.praktik_10;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileMerge {

    public static void main(String[] args) {
        List<String> filePaths = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);

        while (true) {
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePaths.add(selectedFile.getAbsolutePath());

                int choice = JOptionPane.showConfirmDialog(null, "Do you want to choose another file?", "Another file", JOptionPane.YES_NO_OPTION);
                if (choice != JOptionPane.YES_OPTION) {
                    break;
                }
            } else {
                break;
            }
        }

        if (filePaths.isEmpty()) {
            System.err.println("There are no files to merge.");
        } else {
            JFileChooser saveFileChooser = new JFileChooser();
            int saveResult = saveFileChooser.showSaveDialog(null);

            if (saveResult == JFileChooser.APPROVE_OPTION) {
                File saveFile = saveFileChooser.getSelectedFile();
                concatenateFiles(filePaths, saveFile);
            } else {
                System.err.println("Declined by user.");
            }
        }
    }

    private static void concatenateFiles(List<String> filePaths, File outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            boolean firstLine = true;
            for (String filePath : filePaths) {
                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!firstLine) {
                            writer.write(",");
                        }
                        writer.write(line);
                        firstLine = false;
                    }
                }
            }
            System.out.println("Merged in " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error while merging: " + e.getMessage());
        }
    }
}
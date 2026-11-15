# N-Gram Word Predictor

## Overview
This Java program predicts the next word based on training text files. It uses bigrams and trigrams to recognize word patterns and make predictions.

It can also calculate prediction accuracy using a separate test file.

## Features
- Predicts the next word using bigrams and trigrams.
- Uses bigrams if a trigram prediction isn't available.
- Trains on multiple text files.
- Calculates prediction accuracy.
- Compares trigram and bigram accuracy.
- Includes an interactive word prediction prompt.

## How to Run

**Step 1: Compile the Java files**

Open your terminal in the project folder and enter:

`javac TextProcessor.java NGramModel.java Predictor.java`

**Step 2: Run the program**

`java Predictor <training_folder> <test_file>`

For example:

`java Predictor training test.txt`

The test file must be outside the training folder.

**Step 3: View the results**

The program will display:
- Number of predictions made.
- Accuracy using trigrams with bigram fallback.
- Accuracy using bigrams only.

You can also use the interactive prompt to predict the next word.

**Note:** The test file is optional. You can run the program using only the training folder.

## How It Works
- **TextProcessor.java:** Reads files and processes text.
- **NGramModel.java:** Stores word patterns using HashMaps.
- **Predictor.java:** Trains the model, predicts words, and calculates accuracy.

## Technologies Used
- Java
- HashMap and ArrayList
- File I/O
- Object-Oriented Programming

## Author
Jayson Rojas  
University of San Francisco — Computer Science

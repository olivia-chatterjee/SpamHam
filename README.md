# SpamHam
Spam Ham Email Classifier using Naive Bayes Concept.

To Compile: % javac -cp opencsv-5.7.1.jar  SpamHamDetector.java

To Run: % java -classpath .:./opencsv-5.7.1.jar:common-lang3.jar SpamHamDetector

Files:

SpamHamDetector.java - Main Source Code

spam_ham_dataset.csv - Training File

email1.txt & email2.txt - Sample Email Test Files

opencsv-5.7.1.jar & common-lang3.jar - Dependant Libraries




Sample output run:

Initializing SpamHam Detector...

Reading Traing Data...

Training Started...

Training Complete...

3673

1499

Calculating Probabilities...

Input an Email Text File...

email1.txt

SPAM Probability: 0.0

HAM Probability: 0.0

Email is Ham.

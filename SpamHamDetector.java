import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileReader;
import java.io.BufferedReader;
import com.opencsv.CSVReader;

public class SpamHamDetector {

  private List<String> hamEmails;
  private List<String> spamEmails;
  private Map<String, Integer> hamWords;
  private Map<String, Integer> spamWords;
  private Map<String, Double> hamProbabilities;
  private Map<String, Double> spamProbabilities;
  private int hamEmailsCount;
  private int spamEmailsCount;

  public SpamHamDetector() {
    hamEmails = new ArrayList<>();
    spamEmails = new ArrayList<>();
    hamWords = new HashMap<>();
    spamWords = new HashMap<>();
    hamProbabilities = new HashMap<>();
    spamProbabilities = new HashMap<>();
    hamEmailsCount = 0;
    spamEmailsCount = 0;
  }

  public void train(String email, boolean isSpam) {
    if (isSpam) {
      spamEmails.add(email);
      spamEmailsCount++;
      for (String word : email.split(" ")) {
        word = word.toLowerCase();
        if (spamWords.containsKey(word)) {
          spamWords.put(word, spamWords.get(word) + 1);
        } else {
          spamWords.put(word, 1);
        }
      }
    } else {
      hamEmails.add(email);
      hamEmailsCount++;
      for (String word : email.split(" ")) {
        word = word.toLowerCase();
        if (hamWords.containsKey(word)) {
          hamWords.put(word, hamWords.get(word) + 1);
        } else {
          hamWords.put(word, 1);
        }
      }
    }
  }

  public void calculateProbabilities() {
    System.out.println("Calculating Probabilities...");
    for (String word : hamWords.keySet()) {
      hamProbabilities.put(word, (double) hamWords.get(word) / hamEmailsCount);
    }
    for (String word : spamWords.keySet()) {
      spamProbabilities.put(word, (double) spamWords.get(word) / spamEmailsCount);
    }
  }

    public boolean isSpam(String email) {
        double hamProbability = (double) hamEmailsCount / (hamEmailsCount + spamEmailsCount);
        double spamProbability = (double) spamEmailsCount / (hamEmailsCount + spamEmailsCount);
        for (String word : email.split(" ")) {
            word = word.toLowerCase();
            if (hamProbabilities.containsKey(word)) {
                hamProbability *= hamProbabilities.get(word);
            }
            if (spamProbabilities.containsKey(word)) {
                spamProbability *= spamProbabilities.get(word);
            }
        }
        System.out.println("SPAM Probability: " + spamProbability);
        System.out.println("HAM Probability: " + hamProbability);
            
        return spamProbability > hamProbability;
    }
    
    public void readNTrainData(String file) {
        System.out.println("Reading Traing Data...");
        try {
            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            System.out.println("Training Started...");
            while ((nextRecord = csvReader.readNext()) != null) {
                String index = nextRecord[0];
                boolean isSpam = (nextRecord[1].trim().equals("spam")) ? true : false;
                String email = nextRecord[2];
                String labelNum = nextRecord[3];
                
                // Train
                train(email, isSpam);
            }
            System.out.println("Training Complete...");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(hamEmailsCount);
        System.out.println(spamEmailsCount);
        
    }
    
    private static String getStringFromFile(String fileName)
    {
        StringBuilder builder = new StringBuilder();
 
        try (BufferedReader buffer = new BufferedReader(new FileReader(fileName))) 
        {
            String str;
            while ((str = buffer.readLine()) != null) {
                builder.append(str).append("\n");
            }
        }
 
        catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        
        System.out.println("Initializing SpamHam Detector...");
        SpamHamDetector classifier = new SpamHamDetector();
        classifier.readNTrainData("spam_ham_dataset.csv"); 
        classifier.calculateProbabilities();
        
        System.out.println("Input an Email Text File...");
        Scanner sc = new Scanner(System.in);
        String emailFile = sc.nextLine();
        String email = getStringFromFile(emailFile);
        
        if(classifier.isSpam(email))
            System.out.println("Email is Spam.");
        else
            System.out.println("Email is Ham.");
            
    }
    
}
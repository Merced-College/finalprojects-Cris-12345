import java.util.*;

public class Main {
    
    //classes used , Hashmap Stores flashcards using question as the key and answer as the value
    static HashMap<String, String> flashcards = new HashMap<>();
    static Queue<String> retryQueue = new LinkedList<>(); // Stores missed questions for retry (used as the retry algorithm)
    static Scanner scanner = new Scanner(System.in); 

    public static void main(String[] args) {
        
        // Main program loop for user menu

        boolean running = true;
        while (running) {
            System.out.println("\n--- Flashcard Quiz App ---");
            System.out.println("1. Add Flashcard");
            System.out.println("2. Start Quiz");
            System.out.println("3. Show Retry Queue");
            System.out.println("4. Exit");
            int choice = scanner.nextInt(); scanner.nextLine();
            switch (choice) {
                case 1 -> addFlashcard(); // Add flashcard feature
                case 2 -> startQuiz();     // Start quiz feature
                case 3 -> retryMissed();      // Retry missed questions feature
                case 4 -> running = false;      // Exit program
            }
        }
    }

      // Allows user to input a question and answer, which are added to the flashcard collection
    static void addFlashcard() {
        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        System.out.print("Enter answer: ");
        String answer = scanner.nextLine();
        flashcards.put(question, answer);
        System.out.println("Flashcard added!");
    }

    static void startQuiz() {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available. Add some first!");
            return;
        }

        

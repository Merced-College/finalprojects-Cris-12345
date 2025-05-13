import java.util.*; // Import utility classes like Scanner, HashMap, ArrayList, etc.

public class FlashcardStudyTool { // Main class for the Flashcard Study Tool
    private static Scanner scanner = new Scanner(System.in); // Reads user input from the terminal

    private static HashMap<String, String> flashcards = new HashMap<>(); // HashMap to store flashcards (term -> definition)

    private static Queue<String> missedCards = new LinkedList<>(); // Queue to store missed flashcards for retrying

    private static ArrayList<String> quizOrder = new ArrayList<>(); // ArrayList to shuffle quiz order

    public static void main(String[] args) { // Main method - entry point
        int choice; // Variable to store user's menu choice
        do {
            printMenu(); // Display the main menu
            choice = Integer.parseInt(scanner.nextLine()); // Get user input as integer
            switch (choice) { // Handle different menu choices
                case 1 -> addFlashcard(); // Add a new flashcard
                case 2 -> viewFlashcards(); // View all flashcards
                case 3 -> deleteFlashcard(); // Delete a flashcard
                case 4 -> startQuiz(); // Start the quiz
                case 5 -> retryMissed(); // Retry missed flashcards
                case 0 -> System.out.println("Goodbye!"); // Exit message
                default -> System.out.println("Invalid option. Try again."); // Invalid input handler
            }
        } while (choice != 0); // Repeat until user exits
    }

    private static void printMenu() { // Displays the menu options
        System.out.println("\nFlashcard Study Tool"); // Title
        System.out.println("1. Add Flashcard"); // Option 1
        System.out.println("2. View Flashcards"); // Option 2
        System.out.println("3. Delete Flashcard"); // Option 3
        System.out.println("4. Take Quiz"); // Option 4
        System.out.println("5. Retry Missed Flashcards"); // Option 5
        System.out.println("0. Exit"); // Option 0
        System.out.print("Choose an option: "); // Prompt for input
    }

    private static void addFlashcard() { // Adds a flashcard
        System.out.print("Enter term: "); // Prompt for term
        String term = scanner.nextLine(); // Read term
        System.out.print("Enter definition: "); // Prompt for definition
        String definition = scanner.nextLine(); // Read definition
        flashcards.put(term, definition); // Store term and definition in HashMap
        System.out.println("Flashcard added."); // Confirmation
    }

    private static void viewFlashcards() { // View all flashcards
        if (flashcards.isEmpty()) { // Check if any flashcards exist
            System.out.println("No flashcards added yet."); // Inform user if none
            return;
        }
        System.out.println("\nAll Flashcards:"); // Title
        flashcards.forEach((term, def) -> // Loop through HashMap
            System.out.println("Term: " + term + " | Definition: " + def)); // Print each term and definition
    }

    private static void deleteFlashcard() { // Deletes a flashcard
        System.out.print("Enter term to delete: "); // Prompt for term to delete
        String term = scanner.nextLine(); // Read term
        if (flashcards.remove(term) != null) { // Try removing from HashMap
            System.out.println("Flashcard deleted."); // Confirmation
        } else {
            System.out.println("Term not found."); // Inform if term does not exist
        }
    }

    private static void startQuiz() { // Starts the quiz
        if (flashcards.isEmpty()) { // Check if there are any flashcards
            System.out.println("No flashcards to quiz."); // Inform user
            return;
        }

        quizOrder.clear(); // Clear previous quiz order
        quizOrder.addAll(flashcards.keySet()); // Add all terms to quizOrder
        Collections.shuffle(quizOrder); // ALGORITHM 1: Shuffle the quiz order

        int correct = 0; // ALGORITHM 2: Track correct answers
        missedCards.clear(); // Clear previous missed cards

        for (String term : quizOrder) { // Loop through shuffled terms
            System.out.print("Definition of '" + term + "': "); // Ask user for definition
           String answer = scanner.nextLine(); // Read user answer
            if (flashcards.get(term).equalsIgnoreCase(answer)) { // Compare with correct definition
                System.out.println("Correct!"); // Correct answer
                correct++; // Increment score
            } else {
                System.out.println("Incorrect! Correct answer: " + flashcards.get(term)); // Show correct answer
                missedCards.add(term); // Store missed term in Queue
            }
        }

        System.out.println("\nQuiz complete. Score: " + correct + "/" + flashcards.size()); // Show result
    }

    private static void retryMissed() { // Retry missed cards
        if (missedCards.isEmpty()) { // Check if any missed
            System.out.println("No missed flashcards to retry."); // Inform user
            return;
        }

        System.out.println("Retrying missed flashcards..."); // Message
        Queue<String> tempQueue = new LinkedList<>(missedCards); // Copy of missed cards
        missedCards.clear(); // Clear missed queue

        while (!tempQueue.isEmpty()) { // ALGORITHM 3: Retry loop through missed cards
            String term = tempQueue.poll(); // Get the next missed card (FIFO)
            System.out.print("Definition of '" + term + "': "); // Ask for definition
            String answer = scanner.nextLine(); // Read user input
            if (flashcards.get(term).equalsIgnoreCase(answer)) { // Check correctness
                System.out.println("Correct!"); // Correct answer
            } else {
                System.out.println("Incorrect! Correct answer: " + flashcards.get(term)); // Show correct answer
                missedCards.add(term); // Re-add if still incorrect
            }
        }

        if (missedCards.isEmpty()) { // If all missed cards were corrected
            System.out.println("Well done! You corrected all missed cards."); // Success message
        } else {
            System.out.println("Still some cards to review."); // Encouragement message
        }
    }
}
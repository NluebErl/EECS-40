package Dictionary;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.io.*;
import java.util.*;

//--------------------------------------- Custom Exceptions --------------------------------------------

/**
 * InvalidWordError Class used to throw an exception and check if the word the user is trying to input exists or not (real word or not).
 */
class InvalidWordError extends RuntimeException{
    public InvalidWordError()
    {
        super("Invalid word");
    }
    public InvalidWordError(String message)
    {
        super(message);
    }
}
/**
 * WordNotFoundError Class used to throw an exception and check if a word that the user is trying to access doesn't exist in the dictionary.
 */
class WordNotFoundError extends RuntimeException{
    public WordNotFoundError()
    {
        super("Word not found");
    }
    public WordNotFoundError(String message)
    {
        super(message);
    }
}

/**
 * WordDuplicatedError class checks if the word that the user is trying to add to the dictionary is already an entry.
 * Utilizes an entry finder helper function and handles both import and user input.
 */
class WordDuplicatedError extends RuntimeException{
    public WordDuplicatedError()
    {
        super("Duplicate word");
    }
    public WordDuplicatedError(String message)
    {
        super(message);
    }
}

/**
 * FileNotFoundError Class checks the import and export file paths of the import and export functions and throws an error if the file path does not exist or file path yields no file.
 */
class FileNotFoundError extends RuntimeException{
    public FileNotFoundError()
    {
        super("File not found");
    }
    public FileNotFoundError(String message)
    {
        super(message);
    }
}

/**
 * Dictionary class holds all of the funcitonality for the Dictionary program.
 * Holds GUI setups, handles variables for Dictionary functions, holds definitions for the dictionary methods, and sets up GUI using a nested Launcher.
 * Utilizes external error functions to catch and throw errors when they occur.
 */
public class Dictionary {
    // Exceptions
    static{
        try{
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {

        } catch (Exception e) {

        }
    }

    public TextField TextNewWord;
    public TextField TextFreqWord1;
    public TextField TextFreqWord2;
    public TextField TextFreqWord3;
    public TextField TextOriginalWord;
    public TextArea TextArea;
    public Button FINDButton;
    public Button ADDButton;
    public Button REMOVEButton;
    public Button CLEARButton;
    public Button IMPORTButton;
    public Button EXPORTButton;
    public Label frequentWord1Label;
    public Label frequentWord2Label;
    public Label frequentWord3Label;
    public TextField TextFilePath;
    public Button MODIFYButton;
    public Label NewWord;
    public Label OriginalWord;
    public ListView<String> searchHistoryList;
    private ObservableList<String> searchHistoryListModel;

    //----------------------------------------- Entry helper class ----------------------------------------------
    /**
     * Entry class holds constructor and variables for dictionary entries. Specific variables include the word, the frequency of the word, and the meaning of the word.
     * Values of each entry are displayed corresponding to the methods used.
     */
    public static class Entry{
        public String word;
        public int freq;
        public String meaning;

        /**
         * Entry constructor which initializes a new Dictionary Entry based on a given string word and meaning which is provided in the textarea text field.
         * @param word Word parsed from the newWord text field. Used to generate an entry with the given word.
         * @param meaning Meaning parsed from the textarea. Generates a meaning for the word per entry.
         * When initializing a new word, we set the frequency of the word to 0.
         */
        public Entry(String word, String meaning)
        {
            this.word = word;
            this.meaning = meaning;
            this.freq = 0;
        }
    }

    //--------------------------------------- Store Dictionary Entries -----------------------------------------
    public final List<Entry> dictionary;

    /**
     * Initializes the dictionary with an empty array list and sets up the UI along with button functionality
     */
    public Dictionary()
    {
        dictionary = new ArrayList<>();
        createUIComponents();
        setupEventHandlers();
    }

    /**
     * createUIComponents method initializes all TextFields, TextAreas, and Buttons for the dictionary.
     * Sets the UI to have predefined widths and heights for these parameters and determines whether a textfield is editable or not.
     */
    public void createUIComponents(){
        TextNewWord = new TextField();
        TextFreqWord1 = new TextField();
        TextFreqWord2 = new TextField();
        TextFreqWord3 = new TextField();
        TextOriginalWord = new TextField();
        TextArea = new TextArea();

        FINDButton = new Button("FIND");
        ADDButton = new Button("ADD");
        REMOVEButton = new Button("REMOVE");
        CLEARButton = new Button("CLEAR");
        IMPORTButton = new Button("IMPORT");
        EXPORTButton = new Button("EXPORT");
        MODIFYButton = new Button("MODIFY");

        ADDButton.setPrefSize(250, 10);
        FINDButton.setPrefSize(250, 10);

        frequentWord1Label = new Label("\tFrequent word 1: ");
        frequentWord2Label = new Label("\tFrequent word 2: ");
        frequentWord3Label = new Label("\tFrequent word 3: ");

        TextFilePath = new TextField();

        NewWord = new Label("\tNew word: ");
        OriginalWord = new Label("\tOriginal word: ");

        searchHistoryListModel = FXCollections.observableArrayList();
        searchHistoryList  = new ListView<>(searchHistoryListModel);

        // Cannot edit the text box
        TextFreqWord1.setEditable(false);
        TextFreqWord2.setEditable(false);
        TextFreqWord3.setEditable(false);

        // Set up the text boxes
        TextNewWord.setPrefWidth(100);
        TextOriginalWord.setPrefWidth(100);
        TextFilePath.setPrefWidth(200);
        TextArea.setPrefHeight(200);
        searchHistoryList.setPrefHeight(150);
    }

    /**
     * Sets up the layout of the buttons. After creating UI components, the function formats them in an organized pattern using a GridPane.
     * @return grid. The GridPane which holds all the different buttons, textfields, and text areas which were organized.
     */
    public GridPane createLayout()
    {
        // Initialize the GridPane for output
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Crete the search history label
        Label searchHisLabel = new Label("\tSearch history:");
        grid.add(searchHisLabel, 0, 0);
        grid.add(searchHistoryList, 1, 0);

        // Set up the find and add buttons
        HBox findBut = new HBox(FINDButton);
        grid.add(findBut, 0, 1);
        HBox addBut = new HBox(ADDButton);
        grid.add(addBut, 1, 1);

        // Set up the new word and original word text fields and labels
        Label newWord = NewWord;
        newWord.setLabelFor(TextNewWord);
        grid.add(newWord, 0, 2);
        grid.add(TextNewWord, 1, 2);

        Label originalWord = OriginalWord;
        originalWord.setLabelFor(TextOriginalWord);
        grid.add(originalWord, 0, 3);
        grid.add(TextOriginalWord, 1, 3);

        // Set up frequent word text fields and labels
        Label frequentWord1 = frequentWord1Label;
        frequentWord1.setLabelFor(TextFreqWord1);
        grid.add(frequentWord1, 0, 4);
        grid.add(TextFreqWord1, 1, 4);
        Label frequentWord2 = frequentWord2Label;
        frequentWord2.setLabelFor(TextFreqWord2);
        grid.add(frequentWord2, 0, 5);
        grid.add(TextFreqWord2, 1, 5);
        Label frequentWord3 = frequentWord3Label;
        frequentWord3.setLabelFor(TextFreqWord3);
        grid.add(frequentWord3, 0, 6);
        grid.add(TextFreqWord3, 1, 6);

        // Set up the imort and export buttons on the grid pane
        HBox importBut = new HBox(IMPORTButton);
        grid.add(importBut, 0, 7);
        HBox exportBut = new HBox(EXPORTButton);
        grid.add(exportBut, 1, 7);

        // Set up the file path label and text field for importing and exporting
        Label filePathLabel = new Label("\tFile path: ");
        filePathLabel.setLabelFor(TextFilePath);
        grid.add(filePathLabel, 0, 8);
        grid.add(TextFilePath, 1, 8);

        // Set up the text area at the bottom for error display and word definitions
        Label textArea = new Label();
        grid.add(textArea, 0, 9);
        grid.add(TextArea, 0, 9, 2, 1);

        // Set up the clear, modify, and remove buttons on the GridPane
        HBox botBut = new HBox(10, CLEARButton, MODIFYButton, REMOVEButton);
        grid.add(botBut, 0, 10);

        return grid;
    }

    /**
     * The showGUI method creates and displays the GridPane created by createLayout() method. Sets it up on a scene and renames all of the variables corresponding to it.
     * @param primaryStage The stage of which the GridPane will be displaying the program on.
     */
    public void showGUI(Stage primaryStage)
    {
        GridPane grid = createLayout();
        Scene scene = new Scene(grid, 600, 700);
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //------------------------------ Assign event handlers to the buttons ---------------------------------------

    /**
     * setupEventHandlers method will link all of the button functionalities to a handle function for the button.
     * Each handle function is defined in a separate method.
     */
    public void setupEventHandlers()
    {
        FINDButton.setOnAction(e -> handleFindButton());
        ADDButton.setOnAction(e -> handleAddButton());
        MODIFYButton.setOnAction(e -> handleModifyButton());
        REMOVEButton.setOnAction(e -> handleRemoveButton());
        CLEARButton.setOnAction(e -> handleClearButton());
        IMPORTButton.setOnAction(e -> handleImportButton());
        EXPORTButton.setOnAction(e -> handleExportButton());
    }

    //--------------------------------------- Handle methods -------------------------------------------------------

    /**
     * handleAddButton adds a dictionary entry to the dictionary list. This word is parsed from the TextNewWord text field and the meaning of the word is parsed from the TextArea text area.
     * Error occurs and is displayed if the word that the user input is invalid (nothing in the text field or word is not a word).
     * Error also occurs if the word the user is trying to add is already in the dictionary (duplicate words).
     * Adds a new entry to the dictionary list if it hasn't been added yet.
     */
    public void handleAddButton()
    {
        // Parse text area and text field for new word and meaning
        String newWord = TextNewWord.getText();
        String meaning = TextArea.getText();

        // Error if the word is not valid
        if (newWord == null || newWord.isEmpty() || !isValidWord(newWord))
        {
            TextArea.setText("InvalidWordError");
            throw new InvalidWordError("InvalidWordError");
        }
        // Check to see if the word is already a part of the dictionary
        for (Entry e : dictionary)
        {
            if (e.word.equals(newWord))
            {
                TextArea.setText("DuplicatedWordError");
                throw new WordDuplicatedError("DuplicateWordError");
            }
        }
        // Add the new entry to the dictionary
        Entry e = new Entry(newWord, meaning == null ? "" : meaning);
        dictionary.add(e);
        TextArea.setText("Word Added: " + newWord + ".");
    }

    /**
     * handleFindButton method parses the word in the TextNewWord field and checks if there are any words in the dictionary list that contain the substring in the newWord text field.
     * Puts all of the matches with the substring into a new ArrayList and sorts them from highest to lowest frequency.
     * Display the top three words that match with the highest frequency and increase their frequency by 1.
     * Displays errors in the TextArea if there are no words that match.
     * Display a StringBuilder to the TextArea of the top three most frequent words along with their meanings.
     */
    public void handleFindButton()
    {
        // Check if the word in TextNewWord is valid or is in the dictionary
        String keyword = TextNewWord.getText();
        if (keyword == null)
            keyword = "";
        if (keyword.isEmpty())
        {
            clearFreqTextFields();
            TextArea.setText("No Word Matched.");
            return;
        }

        // Create a list for all matches with the keyword
        List<Entry> matches = new ArrayList<>();
        String kwd = keyword;
        for (Entry e : dictionary)
        {
            if (e.word.contains(kwd))
            {
                matches.add(e);
            }
        }
        if (matches.isEmpty())
        {
            clearFreqTextFields();
            TextArea.setText("No Word Matched.");
            return;
        }
        clearFreqTextFields();
        TextArea.setText("");

        // Sort the array to find the biggest frequency and put it on the front
        matches.sort((a,b) -> Integer.compare(b.freq, a.freq));

        // Find how many words to display for frequently appearing (up to 3 max)
        int showFreq = Math.min(matches.size(), 3);
        if (showFreq >= 1)
        {
            TextFreqWord1.setText(matches.get(0).word);
            matches.get(0).freq += 1;
            //searchHistoryListModel.add(0, matches.get(0).word);
        }
        if (showFreq >= 2)
        {
            TextFreqWord2.setText(matches.get(1).word);
            matches.get(1).freq += 1;
            //searchHistoryListModel.add(0, matches.get(1).word);
        }
        if (showFreq >= 3)
        {
            TextFreqWord3.setText(matches.get(2).word);
            matches.get(2).freq += 1;
            //searchHistoryListModel.add(0, matches.get(2).word);
        }

        // Sort the words from the matches and sees if
        List<Entry> rankedFreq = new ArrayList<>(dictionary);
        rankedFreq.sort((a,b) -> Integer.compare(b.freq, a.freq));

        searchHistoryListModel.clear();
        for (int i = 0; i < Math.min(rankedFreq.size(), 10); i++)
        {
            searchHistoryListModel.add(rankedFreq.get(i).word);
        }

        // Display the definitions of the words
        StringBuilder sb = new StringBuilder();
        for (Entry e : matches)
        {
            sb.append(e.word).append("\n").append(e.meaning == null ? "" : e.meaning).append("\n\n");
        }
        TextArea.setText(sb.toString());
    }

    /**
     * handleModifyButton method modifies an existing word spelling using both the original and new word text fields.
     * Displays errors if either one of the text fields are empty or either one does not exist in the dictionary using a helper function.
     * Checks if the user is also trying to change a word to another word currently in the dictionary and throws an error.
     * Changes the word if none of these conditions are true.
     */
    public void handleModifyButton()
    {
        String original = TextOriginalWord.getText();
        String newWord = TextNewWord.getText();

        // Check if the original and new word text fields contain a valid word
        if (original == null ||  newWord == null || newWord.isEmpty() || original.isEmpty() || !isValidWord(original) || !isValidWord(newWord) || !isValidWord(original))
        {
            TextArea.setText("InvalidWordError");
            throw new InvalidWordError("InvalidWordError");
        }

        // Checks if the word that the user is trying to change exists in the dictionary already or not
        Entry originalEntry = findEntry(original);
        if (originalEntry == null)
        {
            TextArea.setText("WordNotFoundError");
            throw new WordNotFoundError("WordNotFoundError");
        }

        // Checks if the user is trying to change an old word to an already existing word
        Entry duplicate =  findEntry(newWord);
        if (duplicate != null && duplicate != originalEntry)
        {
            TextArea.setText("DuplicatedWordError");
            throw new WordDuplicatedError("DuplicatedWordError");
        }

        // Modifies an old word to the new word spelling if all of the above were false. Keep the original meaning and frequency the same
        originalEntry.word = newWord;
        TextArea.setText("Word modified: " + newWord + ".");
    }

    /**
     * handleRemoveButton removes a current entry in the dictionary.
     * Only removes if the word is actually in the dictionary and if the text field is not empty
     */
    public void handleRemoveButton()
    {
        // Checks if the word the user is trying to remove is not empty (text field is not empty)
        String w = TextNewWord.getText();
        if (w.isEmpty() || w == null)
        {
            TextArea.setText("WordNotFoundError");
            throw new WordNotFoundError("WordNotFoundError");
        }

        // Checks if the word the user is trying to remove is actually an entry in the dicitonary list
        Entry e = findEntry(w);
        if (e == null)
        {
            TextArea.setText("WordNotFoundError");
            throw new WordNotFoundError("WordNotFoundError");
        }
        // Remove the word from the dictionary list
        dictionary.remove(e);
        TextArea.setText("Word Removed: " + w + ".");
    }

    /**
     *  handleClearButton clears all the text fields besides the search history text field
     */
   public void handleClearButton()
    {
        TextArea.setText("");
        TextOriginalWord.setText("");
        TextFreqWord1.setText("");
        TextFreqWord2.setText("");
        TextFreqWord3.setText("");
        TextNewWord.setText("");
        TextFilePath.setText("");
    }

    /**
     * handleImportButton method parses an import text file and adds the words to the dictionary.
     * Errors occur when the file path does not exist or if any words in the dictionary that the user is trying to import already is in the dictionary
     * Utilizes a buffer reader to read each line and checks if the words are valid or duplicates.
     * Leaves the meaning as empty if the meaning is null.
     */
    public void handleImportButton()
    {
        // Checks if the file path exists or is not empty
        String pathIn = TextFilePath.getText();
        if (pathIn == null || pathIn.isEmpty())
        {
            TextArea.setText("FileNotFoundError");
            throw new FileNotFoundError("FileNotFoundError");
        }

        File file = new File(pathIn);
        if (!file.exists())
        {
            TextArea.setText("FileNotFoundError");
            throw new FileNotFoundError("FileNotFoundError");
        }

        // Parse the file input
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String word;
            while ((word = br.readLine()) != null)
            {
                // Skips new lines
                if (word.trim().isEmpty())
                {
                    continue;
                }

                // If the meaning of the word is not present, then leave it as empty
                String meaning = br.readLine();
                if (meaning == null)
                {
                    meaning = "";
                }

                // Checks if the imported word is a valid word
                if (!isValidWord(word))
                {
                    TextArea.setText("InvalidWordError");
                    throw new InvalidWordError("InvalidWordError");
                }

                // Checks if the imported word is already in the dictionary already or not
                Entry existing = findEntry(word);
                if (existing != null)
                {
                    TextArea.setText("DuplicatedWordError");
                    throw new WordDuplicatedError("DuplicatedWordError");
                }
                else
                {
                    dictionary.add(new Entry(word, meaning));
                }
            }
            TextArea.setText("Import Successful: " + pathIn);
        }
        // IOException if the file IO fails
        catch (IOException e)
        {
            TextArea.setText("FileNotFoundError");
            throw new FileNotFoundError("FileNotFoundError");
        }
    }

    /**
     * handleExportButton parses through the dictionary entries and wries onto an output file the word, frequency, and meaning
     * This is done from highest frequency to lowest frequency using a sorted list.
     * Use a buffered writer to write into the file.
     */
    public void handleExportButton()
    {
        // Checks if the file path exists or not
        String pathOut = TextFilePath.getText();
        if (pathOut == null || pathOut.isEmpty())
        {
            TextArea.setText("FileNotFoundError");
            throw new FileNotFoundError("FileNotFoundError");
        }
        File file = new File(pathOut);
        /*File parent = file.getParentFile();
        if (parent == null || !parent.exists())
        {
            TextArea.setText("FileNotFoundError");
            throw new FileNotFoundError("FileNotFoundError");
        }*/

        // Parses through the sorted ArrayList from highest frequency to lowest and writes down word, frequency, and word meanings
        List<Entry> temp =  new ArrayList<>(dictionary);
        temp.sort((a,b) -> Integer.compare(b.freq, a.freq));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TextFilePath.getText().trim())))
        {
            for (int i = 0; i < temp.size(); i++)
            {
                Entry e = temp.get(i);
                bw.write(e.word + "\n" + e.freq + "\n" + e.meaning);
                if (i < temp.size() - 1)
                {
                    bw.newLine();
                    bw.newLine();
                }
            }
            TextArea.setText("Export Successful: " + pathOut);
        }
        // Error for IO
        catch (IOException e)
        {
            TextArea.setText("FileNotFoundError");
            throw new FileNotFoundError("FileNotFoundError");
        }
    }

    //----------------------------------- Helper Methods ---------------------------------------------------
    /**
     * isValidWord method is a helper method that utilizes RegEx to check if a word is valid. By valid, it means that it only contains letters from the alphabet either capital or not capital (no special characters or numbers).
     * @param word The word that the method is checking to see if it is valid.
     * @return Either true or false if the word is considered valid (no numbers or special characters)
     */
    boolean isValidWord(String word)
    {
        return word.matches("[a-zA-Z]+");
    }

    /**
     * clearFreqTextFields method clears the frequency text fields
     */
    public void clearFreqTextFields()
    {
        TextFreqWord1.setText("");
        TextFreqWord2.setText("");
        TextFreqWord3.setText("");
    }

    /**
     * findEntry parses through the dictionary list for the word. Sees if the word exists in the list (not case-sensitive) and returns the exact entry.
     * @param word word of the entry that is trying to be found.
     * @return The entry of the word that was found.
     */
    public Entry findEntry(String word)
    {
        // Checks if the word that the user is trying to find is null.
        if (word == null)
        {
            return null;
        }
        // Checks if the word that the user is trying to find actually exists in the dictionary list
        for (Entry e : dictionary)
        {
            if (e.word.equals(word))
            {
                return e;
            }
        }
        return null;
    }

    /**
     * Launches the program using start with the stage
     */
    public static class Launcher extends Application
    {
        @Override
        /**
         * Initializes the dictionary and shows GUI
         */
        public void start(Stage stage)
        {
            Dictionary dict = new Dictionary();
            dict.showGUI(stage);
        }

        /**
         * Launches the main program (main function) in the launcher class
         * @param args String arguments on the command line
         */
        public static void main(String[] args)
        {
            launch(args);
        }
    }

    /**
     * Main function of the program in the dictionary class
     * @param args String arguments on the command line
     */
    public static void main(String[] args)
    {
        Launcher.main(args);
    }
}
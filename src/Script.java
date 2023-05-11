import java.util.ArrayList;
import java.util.Scanner;

public class Script {
    //all characters present in script
    private ArrayList<Character> characters = new ArrayList<Character>();
    //all genders present in script
    private ArrayList<String> genders = new ArrayList<String>();
    private int numCharacters;
    private boolean spoken;
    private String title;

    /**
     * Initializing Script Characters, assigning explicit attributes,
     * calculating total wordCounts for each.
     * @param sc Scanner reading script .txt file
     */
    public Script(Scanner sc) {
        if (sc.hasNextInt()) {
            numCharacters = sc.nextInt();
            initializeCharacters(sc);
            initializeGenders();
        }
        spoken = true;
        Character currentChar = new Character("", "NA");
        while (sc.hasNext()) {
            String line = sc.nextLine();
            //if dialogue being assigned
            if (line.contains(":")) {
                int colonIndex = line.indexOf(":");
                String tempName = line.substring(0, colonIndex);
                //if a different character speaking
                if (!tempName.equalsIgnoreCase(currentChar.getName())) {
                    //if character exists
                    if (containsCharacter(tempName)) {
                        currentChar = getCharacter(tempName);
                    }
                    //if character is new
                    else {
                        currentChar = new Character(tempName, "NA");
                        characters.add(currentChar);
                    }
                    currentChar.incrementTimesSpoke();
                }
                //if same character as before
                if (colonIndex + 1 < line.length()) {
                    currentChar.addWords(countWords(line.substring(colonIndex + 1)));
                }
            } else {
                currentChar.addWords(countWords(line));
            }
        }
        initializeAverageWordCount();
    }

    /**
     * Calculates the average numWords per uninterrupted speech
     * for all characters.
     */
    private void initializeAverageWordCount(){
        for(Character c : characters){
            c.calcAverageWordsSpoke();
        }
    }

    /**
     * Returns true if Script contains Character of name.
     * @param name Character name to search for
     * @return true if script contains character of name
     */
    private boolean containsCharacter(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns Character with name.
     * @param name Character name searching for
     * @return Character with name
     */
    private Character getCharacter(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Initializes Characters array to include
     * all explicitly listed characters in .txt file.
     * Assigning name, gender, and muted attributes.
     * @param sc Scanner reading script .txt file
     */
    private void initializeCharacters(Scanner sc) {
        sc.nextLine();
        for (int i = 0; i < numCharacters; i++) {
            if (sc.hasNext()) {
                //splitting name and gender attributes based on ()
                String[] attributes = sc.nextLine().split("\\(|\\)");
                if (attributes.length > 2 && attributes[2].contains("*")) {
                    characters.add(new Character(attributes[0], attributes[1], true));
                } else {
                    characters.add(new Character(attributes[0], attributes[1]));
                }

            }
        }
    }

    /**
     * Initializes genders array to include
     * all genders specified through character attributes.
     */
    private void initializeGenders() {
        for (Character c : characters) {
            boolean newGender = true;
            for (String g : genders) {
                if (g.equalsIgnoreCase(c.getGender())) {
                    newGender = false;
                    break;
                }
            }
            if (newGender) {
                genders.add(c.getGender());
            }
        }
    }

    /**
     * Returns number of dialogue words in line.
     * Excludes staging marked with <> or [] or () or {}.
     * @param line Script line in question
     * @return number dialogue words in line
     */
    private int countWords(String line) {
        //splits using whitespace
        String[] text = line.split("\s+");
        int count = 0;
        //checking each word is valid dialogue
        for (String s : text) {
            boolean containsOneAlphabet = s.matches(".*[a-zA-Z]+.*");
            if (s.equals("")) {
                continue;
            }
            if (s.contains("[") || s.contains("(") || s.contains("<") || s.contains("{")) {
                spoken = false;
            }
            if (spoken && containsOneAlphabet) {
                count++;
            }
            if (s.contains("]") || s.contains(")") || s.contains(">") || s.contains("}")) {
                spoken = true;
            }
        }
        return count;
    }

    /**
     * Returns String of non-muted Characters names with their
     * gender, wordCounts, speechCounts, and averageWords per speech.
     * @return non-muted character information
     */
    public String toString() {
        StringBuilder r = new StringBuilder("Script Character Details\n");
        for (Character c : characters) {
            if (!c.getMute()) {
                String avgWordSpoke = String.format("%.2f", c.getAverageWordsSpoke());
                r.append(c.getName()).append("(").append(c.getGender())
                        .append(")").append(": ").append(c.getWordCount())
                        .append(" words, spoke: ").append(c.getTimesSpoke())
                        .append(" times. ").append(" Avg. ").append(avgWordSpoke)
                        .append(" words/dialogue.\n");
            }
        }
        return r.toString();
    }

    /**
     * Returns String of muted characters names with their
     * gender, wordCounts, speechCounts, and averageWords per speech.
     * @return muted character information
     */
    public String displayMuted() {
        StringBuilder r = new StringBuilder("Muted Character Details\n");
        for (Character c : characters) {
            if (c.getMute()) {
                r.append(c.getName()).append("(").append(c.getGender())
                        .append(")").append(": ").append(c.getWordCount())
                        .append(" words, spoke: ").append(c.getTimesSpoke())
                        .append(" times. ").append(" Avg. ").append(c.getAverageWordsSpoke())
                        .append(" words/dialogue.\n");
            }
        }
        return r.toString();
    }

    /**
     * Returns list of all Character names.
     * @return Character names
     */
    public ArrayList<String> getNames() {
        ArrayList<String> r = new ArrayList<String>();
        for (Character c : characters) {
            r.add(c.getName());
        }
        return r;
    }

    /**
     * Returns numCharacters in Script.
     * @return numCharacters
     */
    public int getNumCharacters() {
        return numCharacters;
    }

    /**
     * Assigns Character of name to gender.
     * @param name Character name searching for
     * @param gender gender setting to assign
     */
    public void assignGender(String name, String gender) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                c.setGender(gender);
                boolean newGender = true;
                for (String g : genders) {
                    if (g.equalsIgnoreCase(gender)) {
                        newGender = false;
                        break;
                    }
                }
                if (newGender) {
                    genders.add(gender);
                }
            }
        }
    }

    /**
     * Returns String with totalWordCounts, averageSpeechCounts,
     * and averageWords per speech for all non-muted character genders.
     * @return gender balance information
     */
    public String displayGenders() {
        StringBuilder r = new StringBuilder("Script Gender Balance\n" +
                "Total Words\n");
        for (String g : genders) {
            r.append(g).append(": ");
            r.append(getGenderWordCount(g)).append("\n");
        }
        r.append("Average Times Spoke\n");
        for (String g : genders) {
            r.append(g).append(": ");
            r.append(getGenderSpeechTime(g)).append("\n");
        }
        r.append("Average Words/Dialogue\n");
        for (String g : genders) {
            r.append(g).append(": ");
            r.append(getWordsPerDialogue(g)).append("\n");
        }
        return r.toString();
    }

    /**
     * Returns String with averageSpeechCount for all
     * Characters of g gender, truncated to two decimals.
     * @param g gender of interest
     * @return averageSpeechCount for gender
     */
    private String getGenderSpeechTime(String g){
        double sum = 0;
        int total = 0;
        for (Character c : characters) {
            if (c.getGender().equalsIgnoreCase(g) && !c.getMute()) {
                sum += c.getTimesSpoke();
                total++;
            }
        }
        return String.format("%.2f", (sum/total));
    }

    /**
     * Returns String averageWords per speech for all
     * characters of g gender, truncated to two decimals.
     * @param g gender of interest
     * @return averageWords per speech for gender
     */
    private String getWordsPerDialogue(String g){
        double sum = 0;
        int total = 0;
        for (Character c : characters) {
            if (c.getGender().equalsIgnoreCase(g) && !c.getMute()) {
                sum += c.getAverageWordsSpoke();
                total++;
            }
        }
        return String.format("%.2f", (sum/total));
    }

    /**
     * Mutes Character of name.
     * @param name Character name to be muted
     */
    public void mute(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                c.setMute(true);
            }
        }
    }

    /**
     * Un-mutes Character of name.
     * @param name Character name to be un-muted
     */
    public void unmute(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                c.setMute(false);
            }
        }
    }

    /**
     * Returns total numWords spoken by
     * Characters of g gender.
     * @param g gender of interest
     * @return total numWords for gender
     */
    private int getGenderWordCount(String g) {
        int sum = 0;
        for (Character c : characters) {
            if (c.getGender().equalsIgnoreCase(g) && !c.getMute()) {
                sum += c.getWordCount();
            }
        }
        return sum;
    }


}

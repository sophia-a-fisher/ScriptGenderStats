public class Character {
    private String name;
    private int wordCount;
    private String gender;
    private boolean mute;
    private int timesSpoke;
    private double averageWordsSpoke;

    /**
     * Initializing character instance variables.
     * @param name character name
     * @param gender character gender
     */
    public Character(String name, String gender){
        this(name, gender, false);
    }

    /**
     * Initializing character instance variables.
     * @param name character name
     * @param gender character gender
     * @param muted true if character shows muted in display
     */
    public Character(String name, String gender, boolean muted){
        this.name = name;
        this.gender = gender;
        mute = muted;
        timesSpoke = 0;
        wordCount = 0;
        averageWordsSpoke = 0;
    }

    /**
     * Calculates average numWords Character spoke per uninterrupted speech.
     */
    public void calcAverageWordsSpoke(){averageWordsSpoke = ((double) wordCount /timesSpoke);}

    /**
     * Returns average numWords Character spoke per uninterrupted speech.
     * @return averageWordsSpoke
     */
    public double getAverageWordsSpoke(){return averageWordsSpoke;}

    /**
     * Returns total numWords attributed to Character.
     * @return wordCount
     */
    public int getWordCount(){
        return wordCount;
    }

    /**
     * Sets character mute setting to mute.
     * @param mute true to mute,
     *             false to un-mute
     */
    public void setMute(boolean mute){
        this.mute = mute;
    }

    /**
     * Returns mute setting for Character.
     * @return true if Character is muted
     */
    public boolean getMute(){
        return mute;
    }

    /**
     * Returns Character name.
     * @return name
     */
    public String getName(){
        return name;
    }

    /**
     * Adds words to total Character wordCount.
     * @param words numWords to add to total
     */
    public void addWords(int words){
        wordCount += words;
    }

    /**
     * Assigns gender to Character.
     * @param gender gender to be assigned
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * Returns Character gender.
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Increments numTimes Character spoke uninterrupted by one.
     */
    public void incrementTimesSpoke(){timesSpoke++;}

    /**
     * Returns numTimes Character spoke uninterrupted.
     * @return timesSpoke
     */
    public int getTimesSpoke(){return timesSpoke;};
}

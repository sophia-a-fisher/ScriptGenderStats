# ScriptGenderStats
A program designed to provide feedback on gender balance in scripts.
Calculates word counts and dialouge breaks for each character.
Explore scripts from our existing databases: Disney.
Input a .txt file to analyze your own script.

Once a script is selected...
(1) View a detailed breakdown for all non-muted characters in a script.
(2) View a detailed breakdown for all muted characters in a script.
(3) Assign one character's gender
(4) Assign all character's genders
(5) View gender balance statistics
(6) Mute a character
(7) Un-mute a character

For the purposes of this program:

Word counts are defined as the number of words of dialogue
spoken by a character, excluding stage direction indicated by
() or [] or <> or {}.

Dialogue breaks are defined as the number of times a character
was assigned dialogue that was followed by dialogue attributed to a 
different character. This can be understood as the number of
times a character spoke for the entire script.

Muted characters will not be used in the calculation of gender balance
or printed by the <display character details option>.

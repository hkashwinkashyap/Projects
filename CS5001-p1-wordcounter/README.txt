This java class WordCounter in src folder takes the text filename along with one or more words to be searched in the file and counts the number of times it has been appeared in the file. 
Also an additional feature implemented, that is when the user gives duplicate input i.e., similar word more than once, the code just takes in non-repeating words.
The given code is case-sensitive for the words i.e., counts the words given in the input without altering its case.

When tried to implement the caseInSensitive condition, all worked good but the required output for one particular word didn't match with the requirement. 

EXPECTED OUTPUT:
$ java WordCounter "../pride-and-prejudice.txt" Jane Elizabeth Mary Kitty Lydia
|-----------|-------|
| WORD      | COUNT |
|-----------|-------|
| Jane      |   292 |
| Elizabeth |   634 |
| Mary      |    39 |
| Kitty     |    71 |
| Lydia     |   170 |
|-----------|-------|
| TOTAL     |  1206 |
|-----------|-------|

GIVEN OUTPUT:
$ java WordCounter "../pride-and-prejudice.txt" Jane elizabeth Mary kitty lydia
|-----------|-------|
| WORD      | COUNT |
|-----------|-------|
| Jane      |   292 |
| elizabeth |   634 |
| Mary      |    39 |
| kitty     |    71 |
| lydia     |   171 |
|-----------|-------|
| TOTAL     |  1207 |
|-----------|-------|

The major changes done to the code to achieve the case insensitivity were:
Converted all the input words to lower case,
	List<String> wordsToBeCounted = new ArrayList<>();
		for (int i =1; i< fileAndWords.length; i++){
    			wordsToBeCounted.add(i-1, fileAndWords[i].toLowerCase());
    		}
Loaded all the lower case words to the map,
	for (int i = 1; i < fileAndWords.length; i++) {
		wordCountMap.put(fileAndWords[i].toLowerCase(), 0);
	}
Comparing the words with case insensitivity,
	while (textScanner.hasNextLine()) {
                String scannedLine = textScanner.nextLine();
                String[] scannedWords = scannedLine.split("[^(_A-Za-z0-9)]");
                for (String singleWord : scannedWords) {
                    if (wordsToBeCounted.contains(singleWord.toLowerCase())) {
                        wordCountMap.put(singleWord.toLowerCase(), wordCountMap.get(singleWord.toLowerCase()) + 1);
                    } else {
                        continue;
                    }
                }
            }
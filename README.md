# rankingApp

Exercise for a java console app that reads match results from a file a generates the proper ranking.

## Assumptions
- This is only a console app, it doesn't offer any interactive actions from the user more than specified in the parameters the file's path to be processed, and two additional parameters, match type, currently only SOCCER is supported. The file's type, currently TXT is only supported, refer to the section "How to test with executable jar".
- Only txt files are supported
- Text is not normalized, it will be taken as it comes in the file
- Team only contains text, that way validation is done by checking if each line of the file matches with the pattern "text [space] number[separator] text [space] number"
- Comma or colon could be used as a separator
- Any line that does not match the pattern mentioned above will be discarded
- Results are printed to console

## Abstraction
Based on this example, "Lions 3, Snakes 3", the following has been abstracted for applying the solution, consider this as an initial prototype (MVP) that can evolve for future sprints:
- Each line represents the results of a soccer match
- The values are separated by coma, we can interpret that at the left, we have the local team and its result for the match, at the right we have the visitor team and also its result for the match
- Saying that we have the entities "match" of type "soccer match" in this case and team, of type "soccer team"
- Match and Team are the parent types, while Soccer Match and Soccer Team are sub-types.
- With this abstraction, we can implement the ranking or any other statistic specific to each match sub-type in the future. For example, a soccer match has a different duration when compared with a basketball or football, also teams are composed of a different number of players, positions, etc.
- The current implementation for this MVP has considered the mentioned abstraction to have a base that could be used to extend the scope of this app.

## Code specification
- Built with Java 11
- Maven project
- Additional libraries used: slf4j, lombok, junit 5
- No other frameworks used for current prototype

## How to Build exec jar
Run mvn assembly:assembly -DdescriptorId=jar-with-dependencies

## How to build and generate the JaCoCo report:

mvn clean jacoco:prepare-agent install jacoco:report

## How to test with executable jar
java -jar <path_to_SpanInterviewJS-1.0-SNAPSHOT-jar-with-dependencies.jar> <your_txt_file_path> SOCCER TXT

```
PS C:\Users\jvrle\Documents\GitHub\rankingApp> java -jar C:\Users\jvrle\Documents\GitHub\rankingApp\target\SpanInterviewJS-1.0-SNAPSHOT-jar-with-dependencies.jar C:\Users\jvrle\Documents\GitHub\rankingApp\src\test\resources\SpanExam
ple.txt SOCCER TXT

1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
4. Snakes, 1 pt
5. Grouches, 0 pts
```
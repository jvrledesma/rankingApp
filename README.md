# rankingApp

Exercise for a java console app that reads match results from a file a generates the proper ranking.

## Assumptions
- This is only a console app, it doesn't offer any interactive actions from user
- Only txt files are supported
- Results are printed to console

## How to Build exec jar
Run mvn assembly:assembly -DdescriptorId=jar-with-dependencies

## How to build and generate the JaCoCo report:

mvn clean jacoco:prepare-agent install jacoco:report

## How to test with executable jar
java -jar <path_to_SpanInterviewJS-1.0-SNAPSHOT-jar-with-dependencies.jar> <your_txt_file_path> SOCCER TXT
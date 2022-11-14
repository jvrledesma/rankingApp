/*
 * *******************************************************************************************************
 *  * Copyright (C) 2022 Javier Salgado Ledesma
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *******************************************************************************************************
 */

package com.span.interview;

import com.span.interview.entity.Match;
import com.span.interview.entity.SoccerMatch;
import com.span.interview.entity.SoccerTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * General UTs.
 */
class AppTest {

    @Test
    @DisplayName("Simple test to verify entity classes creation")
    void soccerMatch_EntityCreation(){
        SoccerMatch soccerMatch =
                new SoccerMatch(new SoccerTeam("A",0,0,0,0),
                        new SoccerTeam("B",0,0,0,0));
        assertEquals("A",soccerMatch.getLocalTeam().getTeamName());
        soccerMatch.getLocalTeam().setTeamName("A_1");
        soccerMatch.getLocalTeam().setWonMatches(1);
        soccerMatch.getLocalTeam().setLossMatches(1);
        soccerMatch.getLocalTeam().setTiedMatches(1);
        soccerMatch.getLocalTeam().setTotalPoints(1);

        assertEquals(1, soccerMatch.getLocalTeam().getWonMatches());
        assertEquals(1, soccerMatch.getLocalTeam().getLossMatches());
        assertEquals(1, soccerMatch.getLocalTeam().getTiedMatches());
        assertEquals(1, soccerMatch.getLocalTeam().getTotalPoints());


        assertEquals("B",soccerMatch.getVisitorTeam().getTeamName());
        soccerMatch.getLocalTeam().setTeamName("B_1");
        soccerMatch.getVisitorTeam().setWonMatches(1);
        soccerMatch.getVisitorTeam().setLossMatches(1);
        soccerMatch.getVisitorTeam().setTiedMatches(1);
        soccerMatch.getVisitorTeam().setTotalPoints(1);

        assertEquals(1, soccerMatch.getVisitorTeam().getWonMatches());
        assertEquals(1, soccerMatch.getVisitorTeam().getLossMatches());
        assertEquals(1, soccerMatch.getVisitorTeam().getTiedMatches());
        assertEquals(1, soccerMatch.getVisitorTeam().getTotalPoints());

        soccerMatch.setGoalsForVisitor(1);
        soccerMatch.setGoalsForLocal(1);

        assertEquals(1, soccerMatch.getGoalsForVisitor());
        assertEquals(1, soccerMatch.getGoalsForLocal());
    }

    @Test
    @DisplayName("Test with the parameters given by Span expecting a successful result")
    void main_GivenTheSampleFromSpan_ExpectSuccess (){

        final String expectedResults =
                "1. Tarantulas, 6 pts\n" +
                "2. Lions, 5 pts\n" +
                "3. FC Awesome, 1 pt\n" +
                "4. Snakes, 1 pt\n" +
                "5. Grouches, 0 pts";

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SpanExample.txt")).getFile());

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(byteArrayOutputStream);
        final PrintStream stdout = System.out;
        System.setOut(ps);

        //Call main method
        RankingApp.main(new String[]{file.getAbsolutePath(),"SOCCER","TXT"});

        System.setOut(stdout);

        assertNotNull(byteArrayOutputStream.toString());
        assertEquals(byteArrayOutputStream.toString().trim(),expectedResults);

    }

    @Test
    @DisplayName("Test main call with no parameters, expecting message of proper usage")
    void main_GivenNoArgs_ExpectProperUsageMessage (){

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(byteArrayOutputStream);
        final PrintStream stdout = System.out;
        System.setOut(ps);

        //Call main method
        RankingApp.main(new String[]{});

        System.setOut(stdout);
        assertTrue(byteArrayOutputStream.toString().contains("java RankingApp filePath SPORT FILE_EXTENSION"));
    }

    @Test
    @DisplayName("Test with a non-existing file and expect error from message from exception")
    void main_GivenWrongFilePath_ExpectErrorMessageFromException (){

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(byteArrayOutputStream);
        final PrintStream stdout = System.out;
        System.setOut(ps);

        //Call main method
        RankingApp.main(new String[]{"/this/is/a/file/that/does/not/exists.txt","SOCCER","TXT"});

        System.setOut(stdout);

        assertTrue(byteArrayOutputStream.toString().contains("F001 The file for the provided path does not exist, please verify."));

    }

    @Test
    @DisplayName("Test with a bad sport parameter, no processor available expected")
    void main_GivenWrongSport_ExpectErrorMessageFromException (){

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(byteArrayOutputStream);
        final PrintStream stdout = System.out;
        System.setOut(ps);

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SpanExample.txt")).getFile());

        //Call main method
        RankingApp.main(new String[]{file.getAbsolutePath(),"BASKETBALL","TXT"});

        System.setOut(stdout);

        assertTrue(byteArrayOutputStream.toString().contains("R001 There is no file processor associated with your parameters"));

    }

    @Test
    @DisplayName("Test with a bad file extension, no processor available expected")
    void main_GivenWrongFileExtension_ExpectErrorMessageFromException (){

        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(byteArrayOutputStream);
        final PrintStream stdout = System.out;
        System.setOut(ps);

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SpanExample.txt")).getFile());

        //Call main method
        RankingApp.main(new String[]{file.getAbsolutePath(),"SOCCER","JSON"});

        System.setOut(stdout);

        assertTrue(byteArrayOutputStream.toString().contains("R001 There is no file processor associated with your parameters"));

    }
}

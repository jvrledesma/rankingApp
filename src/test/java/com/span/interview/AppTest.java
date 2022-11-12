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

import com.span.interview.entity.SoccerTeam;
import com.span.interview.entity.Team;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * General UTs.
 */
public class AppTest 
{
    @Test
    void patternMatcher_managePattern_expectedOk(){
        //Pattern p = Pattern.compile("([a-zA-Z\\s]*) ([0-9]) , ([a-zA-Z\\s]*) ([0-9])");
        Pattern p = Pattern.compile("([a-zA-Z\\s]*) ([\\d]) , ([a-zA-Z\\s]*) ([\\d])");
        Matcher m = p.matcher("The Lions 3 , Snakes 8");
        assertTrue(m.matches());
        assertEquals(4, m.groupCount());
        assertEquals("The Lions",m.group(1));
        assertEquals("3",m.group(2));
        assertEquals("Snakes",m.group(3));
        assertEquals("8",m.group(4));
    }

    @Test
    void normalizeText_commaSeparated_noSpaces() {

        Pattern p = Pattern.compile("\\s,\\s+|\\s+,|,\\s+|\\s;\\s+|\\s+;|;\\s+");
        final String [] textToNormalize = {
                "The Lions 3 , Snakes 8",
                "The Lions 3, Snakes 8",
                "The Lions 3, Snakes 8",
                "The Lions 3 ; Snakes 8",
                "The Lions 3; Snakes 8",
                "The Lions 3; Snakes 8"
        };
            //\s+,|,\s+
        Matcher m = null;
        for (String s : textToNormalize ) {
            //System.out.println(s.replaceAll("\\s+,|,\\s+|\\s,\\s+", ","));
            //System.out.println(s.replaceAll("\\s,\\s+|\\s+,|,\\s+|\\s;\\s+|\\s+;|;\\s+", ","));
            m = p.matcher(s);
            assertTrue(m.find());
        }
    }

    @Test
    void test_soccerTeamCreation(){
        SoccerTeam soccerTeam = new SoccerTeam("ST1",0,0,0,0);
        soccerTeam.setTiedMatches(10);

        List<SoccerTeam> soccerTeamList = new ArrayList<>();
        soccerTeamList.add(soccerTeam);

        for (SoccerTeam st : soccerTeamList) {
            System.out.println(st.getTiedMatches());
        }
    }
}

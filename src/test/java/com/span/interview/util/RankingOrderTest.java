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

package com.span.interview.util;

import com.span.interview.entity.SoccerTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The ranking order comparator tests.
 *
 * @author Javier Salgado
 */
class RankingOrderTest {

    @Test
    @DisplayName("Comparing soccer team objects to determine the order result")
    void compare_GivenSoccerTeams_VerifyResultOrder() {
        final RankingOrder rankingOrder = new RankingOrder();
        final SoccerTeam soccerTeamA = new SoccerTeam("TeamA", 1, 0, 3, 0);
        final SoccerTeam soccerTeamB = new SoccerTeam("TeamB", 1, 0, 3, 0);
        final SoccerTeam soccerTeamA1 = new SoccerTeam("TeamA", 1, 0, 3, 0);
        // Compare, same total points will follow order for name, team A should be "less" than team B, -1 expected
        assertEquals(-1, rankingOrder.compare(soccerTeamA, soccerTeamB));

        //Update points of team B, result should be 1
        soccerTeamB.setTotalPoints(6);
        assertEquals(1, rankingOrder.compare(soccerTeamA, soccerTeamB));

        //Use team A in both cases, simulating result for equal name and points
        assertEquals(0, rankingOrder.compare(soccerTeamA, soccerTeamA1));

    }
}
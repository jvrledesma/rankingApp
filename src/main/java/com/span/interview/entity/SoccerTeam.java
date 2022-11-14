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

package com.span.interview.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Concrete class that represents a SoccerTeam.
 * <p>
 * A soccer team also has the tied matches
 *
 * @author Javier Salgado
 */
@Getter
@Setter
public class SoccerTeam extends Team {

    private int tiedMatches;

    /**
     * Constructs a soccer team.
     *
     * @param teamName    the name of the team
     * @param wonMatches  the number of won matches
     * @param lossMatches the number of loss matches
     * @param totalPoints the number of total points in the league
     * @param tiedMatches the number of tied matches
     */
    public SoccerTeam(String teamName, int wonMatches, int lossMatches, int totalPoints, final int tiedMatches) {
        super(teamName, wonMatches, lossMatches, totalPoints);
        this.tiedMatches = tiedMatches;
    }


}

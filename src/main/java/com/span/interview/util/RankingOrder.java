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

import java.util.Comparator;

/**
 * Custom comparator class for sorting {@link SoccerTeam} objects considering the logic:
 * <p>
 * Objects must be ordered by the total points, if two or more teams have the same number of points,
 * they printed in alphabetical order.
 *
 * @author Javier Salgado
 */
public class RankingOrder implements Comparator<SoccerTeam> {

    @Override
    public int compare(SoccerTeam o1, SoccerTeam o2) {

        //Alphabetical using team's names when points are the same
        if (o1.getTotalPoints() == o2.getTotalPoints()) {
            return o1.getTeamName().compareTo(o2.getTeamName());
        }

        //Descending order considering total points
        return Integer.compare(o2.getTotalPoints(), o1.getTotalPoints());
    }
}

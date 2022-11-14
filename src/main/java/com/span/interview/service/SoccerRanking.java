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

package com.span.interview.service;

import com.span.interview.entity.SoccerMatch;
import com.span.interview.entity.SoccerTeam;
import com.span.interview.util.RankingOrder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of a Soccer Ranking class, as expected this class implements the ranking calculation methods associated
 * to soccer matches.
 *
 * @author Javier Salgado
 */
@Getter
public class SoccerRanking implements Ranking<SoccerMatch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoccerRanking.class);

    //Map to group the ranking per team
    private final Map<String, SoccerTeam> soccerRankingMap;

    private List<SoccerTeam> sortedResults;

    public SoccerRanking() {
        this.soccerRankingMap = new HashMap<>();
    }

    /**
     * Process the list of soccer matches to set the corresponding values for each team.
     * - Sets the number of points for each team,
     * - a draw (tie) is worth 1 point, a win is worth 3 points, and a loss is worth 0 points
     * - Sets the number of won, loss and tied matches for each team in the match depending on the result.
     *
     * @param soccerMatchList the list of {@link SoccerMatch} objects to be processed.
     */
    @Override
    public void processMatchList(List<SoccerMatch> soccerMatchList) {
        if(null == soccerMatchList){
            LOGGER.warn("Won't process, the list of soccer matches cannot be null.");
            return;
        }
        soccerMatchList.forEach(sm -> {
            if (sm.getGoalsForLocal() == sm.getGoalsForVisitor()) {
                updateRanking(sm.getLocalTeam(), 1);
                updateRanking(sm.getVisitorTeam(), 1);

                //Increment tied matches, teams should exist now in the map
                soccerRankingMap.get(sm.getLocalTeam().getTeamName()).setTiedMatches(
                        soccerRankingMap.get(sm.getLocalTeam().getTeamName()).getTiedMatches() + 1);

                soccerRankingMap.get(sm.getVisitorTeam().getTeamName()).setTiedMatches(
                        soccerRankingMap.get(sm.getVisitorTeam().getTeamName()).getTiedMatches() + 1);

            } else if (sm.getGoalsForLocal() > sm.getGoalsForVisitor()) {
                updateRanking(sm.getLocalTeam(), 3);
                updateRanking(sm.getVisitorTeam(), 0);

                //Increment won matches for local team
                soccerRankingMap.get(sm.getLocalTeam().getTeamName()).setWonMatches(
                        soccerRankingMap.get(sm.getLocalTeam().getTeamName()).getWonMatches() + 1);

                //Increment loss matches for visitor team
                soccerRankingMap.get(sm.getVisitorTeam().getTeamName()).setLossMatches(
                        soccerRankingMap.get(sm.getVisitorTeam().getTeamName()).getLossMatches() + 1);

            } else {
                updateRanking(sm.getLocalTeam(), 0);
                updateRanking(sm.getVisitorTeam(), 3);

                //Increment loss matches for local team
                soccerRankingMap.get(sm.getLocalTeam().getTeamName()).setLossMatches(
                        soccerRankingMap.get(sm.getLocalTeam().getTeamName()).getLossMatches() + 1);

                //Increment won matches for visitor team
                soccerRankingMap.get(sm.getVisitorTeam().getTeamName()).setWonMatches(
                        soccerRankingMap.get(sm.getVisitorTeam().getTeamName()).getWonMatches() + 1);
            }
        });
    }

    /**
     * Prints the ranking for each team, ordered by the total points, if two or more teams have the same number of points,
     * they printed in alphabetical order. Refer to {@link RankingOrder} to understand the sort process.
     *
     * <strong>Note:</strong> After printing results the supporting map that holds ranking values is reset, it is assumed
     * that new file processing, means new rankings, there is no accumulative consideration for ranking results.
     */
    @Override
    public void printRanking() {
        final StringBuilder stringBuilder = new StringBuilder();
        sortedResults = new ArrayList<>(soccerRankingMap.values());

        final AtomicInteger counter = new AtomicInteger(0);
        sortedResults.stream().sorted(new RankingOrder()).forEach(v -> {
            stringBuilder.append(counter.incrementAndGet());
            stringBuilder.append(". ");
            stringBuilder.append(v.getTeamName());
            stringBuilder.append(", ");
            stringBuilder.append(v.getTotalPoints());
            stringBuilder.append(v.getTotalPoints() == 1 ? " pt\n" : " pts\n");
        });
        System.out.println(stringBuilder);
        soccerRankingMap.clear();
    }

    /**
     * Supports the storage of {@link SoccerTeam} in the support map. If the team already exists in the map, then the
     * total points are incremented depending on the match result.
     *
     * @param soccerTeam the {@link SoccerTeam} to store
     * @param points     the points to be added for the team
     */
    private void updateRanking(final SoccerTeam soccerTeam, final int points) {

        //If already exists in map, increment the total points
        if (soccerRankingMap.containsKey(soccerTeam.getTeamName())) {
            int currentPoints = soccerRankingMap.get(soccerTeam.getTeamName()).getTotalPoints();
            soccerRankingMap.get(soccerTeam.getTeamName()).setTotalPoints(currentPoints + points);

        } else {
            soccerTeam.setTotalPoints(points);
            soccerRankingMap.put(soccerTeam.getTeamName(), soccerTeam);

        }
    }

    /**
     * Accessor for getting the last processed results in a sort order.
     *
     * @return List<SoccerTeam>
     */
    public List<SoccerTeam> getSortedResults() {
        return sortedResults;
    }
}

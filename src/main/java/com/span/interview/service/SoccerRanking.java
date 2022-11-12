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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class SoccerRanking implements Ranking<SoccerMatch> {

    private final Map<String, SoccerTeam> soccerRankingMap;

    public SoccerRanking(){
        this.soccerRankingMap = new HashMap<>();
    }

    @Override
    public void processMatchList(List<SoccerMatch> soccerMatchList) {
        soccerMatchList.forEach(sm -> {
            if(sm.getGoalsForLocal() == sm.getGoalsForVisitor()){
                updateRanking(sm.getLocalTeam(),1);
                updateRanking(sm.getVisitorTeam(), 1);

                //Tied matches
                //m.getLocalTeam().setTiedMatches(sm.getVisitorTeam().getTiedMatches() + 1);
                //sm.getVisitorTeam().setTiedMatches(sm.getVisitorTeam().getTiedMatches() + 1);

            } else if (sm.getGoalsForLocal() > sm.getGoalsForVisitor()){
                updateRanking(sm.getLocalTeam(),3);
                updateRanking(sm.getVisitorTeam(),0);
                //Won matches for local team
                //sm.getLocalTeam().setWonMatches(sm.getVisitorTeam().getTiedMatches() + 1);
            } else {
                updateRanking(sm.getLocalTeam(),0);
                updateRanking(sm.getVisitorTeam(), 3);

                //Won matches for visitor team
                //sm.getVisitorTeam().setWonMatches(sm.getVisitorTeam().getTiedMatches() + 1);
            }
        });
    }

    @Override
    public void printRanking(){
        final StringBuilder stringBuilder = new StringBuilder();
        final List<SoccerTeam> orderResults = new ArrayList<>();
        soccerRankingMap.forEach((k,v) -> {
           orderResults.add(v);
        });

        final AtomicInteger counter = new AtomicInteger(0);
        orderResults.stream().sorted(new RankingOrder()).forEach(v -> {
            stringBuilder.append(counter.incrementAndGet());
            stringBuilder.append(". ");
            stringBuilder.append(v.getTeamName());
            stringBuilder.append(", ");
            stringBuilder.append(v.getTotalPoints());
            stringBuilder.append(v.getTotalPoints() == 1 ? " pt\n" : " pts\n");
        });
        System.out.println(stringBuilder.toString());
    }

    private void updateRanking(final SoccerTeam soccerTeam, final int points){

        if(soccerRankingMap.containsKey(soccerTeam.getTeamName())) {
            int currentPoints = soccerRankingMap.get(soccerTeam.getTeamName()).getTotalPoints();
            soccerRankingMap.get(soccerTeam.getTeamName()).setTotalPoints(currentPoints + points);

        } else {
            soccerTeam.setTotalPoints(points);
            soccerRankingMap.put(soccerTeam.getTeamName(),soccerTeam);

        }
    }

}

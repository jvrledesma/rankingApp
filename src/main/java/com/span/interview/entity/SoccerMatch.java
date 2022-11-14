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
 * Concrete class that represents a soccer match. The soccer match is composed by:
 * - A local team
 * - A visitor team
 * - The number of goals for each one respectively
 *
 * @author Javier Salgado
 */
@Getter
@Setter
public class SoccerMatch extends Match<SoccerTeam> {
    private int goalsForLocal;
    private int goalsForVisitor;

    /**
     * Constructs a soccer match
     *
     * @param localTeam   the local team for the match
     * @param visitorTeam the visitor team for the match
     */
    public SoccerMatch(SoccerTeam localTeam, SoccerTeam visitorTeam) {
        super(localTeam, visitorTeam);
    }

    /**
     * Constructs a soccer match
     *
     * @param localTeam       the local team for the match
     * @param visitorTeam     the visitor team for the match
     * @param goalsForLocal   the number of goals for the local team
     * @param goalsForVisitor the number of goals for the visitor team
     */
    public SoccerMatch(SoccerTeam localTeam, SoccerTeam visitorTeam, int goalsForLocal, int goalsForVisitor) {
        super(localTeam, visitorTeam);
        this.goalsForLocal = goalsForLocal;
        this.goalsForVisitor = goalsForVisitor;
    }

}

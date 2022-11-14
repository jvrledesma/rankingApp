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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class to represent the general properties of a Team, it could be extended depending on any new requirement
 * for managing basketball, football, volleyball, or other king of sports teams.
 * <p>
 * In a league, a team has the properties:
 * - Team name
 * - The number of won matches
 * - The number of loss matches
 * - The total points in the league
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class Team {
    private String teamName;
    private int wonMatches;
    private int lossMatches;
    private int totalPoints;
}

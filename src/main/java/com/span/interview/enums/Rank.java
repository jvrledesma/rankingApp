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

package com.span.interview.enums;

import com.span.interview.entity.SoccerMatch;
import com.span.interview.service.Ranking;
import com.span.interview.service.SoccerRanking;

/**
 * Enumeration for supporting the factory pattern for building objects of type Ranking (to process statistics).
 * This enumeration is open for adding new objects as required.
 *
 * @author Javier Salgado
 */
public enum Rank {

    SOCCER_RANKING {
        @Override
        public Ranking<SoccerMatch> make() {
            return new SoccerRanking();
        }
    };

    public abstract <T> Ranking<T> make();
}

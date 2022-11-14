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

import com.span.interview.enums.ErrorCode;
import com.span.interview.enums.MatchType;
import com.span.interview.enums.Rank;
import com.span.interview.exception.RankingAppException;
import com.span.interview.service.Ranking;

/**
 * The file ranking factory, creates objects depending on the match type.
 *
 * @author Javier Salgado
 */
public class RankingFactory {

    public RankingFactory() {
        //Empty constructor
    }

    /**
     * Method to build and get the concrete ranking object.
     *
     * @param matchType the required match type for building the objects
     * @param <T>       the match type
     * @return the concrete class built
     * @throws RankingAppException if there is no current implementation for the requested parameters.
     */
    public <T> Ranking<T> getRankingProcessor(final MatchType matchType) throws RankingAppException {

        if (matchType == MatchType.SOCCER) {
            return Rank.SOCCER_RANKING.make();
        }

        throw new RankingAppException("Ranking processor not found", ErrorCode.RANKING_PROCESSOR_NOT_FOUND,
                new UnsupportedOperationException("The operation requested is not yet supported by Ranking App"));
    }

}

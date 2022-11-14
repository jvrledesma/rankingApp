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
import com.span.interview.exception.RankingAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for SoccerRanking.
 *
 * @author Javier Salgado
 */
class SoccerRankingTest {

    @Test
    @DisplayName("Processing a valid list of soccer matches and print results")
    void processMatchList_ByGivenListOfSoccerMatch_SuccessCalculation() throws RankingAppException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleValidInput.txt")).getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());

        final SoccerRanking soccerRanking = new SoccerRanking();
        soccerRanking.processMatchList(soccerMatchList);
        assertFalse(soccerRanking.getSoccerRankingMap().isEmpty());
        soccerRanking.printRanking();
        assertFalse(soccerRanking.getSortedResults().isEmpty());
    }

    @Test
    @DisplayName("Processing empty list of soccer matches wont provoke errors")
    void processMatchList_ByGivenEmptyList_NoProcessDone() {
        final List<SoccerMatch> soccerMatchList = Collections.emptyList();

        final SoccerRanking soccerRanking = new SoccerRanking();
        soccerRanking.processMatchList(soccerMatchList);
        assertTrue(soccerRanking.getSoccerRankingMap().isEmpty());
    }

    @Test
    @DisplayName("Processing null list of soccer matches wont provoke errors")
    void processMatchList_ByGivenNullList_NoProcessDone() {

        final SoccerRanking soccerRanking = new SoccerRanking();
        soccerRanking.processMatchList(null);
        assertTrue(soccerRanking.getSoccerRankingMap().isEmpty());
    }
}
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

import com.span.interview.entity.SoccerMatch;
import com.span.interview.enums.ErrorCode;
import com.span.interview.enums.MatchType;
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;
import com.span.interview.service.FileProcessor;
import com.span.interview.service.Ranking;
import com.span.interview.service.SoccerRanking;
import com.span.interview.service.TxtSoccerFileProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ranking factory
 *
 * @author Javier Salgado
 */
class RankingFactoryTest {

    @Test
    @DisplayName("Test retrieving a Txt File processor and Ranking class by factory and simulate a successful flow")
    void getRankingProcessor_GivenParameters_SuccessfullyGetRankingProcessor() throws RankingAppException {
        final RankingFactory rankingFactory = new RankingFactory();
        final FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();

        final Ranking<SoccerMatch> soccerMatchRanking = rankingFactory.getRankingProcessor(MatchType.SOCCER);
        final FileProcessor<SoccerMatch> txtSoccerFileProcessor =
                fileProcessorFactory.getProcessor(MatchType.SOCCER, SupportedFileExtension.TXT);

        assertInstanceOf(SoccerRanking.class, soccerMatchRanking);
        assertInstanceOf(TxtSoccerFileProcessor.class, txtSoccerFileProcessor);

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleValidInput.txt")).getFile());

        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());

        final SoccerRanking soccerRanking = new SoccerRanking();
        soccerRanking.processMatchList(soccerMatchList);
        assertFalse(soccerRanking.getSoccerRankingMap().isEmpty());

    }

    @Test
    @DisplayName("Test to process ranking with an unsupported Match type and expect an exception")
    void getRankingProcessor_GivenParameters_ThrowRankingAppException() {
        final RankingFactory rankingFactory = new RankingFactory();
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                rankingFactory.getRankingProcessor(MatchType.BASKETBALL));

        assertEquals(ErrorCode.RANKING_PROCESSOR_NOT_FOUND, rankingAppException.getErrorCode());
        assertInstanceOf(UnsupportedOperationException.class, rankingAppException.getCause());
    }

}
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
import com.span.interview.service.TxtSoccerFileProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for FileProcessorFactory
 *
 * @author Javier Salgado
 */
class FileProcessorFactoryTest {

    @Test
    @DisplayName("Get Txt Soccer File processor by file processor factory and check that it successfully process a file")
    void getProcessor_GivenParameters_SuccessfullyGetFileProcessor() throws RankingAppException {
        final FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();
        final FileProcessor<SoccerMatch> txtSoccerFileProcessor = fileProcessorFactory.getProcessor(MatchType.SOCCER, SupportedFileExtension.TXT);

        assertInstanceOf(TxtSoccerFileProcessor.class,txtSoccerFileProcessor);

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleValidInput.txt")).getFile());

        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());

        assertFalse(soccerMatchList.isEmpty());
        assertEquals(6, soccerMatchList.size());
    }

    @Test
    @DisplayName("Try to get a Txt Soccer File processor by file processor factory with incorrect parameters to receive exception")
    void getProcessor_GivenWrongTypeAndExtensionParameters_ThrowRankingAppException() {
        final FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                fileProcessorFactory.getProcessor(MatchType.BASKETBALL, SupportedFileExtension.JSON));

        assertEquals(ErrorCode.FILE_PROCESSOR_NOT_FOUND, rankingAppException.getErrorCode());
        assertInstanceOf(UnsupportedOperationException.class, rankingAppException.getCause());
    }

    @Test
    @DisplayName("Try to get a Txt Soccer File processor by file processor factory with not supported file extension to receive exception")
    void getProcessor_GivenWrongExtensionParameters_ThrowRankingAppException() {
        final FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                fileProcessorFactory.getProcessor(MatchType.SOCCER, SupportedFileExtension.JSON));

        assertEquals(ErrorCode.FILE_PROCESSOR_NOT_FOUND, rankingAppException.getErrorCode());
        assertInstanceOf(UnsupportedOperationException.class, rankingAppException.getCause());
    }
}
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
import com.span.interview.enums.ErrorCode;
import com.span.interview.exception.RankingAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for TxtSoccerFile processor.
 *
 * @author Javier Salgado
 */
class TxtSoccerFileProcessorTest {

    @Test
    @DisplayName("Exception retrieved when the request file does not exist")
    void process_GivenNotExistingFilePath_ExpectException() {
        TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                txtSoccerFileProcessor.process("/this/is/a/fake/path/file.json"));

        assertEquals(rankingAppException.getErrorCode().getCode(), ErrorCode.FILE_NOT_EXISTS.getCode());
        assertEquals(rankingAppException.getErrorCode().getErrorMessage(), ErrorCode.FILE_NOT_EXISTS.getErrorMessage());
    }


    @Test
    @DisplayName("Processing a file and successfully retrieve the list of match objects")
    void process_GivenCorrectInput_DeliversListOfSoccerMatches() throws RankingAppException {

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleValidInput.txt")).getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());

        assertFalse(soccerMatchList.isEmpty());
        assertEquals(6, soccerMatchList.size());

        /*Check against the first match in the test file
         *  Local - Visitor
         * Lions 3, Snakes 3
         */
        final SoccerMatch soccerMatch = soccerMatchList.get(0);
        assertEquals("Lions", soccerMatch.getLocalTeam().getTeamName());
        assertEquals("Snakes", soccerMatch.getVisitorTeam().getTeamName());
        assertEquals(3, soccerMatch.getGoalsForLocal());
        assertEquals(3, soccerMatch.getGoalsForVisitor());
    }

    @Test
    @DisplayName("Processing a file with malformed data will return empty list")
    void process_GivenFileWithMalformedData_DeliversAnEmptyList() throws RankingAppException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleMalformedInput.txt")).getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());

        assertTrue(soccerMatchList.isEmpty());
    }

    @Test
    @DisplayName("Processing a random file with not related data will return empty list")
    void process_GivenFileWithUnrelatedData_DeliversAnEmptyList() throws RankingAppException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("RandomFile.txt")).getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());

        assertTrue(soccerMatchList.isEmpty());
    }

    @Test
    @DisplayName("Processing a unix format file with successful processing")
    void process_GivenFileWithUnixFormat_SuccessfulProcessing() throws RankingAppException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("TxtSampleUnix.txt")).getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());

        assertFalse(soccerMatchList.isEmpty());

    }

    @Test
    @DisplayName("Processing a binary file with changed extension, throws exception")
    void process_GivenBinaryFileWithChangedExtension_ThrowsRankingAppException() {

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("span.txt")).getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                txtSoccerFileProcessor.process(file.getAbsolutePath()));

        assertEquals("Error in TxtSoccerFIleProcessor", rankingAppException.getMessage());
        assertEquals(ErrorCode.IO_ERROR, rankingAppException.getErrorCode());
        assertTrue(rankingAppException.getCause() instanceof java.io.UncheckedIOException);
    }

    @Test
    @DisplayName("Processing a 200k registries file with successful processing in less than 1 second")
    void process_GivenFileWith200kRegistries_SuccessfulProcessing() throws RankingAppException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("Sample200k.txt")).getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();

        final Instant start = Instant.now();
        final List<SoccerMatch> soccerMatchList = txtSoccerFileProcessor.process(file.getAbsolutePath());
        final Instant end = Instant.now();

        assertTrue(Duration.between(start, end).getSeconds() < 2);
        assertFalse(soccerMatchList.isEmpty());

    }
}
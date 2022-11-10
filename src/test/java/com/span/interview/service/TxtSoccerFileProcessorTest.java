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

import com.span.interview.enums.ErrorCode;
import com.span.interview.exception.RankingAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TxtSoccerFileProcessorTest {

    @Test
    @DisplayName("Exception retrieved when TXT file processor receives a different file extension")
    void process_GivenIncorrectFileExtension_ExpectException(){
        TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                txtSoccerFileProcessor.process("/this/is/a/fake/path/file.json"));

        assertEquals(rankingAppException.getErrorCode().getCode(), ErrorCode.WRONG_FILE_EXTENSION.getCode());
        assertEquals(rankingAppException.getErrorCode().getErrorMessage(), ErrorCode.WRONG_FILE_EXTENSION.getErrorMessage());
    }

    @Test
    @DisplayName("Exception retrieved when a wrong file path has been provided")
    void process_GivenIncorrectFilePath_ExpectException(){
        TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                txtSoccerFileProcessor.process("/this/is/a/fake/path/file.txt"));

        assertEquals(rankingAppException.getErrorCode().getCode(), ErrorCode.IO_ERROR.getCode());
        assertEquals(rankingAppException.getErrorCode().getErrorMessage(), ErrorCode.IO_ERROR.getErrorMessage());
    }


    @Test
    @DisplayName("Successfully retrieve the list of match objects")
    void process_GivenCorrectInput_DeliversListOfSoccerMatches() throws RankingAppException {

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource("SampleValidInput.txt").getFile());

        final TxtSoccerFileProcessor txtSoccerFileProcessor = new TxtSoccerFileProcessor();
        //txtSoccerFileProcessor.process(file.getAbsolutePath());
        assertTrue(txtSoccerFileProcessor.process(file.getAbsolutePath()).isEmpty());
    }
}
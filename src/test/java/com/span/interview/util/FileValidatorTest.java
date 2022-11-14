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
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FileValidator tests cases.
 *
 * @author Javier Salgado
 */
class FileValidatorTest {

    @Test
    @DisplayName("Successful validation of file extension - txt")
    void isValidTest_ExpectedTrue_TxtExtension() {
        assertTrue(FileValidator.isValidExtension("/this/is/a/fake/path/file.txt", SupportedFileExtension.TXT));
    }

    @Test
    @DisplayName("Successful validation of wrong extension - Used txt when expected json")
    void isValidTest_ExpectedFalse_WrongExtension() {
        assertFalse(FileValidator.isValidExtension("/this/is/a/fake/path/file.txt", SupportedFileExtension.JSON));
    }

    @Test
    @DisplayName("Test set of validation with a correct file name and requested extension")
    void isValidFile_GivenAValidTxtFile_ReturnSuccess() throws RankingAppException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleValidInput.txt")).getFile());

        assertTrue(FileValidator.isValidFile(file.getAbsolutePath(), SupportedFileExtension.TXT));
    }

    @Test
    @DisplayName("Test set of validation with an incorrect file path, exception expected")
    void isValidFile_GivenAInvalidPath_ReturnFalse() {

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleValidInput.txt")).getFile());
        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                FileValidator.isValidFile(file.getParent(), SupportedFileExtension.TXT));

        assertEquals("Found directory", rankingAppException.getMessage());
        assertEquals(ErrorCode.DIRECTORY_NOT_SUPPORTED, rankingAppException.getErrorCode());

    }

    @Test
    @DisplayName("Test set of validation with a directory file path, exception expected")
    void isValidFile_GivenADirectory_ReturnFalse() {

        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                FileValidator.isValidFile("/this/is/a/fake/path/file.txt", SupportedFileExtension.TXT));

        assertEquals("Missing file", rankingAppException.getMessage());
        assertEquals(ErrorCode.FILE_NOT_EXISTS, rankingAppException.getErrorCode());

    }

    @Test
    @DisplayName("Test set of validation with a binary file, exception expected")
    void isValidFile_GivenABinaryFile_ReturnFalse() {

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("span.jpeg")).getFile());

        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                FileValidator.isValidFile(file.getAbsolutePath(), SupportedFileExtension.TXT));

        assertEquals("Binary found", rankingAppException.getMessage());
        assertEquals(ErrorCode.UNSUPPORTED_MIME_TYPE, rankingAppException.getErrorCode());

    }

    @Test
    @DisplayName("Test set of validation with a valid file path")
    void isValidFile_GivenAFileWithDifferentExtension_ReturnFalse() {

        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource("SampleValidInput.txt")).getFile());

        final RankingAppException rankingAppException = assertThrows(RankingAppException.class, () ->
                FileValidator.isValidFile(file.getAbsolutePath(), SupportedFileExtension.JSON));

    }
}
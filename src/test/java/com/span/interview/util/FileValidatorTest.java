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

import com.span.interview.enums.SupportedFileExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileValidatorTest {

    @Test
    @DisplayName("Successful validation of file extension - txt")
    void isValidTest_ExpectedTrue_TxtExtension(){
        assertTrue(FileValidator.isValidExtension("/this/is/a/fake/path/file.txt", SupportedFileExtension.TXT));
    }

    @Test
    @DisplayName("Successful validation of wrong extension - Used txt when expected json")
    void isValidTest_ExpectedFalse_WontExtension(){
        assertFalse(FileValidator.isValidExtension("/this/is/a/fake/path/file.txt", SupportedFileExtension.JSON));
    }
}
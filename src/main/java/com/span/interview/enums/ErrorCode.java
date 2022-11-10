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

import lombok.Getter;

@Getter
public enum ErrorCode {

    //File related exceptions
    WRONG_FILE_EXTENSION("F001", "The provided file has a not supported extension."),
    IO_ERROR("F002", "There was a problem when trying to open and read the file.");

    private final String code;
    private final String errorMessage;

    ErrorCode(final String code, final String errorMessage){
        this.code = code;
        this.errorMessage = errorMessage;
    }

}

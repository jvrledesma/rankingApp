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

/**
 * Enumeration for managing the possible error codes and their respective message.
 * Some errors that could happen during the execution of the app are concentrated here.
 *
 * @author Javier Salgado
 */
@Getter
public enum ErrorCode {

    //File related errors
    FILE_NOT_EXISTS("F001", "The file for the provided path does not exist, please verify."),
    DIRECTORY_NOT_SUPPORTED("F002", "Please provide a path to a file, directories are not supported."),
    UNSUPPORTED_MIME_TYPE("F003", "Binary files are not supported."),
    WRONG_FILE_EXTENSION("F004", "Specify a valid path and with a valid extension."),
    IO_ERROR("F005", "There was a problem when trying to access/open/read the file."),

    //Logic related errors
    FILE_PROCESSOR_NOT_FOUND("R001", "There is no file processor associated with your parameters"),
    RANKING_PROCESSOR_NOT_FOUND("R002", "There is no ranking processor associated with your parameters");

    private final String code;
    private final String errorMessage;

    ErrorCode(final String code, final String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

}

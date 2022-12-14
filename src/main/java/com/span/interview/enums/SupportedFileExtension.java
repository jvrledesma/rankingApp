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
 * Enumeration for specifying the type of files tha can be processed to get information from matches.
 * This enumeration is open for adding new objects as required.
 *
 * @author Javier Salgado
 */
@Getter
public enum SupportedFileExtension {
    TXT("txt"),
    JSON("json");

    private final String value;

    SupportedFileExtension(final String value) {
        this.value = value;
    }
}

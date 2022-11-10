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

package com.span.interview.exception;

import com.span.interview.enums.ErrorCode;
import lombok.Getter;

@Getter
public class RankingAppException extends Exception{

    private final ErrorCode errorCode;

    public RankingAppException(final String errorMessage, final ErrorCode errorCode){
        super(errorMessage);
        this.errorCode=errorCode;
    }

    public RankingAppException(final String errorMessage, final ErrorCode errorCode, final Throwable err) {
        super(errorMessage, err);
        this.errorCode=errorCode;
    }
}

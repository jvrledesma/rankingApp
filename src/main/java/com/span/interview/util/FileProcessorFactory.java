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
import com.span.interview.enums.MatchType;
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;
import com.span.interview.service.FileProcessor;
import com.span.interview.service.TxtSoccerFileProcessor;

public class FileProcessorFactory {

    private FileProcessorFactory(){}

    //TODO: Manage nulls in a better way
    public static FileProcessor getFileProcessorInstance(final MatchType matchType, final String filePath) throws RankingAppException {
        switch (matchType){
            case SOCCER:
                return getSoccerFileProcessor(filePath);
            case FOOTBALL:
                //TODO: Throw a not implemented exception
                return null;
            case BASKETBALL:
                //TODO: Throw a not implemented exception
                return null;
            default:
                return null;
        }
    }

    private static FileProcessor getSoccerFileProcessor(final String filePath) throws RankingAppException {
        if(FileValidator.isValidExtension(filePath, SupportedFileExtension.TXT)){
            return new TxtSoccerFileProcessor();
        }

        throw new RankingAppException("FileProcessor getSoccerFileProcessor", ErrorCode.WRONG_FILE_EXTENSION);

    }
}

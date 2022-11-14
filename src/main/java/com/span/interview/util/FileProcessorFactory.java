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
import com.span.interview.enums.Processor;
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;
import com.span.interview.service.FileProcessor;

/**
 * The file processor factory, creates objects depending on the match type and the supported file extension.
 *
 * @author Javier Salgado
 */
public class FileProcessorFactory {

    public FileProcessorFactory() {
        //Empty constructor
    }

    /**
     * Method to build and get the concrete file processor object.
     *
     * @param matchType              the required match type for building the objects
     * @param supportedFileExtension the supported file extension for the objects
     * @param <T>                    the match type
     * @return the concrete class built
     * @throws RankingAppException if there is no current implementation for the requested parameters.
     */
    public <T> FileProcessor<T> getProcessor(final MatchType matchType,
                                             final SupportedFileExtension supportedFileExtension) throws RankingAppException {

        if (matchType == MatchType.SOCCER && SupportedFileExtension.TXT == supportedFileExtension) {
            return Processor.TXT_PROCESSOR.make();
        }

        throw new RankingAppException("File processor not found", ErrorCode.FILE_PROCESSOR_NOT_FOUND,
                new UnsupportedOperationException("The operation requested is not yet supported by Ranking App"));
    }

}

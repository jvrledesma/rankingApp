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
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;
import com.span.interview.util.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class TxtSoccerFileProcessor implements FileProcessor<SoccerMatch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxtSoccerFileProcessor.class);

    /**
     * Method that performs the txt file processing containing the information of soccer matches in the format:
     *  Lions 3, Snakes 3
     *  Tarantulas 1, FC Awesome 0
     *  Lions 1, FC Awesome 1
     *  Tarantulas 3, Snakes 1
     *  Lions 4, Grouches 0
     *  ...
     * @param filePath the path of the file to read
     * @return List<SoccerMatch>
     */
    @Override
    public List<SoccerMatch> process(final String filePath) throws RankingAppException {

        LOGGER.info("Processing file: {}", filePath);

        //As this file processor could be used independently, validation for file format has to be done
        if(!FileValidator.isValidExtension(filePath, SupportedFileExtension.TXT)){
            throw new RankingAppException("FileProcessor getSoccerFileProcessor", ErrorCode.WRONG_FILE_EXTENSION);
        }

        //If file validation is succeeded the process the file
        try(final Stream<String> lines = Files.lines(Paths.get(filePath))) {

            lines.forEach(System.out::println);//print each line

        } catch (final IOException ioException) {

            throw new RankingAppException("Error in TxtSoccerFIleProcessor", ErrorCode.IO_ERROR, ioException);
        }

        LOGGER.info("Processing file: {}", filePath);

        return Collections.emptyList();
    }
}

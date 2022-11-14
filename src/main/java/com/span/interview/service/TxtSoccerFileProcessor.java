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
import com.span.interview.entity.SoccerTeam;
import com.span.interview.enums.ErrorCode;
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;
import com.span.interview.util.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Specialized implementation of FileProcessor as txt format, associated with SoccerMatch types.
 *
 * @author Javier Salgado
 */
public class TxtSoccerFileProcessor implements FileProcessor<SoccerMatch> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TxtSoccerFileProcessor.class);
    private static final String SEPARATOR_PATTERN = "\\s,\\s+|\\s+,|,\\s+|\\s;\\s+|\\s+;|;\\s+";
    private static final Pattern SOCCER_MATCH_INFO_PATTERN = Pattern.compile("([a-zA-Z\\s]*) ([\\d]),([a-zA-Z\\s]*) ([\\d])");

    /**
     * Method that performs the txt file processing containing the information of soccer matches in the format:
     * <p>
     * Lions 3, Snakes 3
     * Tarantulas 1, FC Awesome 0
     * Lions 1, FC Awesome 1
     * Tarantulas 3, Snakes 1
     * Lions 4, Grouches 0
     * ...
     * <p>
     * The considerations for processing the file are:
     * - Validate that file has the correct extension, txt, could be considered redundant validation but since this
     * class can be used by different flows is better to take care of it.
     * - For performance, in case a large file is processed, process the file as stream, this will load items in a
     * lazy loading fashion
     * - The stream processing includes:
     * - String normalization, considering everything as lower-case
     * - Replace spaces before and after comma and colon in case file has a different separator
     * - This processing will deliver lines in a format like "lions 3,snakes 3"
     * - A pattern matcher is used to separate each group in the line "text" "number","text" "number" (2 groups
     * before and after separator)
     * - In case of lines that does not match, a null object will be returned and will be filtered in the pipeline
     * to be discarded
     * - Terminal operation returns the final list of SoccerMatch objects.
     *
     * @param filePath the path of the file to read
     * @return List<SoccerMatch> the list of SoccerMatch objects
     * @throws RankingAppException if there is a problem processing the requested file
     */
    @Override
    public List<SoccerMatch> process(final String filePath) throws RankingAppException {

        //Will throw an exception if something goes wrong
        FileValidator.isValidFile(filePath, SupportedFileExtension.TXT);

        //If file validation is succeeded the process the file
        try (final Stream<String> fileLines = Files.lines(Paths.get(filePath))) {

            return fileLines
                    .map(fileLine -> fileLine.replaceAll(SEPARATOR_PATTERN, ","))
                    .map(this::getSoccerMatch)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (final IOException | UncheckedIOException ioException) {

            throw new RankingAppException("Error in TxtSoccerFIleProcessor", ErrorCode.IO_ERROR, ioException);
        }

    }

    /**
     * This method will process the text line extracted from the file stream processing.
     * It will use a Pattern to extract the information for local and visitor teams, this information will be used
     * to construct the SoccerMatch object.
     * <p>
     * If the pattern matcher gets info, there will be 4 groups returned:
     * - Group 1: Local team name
     * - Group 2: Local team goals
     * - Group 3: Visitor team name
     * - Group 4: Visitor team goals
     * <p>
     * This information will be used to construct each team info and the construct the soccer match.
     * <p>
     * Malformed lines, which does not match the pattern will be logged as warning and skipped, null will be returned,
     * this null must be filtered in the pipeline processing.
     *
     * @param fileLine the file line to be processed to extract soccer match data
     * @return {@link SoccerMatch} object with the soccer match info
     */
    private SoccerMatch getSoccerMatch(final String fileLine) {
        final Matcher matcher = SOCCER_MATCH_INFO_PATTERN.matcher(fileLine);
        if ( matcher.matches()) {
            final SoccerTeam soccerTeamA = new SoccerTeam(matcher.group(1), 0, 0, 0, 0);
            final SoccerTeam soccerTeamB = new SoccerTeam(matcher.group(3), 0, 0, 0, 0);

            return new SoccerMatch(soccerTeamA, soccerTeamB,
                    Short.parseShort(matcher.group(2)), Short.parseShort(matcher.group(4)));
        }

        //If there is no match based on pattern, log a warning message
        LOGGER.warn("Malformed line {} won't be processed", fileLine);
        return null;
    }
}

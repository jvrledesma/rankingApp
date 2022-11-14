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

package com.span.interview;

import com.span.interview.entity.SoccerMatch;
import com.span.interview.enums.MatchType;
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;
import com.span.interview.service.FileProcessor;
import com.span.interview.service.Ranking;
import com.span.interview.util.FileProcessorFactory;
import com.span.interview.util.RankingFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class RankingApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(RankingApp.class);

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Proper Usage is: java RankingApp filePath SPORT FILE_EXTENSION");
        }else {

            final RankingFactory rankingFactory = new RankingFactory();
            final FileProcessorFactory fileProcessorFactory = new FileProcessorFactory();
            try {
                final FileProcessor<SoccerMatch> txtSoccerFileProcessor =
                        fileProcessorFactory.getProcessor(MatchType.valueOf(args[1]), SupportedFileExtension.valueOf(args[2]));
                final Ranking<SoccerMatch> soccerMatchRanking = rankingFactory.getRankingProcessor(MatchType.SOCCER);

                processFileAndRanking(txtSoccerFileProcessor, soccerMatchRanking, args[0]);

            } catch (final RankingAppException rankingAppException) {
                System.out.println(rankingAppException.getErrorCode().getCode() + " " + rankingAppException.getErrorCode().getErrorMessage());
                LOGGER.error("Something wrong happened {} - {}", rankingAppException.getErrorCode().getCode(),
                        rankingAppException.getErrorCode().getErrorMessage());
            }
        }
    }

    private static void processFileAndRanking(final FileProcessor<SoccerMatch> txtSoccerFileProcessor,
                                              final Ranking<SoccerMatch> soccerMatchRanking, final String filePath) {
        try {
            soccerMatchRanking.processMatchList(txtSoccerFileProcessor.process(filePath));
            soccerMatchRanking.printRanking();
        } catch (final RankingAppException rankingAppException) {
            System.out.println(rankingAppException.getErrorCode().getCode() + " " + rankingAppException.getErrorCode().getErrorMessage());
            LOGGER.error("Something wrong happened {} - {}", rankingAppException.getErrorCode().getCode(),
                    rankingAppException.getErrorCode().getErrorMessage());
        }
    }
}

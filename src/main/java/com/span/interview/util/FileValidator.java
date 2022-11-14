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
import com.span.interview.enums.SupportedFileExtension;
import com.span.interview.exception.RankingAppException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for performing validations associated with file retrieval.
 *
 * @author Javier Salgado
 */
public class FileValidator {

    private FileValidator() {
    }

    /**
     * Determines if the path to a file points to a file with the required extension
     *
     * @param filePath  the file's path.
     * @param extension the required extension for the file.
     * @return boolean, if the extension matches or not.
     */
    public static boolean isValidExtension(final String filePath, final SupportedFileExtension extension) {
        return filePath.endsWith(extension.getValue());
    }

    /**
     * Performs a set of validations which include:
     * - File exists
     * - If path points to a directory instead of a file
     * - If mime type is supported(no binary files)
     * - If extension is correct
     * <p>
     * Will throw a {@link RankingAppException} if a validation fails.
     *
     * @param filePath  the file's path.
     * @param extension the required extension for the file.
     * @return boolean if validation succeeded or not.
     * @throws RankingAppException if validation fails or requested extension is not supported yet.
     */
    public static boolean isValidFile(final String filePath, final SupportedFileExtension extension) throws RankingAppException {
        final Path path = Paths.get(filePath);

        if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
            throw new RankingAppException("Missing file", ErrorCode.FILE_NOT_EXISTS);
        }

        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            throw new RankingAppException("Found directory", ErrorCode.DIRECTORY_NOT_SUPPORTED);
        }

        try {
            if (!Files.probeContentType(path).startsWith("text/")) {
                throw new RankingAppException("Binary found", ErrorCode.UNSUPPORTED_MIME_TYPE);
            }
        } catch (final IOException | SecurityException ioe) {
            throw new RankingAppException("Error accessing the file", ErrorCode.IO_ERROR, ioe);
        }

        if (!filePath.endsWith(extension.getValue())) {
            throw new RankingAppException("Wrong extension", ErrorCode.WRONG_FILE_EXTENSION);
        }

        return true;
    }
}

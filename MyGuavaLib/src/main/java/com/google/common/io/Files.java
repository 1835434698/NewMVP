package com.google.common.io;

import com.google.common.annotations.Beta;

import java.io.File;

import static com.google.common.base.Predicates.checkNotNull;

public class Files {


    /**
     * Returns the <a href="http://en.wikipedia.org/wiki/Filename_extension">file extension</a> for
     * the given file name, or the empty string if the file has no extension. The result does not
     * include the '{@code .}'.
     *
     * <p><b>Note:</b> This method simply returns everything after the last '{@code .}' in the file's
     * name as determined by {@link File#getName}. It does not account for any filesystem-specific
     * behavior that the {@link File} API does not already account for. For example, on NTFS it will
     * report {@code "txt"} as the extension for the filename {@code "foo.exe:.txt"} even though NTFS
     * will drop the {@code ":.txt"} part of the name when the file is actually created on the
     * filesystem due to NTFS's <a href="https://goo.gl/vTpJi4">Alternate Data Streams</a>.
     *
     * @since 11.0
     */
    @Beta
    public static String getFileExtension(String fullName) {
        checkNotNull(fullName);
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}

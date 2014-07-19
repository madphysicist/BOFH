/*
 * BOFHExcuseModel.java (Class: com.madphysicist.bofh.BOFHExcuseModel)
 *
 * Mad Physicist BOFH Excuse Generator Project
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 by Joseph Fox-Rabinovitz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.madphysicist.bofh;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * Implements a multi-column model for a BOFH excuse generator. To generate a
 * BOFH excuse, one word (any word) should be selected from each column. The
 * excuse model can be configured from a set of properties files or directly
 * from arrays of strings. The contents of this class are immutable.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0.0, 11 Feb 2013
 * @since 1.0.0.0
 */
public class BOFHExcuseModel implements Serializable, Iterable<List<String>>
{
    /**
     * The version ID for serialization.
     *
     * @serial Increment the least significant three digits when compatibility
     * is not compromised by a structural change (e.g. adding a new field with
     * a sensible default value), and the upper digits when the change makes
     * serialized versions of of the class incompatible with previous releases.
     * @since 1.0.0.0
     */
    private static final long serialVersionUID = 1000L;

    /**
     * Lines in the configuration file starting with this prefix after optional
     * whitespace will be regarded as comments and ignored when loading column
     * data from a file. The value of this constant is {@value}. Empty lines and
     * lines containing only whitespace will also be ignored.
     *
     * @since 1.0.0.0
     */
    public static final String COMMENT_PREFIX = "#";

    /**
     * A {@code Random} object used to generate random excuses. The {@link
     * Random#nextInt(int)} method is used to pick an index within each column.
     *
     * @see #getRandomExcuse()
     * @since 1.0.0.0
     */
    private static final Random RANDOMIZER = new Random();

    /**
     * An array of the default files that is expected to provide lists of words
     * for each column if a user-defined list is not provided. The number of
     * elements in this array is the number of columns in the default model. The
     * defaults should contain the classical BOFH excuses.
     *
     * @since 1.0.0.0
     */
    private static final String[] DEFAULT_FILES = new String[] {"A", "B", "C", "D"};

    /**
     * A list of the columns and their contents. This list is unmodifiable, so
     * it can be passed outside the class and used freely. The lists it contains
     * for each column are similarly unmodifiable.
     *
     * @serial
     * @since 1.0.0.0
     */
    private final List<List<String>> excuseLists;

    /**
     * Creates a default excuse model based on default configutation files. The
     * defaults should contain the original version of the BOFH excuse model.
     *
     * @throws IOException if the default lists could not be found, opened or
     * read.
     * @since 1.0.0.0
     */
    public BOFHExcuseModel() throws IOException
    {
        this(DEFAULT_FILES);
    }

    /**
     * Constructs a model form the specified file list. Files are expected to
     * be lists of words separated by newlines. All empty or whitespace only
     * lines as well as lines prefixed by {@link #COMMENT_PREFIX} following
     * optional whitespace will be ignored. One file must appear for each
     * column. Columns will be loaded in the same order as they appear in the
     * file list.
     *
     * @param excuseFiles a list of file names, each one defining a column of
     * the excuse model. Search for files will be done by the default system
     * class loader.
     * @throws IOException if any of the specified files could not be found,
     * opened or read.
     * @see ClassLoader#getSystemClassLoader()
     * @since 1.0.0.0
     */
    public BOFHExcuseModel(String[] excuseFiles) throws IOException
    {
        this(loadFiles(excuseFiles));
    }

    /**
     * Constructs an excuse model from user-defined arrays of excuse words. Both
     * levels of arrays are copied internally, so that subsequent changes will
     * not affect the contents of this object.
     *
     * @param excuseArrays arrays of excuse words. Each sub-array represents a
     * column in the model.
     * @since 1.0.0.0
     */
    public BOFHExcuseModel(String[][] excuseArrays)
    {
        List<List<String>> mutableList = new ArrayList<>(excuseArrays.length);
        for(String[] array : excuseArrays) {
            List<String> list = new ArrayList<>(array.length);
            list.addAll(Arrays.asList(array));
            mutableList.add(Collections.unmodifiableList(list));
        }
        this.excuseLists = Collections.unmodifiableList(mutableList);
    }

    /**
     * Creates an excuse based on the specified selection from each column. The
     * excuse can be turned into a {@code String} by invoking {@link
     * BOFHExcuse#toString()} on the result.
     *
     * @param indices an array of indices, one from each column.
     * @return an excuse encapsulating the specified selection from each column.
     * This model is the referenced by the excuse.
     * @throws IndexOutOfBoundsException if the length of indices does not match
     * the number of columns or any of the specified indices do not fall within
     * the bounds of the corresponding column.
     * @since 1.0.0.0
     */
    public BOFHExcuse getExcuse(int[] indices)
    {
        if(indices.length != excuseLists.size())
            throw new ArrayIndexOutOfBoundsException(indices.length);
        
        List<String> components = new ArrayList<>(indices.length);
        
        // initialize components (may also throw ArrayIndexOutOfBounds
        for(int index = 0; index < indices.length; index++)
            components.add(excuseLists.get(index).get(indices[index]));

        return new BOFHExcuse(components);
    }

    /**
     * Generates a random excuse from this model. A random element is picked
     * from each of the model's columns.
     *
     * @return an excuse encapsulating a random selection from each column. This
     * model is the referenced by the excuse.
     * @since 1.0.0.0
     */
    public BOFHExcuse getRandomExcuse()
    {
        int[] randomIndices = new int[excuseLists.size()];
        for(int index = 0; index < randomIndices.length; index++)
            randomIndices[index] = RANDOMIZER.nextInt(excuseLists.get(index).size());
        return getExcuse(randomIndices);
    }

    /**
     * Returns the number of columns in this model.
     *
     * @return the number of columns in this model.
     * @since 1.0.0.0
     */
    public int getColumnCount()
    {
        return excuseLists.size();
    }

    /**
     * Returns the specified column from the model. Column indices range from
     * zero (inclusive) to the column count (exclusive). The returned list is
     * immutable.
     *
     * @param index the index of the column to return.
     * @return an unmodifiable list representing the requested column of the
     * model.
     * @throws IndexOutOfBoundsException if the specified column does not exist.
     * @since 1.0.0.0
     */
    public List<String> getColumn(int index)
    {
        return excuseLists.get(index);
    }

    /**
     * Returns an iterator over the columns of the model. The elements of the
     * iterator are unmodifiable. The {@link Iterator#remove() remove()} method
     * of the iterator will not work because the underlying list of columns is
     * unmodifyable as well.
     *
     * @return an iterator over the columns of this model.
     * @since 1.0.0.0
     */
    @Override public ListIterator<List<String>> iterator()
    {
        return excuseLists.listIterator();
    }

    /**
     * Returns an iterator over the elements of a specified column. The {@link
     * Iterator#remove() remove()} method of the iterator will not work because
     * the columns are unmodifyable.
     *
     * @param column the index of the column to return. This index must be
     * between zero (inclusive) and the number of columns (exclusive).
     * @return an iterator over the elements of the requested column.
     * @throws IndexOutOfBoundsException if the specified column index does not
     * exist in this model. The valid indices are zero (inclusive) to the number
     * of columns (exclusive).
     * @see #getColumnCount()
     * @since 1.0.0.0
     */
    public ListIterator<String> columnIterator(int column)
    {
        return excuseLists.get(column).listIterator();
    }

    /**
     * Returns an informative string representation of this model. The resulting
     * string will contain information about all of the object's properties.
     * This method is useful for debugging and should not be used as a formal
     * description of this object since the contents of the string may change at
     * any time.
     *
     * @return a {@code String} representation of this object.
     * @since 1.0.0.0
     */
    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[numColumns=").append(this.excuseLists.size());
        for(int i = 0; i < this.excuseLists.size(); i++) {
            sb.append(", column_").append(i).append('=');
            sb.append(this.excuseLists.get(i).toString());
        }
        sb.append(']');
        return sb.toString();
    }

    /**
     * Loads excuse files line-by-line. Each file in the array represents a
     * column of the model. Lines are trimmed of surrounding whitespace before
     * any further processing. If a trimmed line is empty or starts with the
     * sequence specified by {@link #COMMENT_PREFIX}, it is ignored. This method
     * searches for files and resources using the default system class loader.
     *
     * @param excuseFiles an array of file names, each one of which is to be
     * used to initialize the corresponding column of the model. Files may
     * actually be named resources anywhere on the system classloader's path.
     * @return an array of string arrays. Each sub-array represents a column of
     * the model. Each string elements represents a line from one of the loaded
     * files.
     * @throws IOException if any of the files could not be found, opened or
     * @see ClassLoader#getSystemClassLoader()
     * @since 1.0.0.0
     */
    private static String[][] loadFiles(String[] excuseFiles) throws IOException
    {
        String[][] columnArray = new String[excuseFiles.length][];
        for(int index = 0; index < excuseFiles.length; index++) {

            String file = excuseFiles[index];

            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
            if(stream == null)
                throw new FileNotFoundException(file);

            List<String> excuses = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
                String line;

                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(!line.isEmpty() && !line.startsWith(COMMENT_PREFIX))
                        excuses.add(line);
                }
            }

            columnArray[index] = excuses.toArray(new String[excuses.size()]);
        }

        return columnArray;
    }
}

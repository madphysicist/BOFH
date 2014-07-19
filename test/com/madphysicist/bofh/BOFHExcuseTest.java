/*
 * BOFHExcuseTest.java (Class: com.madphysicist.bofh.BOFHExcuseTest)
 *
 * Mad Physicist BOFH Excuse Generator Project (Tests)
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

import java.util.Arrays;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This class tests the methods of {@code BOFHExcuse}.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0.0, 11 Feb 2013
 * @since 1.0.0.0
 */
public class BOFHExcuseTest
{
    @Test(dataProvider = "toStringDataProvider")
    public void testToString(String label, String[] input, String expectedOutput)
    {
        BOFHExcuse excuse = new BOFHExcuse((input == null) ? null : Arrays.asList(input));
        Assert.assertEquals(excuse.toString(), expectedOutput);
    }

    @DataProvider(name = "toStringDataProvider")
    private Object[][] toStringDataProvider()
    {
        return new Object[][] {
            {"null",         null,                                   null},
            {"empty",        new String[] {},                        ""},
            {"one null",     new String[] {null},                    ""},
            {"one empty",    new String[] {""},                      ""},
            {"one normal",   new String[] {"A"},                     "A"},
            {"multi nulls",  new String[] {"A", null, "B", "", "C"}, "A B  C"},
            {"multi normal", new String[] {"A", "B", "C"},           "A B C"},
        };
    }
}

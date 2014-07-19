/*
 * package-info.java (Package: com.madphysicist.bofh)
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

/**
 * <p>
 * The BOFH package is the root package for the BOFH excuse generator. It
 * includes all of the BOFH classes to generate and display excuses. The BOFH
 * project is not just a useful tool for getting out of work. It is also a
 * showcase for some interesting Java features as well as tools made especially
 * for the project itself.
 * </p>
 * <p>
 * The {@link com.madphysicist.bofh.BOFHMain BOFHMain} class can be used to run
 * the excuse generator as a standalone application. It also provides more
 * information about the features this package showcases.
 * </p>
 * <p>
 * The {@link com.madphysicist.bofh.BOFHExcuseGenerator BOFHExcuseGenerator} and
 * {@link com.madphysicist.bofh.BOFHColumn BOFHColumn} classes provide the GUI
 * containers to display the BOFH components. The GUI is configurable and model
 * driven.
 * </p>
 * <p>
 * The {@link com.madphysicist.bofh.BOFHExcuse BOFHExcuse} and {@link
 * com.madphysicist.bofh.BOFHExcuseModel BOFHExcuseModel} classes provide the
 * underlying data model for excuses. The model consists of a series of word
 * lists that can be used to construct random excuses by selecting one entry
 * from each list and combining them into a phrase.
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0.0, 10 Apr 2013
 * @since 1.0.0.0
 */
package com.madphysicist.bofh;

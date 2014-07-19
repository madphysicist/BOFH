/*
 * BOFHExcuse.java (Class: com.madphysicist.bofh.BOFHExcuse)
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * A BOFH excuse is a collection of terms that, when spoken in sequence, can
 * induce dummy mode among the uninitiated. This class encapsulates such a
 * sequence. This class is immutable.
 *
 * @see BOFHExcuseModel
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0.0, 11 Feb 2013
 * @since 1.0.0.0
 */
public class BOFHExcuse implements Serializable
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
     * The sequence of words that make up the excuse. This is stored internally
     * as an unmodifiable list, so it can be passed and used outside the class
     * freely.
     *
     * @serial
     * @since 1.0.0.0
     */
    private final List<String> components;

    /**
     * The model from which this excuse was generated. The columns of the model
     * will be the sources of the corresponding elements of the excuse. This
     * field may be {@code null}, in the case of a standalone excuse.
     *
     * @serial
     * @since 1.0.0.0
     */
    private final BOFHExcuseModel model;

    /**
     * Constructs a standalone excuse with the specified sequence of elements.
     * The list parameter is copied so that subsequent changes to it do not
     * affect the contents of this object.
     *
     * @param components a list of the excuse's components.
     * @since 1.0.0.0
     */
    public BOFHExcuse(List<String> components)
    {
        this(components, null);
    }

    /**
     * Constructs an excuse with the specified sequence of elements, based on a
     * specific model. The model may be {@code null} to indicate a standalone
     * excuse. The list parameter is copied so that subsequent changes to it do
     * not affect the contents of this object.
     *
     * @param components a list of the excuse's components.
     * @param model the model from which this excuse was generated, possibly
     * {@code null}.
     * @since 1.0.0.0
     */
    public BOFHExcuse(List<String> components, BOFHExcuseModel model)
    {
        if(components == null) {
            this.components = null;
        } else {
            final List<String> componentsCopy = new ArrayList<>(components.size());
            componentsCopy.addAll(components);
            this.components = Collections.unmodifiableList(componentsCopy);
        }

        this.model = model;
    }

    /**
     * Returns a list of the components of the excuse. The returned list is
     * immutable.
     *
     * @return an unmodifiable list of the components of this excuse.
     * @since 1.0.0.0
     */
    public List<String> getComponents()
    {
        return components;
    }

    /**
     * Returns the model from which this object was generated. If this is a
     * standalone excuse, the model will be {@code null}. The components of this
     * excuse will come from the columns of the model, in the same order.
     *
     * @return the model from which this excuse was generated, or {@code null}
     * if there was none.
     * @since 1.0.0.0
     */
    public BOFHExcuseModel getModel()
    {
        return model;
    }

    /**
     * Returns the text of the excuse. This is a space separated sequence of the
     * components of the excuse.
     *
     * @return the text of the excuse.
     * @since 1.0.0.0
     */
    @Override public String toString()
    {
        if(components == null)
            return null;
        StringBuilder sb = new StringBuilder();
        ListIterator<String> iter = components.listIterator();
        while(iter.hasNext()) {
            String component = iter.next();
            if(component != null) {
                sb.append(component);
                if(iter.nextIndex() != components.size())
                    sb.append(" ");
            }
        }
        return sb.toString();
    }
}

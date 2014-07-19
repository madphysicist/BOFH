/*
 * BOFHExcuseGenerator.java (Class: com.madphysicist.bofh.BOFHExcuseGenerator)
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

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import com.madphysicist.tools.swing.CasinoSpinner;

/**
 * Implements a GUI to generate BOFH excuses. This class can be inserted as a
 * component into virtually any swing GUI. The primary purpose of this class is
 * to showcase the {@link CasinoSpinner} component. It is also meant to be a fun
 * tool to see just how far you can go at work.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0.0, 11 Feb 2013
 * @version 1.0.0.1, 10 Apr 2013 - Refactored BOFHColumn into a public class.
 *                                 Added public accessors for column elements.
 * @since 1.0.0.0
 */
public class BOFHExcuseGenerator extends JPanel implements Iterable<BOFHColumn>
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
     * A list of the graphical columns displayed by the GUI. Each column
     * provides a view to the underlying {@code BOFHExcuseModel} column via the
     * {@code CasinoSpinner} class, as well as a button to spin the spinner.
     *
     * @see #model
     * @serial
     * @since 1.0.0.0
     */
    private List<BOFHColumn> columns;

    /**
     * The underlying model of this class. The model contains the elements of
     * each column. The model can be accessed through the {@link #getModel()}
     * method.
     *
     * @serial
     * @since 1.0.0.0
     */
    private BOFHExcuseModel model;

    /**
     * A button to spin all of the spinners randomly and generate a new excuse.
     * Each {@linkplain #columns individual column} of the GUI can be spun
     * separately as well. The button can be accessed through the {@link
     * #getBigButton()} method, for example to make it a default button.
     *
     * @serial
     * @since 1.0.0.0
     */
    private JButton bigButton;

    /**
     * Creates a default view of the default model.
     *
     * @throws IOException if the default model could not be configured.
     * @since 1.0.0.0
     */
    public BOFHExcuseGenerator() throws IOException
    {
        this(new BOFHExcuseModel());
    }

    /**
     * Creates a view of the specified model.
     *
     * @param model the model to use for this generator. All columns will be
     * displayed in their own {@code CasinoSpinner} component.
     * @since 1.0.0.0
     */
    public BOFHExcuseGenerator(BOFHExcuseModel model)
    {
        this.model = model;
        initComponents();
    }

    /**
     * Initializes the GUI components of this class. This method is intended for
     * use exclusively in the constructor. The following setup is done:
     * <ul>
     * <li>The panel is given a raised {@link BevelBorder}.</li>
     * <li>The panel is given a {@link BorderLayout}.</li>
     * <li>{@link #bigButton} is initialized and added to the North side.</li>
     * <li>A panel for the columns is initialized and added to the center.</li>
     * </ul>
     * 
     * @since 1.0.0.0
     */
    private void initComponents()
    {
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setLayout(new BorderLayout());

        this.bigButton = new JButton(new AbstractAction("Generate!") {
            private static final long serialVersionUID = 1000L;
            @Override public void actionPerformed(ActionEvent e) {
                spinAll();
            }
        });
        bigButton.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        add(bigButton, BorderLayout.NORTH);

        columns = new ArrayList<>(model.getColumnCount());

        // create a panel and layout for the columns
        JPanel columnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(
                0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        // add the columns to the panel
        for(int index = 0; index < model.getColumnCount(); index++) {
            BOFHColumn column = new BOFHColumn(model.getColumn(index));
            constraints.gridx = index;
            columnPanel.add(column, constraints);
            columns.add(column);
        }
        
        add(columnPanel, BorderLayout.CENTER);
    }

    /**
     * Returns the underlying model of the excuse generator.
     *
     * @return The model of the excuse generator.
     * @since 1.0.0.0
     */
    public BOFHExcuseModel getModel()
    {
        return model;
    }

    /**
     * Returns a refrence to the button that randomly spins all of the columns.
     * Activating this button will spin the spinners for all columns which are
     * not already spinning.
     *
     * @return the button that spins all of the spinners in the generator.
     * @since 1.0.0.0
     */
    public JButton getBigButton()
    {
        return bigButton;
    }

    /**
     * Spins all of the spinners by a random amount. Only spinners which are not
     * already spinning can be spun. This method is activated by the big button.
     * 
     * @see #bigButton
     * @since 1.0.0.0
     */
    public void spinAll()
    {
        for(BOFHColumn column : this.columns)
            column.spin();
    }

    /**
     * Returns the number of columns in this generator. This method is a
     * convenience for {@code getModel().getColumnCount()}.
     *
     * @return the number of columns in this generator.
     * @since 1.0.0.1
     */
    public int getColumnCount()
    {
        return this.model.getColumnCount();
    }

    /**
     * Returns an iteraror over the GUI components used to display the columns
     * of this model. Each column in the iterator displays the corresponding
     * model column.
     *
     * @return the columns of this generator.
     * @since 1.0.0.1
     */
    @Override public ListIterator<BOFHColumn> iterator()
    {
        return this.columns.listIterator();
    }

    /**
     * Returns the specified column of this generator. The column index must be
     * between zero (inclusive) and the number of columns (exclusive).
     *
     * @param index the index of the column to retreive.
     * @return the requested graphical column of the generator.
     * @throws IndexOutOfBoundsException if {@code index} is not a valid column
     * index.
     * @see #getColumnCount()
     * @since 1.0.0.1
     */
    public BOFHColumn getColumn(int index)
    {
        return this.columns.get(index);
    }
}

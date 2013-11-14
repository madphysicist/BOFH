/*
 * BOFHColumn.java
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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import com.madphysicist.tools.swing.CasinoSpinner;
import com.madphysicist.tools.swing.CasinoSpinnerModel;

/**
 * A GUI element for displaying a single component of the excuse generator as a
 * CasinoSpinner with a small spin button at the bottom. The button is intended
 * as a way to trigger random generation for an individual column.
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0.0, 11 Feb 2013
 * @version 1.0.0.1, 10 Apr 2013 - Refactored this into a public class.
 * @version 1.0.0.2, 10 Apr 2013 - Added constructors and updated initComponents.
 * @since 1.0.0.0
 */
public class BOFHColumn extends JPanel
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
     * The spinner which displays the data. This spinner can be triggered only
     * programatically, so a {@linkplain #spinButton button} is provided to do
     * so.
     *
     * @serial
     * @since 1.0.0.0
     */
    private CasinoSpinner spinner;

    /**
     * A small button that allows the spinner to be triggered by the user. This
     * button is meant to be small and unobtrisive. It only works if the spinner
     * is not already spinning.
     *
     * @serial
     * @since 1.0.0.0
     */
    private JButton spinButton;

    /**
     * Constructs a panel for a column containing the specified data. The data
     * will be copied into a new {@code CasinoSpinnerModel} which will be used
     * to create a new {@code CasinoSpinner}.
     *
     * @param data the data with which to intialize the spinner's model. This
     * list should generally come from the column of an excuse model that
     * corresponds to this GUI.
     * @since 1.0.0.0
     */
    public BOFHColumn(List<String> data)
    {
        this(new CasinoSpinnerModel(data));
    }

    /**
     * Constructs a panel for a column containing the specified model. The
     * model will be used to create a new {@code CasinoSpinner}.
     *
     * @param model the model with which to intialize the spinner. This model
     * should generally be constructed from the column of an excuse model that
     * corresponds to this GUI.
     * @since 1.0.0.2
     */
    public BOFHColumn(CasinoSpinnerModel model)
    {
        this.spinner = new CasinoSpinner(model);
        initComponents();
    }

    /**
     * Constructs a panel for a {@code CasinoSpinner}.
     *
     * @param spinner the spinner with which to intialize this column. The
     * spinner's model should generally be constructed from the column of an
     * excuse model that corresponds to this GUI.
     * @since 1.0.0.2
     */
    public BOFHColumn(CasinoSpinner spinner)
    {
        this.spinner = spinner;
        initComponents();
    }

    /**
     * Initializes the GUI components of this class. This method is intended
     * for use exclusively in the constructor. The spinner field must have been
     * set before this method is called. The spinner's appearance is configured.
     * {@link #spinButton} is created and added to the spinner.
     *
     * @since 1.0.0.0
     */
    private void initComponents()
    {
        setLayout(new GridBagLayout());

        spinner.setBorder(new BevelBorder(BevelBorder.LOWERED));
        spinner.setFont(Font.decode("Courier New-bold-14"));

        this.spinButton = new JButton("Spin!");
        spinButton.setFont(spinButton.getFont().deriveFont(8.0f));
        spinButton.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

        spinner.addToButton(spinButton);

        add(spinner, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 10, 0));
        add(spinButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * Spins the spinner of this column if it is not already spinning. This
     * method is a no-op if the spinner is already spinning.
     *
     * @since 1.0.0.0
     */
    public void spin()
    {
        this.spinner.spin();
    }

    /**
     * Returns the spinner of this component.
     *
     * @return the spinner of this component.
     * @since 1.0.0.1
     */
    public CasinoSpinner getSpinner()
    {
        return this.spinner;
    }

    /**
     * Returns the button that triggers the spinner of this component.
     *
     * @return the button that triggers the spinner of this component.
     * @since 1.0.0.1
     */
    public JButton getButton()
    {
        return this.spinButton;
    }
}

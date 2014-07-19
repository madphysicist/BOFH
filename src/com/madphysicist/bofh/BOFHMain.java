/*
 * BOFHMain.java (Class: com.madphysicist.bofh.BOFHMain)
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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import com.madphysicist.tools.util.AudioTools;
import com.madphysicist.tools.util.ResourceUtilities;
import com.madphysicist.tools.swing.CasinoSpinner;
import com.madphysicist.tools.swing.SwingUtilities;

/**
 * <p>
 * Creates a window and adds all of the necessary trappings to properly display
 * a {@link BOFHExcuseGenerator} component. This GUI is intended to be a driver
 * to showcase the {@link CasinoSpinner} component. This class can only be
 * accessed through its {@code main()} method. Do not tell it your user name!
 * </p>
 * <p>
 * In addition to showcasing the {@code CasinoSpinner} component, this class
 * provides examples for a set of interesting Java features:
 * <ul>
 * <li>Playing an audio clip (The clickety-click when the user types in a luser
 * name)</li>
 * <li>Opening a hyperlink in the system browser (the link to Simon's site in
 * the Help->About dialog)</li>
 * <li>Creating a custom JOptionPane dialog (the Help->About dialog)</li>
 * <li></li>
 * </ul>
 * </p>
 *
 * @author Joseph Fox-Rabinovitz
 * @version 1.0.0.0 6 Apr, 2012
 * @since 1.0.0.0
 */
public class BOFHMain
{
    /**
     * The name of the audio resource that plays "clickety click" when the
     * operator enters a luser name instead of a user name.
     *
     * @since 1.0.0.0
     */
    private static final String CLICKETY_CLIP = "ClicketyClick.wav";

    /**
     * A private constructor to prevent instantiation.
     * 
     * @since 1.0.0.0
     */
    private BOFHMain() {}

    /**
     * Returns the product name and version as a string.
     *
     * @return The product name and version string.
     * @since 1.0.0.0
     */
    private static String productString()
    {
        return "BOFH Excuse Generator v1.0";
    }

    /**
     * Creates a basic menu bar for the application. There is a
     * <u>F</u>ile-><u>Q</u>uit option and a <u>H</u>elp-><u>A</u>bout option.
     * The quit option closes the application. The about option shows a small
     * dialog with information about the program and a hyperlink to the original
     * BOFH page.
     *
     * @param frame the frame to assign the menu to. This refrence has to be
     * passed in so it can be the parent of the about dialog.
     * @since 1.0.0.0
     */
    private static void createJMenuBar(final JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        fileMenu.setMnemonic(KeyEvent.VK_F);

        Action quitAction = new AbstractAction("Quit") {
            private static final long serialVersionUID = 1000L;
            @Override public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        quitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
        fileMenu.add(quitAction);

        helpMenu.setMnemonic(KeyEvent.VK_H);
        Action aboutAction = new AbstractAction("About") {
            private static final long serialVersionUID = 1000L;
            @Override public void actionPerformed(ActionEvent e) {
                JPanel messagePanel = new JPanel();
                messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
                messagePanel.add(centerLabel(new JLabel(productString())));
                messagePanel.add(centerLabel(new JLabel("\u00A9 2013 by Joseph Fox-Rabinovitz")));
                messagePanel.add(centerLabel(new JLabel("\"Inspired\" by Simon Travaglia's BOFH column:")));
                messagePanel.add(centerLabel(SwingUtilities.createHyperlinkLabel(null, "http://bofh.ntk.net/BOFH/index.php")));
                JOptionPane.showMessageDialog(frame, messagePanel,
                        "About This Masterpiece", JOptionPane.INFORMATION_MESSAGE);
            }
            private JLabel centerLabel(JLabel label) {
                label.setAlignmentX(0.5f);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            };
        };
        aboutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
        helpMenu.add(aboutAction);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);
    }

    /**
     * Asks for the user name. If OK is pressed under any circumstance without
     * the correct user name, the user name or "all users" will be deleted from
     * the system with a "clickety click". If you try to cancel or escape, you
     * will not be able to hide. The only way to successfully circumvent this
     * sequence of dialogs is by entering the correct user name. What is the
     * correct user name?
     *
     * @return {@code true} if the user successfully circumvented the query,
     * {@code false} otherwise.
     * @since 1.0.0.0
     */
    private static boolean askForUserName()
    {
        String output;
        do {
            output = JOptionPane.showInputDialog(null,
                    "Please enter your user name",
                    "Experience Enhancement Query",
                    JOptionPane.INFORMATION_MESSAGE);

            if(output == null) {
                JOptionPane.showMessageDialog(null,
                        "You can run, but you can\'t hide",
                        "Informational Communication",
                        JOptionPane.WARNING_MESSAGE);
            } else if(output.equals("bofh")) {
                return true;
            }
        } while(output == null);

        try {
            AudioTools.playClip(ResourceUtilities.getResourceAsFile(CLICKETY_CLIP, true));
        } catch(IOException | InterruptedException
              | LineUnavailableException | UnsupportedAudioFileException ex) {
            // Ignore any exception. If the audio clip can not be played, too bad.
        }

        String userString = output.isEmpty() ? "All users have" : "User " + output.trim() + " has";
        JOptionPane.showMessageDialog(null,
                userString + " been deleted from your system. Good bye!",
                "Do Not Call Again.", JOptionPane.INFORMATION_MESSAGE);
        return false;
    }

    /**
     * The main method. This method is the only public access to this class.
     * After asking the user to input his user name, a frame with an excuse
     * generator is displayed. The frame contains a {@link BOFHExcuseGenerator}
     * panel as well as tools to configure it.
     *
     * @param args input arguments which can be anything, as they will all be
     * ignored.
     * @throws IOException if the excuse configuration resources can not be
     * loaded for any reason. The message or even the subtype of the exception
     * may be more informative.
     * @since 1.0.0.0
     */
    public static void main(String[] args) throws IOException
    {
        if(askForUserName()) {
            JFrame frame = new JFrame(productString());
            createJMenuBar(frame);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            BOFHExcuseGenerator generator = new BOFHExcuseGenerator();
            frame.getContentPane().add(generator);
            frame.getRootPane().setDefaultButton(generator.getBigButton());

            frame.pack();
            //frame.setResizable(false);
            frame.setVisible(true);
        }
    }
}

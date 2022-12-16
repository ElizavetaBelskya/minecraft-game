package ru.kpfu.itis.belskaya.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePadPanel extends JPanel {

    public GamePadPanel(GameFrame frame) {
        setBackground(Color.lightGray);
        setLayout(new GridBagLayout());
        JPanel blockPanel = new JPanel();
        blockPanel.setBackground(Color.gray);
        blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.Y_AXIS));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        for (int i = 1; i < 9; i++) {
            JButton button = new JButton();
            button.setFocusable(false);
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setBlockId(finalI);
                }
            });
            button.add(new JBlock(i));
            blockPanel.add(button);
        }
        add(blockPanel, constraints);
    }


}

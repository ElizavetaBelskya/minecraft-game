package ru.kpfu.itis.belskaya.gui;

import ru.kpfu.itis.belskaya.exceptions.ResourceLoadingException;
import ru.kpfu.itis.belskaya.protocol.entities.BlockType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePadPanel extends JPanel {

    public GamePadPanel(GameFrame frame) {
        setBackground(Color.lightGray);
        setLayout(new GridBagLayout());
        JPanel blockPanel = new JPanel();
        blockPanel.setBackground(Color.GRAY);
        blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.Y_AXIS));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        for (BlockType blockType: BlockType.values()) {
            JButton button = new JButton();
            button.setFocusable(false);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setBlockType(blockType);
                }
            });

            try {
                button.add(new JBlockPanel(blockType));
            } catch (ResourceLoadingException e) {
                throw new RuntimeException(e);
            }
            blockPanel.add(button);
        }
        add(blockPanel, constraints);
    }


}

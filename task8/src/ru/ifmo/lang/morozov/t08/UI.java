package ru.ifmo.lang.morozov.t08;

import ru.ifmo.lang.morozov.t08.gun.ColtMk3TrooperLawman;
import ru.ifmo.lang.morozov.t08.gun.Gun;
import ru.ifmo.lang.morozov.t08.gun.UnholyHellGun;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vks on 23/04/15.
 */
public class UI {

    private Guns pickedGun;
    private int bulletsAvailable;
    private JLabel gunName;
    private JLabel bulletCount;
    private JMenuItem reload;
    private JTextArea textArea;
    private JFrame frame;

    public UI() {
        pickedGun = Guns.BORING;
        bulletsAvailable = 6;
        createUI();
    }

    private void createUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("File based Russian Roulette");
        BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        frame.setLayout(boxLayout);
        frame.setMinimumSize(new Dimension(600, 175));
        Container contentPane = frame.getContentPane();

        contentPane.add(createMainMenu());
        contentPane.add(Box.createVerticalStrut(15));
        contentPane.add(createGunInfo());
        contentPane.add(Box.createVerticalStrut(15));
        contentPane.add(createBulletsInfo());
        contentPane.add(Box.createVerticalStrut(15));
        contentPane.add(createPathEditor());
        contentPane.add(Box.createVerticalStrut(15));
        contentPane.add(createFirePanel());
        contentPane.add(Box.createVerticalStrut(15));
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    private void setCurrentGun(Guns gun) {
        pickedGun = gun;
        gunName.setText(pickedGun.toString());
        if (gun == Guns.BONUS) {
            bulletCount.setText("Infinity");
            reload.setEnabled(false);
        } else {
            bulletCount.setText("" + bulletsAvailable);
            reload.setEnabled(true);
        }
    }

    private JMenuBar createMainMenu() {
        JMenuItem pickBoring = new JMenuItem("Pick boring one");
        JMenuItem pickBonus = new JMenuItem("Pick bonus one");
        reload = new JMenuItem("Reload");

        JMenu gunMenu = new JMenu("Gun");
        gunMenu.add(pickBoring);
        gunMenu.add(pickBonus);
        gunMenu.add(reload);

        JMenuItem quit = new JMenuItem("Finish game");
        JMenu programMenu = new JMenu("Program");
        programMenu.add(quit);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(gunMenu);
        menuBar.add(programMenu);
        menuBar.setMinimumSize(new Dimension(100, 20));
        menuBar.setMaximumSize(new Dimension(10000, 20));

        pickBoring.addActionListener(e -> {
            if (pickedGun == Guns.BONUS) {
                setCurrentGun(Guns.BORING);
            }
        });

        pickBonus.addActionListener(e -> {
            if (pickedGun == Guns.BORING) {
                setCurrentGun(Guns.BONUS);
            }
        });

        reload.addActionListener(e -> {
            bulletsAvailable = 6;
            bulletCount.setText("" + bulletsAvailable);
        });

        quit.addActionListener(e -> frame.dispose());
        return menuBar;
    }

    private JPanel createGunInfo() {
        JLabel currentGun = new JLabel("Using gun: ");
        gunName = new JLabel(pickedGun.toString());

        JPanel gunInfo = new JPanel();
        gunInfo.setLayout(new BoxLayout(gunInfo, BoxLayout.X_AXIS));
        gunInfo.add(Box.createHorizontalStrut(20));
        gunInfo.add(currentGun);
        gunInfo.add(gunName);
        gunInfo.add(Box.createHorizontalStrut(20));
        return gunInfo;
    }

    private JPanel createBulletsInfo() {
        JLabel bullets = new JLabel("Bullets remaining: ");
        bulletCount = new JLabel("" + bulletsAvailable);

        JPanel bulletsInfo = new JPanel();
        bulletsInfo.setLayout(new BoxLayout(bulletsInfo, BoxLayout.X_AXIS));
        bulletsInfo.add(Box.createHorizontalStrut(20));
        bulletsInfo.add(bullets);
        bulletsInfo.add(bulletCount);
        bulletsInfo.add(Box.createHorizontalStrut(20));
        return bulletsInfo;
    }

    private JPanel createFirePanel() {
        JButton fireButton = new JButton("Fire!");
        fireButton.addActionListener(e -> {
            String path = textArea.getText();
            if (path.equals("")) {
                System.out.println("Please enter the directory!");
            } else {
                if (pickedGun == Guns.BORING) {
                    Gun gun = new ColtMk3TrooperLawman(path, bulletsAvailable);
                    if (bulletsAvailable > 0) {
                        if (gun.fire()) {
                            bulletsAvailable--;
                            bulletCount.setText("" + bulletsAvailable);
                            gun.reload(bulletsAvailable);
                        }
                    } else {
                        System.out.println("Please reload the gun!");
                    }
                } else {
                    Gun gun = new UnholyHellGun(path);
                    gun.fire();
                }
            }
        });

        JPanel firePanel = new JPanel();
        firePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        firePanel.add(fireButton);
        return firePanel;
    }

    private JPanel createPathEditor() {
        JPanel pathEditor = new JPanel();
        pathEditor.setLayout(new BoxLayout(pathEditor, BoxLayout.X_AXIS));

        JLabel query = new JLabel("Working directory: ");
        textArea = new JTextArea();
        pathEditor.add(Box.createHorizontalStrut(20));
        pathEditor.add(query);
        pathEditor.add(textArea);
        pathEditor.add(Box.createHorizontalStrut(20));
        return pathEditor;
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class Minesweeper extends JFrame {
    private int row = 9;
    private int col = 9;

    private Font font = new Font("黑體", Font.PLAIN, 23);
    static final ResourceBundle def = ResourceBundle.getBundle("content");

    private int pw = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int ph = Toolkit.getDefaultToolkit().getScreenSize().height;

    private JRadioButtonMenuItem[] levelItems = new JRadioButtonMenuItem[3];
    private String[] levelName = {
            def.getString("newbie"),
            def.getString("midbie"),
            def.getString("highbie")};

    private MyPanel panel;

    public Minesweeper() {
        setTitle(def.getString("title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JMenu gameMenu = new JMenu(def.getString("game"));
        gameMenu.setFont(font);

        JMenuItem newItem = new JMenuItem(def.getString("new"));
        newItem.setFont(font);
        gameMenu.add(newItem);
        newItem.addActionListener(
                e -> newGame()
        );

        gameMenu.addSeparator();
        for (int i = 0; i < levelItems.length; i++) {
            levelItems[i] = new JRadioButtonMenuItem(levelName[i]);
            levelItems[i].setFont(font);
            gameMenu.add(levelItems[i]);
        }

        levelItems[0].setSelected(true);

        levelItems[0].addActionListener(
                e -> {
                    levelItems[0].setSelected(true);
                    levelItems[1].setSelected(false);
                    levelItems[2].setSelected(false);
                    row = 9;
                    col = 9;
                    newGame();
                }
        );

        levelItems[1].addActionListener(
                e -> {
                    levelItems[1].setSelected(true);
                    levelItems[0].setSelected(false);
                    levelItems[2].setSelected(false);
                    row = 16;
                    col = 16;
                    newGame();
                }
        );

        levelItems[2].addActionListener(
                e -> {
                    levelItems[2].setSelected(true);
                    levelItems[0].setSelected(false);
                    levelItems[1].setSelected(false);
                    row = 16;
                    col = 30;
                    newGame();
                }
        );

        gameMenu.addSeparator();

        JMenuItem aboutItem = new JMenuItem(def.getString("about"));
        aboutItem.setFont(font);
        gameMenu.add(aboutItem);
        aboutItem.addActionListener(
                e -> JOptionPane.showMessageDialog(this,
                        "<html><h1>踩地雷</h1><h2>版本：V 2.1</h2></html>",
                        "關於", JOptionPane.PLAIN_MESSAGE)
        );

        JMenuItem exitItem = new JMenuItem(def.getString("exit"));
        exitItem.setFont(font);
        gameMenu.add(exitItem);
        exitItem.addActionListener(
                e -> System.exit(0)
        );

        JMenuBar bar = new JMenuBar();
        bar.add(gameMenu);
        this.setJMenuBar(bar);

        int d = 51;

        int width = col * d + 4;
        int height = row * d + 140;

        setBounds((pw - width) / 2, (ph - height) / 2, width, height);
        panel = new MyPanel(row, col);
        panel.startGame();
        this.add(panel);

        setVisible(true);
    }

    	private void newGame() {
    		this.remove(panel);
    		int d = 51;

    		int width = col * d + 4;
    		int height = row * d + 140;

    		setBounds((pw - width) / 2, (ph - height) / 2, width, height);
    		panel = new MyPanel(row, col);
    		panel.startGame();
    		this.add(panel);
    		this.setVisible(true);
    	}

    	public static void main(String[] args) {
    		new Minesweeper();
    	}
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyPanel extends JPanel implements MouseListener, Runnable {
    private int row;
    private int col;

    private int sum = 10;
    private int remain = 10;

    private long startTime;
    private long endTime = 0;

    private static Icon[] icon = new ImageIcon[3];

    private JButton label1 = new JButton(Minesweeper.def.getString("remain") + remain);
    private JButton label2 = new JButton(Minesweeper.def.getString("time") + endTime);
    private JButton face = new JButton(icon[0]);

    private Font font = new Font("黑體", Font.BOLD, 25);

    private final int d = 51;
    private final int h = 70;

    private Block blocks[][];

    private boolean over = false;
    private boolean begin = false;
    private boolean win = false;

    static {
        for (int i = 0; i < icon.length; i++) {
            icon[i] = new ImageIcon("images/face" + i + ".png");
        }
    }

    MyPanel(int row, int col) {
        this.row = row;
        this.col = col;
        setBackground(Color.BLACK);
        label1.setFont(font);
        label1.setBackground(Color.BLACK);
        label1.setEnabled(false);
        label1.setBorderPainted(false);
        this.add(label1);

        face.setBackground(Color.BLACK);
        face.setBorder(null);
        face.setSize(40,40);
        face.setBorderPainted(false);
        face.setIcon(icon[0]);
        face.addActionListener(
                e -> {
                    if (over || win || begin) {
                    	face.setIcon(icon[0]);
                        remain = sum;
                        startTime = 0;
                        endTime = 0;
                        win = false;
                        over = false;
                        begin = false;

                        int count = 0;

                        blocks = new Block[row][col];
                        if (row == 9 && col == 9) {
                            sum = 10;
                        } else if (row == 16 && col == 16) {
                            sum = 40;
                        } else if (row == 16 && col == 30) {
                            sum = 99;
                        }

                        remain = sum;

                        for (int i = 0; i < row; i++) {
                            for (int j = 0; j < col; j++) {
                                blocks[i][j] = new Block(d * j, d * i + h);
                            }
                        }

                        while (count < sum) {
                            int i = (int) (Math.random() * row);
                            int j = (int) (Math.random() * col);
                            if (blocks[i][j].category != 9) {
                                blocks[i][j].category = 9;
                                count++;
                            }
                        }

                        for (int i = 0; i < row; i++) {
                            for (int j = 0; j < col; j++) {
                                if (blocks[i][j].category != 9) {
                                    if (i - 1 >= 0 && j - 1 >= 0 && blocks[i - 1][j - 1].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                    if (i - 1 >= 0 && blocks[i - 1][j].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                    if (i - 1 >= 0 && j + 1 < col && blocks[i - 1][j + 1].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                    if (j - 1 >= 0 && blocks[i][j - 1].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                    if (j + 1 < col && blocks[i][j + 1].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                    if (i + 1 < row && j - 1 >= 0 && blocks[i + 1][j - 1].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                    if (i + 1 < row && blocks[i + 1][j].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                    if (i + 1 < row && j + 1 < col && blocks[i + 1][j + 1].category == 9) {
                                        blocks[i][j].category++;
                                    }
                                }
                            }
                        }
                        startGame();
                    }
                });
        this.add(face);

        label2.setFont(font);
        label2.setBackground(Color.BLACK);
        label2.setEnabled(false);
        label2.setBorderPainted(false);
        this.add(label2);
        int count = 0;

        blocks = new Block[row][col];
        if (row == 9 && col == 9) {
            sum = 10;
        } else if (row == 16 && col == 16) {
            sum = 40;
        } else if (row == 16 && col == 30) {
            sum = 99;
        }

        remain = sum;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                blocks[i][j] = new Block(d * j, d * i + h);
            }
        }

        while (count < sum) {
            int i = (int) (Math.random() * row);
            int j = (int) (Math.random() * col);
            if (blocks[i][j].category != 9) {
                blocks[i][j].category = 9;
                count++;
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (blocks[i][j].category != 9) {
                    if (i - 1 >= 0 && j - 1 >= 0 && blocks[i - 1][j - 1].category == 9) {
                        blocks[i][j].category++;
                    }
                    if (i - 1 >= 0 && blocks[i - 1][j].category == 9) {
                        blocks[i][j].category++;
                    }
                    if (i - 1 >= 0 && j + 1 < col && blocks[i - 1][j + 1].category == 9) {
                        blocks[i][j].category++;
                    }
                    if (j - 1 >= 0 && blocks[i][j - 1].category == 9) {
                        blocks[i][j].category++;
                    }
                    if (j + 1 < col && blocks[i][j + 1].category == 9) {
                        blocks[i][j].category++;
                    }
                    if (i + 1 < row && j - 1 >= 0 && blocks[i + 1][j - 1].category == 9) {
                        blocks[i][j].category++;
                    }
                    if (i + 1 < row && blocks[i + 1][j].category == 9) {
                        blocks[i][j].category++;
                    }
                    if (i + 1 < row && j + 1 < col && blocks[i + 1][j + 1].category == 9) {
                        blocks[i][j].category++;
                    }
                }
            }
        }
        addMouseListener(this);
    }

    void startGame() {
        new Thread(this).start();
    }

    private void flipAround(int i, int j) {
        if (i - 1 >= 0 && j - 1 >= 0 && !blocks[i - 1][j - 1].flip && blocks[i - 1][j - 1].flag == 0) {
            blocks[i - 1][j - 1].flip = true;
            if (blocks[i - 1][j - 1].category == 0) {
                flipAround(i - 1, j - 1);
            }
        }
        if (i - 1 >= 0 && !blocks[i - 1][j].flip && blocks[i - 1][j].flag == 0) {
            blocks[i - 1][j].flip = true;
            if (blocks[i - 1][j].category == 0) {
                flipAround(i - 1, j);
            }
        }
        if (i - 1 >= 0 && j + 1 < col && !blocks[i - 1][j + 1].flip && blocks[i - 1][j + 1].flag == 0) {
            blocks[i - 1][j + 1].flip = true;
            if (blocks[i - 1][j + 1].category == 0) {
                flipAround(i - 1, j + 1);
            }
        }
        if (j - 1 >= 0 && !blocks[i][j - 1].flip && blocks[i][j - 1].flag == 0) {
            blocks[i][j - 1].flip = true;
            if (blocks[i][j - 1].category == 0) {
                flipAround(i, j - 1);
            }
        }
        if (j + 1 < col && !blocks[i][j + 1].flip && blocks[i][j + 1].flag == 0) {
            blocks[i][j + 1].flip = true;
            if (blocks[i][j + 1].category == 0) {
                flipAround(i, j + 1);
            }
        }
        if (i + 1 < row && j - 1 >= 0 && !blocks[i + 1][j - 1].flip && blocks[i + 1][j - 1].flag == 0) {
            blocks[i + 1][j - 1].flip = true;
            if (blocks[i + 1][j - 1].category == 0) {
                flipAround(i + 1, j - 1);
            }
        }
        if (i + 1 < row && !blocks[i + 1][j].flip && blocks[i + 1][j].flag == 0) {
            blocks[i + 1][j].flip = true;
            if (blocks[i + 1][j].category == 0) {
                flipAround(i + 1, j);
            }
        }
        if (i + 1 < row && j + 1 < col && !blocks[i + 1][j + 1].flip && blocks[i + 1][j + 1].flag == 0) {
            blocks[i + 1][j + 1].flip = true;
            if (blocks[i + 1][j + 1].category == 0) {
                flipAround(i + 1, j + 1);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        label1.setText(Minesweeper.def.getString("remain") + remain);
        if (begin) {
            endTime = (System.currentTimeMillis() - startTime) / 1000;
        }
        label2.setText(Minesweeper.def.getString("time") + endTime);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                blocks[i][j].drawBlock(g);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int i = (e.getY() - h) / d;
        int j = e.getX() / d;

        if ((!over || !win) && i >= 0 && i < row && j >= 0 && j < col && !blocks[i][j].flip) {
            if (e.getButton() == MouseEvent.BUTTON1 && blocks[i][j].flag == 0) {
                if (!begin) {
                    begin = true;
                    startTime = System.currentTimeMillis();
                }

                blocks[i][j].flip = true;
                if (blocks[i][j].category == 0) {
                    flipAround(i, j);
                } else if (blocks[i][j].category == 9) {
                    over = true;
                    begin = false;
                    face.setIcon(icon[2]);
                    for (int k = 0; k < row; k++) {
                        for (int l = 0; l < col; l++) {
                            blocks[k][l].flip = true;
                            blocks[k][l].over = true;
                        }
                    }
                    repaint();
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                int temp = blocks[i][j].flag;
                blocks[i][j].updateFlag();
                if (blocks[i][j].flag == 1 && remain > 0) {
                    remain--;
                }
                if (temp == 1 && blocks[i][j].flag != 1) {
                    remain++;
                }
            }
        } else if ((!over || !win) && i >= 0 && i < row && j >= 0 && j < col && e.getClickCount() == 2) {
            if (blocks[i][j].flip && blocks[i][j].category < 9) {
            	int number = 0;
                boolean wrong = false;
                if (i - 1 >= 0 && j - 1 >= 0 && !blocks[i - 1][j - 1].flip) {
                    if (blocks[i - 1][j - 1].category == 9 && blocks[i - 1][j - 1].flag == 1) {
                        number++;
                    } else if (blocks[i - 1][j - 1].category != 9 && blocks[i - 1][j - 1].flag == 1) {
                        wrong = true;
                    }
                }
                if (i - 1 >= 0 && !blocks[i - 1][j].flip) {
                    if (blocks[i - 1][j].category == 9 && blocks[i - 1][j].flag == 1) {
                        number++;
                    } else if (blocks[i - 1][j].category != 9 && blocks[i - 1][j].flag == 1) {
                        wrong = true;
                    }
                }
                if (i - 1 >= 0 && j + 1 < col && !blocks[i - 1][j + 1].flip) {
                    if (blocks[i - 1][j + 1].category == 9 && blocks[i - 1][j + 1].flag == 1) {
                        number++;
                    } else if (blocks[i - 1][j + 1].category != 9 && blocks[i - 1][j + 1].flag == 1) {
                        wrong = true;
                    }
                }
                if (j - 1 >= 0 && !blocks[i][j - 1].flip) {
                    if (blocks[i][j - 1].category == 9 && blocks[i][j - 1].flag == 1) {
                        number++;
                    } else if (blocks[i][j - 1].category != 9 && blocks[i][j - 1].flag == 1) {
                        wrong = true;
                    }
                }
                if (j + 1 < col && !blocks[i][j + 1].flip) {
                    if (blocks[i][j + 1].category == 9 && blocks[i][j + 1].flag == 1) {
                        number++;
                    } else if (blocks[i][j + 1].category != 9 && blocks[i][j + 1].flag == 1) {
                        wrong = true;
                    }
                }
                if (i + 1 < row && j - 1 >= 0 && !blocks[i + 1][j - 1].flip) {
                    if (blocks[i + 1][j - 1].category == 9 && blocks[i + 1][j - 1].flag == 1) {
                        number++;
                    } else if (blocks[i + 1][j - 1].category != 9 && blocks[i + 1][j - 1].flag == 1) {
                        wrong = true;
                    }
                }
                if (i + 1 < row && !blocks[i + 1][j].flip) {
                    if (blocks[i + 1][j].category == 9 && blocks[i + 1][j].flag == 1) {
                        number++;
                    } else if (blocks[i + 1][j].category != 9 && blocks[i + 1][j].flag == 1) {
                        wrong = true;
                    }
                }
                if (i + 1 < row && j + 1 < col && !blocks[i + 1][j + 1].flip) {
                    if (blocks[i + 1][j + 1].category == 9 && blocks[i + 1][j + 1].flag == 1) {
                        number++;
                    } else if (blocks[i + 1][j + 1].category != 9 && blocks[i + 1][j + 1].flag == 1) {
                        wrong = true;
                    }
                }

                if (wrong) {
                    over = true;
                    begin = false;
                    face.setIcon(icon[2]);
                    for (int k = 0; k < row; k++) {
                        for (int l = 0; l < col; l++) {
                            blocks[k][l].flip = true;
                            blocks[k][l].over = true;
                        }
                    }
                    repaint();
                    return;
                }

                if (number == blocks[i][j].category) {
                    flipAround(i, j);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void run() {
        while (!over && !win) {
            if (remain == 0) {
                int count = 0;
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        Block b = blocks[i][j];
                        if (b.category == 9 && b.flag == 1) {
                            count++;
                        }
                    }
                }
                if (count == sum) {
                    win = true;
                    begin = false;
                    face.setIcon(icon[1]);
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            blocks[i][j].flip = true;
                            blocks[i][j].over = true;
                        }
                    }
                    repaint();
                }
            }

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

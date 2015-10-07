import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class Block {
    private int x;
    private int y;

    boolean flip = false;
    boolean over = false;

    int flag = 0;

    int category = 0;

    private static Image nums[] = new Image[9];
    private static Image bombs[] = new Image[2];
    private static Image flags[] = new Image[3];

    static {
        try {
            for (int i = 0; i < 9; i++) {
                nums[i] = ImageIO.read(new File("images/" + i + ".jpg"));
            }
            for (int i = 0; i < 2; i++) {
                bombs[i] = ImageIO.read(new File("images/bomb" + i + ".jpg"));
            }
            for (int i = 0; i < 3; i++) {
                flags[i] = ImageIO.read(new File("images/flag" + i + ".jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void updateFlag() {
        flag++;
        flag = flag % 3;
    }

    void drawBlock(Graphics g) {
        if (flip) {
            if (over) {
                if (category == 9 && flag == 1) {
                    g.drawImage(bombs[1], x, y, null);
                } else if (category == 9) {
                    g.drawImage(bombs[0], x, y, null);
                }
            } else {
                if (category == 9) {
                    g.drawImage(bombs[0], x, y, null);
                }
            }
            if (category >= 0 && category <= 8) {
                g.drawImage(nums[category], x, y, null);
            }
        } else {
            g.drawImage(flags[flag], x, y, null);
        }
    }
}

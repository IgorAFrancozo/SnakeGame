import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tabela extends JPanel implements ActionListener {

    private final int B_WIDTH = 500;
    private final int B_HEIGHT = 500;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 1000;
    private final int RAND_POS = 29;
    private final int DELAY = 34;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean direcaoEsquerda = false;
    private boolean direcaoDireita = true;
    private boolean direcaoCima = false;
    private boolean direcaoBaixo = false;
    private boolean inGame = true;

    private Timer timer;
    private Image corpo;
    private Image apple;
    private Image head;

    public Tabela() {

        tabela();
    }

    private void tabela() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        Imagens();
        inicioGame();
    }

    private void Imagens() {

        ImageIcon corpoImg = new ImageIcon("dot.png");
        corpo = corpoImg.getImage();

        ImageIcon masan = new ImageIcon("apple.png");
        apple = masan.getImage();

        ImageIcon cabe = new ImageIcon("head.png");
        head = cabe.getImage();
    }

    private void inicioGame() {

        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        localizacaoApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(corpo, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {

        String msg = "Noob.. tu perdeu!";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    private void checarApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            localizacaoApple();
        }
    }

    private void movimentar() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (direcaoEsquerda) {
            x[0] -= DOT_SIZE;
        }

        if (direcaoDireita) {
            x[0] += DOT_SIZE;
        }

        if (direcaoCima) {
            y[0] -= DOT_SIZE;
        }

        if (direcaoBaixo) {
            y[0] += DOT_SIZE;
        }
    }

    private void checarColisao() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void localizacaoApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checarApple();
            checarColisao();
            movimentar();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!direcaoDireita)) {
                direcaoEsquerda = true;
                direcaoCima = false;
                direcaoBaixo = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!direcaoEsquerda)) {
                direcaoDireita = true;
                direcaoCima = false;
                direcaoBaixo = false;
            }

            if ((key == KeyEvent.VK_UP) && (!direcaoBaixo)) {
                direcaoCima = true;
                direcaoDireita = false;
                direcaoEsquerda = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!direcaoCima)) {
                direcaoBaixo = true;
                direcaoDireita = false;
                direcaoEsquerda = false;
            }
        }
    }
}
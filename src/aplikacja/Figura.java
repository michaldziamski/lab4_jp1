package aplikacja;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Figura {
private int x, y, size;
private Color color;
private float alpha = 1.0f;

public Figura(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        }

public void paintComponent(Graphics2D g2d) {
        Composite originalComposite = g2d.getComposite();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue()));
        g2d.fillOval(x, y, size, size);

        g2d.setComposite(originalComposite);
        }

public void zmieńKolor() {
        Random random = new Random();
        color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

public void zmieńRozmiar() {
        Random random = new Random();
        size = 50 + random.nextInt(100);
        }

public void ustawPrzeźroczystość(float alpha) {
        this.alpha = alpha;
        }

        }

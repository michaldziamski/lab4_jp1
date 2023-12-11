import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FiguryApp extends JFrame {
    private JPanel panel;
    private JButton generujFiguręButton;
    private JButton zmienKolorButton;
    private JButton zmienRozmiarButton;
    private JButton przeźroczystośćButton;

    private List<Figura> figury = new ArrayList<>();

    public FiguryApp() {
        setTitle("Apka z figurami");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                for (Figura figura : figury) {
                    figura.paintComponent(g2d);
                }
            }
        };

        generujFiguręButton = new JButton("Generuj Figurę");
        zmienKolorButton = new JButton("Zmień Kolor");
        zmienRozmiarButton = new JButton("Zmień Rozmiar");
        przeźroczystośćButton = new JButton("Przeźroczystość");

        generujFiguręButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generujNowąFigurę();
            }
        });

        zmienKolorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zmieńKolorFigur();
            }
        });

        zmienRozmiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zmieńRozmiarFigur();
            }
        });

        przeźroczystośćButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zmieńPrzeźroczystośćFigur();
            }
        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(generujFiguręButton, BorderLayout.NORTH);
        add(zmienKolorButton, BorderLayout.EAST);
        add(zmienRozmiarButton, BorderLayout.SOUTH);
        add(przeźroczystośćButton, BorderLayout.WEST);
        setLocationRelativeTo(null);
    }

    private void generujNowąFigurę() {
        Random random = new Random();
        int size = 50 + random.nextInt(100);
        Color color = losowyKolor();

        int x = random.nextInt(panel.getWidth() - size);
        int y = random.nextInt(panel.getHeight() - size);

        Figura figura = new Figura(x, y, size, color);
        figury.add(figura);
        panel.revalidate();
        panel.repaint();
    }

    private void zmieńKolorFigur() {
        Thread kolorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Figura figura : figury) {
                    figura.zmieńKolor();
                }
                panel.repaint();
            }
        });
        kolorThread.start();
    }

    private void zmieńRozmiarFigur() {
        Thread rozmiarThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Figura figura : figury) {
                    figura.zmieńRozmiar();
                }
                panel.repaint();
            }
        });
        rozmiarThread.start();
    }

    private void zmieńPrzeźroczystośćFigur() {
        Thread przeźroczystośćThread = new Thread(new Runnable() {
            @Override
            public void run() {
                zmniejszPrzeźroczystość();
                panel.repaint();
            }
        });
        przeźroczystośćThread.start();
    }

    private void zmniejszPrzeźroczystość() {
        for (float alpha = 1.0f; alpha >= 0.5f; alpha -= 0.01f) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Figura figura : figury) {
                figura.ustawPrzeźroczystość(alpha);
            }
            panel.repaint();
        }

        for (float alpha = 0.5f; alpha <= 1.0f; alpha += 0.01f) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Figura figura : figury) {
                figura.ustawPrzeźroczystość(alpha);
            }
            panel.repaint();
        }
    }

    private Color losowyKolor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FiguryApp().setVisible(true);
            }
        });
    }
}

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import javax.swing.*;
import Logic.Player;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 *
 * @author USER
 */
public class Game extends javax.swing.JFrame {

    /**
     * Creates new form Game
     */
    Player p1 = new Player(1);
    Player p2 = new Player(2);
    Player p3 = new Player(3);
    Player p4 = new Player(4);
    Player p5 = new Player(5);

    int contador = 0;
    boolean calcular = false;


    ArrayList<JLabel> imagenes = new ArrayList<>();
    ArrayList<JTextField> ingr_n = new ArrayList<>();

    ArrayList<Player> players = new ArrayList<>();
    Icon icono1 = new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/Play_pato.jpg")).getImage());
    Icon icono2 = new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/Play_musculoso.jpg")).getImage());
    Icon icono3 = new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/chems_gamer.jpg")).getImage());
    Icon icono4 = new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/Play_digovegeta.jpg")).getImage());
    Icon icono5 = new ImageIcon(new ImageIcon(getClass().getResource("/Imagenes/pato_asesino.jpg")).getImage());

    public Game() {
        initComponents();

        L_J1.setIcon(icono1);
        L_J2.setIcon(icono2);
        L_J3.setIcon(icono3);
        L_J4.setIcon(icono4);
        L_J5.setIcon(icono5);

        players.add(p1);

        players.add(p2);

        players.add(p3);

        players.add(p4);

        players.add(p5);

        imagenes.add(L_J1);
        imagenes.add(L_J2);
        imagenes.add(L_J3);
        imagenes.add(L_J4);
        imagenes.add(L_J5);

        ingr_n.add(T_J1);
        ingr_n.add(T_J2);
        ingr_n.add(T_J3);
        ingr_n.add(T_J4);
        ingr_n.add(T_J5);

        T_J1.setEnabled(false);
        T_J2.setEnabled(false);
        T_J3.setEnabled(false);
        T_J4.setEnabled(false);
        T_J5.setEnabled(false);

        T_M.setEnabled(false);
        T_RE.setEnabled(false);

    }

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (contador >= 0) {
                T_CRO.setText(String.valueOf(contador));
                contador--;
            } else {
                ((Timer) e.getSource()).stop();
                calcular = true;
                Procesar();
                T_CRO.setText("¡Tiempo terminado!");
            }
        }
    });

    public void Procesar() {
        boolean validacion = true;

        // Validar que todas las entradas estén dentro del rango y sean números
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getLinea()) {
                String input = ingr_n.get(i).getText();
                System.out.println(input);
                if (!validarEntrada(input, 0, 100)) {
                    validacion = false;
                    JOptionPane.showMessageDialog(null, "Se ingreso uno o mas numeros fuera del rango, juege otra ronda");

                    break;
                }
            }

        }

        if (validacion) {

            DecimalFormat df = new DecimalFormat("#,##0.00");

            System.out.println(calcular);

            // Asignar números a los jugadores con validación
            try {
                if (validarEntrada(T_J1.getText(), 0, 100)) {
                    p1.setNumero(Integer.parseInt(T_J1.getText()));
                }
                if (validarEntrada(T_J2.getText(), 0, 100)) {
                    p2.setNumero(Integer.parseInt(T_J2.getText()));
                }
                if (validarEntrada(T_J3.getText(), 0, 100)) {
                    p3.setNumero(Integer.parseInt(T_J3.getText()));
                }
                if (validarEntrada(T_J4.getText(), 0, 100)) {
                    p4.setNumero(Integer.parseInt(T_J4.getText()));
                }
                if (validarEntrada(T_J5.getText(), 0, 100)) {
                    p5.setNumero(Integer.parseInt(T_J5.getText()));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Uno o más campos contienen valores no numéricos.");
                return;  // Salir del método si ocurre un error.
            }

            // Calcula la media y muestra por pantalla
            if (calcular) {
                int media = Logic.Logica.Media(players);

                T_M.setText(String.valueOf(media));
                T_RE.setText(df.format(Logic.Logica.Result(players)));

                System.out.println(media);
            }

            System.out.println("    wejkl   w");

            Logic.Logica.WinnerRound(players);

            // Actualiza el puntaje de los jugadores
            P_J1.setText(String.valueOf(p1.getPuntaje()));
            P_J2.setText(String.valueOf(p2.getPuntaje()));
            P_J3.setText(String.valueOf(p3.getPuntaje()));
            P_J4.setText(String.valueOf(p4.getPuntaje()));
            P_J5.setText(String.valueOf(p5.getPuntaje()));

            // Eliminación de jugadores
            for (Player player : players) {
                System.out.println(player.toString());
            }
            Bloquear(players, ingr_n);
            Opacidad(imagenes, players);

            // Habilita el botón de iniciar juego
            for (JTextField texto : ingr_n) {
                texto.setEnabled(false);
            }
            B_START.setEnabled(true);

            boolean reiniciar = Logic.Logica.Winner(players);

            if (reiniciar) {
                for (int i = 0; i < players.size(); i++) {
                    players.get(i).setPuntaje(0);
                    players.get(i).setLinea(true);
                    ingr_n.get(i).setEnabled(true);

                }
                System.out.println("se regresa opacidad");

                L_J1.setIcon(icono1);
                L_J2.setIcon(icono2);
                L_J3.setIcon(icono3);
                L_J4.setIcon(icono4);
                L_J5.setIcon(icono5);

                for (int i = 0; i < players.size(); i++) {
                    players.get(i).getLinea();
                    ingr_n.get(i).setText("");
                    ingr_n.get(i).setEnabled(true);

                }
            }

        } else {
            for (JTextField texto : ingr_n) {
                texto.setEnabled(false);
            }
            B_START.setEnabled(true);
        }
    }

    public static boolean validarEntrada(String input, int min, int max) {
        try {
            int numero = Integer.parseInt(input);
            boolean validado = false;
            if (numero >= 0 && numero <= max) {
                validado = true;
            }

            return validado;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, ingresa un número.");
            return false;
        }
    }

    public static void Bloquear(ArrayList<Player> players, ArrayList<JTextField> textos) {
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).getLinea()) {
                textos.get(i).setText("-");
                textos.get(i).setEnabled(false);
            }
        }
    }

    public static void Opacidad(ArrayList<JLabel> imagenes, ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JLabel label = imagenes.get(i);

            boolean haPerdido = !player.getLinea();
            cambiarOpacidad(haPerdido, label);
        }
    }

    // Método auxiliar para cambiar la opacidad de una imagen en un JLabel.
    private static void cambiarOpacidad(boolean haPerdido, JLabel label) {

        ImageIcon icono = (ImageIcon) label.getIcon();
        BufferedImage imagen = new BufferedImage(
                icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = imagen.createGraphics();

        float opacidad = haPerdido ? 0.3f : 1.0f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidad));
        g2d.drawImage(icono.getImage(), 0, 0, null);
        g2d.dispose();

        label.setIcon(new ImageIcon(imagen));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        P_J1 = new java.awt.Label();
        P_J2 = new java.awt.Label();
        P_J3 = new java.awt.Label();
        P_J4 = new java.awt.Label();
        P_J5 = new java.awt.Label();
        T_J2 = new javax.swing.JTextField();
        T_J1 = new javax.swing.JTextField();
        T_J4 = new javax.swing.JTextField();
        T_J5 = new javax.swing.JTextField();
        T_J3 = new javax.swing.JTextField();
        B_START = new java.awt.Button();
        T_CRO = new java.awt.Label();
        label9 = new java.awt.Label();
        T_M = new javax.swing.JTextField();
        label10 = new java.awt.Label();
        T_RE = new javax.swing.JTextField();
        label11 = new java.awt.Label();
        label12 = new java.awt.Label();
        label13 = new java.awt.Label();
        label14 = new java.awt.Label();
        label15 = new java.awt.Label();
        L_J2 = new javax.swing.JLabel();
        L_J5 = new javax.swing.JLabel();
        L_J4 = new javax.swing.JLabel();
        L_J3 = new javax.swing.JLabel();
        L_J1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);

        jPanel1.setBackground(new java.awt.Color(202, 255, 191));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Monospaced", 1, 36)); // NOI18N
        label1.setForeground(new java.awt.Color(160, 196, 255));
        label1.setText("Juego del Rey de Diamantes");

        P_J1.setAlignment(java.awt.Label.CENTER);
        P_J1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        P_J1.setForeground(new java.awt.Color(51, 51, 51));
        P_J1.setText("0");

        P_J2.setAlignment(java.awt.Label.CENTER);
        P_J2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        P_J2.setForeground(new java.awt.Color(51, 51, 51));
        P_J2.setText("0");

        P_J3.setAlignment(java.awt.Label.CENTER);
        P_J3.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        P_J3.setForeground(new java.awt.Color(51, 51, 51));
        P_J3.setText("0");

        P_J4.setAlignment(java.awt.Label.CENTER);
        P_J4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        P_J4.setForeground(new java.awt.Color(51, 51, 51));
        P_J4.setText("0");

        P_J5.setAlignment(java.awt.Label.CENTER);
        P_J5.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        P_J5.setForeground(new java.awt.Color(51, 51, 51));
        P_J5.setText("0");

        T_J2.setBackground(new java.awt.Color(255, 255, 255));
        T_J2.setFont(new java.awt.Font("Hack Nerd Font", 0, 18)); // NOI18N
        T_J2.setForeground(new java.awt.Color(51, 51, 51));
        T_J2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_J2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_J2ActionPerformed(evt);
            }
        });

        T_J1.setBackground(new java.awt.Color(255, 255, 255));
        T_J1.setFont(new java.awt.Font("Hack Nerd Font", 0, 18)); // NOI18N
        T_J1.setForeground(new java.awt.Color(51, 51, 51));
        T_J1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_J1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_J1ActionPerformed(evt);
            }
        });

        T_J4.setBackground(new java.awt.Color(255, 255, 255));
        T_J4.setFont(new java.awt.Font("Hack Nerd Font", 0, 18)); // NOI18N
        T_J4.setForeground(new java.awt.Color(51, 51, 51));
        T_J4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_J4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_J4ActionPerformed(evt);
            }
        });

        T_J5.setBackground(new java.awt.Color(255, 255, 255));
        T_J5.setFont(new java.awt.Font("Hack Nerd Font", 0, 18)); // NOI18N
        T_J5.setForeground(new java.awt.Color(51, 51, 51));
        T_J5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_J5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_J5ActionPerformed(evt);
            }
        });

        T_J3.setBackground(new java.awt.Color(255, 255, 255));
        T_J3.setFont(new java.awt.Font("Hack Nerd Font", 0, 18)); // NOI18N
        T_J3.setForeground(new java.awt.Color(51, 51, 51));
        T_J3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_J3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_J3ActionPerformed(evt);
            }
        });

        B_START.setBackground(new java.awt.Color(102, 102, 255));
        B_START.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        B_START.setFont(new java.awt.Font("Hack Nerd Font", 0, 18)); // NOI18N
        B_START.setLabel("START");
        B_START.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_STARTActionPerformed(evt);
            }
        });

        T_CRO.setAlignment(java.awt.Label.CENTER);
        T_CRO.setFont(new java.awt.Font("Hack Nerd Font", 0, 30)); // NOI18N
        T_CRO.setForeground(new java.awt.Color(51, 51, 51));
        T_CRO.setText("-");

        label9.setAlignment(java.awt.Label.CENTER);
        label9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label9.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label9.setForeground(new java.awt.Color(51, 51, 51));
        label9.setText("X=0.8=");

        T_M.setBackground(new java.awt.Color(255, 255, 255));
        T_M.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        T_M.setForeground(new java.awt.Color(51, 51, 51));
        T_M.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_M.setCaretColor(new java.awt.Color(255, 255, 255));
        T_M.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        T_M.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_MActionPerformed(evt);
            }
        });

        label10.setAlignment(java.awt.Label.CENTER);
        label10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label10.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label10.setForeground(new java.awt.Color(51, 51, 51));
        label10.setText("Media");

        T_RE.setBackground(new java.awt.Color(255, 255, 255));
        T_RE.setFont(new java.awt.Font("Hack Nerd Font", 0, 18)); // NOI18N
        T_RE.setForeground(new java.awt.Color(51, 51, 51));
        T_RE.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_RE.setCaretColor(new java.awt.Color(255, 255, 255));
        T_RE.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        T_RE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_REActionPerformed(evt);
            }
        });

        label11.setAlignment(java.awt.Label.CENTER);
        label11.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label11.setForeground(new java.awt.Color(51, 51, 51));
        label11.setText("Jugador 1");

        label12.setAlignment(java.awt.Label.CENTER);
        label12.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label12.setForeground(new java.awt.Color(51, 51, 51));
        label12.setText("Jugador 3");

        label13.setAlignment(java.awt.Label.CENTER);
        label13.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label13.setForeground(new java.awt.Color(51, 51, 51));
        label13.setText("Jugador 4");

        label14.setAlignment(java.awt.Label.CENTER);
        label14.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label14.setForeground(new java.awt.Color(51, 51, 51));
        label14.setText("Jugador 5");

        label15.setAlignment(java.awt.Label.CENTER);
        label15.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        label15.setForeground(new java.awt.Color(51, 51, 51));
        label15.setText("Jugador 2");

        L_J2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        L_J5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        L_J4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        L_J3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        L_J1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(L_J1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(P_J1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(T_J1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(L_J2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(66, 66, 66))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(T_J2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50)))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(L_J3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(T_J3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(117, 117, 117)
                                                .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(89, 89, 89))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(L_J4, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(35, 35, 35)
                                                .addComponent(L_J5, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(52, 52, 52))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(54, 54, 54)
                                                .addComponent(T_J4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(T_J5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(73, 73, 73))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(P_J2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(172, 172, 172)
                                .addComponent(P_J3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(180, 180, 180)
                                .addComponent(P_J4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(P_J5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(128, 128, 128))))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(T_M, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(T_RE, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(B_START, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(T_CRO, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(P_J1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P_J2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P_J3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P_J4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(P_J5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(L_J4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(L_J2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(L_J1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(L_J5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(L_J3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(T_J5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(T_J3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(T_J4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(T_J2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(T_J1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(7, 7, 7))
                        .addComponent(T_CRO, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                        .addComponent(T_M))
                    .addComponent(T_RE, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(B_START, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        B_START.getAccessibleContext().setAccessibleName("BUTTON");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getAccessibleContext().setAccessibleName("frame1");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void T_REActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_REActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_REActionPerformed

    private void T_MActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_MActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_MActionPerformed

    private void T_J3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_J3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_J3ActionPerformed

    private void T_J5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_J5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_J5ActionPerformed

    private void T_J4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_J4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_J4ActionPerformed

    private void T_J1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_J1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_J1ActionPerformed

    private void T_J2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_J2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_J2ActionPerformed

    private void B_STARTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_STARTActionPerformed
        // TODO add your handling code here:
        B_START.setEnabled(false);
        calcular = false;

        T_M.setText(" - ");
        T_RE.setText(" - ");

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getLinea()) {
                ingr_n.get(i).setText("");
                ingr_n.get(i).setEnabled(true);
            }
        }

        contador = 25;
        timer.start();
    }//GEN-LAST:event_B_STARTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Game().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button B_START;
    private javax.swing.JLabel L_J1;
    private javax.swing.JLabel L_J2;
    private javax.swing.JLabel L_J3;
    private javax.swing.JLabel L_J4;
    private javax.swing.JLabel L_J5;
    private java.awt.Label P_J1;
    private java.awt.Label P_J2;
    private java.awt.Label P_J3;
    private java.awt.Label P_J4;
    private java.awt.Label P_J5;
    private java.awt.Label T_CRO;
    private javax.swing.JTextField T_J1;
    private javax.swing.JTextField T_J2;
    private javax.swing.JTextField T_J3;
    private javax.swing.JTextField T_J4;
    private javax.swing.JTextField T_J5;
    private javax.swing.JTextField T_M;
    private javax.swing.JTextField T_RE;
    private javax.swing.JPanel jPanel1;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label9;
    // End of variables declaration//GEN-END:variables
}

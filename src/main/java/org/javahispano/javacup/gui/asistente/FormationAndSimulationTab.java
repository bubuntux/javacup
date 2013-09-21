package org.javahispano.javacup.gui.asistente;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import static org.javahispano.javacup.model.util.Constants.RADIO_CIRCULO_CENTRAL;

public class FormationAndSimulationTab {
    final String constNames[] =
        new String[]{"centroArcoInf", "posteDerArcoSup", "posteIzqArcoSup", "cornerSupDer", "cornerSupIzq", "penalSup", "centroArcoSup",
            "posteDerArcoInf", "posteIzqArcoInf", "cornerInfDer", "cornerInfIzq", "penalInf", "centroCampoJuego"};
    final Position constPos[] =
        new Position[]{Constants.centroArcoInf, Constants.posteDerArcoSup, Constants.posteIzqArcoSup, Constants.cornerSupDer, Constants.cornerSupIzq,
            Constants.penalSup, Constants.centroArcoSup, Constants.posteDerArcoInf, Constants.posteIzqArcoInf, Constants.cornerInfDer,
            Constants.cornerInfIzq, Constants.penalInf, Constants.centroCampoJuego};
    private final TacticDetailImpl impl;
    private final JList<JugadorImpl> jList1;
    boolean cupdate = true;
    JPanel jPanel4 = new JPanel();
    double fuerza = 1;
    double ang = 0;
    double elev = 0;
    double sx = 442, sy = 286;
    double distMin;
    int type = 0;
    Position p0;
    String tooltip = "";
    int x0, y0;
    Position pos = null;
    DecimalFormat df = new DecimalFormat();
    private JToggleButton jToggleButton2;
    private JPanel jPanel5;
    private JLabel jLabel14;
    private JComboBox<String> jComboBox2;
    private JTextField jTextField5;
    private JComboBox<String> jComboBox3;
    private DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
    private JTextField jTextField6;
    private JTextField jTextField7;
    private Image campo;
    private JCheckBox simulationCheckBox = new JCheckBox();

    public FormationAndSimulationTab(TacticDetailImpl imp2l, JList<JugadorImpl> jList1) {
        this.impl = imp2l;
        this.jList1 = jList1;
        campo = new ImageIcon(getClass().getResource("/imagenes/campo.jpg")).getImage();

        jPanel4.setFont(new Font("Arial", 0, 14));

        jToggleButton2 = new JToggleButton();
        jToggleButton2.setFont(new Font("Arial", 0, 12));
        jToggleButton2.setText("Constantes");
        jToggleButton2.setFocusable(false);
        jToggleButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jLabel14 = new JLabel();
        jLabel14.setFont(new Font("Arial", 0, 12));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("0,0"); // NOI18N

        JButton jButton11 = new JButton();
        JButton jButton12 = new JButton();
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/add.png"))); // NOI18N
        jButton11.setFocusable(false);
        jButton11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/delete.png"))); // NOI18N
        jButton12.setFocusable(false);
        jButton12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        JButton jButton10 = new JButton();
        JButton jButton13 = new JButton();
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/up.png"))); // NOI18N
        jButton10.setFocusable(false);
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/down.png"))); // NOI18N
        jButton13.setFocusable(false);
        jButton13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jComboBox3 = new JComboBox<>();
        jComboBox2 = new JComboBox<>();
        jComboBox3.setFont(new Font("Arial", 0, 12));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"1"}));
        jComboBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jComboBox3.setModel(model1);

        jComboBox2.setFont(new Font("Arial", 0, 12));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Normal", "Inicio Sacando", "Inicio Recibiendo"}));
        jComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        JPanel jPanel8 = new JPanel();
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.PAGE_AXIS));

        JLabel jLabel18 = new JLabel();
        jLabel18.setFont(new Font("Arial", 0, 12));
        jLabel18.setText("Fuerza"); // NOI18N
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel18.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel18.setMinimumSize(new java.awt.Dimension(50, 15));
        jLabel18.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel8.add(jLabel18);

        jTextField5 = new JTextField();
        jTextField5.setFont(new Font("Arial", 0, 12));
        jTextField5.setHorizontalAlignment(JTextField.RIGHT);
        jTextField5.setText("1"); // NOI18N
        jTextField5.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jTextField5.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent evt) {
                jTextField5CaretUpdate(evt);
            }
        });
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                jTextField5FocusLost(evt);
            }
        });
        jPanel8.add(jTextField5);

        JLabel jLabel23 = new JLabel();
        jLabel23.setFont(new Font("Arial", 0, 12));
        jLabel23.setText("Angulo"); // NOI18N
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel23.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel23.setMinimumSize(new java.awt.Dimension(50, 15));
        jLabel23.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel8.add(jLabel23);

        jTextField6 = new JTextField();
        jTextField6.setFont(new Font("Arial", 0, 12));
        jTextField6.setHorizontalAlignment(JTextField.RIGHT);
        jTextField6.setText("0"); // NOI18N
        jTextField6.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jTextField6.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent evt) {
                jTextField6CaretUpdate(evt);
            }
        });
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                jTextField6FocusLost(evt);
            }
        });
        jPanel8.add(jTextField6);

        JLabel jLabel24 = new JLabel();
        jLabel24.setFont(new Font("Arial", 0, 12));
        jLabel24.setText("Angulo Z"); // NOI18N
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel24.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel24.setMinimumSize(new java.awt.Dimension(50, 15));
        jLabel24.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel8.add(jLabel24);

        jTextField7 = new JTextField();
        JLabel jLabel21 = new JLabel();
        jTextField7.setFont(new Font("Arial", 0, 12));
        jTextField7.setHorizontalAlignment(JTextField.RIGHT);
        jTextField7.setText("0"); // NOI18N
        jTextField7.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jTextField7.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(CaretEvent evt) {
                jTextField7CaretUpdate(evt);
            }
        });
        jTextField7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                jTextField7FocusLost(evt);
            }
        });
        jPanel8.add(jTextField7);

        jLabel21.setText(" "); // NOI18N
        jPanel8.add(jLabel21);

        simulationCheckBox.setFont(new Font("Arial", 0, 12));
        simulationCheckBox.setText(" >> ");
        simulationCheckBox.setFocusable(false);

        simulationCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPanel5.repaint();
            }
        });

        jPanel8.add(simulationCheckBox);

        JPanel jPanel10 = new JPanel();
        JLabel jLabel22 = new JLabel();
        GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(LEADING).addGap(0, 50, Short.MAX_VALUE));
        jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(LEADING).addGap(0, 72, Short.MAX_VALUE));

        jPanel8.add(jPanel10);

        jLabel22.setFont(new Font("Arial", 0, 12));
        jLabel22.setText("Leyenda"); // NOI18N
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel22.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel22.setMinimumSize(new java.awt.Dimension(50, 15));
        jLabel22.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel8.add(jLabel22);

        JPanel jPanel11 = new JPanel();
        JPanel jPanel12 = new JPanel();
        jPanel11.setBackground(new Color(255, 0, 51));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jPanel11.setToolTipText("Fuera del alcance");

        GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(LEADING).addGap(0, 48, Short.MAX_VALUE));
        jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(LEADING).addGap(0, 22, Short.MAX_VALUE));

        jPanel8.add(jPanel11);

        jPanel12.setBackground(new Color(0, 0, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jPanel12.setToolTipText("Alcance de portero");

        GroupLayout jPanel12Layout = new GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(LEADING).addGap(0, 48, Short.MAX_VALUE));
        jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(LEADING).addGap(0, 22, Short.MAX_VALUE));

        jPanel8.add(jPanel12);

        JPanel jPanel13 = new JPanel();
        jPanel13.setBackground(new Color(0, 255, 0));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jPanel13.setToolTipText("Alcance de jugadores");

        GroupLayout jPanel13Layout = new GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(jPanel13Layout.createParallelGroup(LEADING).addGap(0, 48, Short.MAX_VALUE));
        jPanel13Layout.setVerticalGroup(jPanel13Layout.createParallelGroup(LEADING).addGap(0, 22, Short.MAX_VALUE));

        jPanel8.add(jPanel13);

        jPanel5 = new JPanel() {

            Font f = new Font("lucida console", 0, 10);

            public void paint(Graphics g) {
                g.drawImage(campo, 0, 0, null);
                Position p;
                int num;
                int i = 0;
                Position[] posiciones = impl.getAlineacion(jComboBox3.getSelectedIndex());
                double remate = 0, error = 0;
                double x = 0, y = 0;
                for (org.javahispano.javacup.model.PlayerDetail j : impl.getPlayers()) {
                    p = posiciones[i];
                    boolean ok = true;
                    int idx = jComboBox2.getSelectedIndex();
                    if (idx == 1 && p.getY() > 0) {
                        ok = false;
                    }
                    if (idx == 2 &&
                        (p.getY() > 0 || p.distance(org.javahispano.javacup.model.util.Constants.centroCampoJuego) <= RADIO_CIRCULO_CENTRAL)) {
                        ok = false;
                    }
                    if (p == null) {
                        posiciones[i] = new Position(0, 0);
                        p = posiciones[i];
                    }
                    num = j.getNumber();
                    if (j.equals(getJugador())) {
                        g.setColor(Color.yellow);
                        remate = org.javahispano.javacup.model.util.Constants.getVelocidadRemate(j.getPower());
                        error = org.javahispano.javacup.model.util.Constants.getErrorAngular(j.getPrecision());
                        x = p.getX();
                        y = p.getY();
                    } else {
                        g.setColor(Color.lightGray);
                    }
                    p = transformAsistente(p);
                    g.setFont(f);
                    g.drawOval((int) p.getX() - 3, (int) p.getY() - 3, 6, 6);
                    g.drawString("" + num, (int) p.getX() + 4, (int) p.getY());
                    if (j.equals(getJugador())) {
                        g.drawString(j.getPlayerName(), (int) p.getX() - 6 * j.getPlayerName().length() / 2, (int) p.getY() + 10);
                    }
                    if (!ok) {
                        g.setColor(Color.red);
                        g.drawLine((int) p.getX() - 3, (int) p.getY() - 3, (int) p.getX() + 3, (int) p.getY() + 3);
                        g.drawLine((int) p.getX() - 3, (int) p.getY() + 3, (int) p.getX() + 3, (int) p.getY() - 3);
                    }
                    i++;
                }
                if (jToggleButton2.isSelected()) {
                    for (Position pp : constPos) {
                        pp = transformAsistente(pp);
                        g.setColor(Color.white);
                        g.drawRect((int) pp.getX() - 2, (int) pp.getY() - 2, 5, 5);
                    }
                    if (pos != null) {
                        g.setColor(Color.red);
                        g.drawRect((int) pos.getX() - 2, (int) pos.getY() - 2, 5, 5);
                        Position pkt = unTransformAsistente(pos);
                        g.setColor(Color.white);
                        g.drawString(tooltip, x0 - (int) (pkt.getY() / 3), y0);
                    }
                }
                if (remate > 0) {
                    simula(g, x, y, remate, error);
                }
            }
        };

        jPanel5.setBackground(new Color(102, 102, 102));
        jPanel5.setMaximumSize(new java.awt.Dimension(216, 145));
        jPanel5.setMinimumSize(new java.awt.Dimension(216, 145));
        jPanel5.setPreferredSize(new java.awt.Dimension(476, 307));
        jPanel5.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jPanel5MouseWheelMoved(evt);
            }
        });
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jPanel5MousePressed(evt);
            }
        });
        jPanel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                jPanel5MouseDragged(evt);
            }

            public void mouseMoved(MouseEvent evt) {
                jPanel5MouseMoved(evt);
            }
        });
        jPanel5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                jPanel5KeyTyped(evt);
            }
        });

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(LEADING).addGap(0, 476, Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(LEADING).addGap(0, 307, Short.MAX_VALUE));

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(LEADING).addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(LEADING).addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(RELATED)
                .addComponent(jPanel8, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)).addGroup(
                jPanel4Layout.createSequentialGroup().addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(RELATED).addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(RELATED).addComponent(jButton11, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(RELATED).addComponent(jButton12, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(RELATED).addComponent(jButton10, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(RELATED).addComponent(jButton13, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(RELATED).addComponent(jToggleButton2).addPreferredGap(RELATED)
                    .addComponent(jLabel14, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE))).addContainerGap()));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(LEADING).addGroup(GroupLayout.Alignment.TRAILING,
            jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(jPanel8, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addComponent(jPanel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(LEADING).addGroup(jPanel4Layout.createParallelGroup(LEADING, false).addGroup(
                    jPanel4Layout.createParallelGroup(BASELINE).addComponent(jComboBox3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton11, 0, 0, Short.MAX_VALUE).addComponent(jButton12, 0, 0, Short.MAX_VALUE)
                    .addComponent(jButton10, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton13, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE).addGroup(
                        jPanel4Layout.createParallelGroup(BASELINE).addComponent(jLabel14, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton2, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))).addContainerGap()));
    }

    private void jToggleButton2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        jPanel5.repaint();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    public JPanel getjPanel4() {
        return jPanel4;
    }

    private void jButton12ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int idx = jComboBox3.getSelectedIndex();
        int size = jComboBox3.getModel().getSize();
        if (size == 1) {
            JOptionPane.showMessageDialog(jPanel4, "No puede eliminar todas las alineaciones", "", JOptionPane.WARNING_MESSAGE);
        } else {
            model1.removeElementAt(size - 1);
            impl.delAlineacion(idx);
            jPanel5.repaint();
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField5FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusLost
        try {
            fuerza = Double.parseDouble(jTextField5.getText().trim());
        } catch (Exception ex) {
        }
        if (fuerza < 0) {
            fuerza = 0;
        }
        if (fuerza > 1) {
            fuerza = 1;
        }
        jTextField5.setText("" + fuerza);
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField5FocusLost

    private void jTextField5CaretUpdate(CaretEvent evt) {//GEN-FIRST:event_jTextField5CaretUpdate
        try {
            fuerza = Double.parseDouble(jTextField5.getText().trim());
            jTextField5.setForeground(Color.black);
        } catch (Exception ex) {
            jTextField5.setForeground(Color.red);
        }
        if (fuerza < 0) {
            fuerza = 0;
        }
        if (fuerza > 1) {
            fuerza = 1;
        }
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField5CaretUpdate

    private void jComboBox2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        try {
            jPanel5.repaint();
            int idx = jComboBox3.getSelectedIndex();
            impl.setAlineacion(idx, impl.getAlineacion(idx), jComboBox2.getSelectedIndex());
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        int idx = jComboBox3.getSelectedIndex();
        if (idx == -1) {
            idx = 0;
        }
        jComboBox2.setSelectedIndex(impl.getTipoAlineacion(idx));
        jPanel5.repaint();
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton11ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int idx = jComboBox3.getSelectedIndex();
        impl.addAlineacion(idx);
        int size = jComboBox3.getModel().getSize() + 1;
        model1.addElement("" + size);
        jComboBox3.setSelectedIndex(size - 1);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton13ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        int idx = jComboBox3.getSelectedIndex();
        if (idx < jComboBox3.getModel().getSize() - 1) {
            cupdate = false;
            Position[] al = impl.getAlineacion(idx + 1);
            int tipo = impl.getTipoAlineacion(idx + 1);
            impl.setAlineacion(idx + 1, impl.getAlineacion(idx), impl.getTipoAlineacion(idx));
            impl.setAlineacion(idx, al, tipo);
            jComboBox3.setSelectedIndex(idx + 1);
            jComboBox2.setSelectedIndex(impl.getTipoAlineacion(idx + 1));
            jPanel5.repaint();
            cupdate = true;
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton10ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        int idx = jComboBox3.getSelectedIndex();
        if (idx > 0) {
            cupdate = false;
            Position[] al = impl.getAlineacion(idx - 1);
            int tipo = impl.getTipoAlineacion(idx - 1);
            impl.setAlineacion(idx - 1, impl.getAlineacion(idx), impl.getTipoAlineacion(idx));
            impl.setAlineacion(idx, al, tipo);
            jComboBox3.setSelectedIndex(idx - 1);
            jComboBox2.setSelectedIndex(impl.getTipoAlineacion(idx - 1));
            jPanel5.repaint();
            cupdate = true;
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jTextField6FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusLost
        try {
            ang = Double.parseDouble(jTextField6.getText().trim());
        } catch (Exception ex) {
        }
        jTextField6.setText("" + ang);
    }//GEN-LAST:event_jTextField6FocusLost

    private void jTextField6CaretUpdate(CaretEvent evt) {//GEN-FIRST:event_jTextField6CaretUpdate
        try {
            ang = Double.parseDouble(jTextField6.getText().trim());
            jTextField6.setForeground(Color.black);
        } catch (Exception ex) {
            jTextField6.setForeground(Color.red);
        }
    }//GEN-LAST:event_jTextField6CaretUpdate

    private void jTextField7FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextField7FocusLost
        try {
            elev = Double.parseDouble(jTextField7.getText().trim());
        } catch (Exception ex) {
        }
        if (elev < 0) {
            elev = 0;
        }
        if (elev > Constants.ANGULO_VERTICAL_MAX) {
            elev = Constants.ANGULO_VERTICAL_MAX;
        }
        jTextField7.setText("" + elev);
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField7FocusLost

    private void jTextField7CaretUpdate(CaretEvent evt) {//GEN-FIRST:event_jTextField7CaretUpdate
        try {
            elev = Double.parseDouble(jTextField7.getText().trim());
            jTextField7.setForeground(Color.black);
        } catch (Exception ex) {
            jTextField7.setForeground(Color.red);
        }
        if (elev < 0) {
            elev = 0;
        }
        if (elev > Constants.ANGULO_VERTICAL_MAX) {
            elev = Constants.ANGULO_VERTICAL_MAX;
        }
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField7CaretUpdate

    private void jPanel5MouseDragged(MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseDragged
        if (type == MouseEvent.BUTTON1) {
            if (distMin < 20) {
                Position[] Positiones = impl.getAlineacion(jComboBox3.getSelectedIndex());
                Position p = unTransformAsistente(new Position((double) evt.getX() - 8, (double) evt.getY() - 3));
                Positiones[jList1.getSelectedIndex()] = p;
                jLabel14.setText(df.format(p.getY()) + ":" + df.format(p.getX()));
                jPanel5.repaint();
            }
        } else {
            Position p1 = new Position(evt.getX(), evt.getY());
            double angu = -p0.angle(p1) * 180 / Math.PI;
            if (angu < 0) {
                angu = 360 + angu;
            }
            double dist = p0.distance(p1) / 50;
            if (dist > 1) {
                dist = 1;
            }
            jTextField6.setText(df.format(angu).replace(",", "."));
            jTextField5.setText(df.format(dist).replace(",", "."));
            jPanel5.repaint();
        }
    }//GEN-LAST:event_jPanel5MouseDragged

    private void jPanel5MousePressed(MouseEvent evt) {//GEN-FIRST:event_jPanel5MousePressed
        type = evt.getButton();
        jPanel5.requestFocus();
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Position click = new Position((double) evt.getX() - 8, (double) evt.getY() - 3);
            double dist;
            distMin = Double.MAX_VALUE;
            int idx = 0;
            Position[] Positiones = impl.getAlineacion(jComboBox3.getSelectedIndex());
            for (int i = 0; i < 11; i++) {
                dist = click.distance(transformAsistente(Positiones[i]));
                if (dist < distMin) {
                    distMin = dist;
                    idx = i;
                }
            }
            if (distMin < 20) {
                jList1.setSelectedIndex(idx);
                jPanel5.repaint();
            }
        } else {
            p0 = transformAsistente(impl.getAlineacion(jComboBox3.getSelectedIndex())[jList1.getSelectedIndex()]);
        }
    }//GEN-LAST:event_jPanel5MousePressed

    Position unTransformAsistente(Position p) {
        double xp0 = (p.getX() - sx / 2 - 6);
        double yp0 = (p.getY() - sy / 2 - 6);
        xp0 = xp0 / sx;
        yp0 = yp0 / sy;
        double y = (Constants.LARGO_CAMPO_JUEGO * xp0);
        double x = (Constants.ANCHO_CAMPO_JUEGO * yp0);
        Position posi = new Position(x, y);
        return posi.setInsideGameField();
    }

    private void jPanel5MouseMoved(MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseMoved
        Position p = unTransformAsistente(new Position(evt.getX() - 8, evt.getY() - 3));
        jLabel14.setText(df.format(p.getY()) + ":" + df.format(p.getX()));
        int idx = p.nearestIndex(constPos);
        if (p.distance(constPos[idx]) < 10) {
            tooltip = constNames[idx];
            jPanel5.repaint();
            x0 = evt.getX() - tooltip.length() * 3 + 10;
            y0 = evt.getY();
            pos = transformAsistente(constPos[idx]);
        } else {
            tooltip = "";
            jPanel5.repaint();
            pos = null;
        }
    }//GEN-LAST:event_jPanel5MouseMoved

    private void jPanel5KeyTyped(KeyEvent evt) {//GEN-FIRST:event_jPanel5KeyTyped
    }//GEN-LAST:event_jPanel5KeyTyped

    private void jPanel5MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jPanel5MouseWheelMoved
        int units = -evt.getUnitsToScroll() / Math.abs(evt.getUnitsToScroll());
        try {
            units = (int) (Double.parseDouble(jTextField7.getText().trim())) + units;
            if (units < 0) {
                units = 0;
            }
            if (units > Constants.ANGULO_VERTICAL_MAX) {
                units = (int) Constants.ANGULO_VERTICAL_MAX;
            }
            jTextField7.setText("" + units);
            jPanel5.repaint();
        } catch (Exception e) {
            jTextField7.setText("0");
        }
    }//GEN-LAST:event_jPanel5MouseWheelMoved

    private void simula(Graphics graphics, double x, double y, double remate, double error) {
        if (simulationCheckBox.isSelected()) {
            Graphics2D gr = (Graphics2D) graphics;
            //ang -> rad
            error = 90d * error;
            double ang0 = ang - error;
            double av = elev * Math.PI / 180d;
            //velocidad
            double vel = remate * fuerza;
            //direccion;
            double dz = vel * Math.sin(av);
            double dr = vel * Math.cos(av);
            AbstractTrajectory trayectoria = new AirTrajectory(dr, dz, 0, 0);
            //coordenadas
            double r;
            double z;
            //booleans
            boolean alcanceJugador;
            boolean alcancePortico;
            //bucle
            Color c;
            Color red = new Color(1f, 0f, 0f, .5f);
            Color green = new Color(0f, 1f, 0f, .5f);
            Color blue = new Color(0f, 0f, 1f, .5f);
            double rr;
            Position p = transformAsistente(new Position(x, y));
            for (int i = 0; i < 500; i++) {
                r = trayectoria.getX((double) i / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
                z = trayectoria.getY((double) i / 60d) * Constants.AMPLIFICA_VEL_TRAYECTORIA;
                alcanceJugador = z < Constants.ALTURA_CONTROL_BALON;
                alcancePortico = z < Constants.ALTO_ARCO;
                if (!alcancePortico) {
                    c = red;
                } else {
                    if (alcanceJugador) {
                        c = green;
                    } else {
                        c = blue;
                    }
                }
                rr = transformAsistente(r);
                gr.setColor(c);
                gr.drawArc((int) (p.getX() - rr), (int) (p.getY() - rr), (int) (rr * 2), (int) (rr * 2), (int) ang0, (int) error * 2);
            }
        }
    }

    Position transformAsistente(Position pos) {
        double x = 15 + sx / 2 + sx * pos.getY() / Constants.LARGO_CAMPO_JUEGO;
        double y = 10 + sy / 2 + sy * pos.getX() / Constants.ANCHO_CAMPO_JUEGO;
        return new Position(x, y);
    }

    double transformAsistente(double r) {
        return sx * r / Constants.LARGO_CAMPO_JUEGO;
    }

    private JugadorImpl getJugador() {
        return jList1.getSelectedValue();
    }

    public void postInit() {
        model1.addElement("1");
        model1.addElement("2");
        model1.addElement("3");
        model1.addElement("4");
        model1.addElement("5");
        model1.addElement("6");
        jComboBox3.setSelectedIndex(0);
    }

    public void setFormationCount(int formationCount) {
        model1.removeAllElements();
        for (int i = 0; i < formationCount; i++) {
            model1.addElement("" + (i + 1));
        }
        jComboBox3.setSelectedIndex(0);
    }
}



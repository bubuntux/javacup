package org.javahispano.javacup.gui.asistente;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import org.javahispano.javacup.model.PlayerDetail;
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
    private final JList<JugadorImpl> _playerList;
    private final TacticDetailImpl _tacticDetail;
    boolean cupdate = true;
    JPanel jPanel4 = new JPanel();
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
    private JComboBox<String> jComboBox3;
    private DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>();
    private Image campo;
    private JCheckBox _simulationCheckBox;
    private Font _font;
    private double _strength = 0.8;
    private int _angle;
    private int _angleZ;

    public FormationAndSimulationTab(TacticDetailImpl tacticDetail, JList<JugadorImpl> playerList) {
        _tacticDetail = tacticDetail;
        _playerList = playerList;

        _font = new Font("Arial", 0, 12);

        campo = new ImageIcon(getClass().getResource("/imagenes/campo.jpg")).getImage();

        jPanel4.setFont(new Font("Arial", 0, 14));

        jToggleButton2 = new JToggleButton();
        jToggleButton2.setFont(_font);
        jToggleButton2.setText("Constantes");
        jToggleButton2.setFocusable(false);
        jToggleButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jToggleButton2ActionPerformed();
            }
        });

        jLabel14 = new JLabel();
        jLabel14.setFont(_font);
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("0,0");

        JButton jButton11 = new JButton();
        JButton jButton12 = new JButton();
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/add.png"))); // NOI18N
        jButton11.setFocusable(false);
        jButton11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton11ActionPerformed();
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/delete.png"))); // NOI18N
        jButton12.setFocusable(false);
        jButton12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton12ActionPerformed();
            }
        });

        JButton jButton10 = new JButton();
        JButton jButton13 = new JButton();
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/up.png"))); // NOI18N
        jButton10.setFocusable(false);
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton10ActionPerformed();
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconos/down.png"))); // NOI18N
        jButton13.setFocusable(false);
        jButton13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton13ActionPerformed();
            }
        });

        jComboBox3 = new JComboBox<>();
        jComboBox2 = new JComboBox<>();
        jComboBox3.setFont(_font);
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"1"}));
        jComboBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox3ActionPerformed();
            }
        });
        jComboBox3.setModel(model1);

        jComboBox2.setFont(_font);
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Normal", "Inicio Sacando", "Inicio Recibiendo"}));
        jComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox2ActionPerformed();
            }
        });

        JPanel jPanel8 = new JPanel();
        jPanel8.setLayout(new BoxLayout(jPanel8, BoxLayout.PAGE_AXIS));

        addStrengthComponents(jPanel8);

        addAngleComponents(jPanel8);

        addAngleZComponents(jPanel8);

        JLabel emptyLabel = new JLabel();
        emptyLabel.setText(" ");
        jPanel8.add(emptyLabel);

        addSimulationComponents(jPanel8);

        JPanel jPanel10 = new JPanel();
        JLabel jLabel22 = new JLabel();
        GroupLayout jPanel10Layout = new GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(LEADING).addGap(0, 50, Short.MAX_VALUE));
        jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(LEADING).addGap(0, 72, Short.MAX_VALUE));

        jPanel8.add(jPanel10);

        jLabel22.setFont(_font);
        jLabel22.setText("Leyenda");
        jLabel22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel22.setMaximumSize(new Dimension(50, 15));
        jLabel22.setMinimumSize(new Dimension(50, 15));
        jLabel22.setPreferredSize(new Dimension(50, 15));
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
                Position[] posiciones = _tacticDetail.getAlineacion(jComboBox3.getSelectedIndex());
                double remate = 0, error = 0;
                double x = 0, y = 0;
                for (PlayerDetail j : _tacticDetail.getPlayers()) {
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
        Dimension preferredSize = new Dimension(400, 300);
        jPanel5.setMaximumSize(preferredSize);
        jPanel5.setMinimumSize(preferredSize);
        jPanel5.setPreferredSize(preferredSize);

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

    private void addSimulationComponents(JPanel panel) {
        _simulationCheckBox = new JCheckBox();
        _simulationCheckBox.setFont(_font);
        _simulationCheckBox.setText("Simulation");

        _simulationCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPanel5.repaint();
            }
        });
        panel.add(_simulationCheckBox);
    }

    private void addAngleZComponents(JPanel panel) {
        final String angleZText = "Angle Z";

        final JLabel angleZLabel = new JLabel();
        angleZLabel.setFont(_font);
        setText(angleZLabel, angleZText, _angleZ);
        angleZLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        panel.add(angleZLabel);

        final JSlider angleZSlider = new JSlider();
        angleZSlider.setValue(_angleZ);
        angleZSlider.setMinimum(Constants.ANGULO_VERTICAL_MIN);
        angleZSlider.setMaximum(Constants.ANGULO_VERTICAL_MAX);
        angleZSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                _angleZ = angleZSlider.getValue();
                setText(angleZLabel, angleZText, _angleZ);
                jPanel5.repaint();
            }
        });
        panel.add(angleZSlider);
    }

    private void addAngleComponents(JPanel panel) {
        final String angleText = "Angle";

        final JLabel angleLabel = new JLabel();
        angleLabel.setFont(_font);
        setText(angleLabel, angleText, _angle);
        angleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        panel.add(angleLabel);

        final JSlider angleSlider = new JSlider();
        angleSlider.setValue(_angle);
        angleSlider.setMinimum(-180);
        angleSlider.setMaximum(180);
        angleSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                _angle = angleSlider.getValue();
                setText(angleLabel, angleText, _angle);
                jPanel5.repaint();
            }
        });
        panel.add(angleSlider);
    }

    private void addStrengthComponents(JPanel panel) {
        final String strengthText = "Strength";

        final JLabel strengthLabel = new JLabel();
        strengthLabel.setFont(_font);
        strengthLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        setText(strengthLabel, strengthText, _strength);
        panel.add(strengthLabel);

        final JSlider strengthSlider = new JSlider();
        strengthSlider.setValue((int) (_strength * 100));
        strengthSlider.setMinimum(0);
        strengthSlider.setMaximum(100);
        strengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                _strength = strengthSlider.getValue() / 100d;
                setText(strengthLabel, strengthText, _strength);
                jPanel5.repaint();
            }
        });
        panel.add(strengthSlider);
    }

    private void setText(JLabel label, String name, double value) {
        label.setText(name + " (" + value + ")");
    }

    private void setText(JLabel label, String name, int value) {
        label.setText(name + " (" + value + ")");
    }

    private void jToggleButton2ActionPerformed() {
        jPanel5.repaint();
    }

    public JPanel getjPanel4() {
        return jPanel4;
    }

    private void jButton12ActionPerformed() {
        int idx = jComboBox3.getSelectedIndex();
        int size = jComboBox3.getModel().getSize();
        if (size == 1) {
            JOptionPane.showMessageDialog(jPanel4, "No puede eliminar todas las alineaciones", "", JOptionPane.WARNING_MESSAGE);
        } else {
            model1.removeElementAt(size - 1);
            _tacticDetail.delAlineacion(idx);
            jPanel5.repaint();
        }
    }

    private void jComboBox2ActionPerformed() {
        try {
            jPanel5.repaint();
            int idx = jComboBox3.getSelectedIndex();
            _tacticDetail.setAlineacion(idx, _tacticDetail.getAlineacion(idx), jComboBox2.getSelectedIndex());
        } catch (Exception ignored) {
        }
    }

    private void jComboBox3ActionPerformed() {
        int idx = jComboBox3.getSelectedIndex();
        if (idx == -1) {
            idx = 0;
        }
        jComboBox2.setSelectedIndex(_tacticDetail.getTipoAlineacion(idx));
        jPanel5.repaint();
    }

    private void jButton11ActionPerformed() {
        int idx = jComboBox3.getSelectedIndex();
        _tacticDetail.addAlineacion(idx);
        int size = jComboBox3.getModel().getSize() + 1;
        model1.addElement("" + size);
        jComboBox3.setSelectedIndex(size - 1);
    }

    private void jButton13ActionPerformed() {
        int idx = jComboBox3.getSelectedIndex();
        if (idx < jComboBox3.getModel().getSize() - 1) {
            cupdate = false;
            Position[] al = _tacticDetail.getAlineacion(idx + 1);
            int tipo = _tacticDetail.getTipoAlineacion(idx + 1);
            _tacticDetail.setAlineacion(idx + 1, _tacticDetail.getAlineacion(idx), _tacticDetail.getTipoAlineacion(idx));
            _tacticDetail.setAlineacion(idx, al, tipo);
            jComboBox3.setSelectedIndex(idx + 1);
            jComboBox2.setSelectedIndex(_tacticDetail.getTipoAlineacion(idx + 1));
            jPanel5.repaint();
            cupdate = true;
        }
    }

    private void jButton10ActionPerformed() {
        int idx = jComboBox3.getSelectedIndex();
        if (idx > 0) {
            cupdate = false;
            Position[] al = _tacticDetail.getAlineacion(idx - 1);
            int tipo = _tacticDetail.getTipoAlineacion(idx - 1);
            _tacticDetail.setAlineacion(idx - 1, _tacticDetail.getAlineacion(idx), _tacticDetail.getTipoAlineacion(idx));
            _tacticDetail.setAlineacion(idx, al, tipo);
            jComboBox3.setSelectedIndex(idx - 1);
            jComboBox2.setSelectedIndex(_tacticDetail.getTipoAlineacion(idx - 1));
            jPanel5.repaint();
            cupdate = true;
        }
    }

    private void jPanel5MouseDragged(MouseEvent evt) {
        if (type == MouseEvent.BUTTON1) {
            if (distMin < 20) {
                Position p = unTransformAsistente(new Position((double) evt.getX() - 8, (double) evt.getY() - 3));
                Position[] Positiones = _tacticDetail.getAlineacion(jComboBox3.getSelectedIndex());
                Positiones[_playerList.getSelectedIndex()] = p;
                jLabel14.setText(df.format(p.getY()) + ":" + df.format(p.getX()));
                jPanel5.repaint();
            }
        } else {
            jPanel5.repaint();
        }
    }

    private void jPanel5MousePressed(MouseEvent evt) {//GEN-FIRST:event_jPanel5MousePressed
        type = evt.getButton();
        jPanel5.requestFocus();
        if (evt.getButton() == MouseEvent.BUTTON1) {
            Position click = new Position((double) evt.getX() - 8, (double) evt.getY() - 3);
            double dist;
            distMin = Double.MAX_VALUE;
            int idx = 0;
            Position[] Positiones = _tacticDetail.getAlineacion(jComboBox3.getSelectedIndex());
            for (int i = 0; i < 11; i++) {
                dist = click.distance(transformAsistente(Positiones[i]));
                if (dist < distMin) {
                    distMin = dist;
                    idx = i;
                }
            }
            if (distMin < 20) {
                _playerList.setSelectedIndex(idx);
                jPanel5.repaint();
            }
        } else {
            p0 = transformAsistente(_tacticDetail.getAlineacion(jComboBox3.getSelectedIndex())[_playerList.getSelectedIndex()]);
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

    private void jPanel5MouseMoved(MouseEvent evt) {
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
    }

    private void simula(Graphics graphics, double x, double y, double remate, double error) {
        if (_simulationCheckBox.isSelected()) {
            Graphics2D gr = (Graphics2D) graphics;
            //ang -> rad
            error = 90d * error;
            double ang0 = _angle - error;
            double av = _angleZ * Math.PI / 180d;
            //velocidad
            double vel = remate * _strength;
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
        return _playerList.getSelectedValue();
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



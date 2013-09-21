package org.javahispano.javacup.gui.asistente;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;
import com.thoughtworks.xstream.XStream;
import org.javahispano.javacup.model.PlayerDetail;
import org.javahispano.javacup.model.trajectory.AbstractTrajectory;
import org.javahispano.javacup.model.trajectory.AirTrajectory;
import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;
import org.javahispano.javacup.model.util.TacticValidate;
import org.javahispano.javacup.render.EstiloUniforme;
import org.javahispano.javacup.render.PintaJugador;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import static org.javahispano.javacup.model.util.Constants.RADIO_CIRCULO_CENTRAL;

/**
 * Aplicacion que permite generar codigo para la implementacion de la clase TacticDetail.
 * Ademas permite gestionar alineaciones y simular remates
 */
public class AsistenteFrame extends javax.swing.JFrame implements Runnable {

    private static final String[] paises =
        new String[]{"", "Afganistán", "Albania", "Argelia", "Samoa Americana", "Andorra", "Angola", "Anguila", "Antigua y Barbuda", "Argentina",
            "Armenia", "Aruba", "Australia", "Austria", "Azerbaiyán", "Bahamas", "Bahrein", "Bangladesh", "Barbados", "Bélgica", "Belice", "Benin",
            "Bermudas", "Bhután", "Bolivia", "Bosnia y Herzegovina", "Botswana", "Brasil", "Islas Vírgenes Británicas", "Brunei Darussalam",
            "Bulgaria", "Burkina Faso", "Burundi", "Belarús", "Camboya", "Camerún", "Canadá", "Cabo Verde", "Islas Caimán",
            "República Centroafricana", "Chad", "Chile", "China", "Colombia", "Comoras", "Congo", "Costa Rica", "Costa de Marfil", "Croacia", "Cuba",
            "Chipre", "República Checa", "República Popular Democrática de Corea", "Dinamarca", "Djibouti", "Dominica", "República Dominicana",
            "Ecuador", "Egipto", "El Salvador", "Guinea Ecuatorial", "Eritrea", "Estonia", "Etiopía", "Islas Malvinas", "Islas Feroe",
            "República Federal de Alemania", "Fiji", "Finlandia", "Francia", "Gabón", "Gambia", "Georgia", "Ghana", "Grecia", "Groenlandia",
            "Granada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haití", "Honduras", "Hong Kong", "Hungría", "Islandia", "India",
            "Indonesia", "Irán", "Iraq", "Irlanda", "Israel", "Italia", "Jamaica", "Japón", "Jordania", "Kazajstán", "Kenya", "Kiribati", "Kuwait",
            "Kirguistán", "República Democrática Popular Lao", "Letonia", "Líbano", "Lesotho", "Liberia", "Libia", "Liechtenstein", "Lituania",
            "Luxemburgo", "Macao", "Madagascar", "Malawi", "Malasia", "Maldivas", "Malí", "Malta", "Islas Marshall", "Mauritania", "Mauricio",
            "México", "Micronesia", "Moldova", "Mónaco", "Mongolia", "Montserrat", "Marruecos", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal",
            "Hollanda", "Antillas Holandesas", "Nueva Zelanda", "Nicaragua", "Níger", "Nigeria", "Isla Norfolk", "Islas Marianas del Norte",
            "Noruega", "Omán", "Pakistán", "Palau", "Panamá", "Papua Nueva Guinea", "Paraguay", "Perú", "Filipinas", "Isla Pitcairn", "Polonia",
            "Portugal", "Puerto Rico", "Qatar", "República de Corea", "Rumania", "Federación de Rusia", "Rwanda", "Saint Kitts y Nevis",
            "Santa Lucía", "Samoa", "San Marino", "Santo Tomé y Príncipe", "Arabia Saudita", "Senegal", "Seychelles", "Sierra Leona", "Singapur",
            "República Eslovaca", "Eslovenia", "Islas Salomón", "Somalia", "Sudáfrica", "Georgia del Sur e Islas Sandwich del Sur", "España",
            "Sri Lanka", "Santa Elena", "San Vicente y las Granadinas", "Sudán", "Suriname", "Swazilandia", "Suecia", "Suiza",
            "República Árabe Siria", "Taiwan", "Tayikistán", "Tailandia", "Togo", "Tonga", "Trinidad y Tabago", "Túnez", "Turquía", "Turkmenistán",
            "Islas Turcas y Caicos", "Tuvalu", "Uganda", "Ucrania", "Emiratos Árabes Unidos", "Reino Unido de Gran Bretaña e Irlanda del Norte",
            "República Unida de Tanzanía", "Estados Unidos de América", "Islas Vírgenes de los Estados Unidos", "Uruguay", "Uzbekistán", "Vanuatu",
            "Venezuela", "Viet Nam", "Sáhara Occidental", "Yemen", "Zambia", "Zimbabwe"};
    private static final EstiloUniforme[] estilos =
        {EstiloUniforme.FRANJA_HORIZONTAL, EstiloUniforme.FRANJA_VERTICAL, EstiloUniforme.FRANJA_DIAGONAL, EstiloUniforme.LINEAS_HORIZONTALES,
            EstiloUniforme.LINEAS_VERTICALES, EstiloUniforme.SIN_ESTILO};
    private static final PoligonosData[] data = new PoligonosData[estilos.length];
    private static final long serialVersionUID = -6836315756188587417L;
    private static Logger logger = LoggerFactory.getLogger(AsistenteFrame.class);
    final String constNames[] =
        new String[]{"centroArcoInf", "posteDerArcoSup", "posteIzqArcoSup", "cornerSupDer", "cornerSupIzq", "penalSup", "centroArcoSup",
            "posteDerArcoInf", "posteIzqArcoInf", "cornerInfDer", "cornerInfIzq", "penalInf", "centroCampoJuego"};
    final Position constPos[] =
        new Position[]{Constants.centroArcoInf, Constants.posteDerArcoSup, Constants.posteIzqArcoSup, Constants.cornerSupDer, Constants.cornerSupIzq,
            Constants.penalSup, Constants.centroArcoSup, Constants.posteDerArcoInf, Constants.posteIzqArcoInf, Constants.cornerInfDer,
            Constants.cornerInfIzq, Constants.penalInf, Constants.centroCampoJuego};
    Game game = null;
    Properties props = new Properties();
    ArrayList<Integer[]> intx = new ArrayList<>();
    ArrayList<Integer[]> inty = new ArrayList<>();
    ArrayList<Color> cols = new ArrayList<>();
    DecimalFormat df = new DecimalFormat();
    JFileChooser jfc;
    String name = "";
    double sx = 442, sy = 286;
    double distMin;
    boolean cupdate = true;
    int type = 0;
    Position p0;
    boolean sim = false;
    String tooltip = "";
    int x0, y0;
    Position pos = null;
    double fuerza = 1;
    double ang = 0;
    double elev = 0;
    Color c1 = new Color(255, 0, 0);
    Color c2 = new Color(0, 0, 153);
    Color c3 = new Color(255, 255, 255);
    // End of variables declaration//GEN-END:variables
    boolean update = false;
    boolean newImpl = false;
    private boolean pintando = false;
    private TacticDetailImpl impl = new TacticDetailImpl();
    private Image cancha, campo;
    private DefaultListModel model = new DefaultListModel();
    private PintaJugador jp = null;
    private CanvasGameContainer cgc = null;
    private DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<String>();
    private boolean uniformeAlternativo = false;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JComboBox<String> jComboBox3;
    private JComboBox<String> jComboBox4;
    private JComboBox<EstiloUniforme> jComboBox7;
    private JDialog jDialog1;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel20;
    private javax.swing.JList jList1;
    private JPanel jPanel1;
    private JPanel jPanel5;
    private JPanel jPanel7;
    private javax.swing.JProgressBar jProgressBar1;
    private JSlider jSlider1;
    private JSlider jSlider2;
    private JSlider jSlider3;
    private JTextArea jTextArea1;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private JTextField jTextField5;
    private JTextField jTextField6;
    private JTextField jTextField7;
    private javax.swing.JToggleButton jToggleButton2;

    public AsistenteFrame() throws Exception {
        File dir = new File(System.getProperty("user.dir"));
        try {
            props.load(new FileInputStream("props"));
            dir = new File(props.getProperty("currentDir"));
        } catch (Exception e) {
        }
        jfc = new JFileChooser(dir);
        df.setMinimumFractionDigits(2);
        try {
            XStream xs = new XStream();
            for (int i = 0; i < data.length; i++) {
                data[i] = (PoligonosData) xs.fromXML(getClass().getResourceAsStream("/styles/" + (i + 1)));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Debe limpiar y construir el proyecto");
            System.exit(1);
        }
        initComponents();
        Image img = new ImageIcon(getClass().getResource("/imagenes/javacup.png")).getImage();
        setIconImage(img);
        jDialog1.setSize(700, 500);
        cancha = new ImageIcon(getClass().getResource("/imagenes/cancha.jpg")).getImage();
        campo = new ImageIcon(getClass().getResource("/imagenes/campo.jpg")).getImage();
        setLocationRelativeTo(null);
        jComboBox1.setModel(new DefaultComboBoxModel(paises));
        jComboBox7.setModel(new DefaultComboBoxModel(estilos));
        jComboBox3.setModel(model1);
        for (PlayerDetail jug : impl.getPlayers()) {
            model.addElement(jug);
        }
        jList1.setModel(model);
        jList1.setSelectedIndex(0);
        game = new Game() {

            org.newdawn.slick.Image pasto = null;
            double deg = 0;
            double x = 0
                ,
                y = 0;

            @Override
            public void init(GameContainer gc) throws SlickException {
                SoundStore.get().clear();
                InternalTextureLoader.get().clear();
                jp = new PintaJugador(impl, uniformeAlternativo);
                pasto = new org.newdawn.slick.Image("imagenes/pasto.png");
                gc.setTargetFrameRate(Constants.FPS);
                gc.setShowFPS(false);
                gc.setTargetFrameRate(20);
                gc.setVSync(true);
                gc.setForceExit(false);
                gc.setAlwaysRender(true);
            }

            @Override
            public void update(GameContainer gc, int arg1) throws SlickException {

                if (update) {
                    try {
                        jp.update(uniformeAlternativo);
                    } catch (SlickException ex) {
                        logger.error("Error al pintar jugador", ex);
                    }
                    update = false;
                }
                if (newImpl) {
                    try {
                        jp = new PintaJugador(impl, uniformeAlternativo);
                        jp.update(uniformeAlternativo);
                    } catch (SlickException ex) {
                        logger.error("Error al actualizar uniforme", ex);
                    }
                    newImpl = false;
                }
            }

            @Override
            public void render(GameContainer gc, org.newdawn.slick.Graphics g) throws SlickException {
                PlayerDetail j = getJugador();
                double vel = Constants.getVelocidad(j.getSpeed()) * 3;
                deg = deg + vel;
                x = x - 4f * vel * Math.sin(2f * Math.PI * (float) deg / 360f);
                y = y + 4f * vel * Math.cos(2f * Math.PI * (float) deg / 360f);
                for (int x0 = -4; x0 < 6; x0++) {
                    for (int y0 = -2; y0 < 6; y0++) {
                        g.drawImage(pasto, (int) x + x0 * 100 + 150, (int) y + y0 * 100 - 50);
                    }
                }

                g.setColor(org.newdawn.slick.Color.yellow);
                g.drawString("" + getJugador().getNumber(), 122, 3);
                g.drawString(getJugador().getPlayerName(), 4, 80);
                jp.pintaJugador(jList1.getSelectedIndex(), (int) deg, (int) deg, 20, 55, 50, g);
            }

            @Override
            public boolean closeRequested() {
                return true;
            }

            @Override
            public String getTitle() {
                return "preview";
            }
        };
        try {
            cgc = new CanvasGameContainer(game);
            jPanel7.add(cgc, "");
            cgc.setSize(140, 100);
            new Thread(this).start();
        } catch (Exception ex) {
            logger.error("Error al iniciar el gameContainer", ex);
        }

        repinta();
        repintaCreditos();
        Dimension size = new Dimension(565, 425);
        setSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        setSize(size);
        model1.addElement("1");
        model1.addElement("2");
        model1.addElement("3");
        model1.addElement("4");
        model1.addElement("5");
        model1.addElement("6");
        jComboBox3.setSelectedIndex(0);
        jTextField1.requestFocus();
    }

    private String convertVelocidad(double f) {
        return df.format(Constants.getVelocidad(f)) + " m/iter";
    }

    private String convertRemate(double f) {
        return df.format(Constants.getVelocidadRemate(f)) + " m/iter";
    }

    private String convertError(double f) {
        return df.format(Constants.getErrorAngular(f) * 100) + " %";
    }

    private int[] toInt(Integer[] x) {
        return toInt(x, 0);
    }

    private int[] toInt(Integer[] x, int middle) {
        int[] ints = new int[x.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = x[i] + middle;
        }
        return ints;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new JDialog();
        JScrollPane jScrollPane2 = new JScrollPane();
        jTextArea1 = new JTextArea();
        JMenuBar jMenuBar2 = new JMenuBar();
        JMenu jMenu2 = new JMenu();
        JMenu jMenu3 = new JMenu();
        JTabbedPane jTabbedPane1 = new JTabbedPane();
        jPanel1 = new JPanel();
        JLabel jLabel1 = new JLabel();
        jTextField1 = new JTextField();
        JLabel jLabel2 = new JLabel();
        jComboBox1 = new JComboBox();
        JLabel jLabel3 = new JLabel();
        jTextField2 = new JTextField();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JLabel jLabel8 = new JLabel();
        JLabel jLabel9 = new JLabel();
        jComboBox7 = new JComboBox();
        JPanel jPanel3 = new JPanel() {

            public void paint(Graphics g) {
                g.drawImage(cancha, 0, 0, null);
                Color c;
                Color cc;
                int eq = jComboBox4.getSelectedIndex();
                for (int i = 0; i < cols.size(); i++) {
                    c = cols.get(i);
                    if (eq == 0) {
                        if (c.equals(c1)) {
                            cc = impl.getShirtColor();
                        } else if (c.equals(c2)) {
                            cc = impl.getShortsColor();
                        } else if (c.equals(c3)) {
                            cc = impl.getSocksColor();
                        } else {
                            cc = impl.getShirtLineColor();
                        }
                    } else {
                        if (c.equals(c1)) {
                            cc = impl.getShirtColor2();
                        } else if (c.equals(c2)) {
                            cc = impl.getShortsColor2();
                        } else if (c.equals(c3)) {
                            cc = impl.getSocksColor2();
                        } else {
                            cc = impl.getShirtLineColor2();
                        }
                    }
                    g.setColor(cc);
                    g.fillPolygon(toInt(intx.get(i), getWidth() / 2), toInt(inty.get(i)), inty.get(i).length);
                    //Color color = cols.get(i);
                }
            }
        };
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jButton5 = new JButton();
        JButton jButton8 = new JButton();
        jComboBox4 = new JComboBox();
        JPanel jPanel2 = new JPanel();
        JLabel jLabel10 = new JLabel();
        JLabel jLabel11 = new JLabel();
        JLabel jLabel12 = new JLabel();
        JLabel jLabel13 = new JLabel();
        jTextField3 = new JTextField();
        jTextField4 = new JTextField();
        jButton6 = new JButton();
        jButton7 = new JButton();
        jLabel15 = new JLabel();
        jLabel16 = new JLabel();
        jLabel17 = new JLabel();
        JButton jButton9 = new JButton();
        jSlider1 = new JSlider();
        jSlider2 = new JSlider();
        jSlider3 = new JSlider();
        JPanel jPanel9 = new JPanel();
        jLabel20 = new JLabel();
        jProgressBar1 = new JProgressBar() {

            @Override
            public void paint(Graphics g) {
                int w = getWidth();
                int h = getHeight();
                int f = (int) ((double) h * ((double) this.getValue() / (double) this.getMaximum()));
                g.setColor(Color.black);
                g.fillRect(0, 0, w, h);
                g.setColor(Color.blue);
                g.fillRect(0, h - f, w, f);
            }
        };
        JLabel jLabel19 = new JLabel();
        JButton jButton18 = new JButton();
        JPanel jPanel4 = new JPanel();
        jComboBox3 = new JComboBox();
        jComboBox2 = new JComboBox();
        JButton jButton11 = new JButton();
        JButton jButton12 = new JButton();
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
        jLabel14 = new JLabel();
        JButton jButton10 = new JButton();
        JButton jButton13 = new JButton();
        JPanel jPanel8 = new JPanel();
        JLabel jLabel18 = new JLabel();
        jTextField5 = new JTextField();
        JLabel jLabel23 = new JLabel();
        jTextField6 = new JTextField();
        JLabel jLabel24 = new JLabel();
        jTextField7 = new JTextField();
        JLabel jLabel21 = new JLabel();
        JButton jButton14 = new JButton();
        JPanel jPanel10 = new JPanel();
        JLabel jLabel22 = new JLabel();
        JPanel jPanel11 = new JPanel();
        JPanel jPanel12 = new JPanel();
        JPanel jPanel13 = new JPanel();
        jToggleButton2 = new javax.swing.JToggleButton();
        JPanel jPanel6 = new JPanel();
        jPanel7 = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        jList1 = new javax.swing.JList();
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu();
        JMenuItem jMenuItem1 = new JMenuItem();
        JMenuItem jMenuItem2 = new JMenuItem();
        JSeparator jSeparator1 = new JSeparator();
        JMenuItem jMenuItem3 = new JMenuItem();
        JMenuItem jMenuItem4 = new JMenuItem();
        JMenuItem jMenuItem5 = new JMenuItem();
        JMenuItem jMenuItem6 = new JMenuItem();
        JSeparator jSeparator2 = new JSeparator();
        JMenuItem jMenuItem7 = new JMenuItem();

        jDialog1.setModal(true);
        jDialog1.getContentPane().setLayout(new java.awt.CardLayout());

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(new Font("Bitstream Vera Sans Mono", 0, 10));
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jDialog1.getContentPane().add(jScrollPane2, "card2");

        jMenu2.setText("File"); // NOI18N
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit"); // NOI18N
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Asistente Javacup"); // NOI18N
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new Font("Arial", 0, 12));

        jPanel1.setLayout(null);

        jLabel1.setFont(new Font("Arial", 0, 12));
        jLabel1.setText("Nombre del Equipo"); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 120, 15);

        jTextField1.setFont(new Font("Arial", 0, 12));
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(130, 10, 170, 21);

        jLabel2.setFont(new Font("Arial", 0, 12));
        jLabel2.setText("Pais"); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 40, 90, 15);

        jComboBox1.setFont(new Font("Arial", 0, 12));
        jComboBox1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                jComboBox1FocusLost(evt);
            }
        });
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(130, 40, 170, 21);

        jLabel3.setFont(new Font("Arial", 0, 12));
        jLabel3.setText("Entrenador"); // NOI18N
        jPanel1.add(jLabel3);
        jLabel3.setBounds(10, 70, 90, 15);

        jTextField2.setFont(new Font("Arial", 0, 12));
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
        jPanel1.add(jTextField2);
        jTextField2.setBounds(130, 70, 170, 21);

        jLabel4.setFont(new Font("Arial", 0, 12));
        jLabel4.setText("Color Camiseta"); // NOI18N
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 140, 110, 15);

        jLabel5.setFont(new Font("Arial", 0, 12));
        jLabel5.setText("Color Pantalones"); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(10, 170, 110, 15);

        jLabel6.setFont(new Font("Arial", 0, 12));
        jLabel6.setText("Color Calcetas"); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 200, 100, 15);

        jLabel7.setFont(new Font("Arial", 0, 12));
        jLabel7.setText("Color Franja"); // NOI18N
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 230, 100, 15);

        jLabel8.setFont(new Font("Arial", 0, 12));
        jLabel8.setText("Color Portero"); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 260, 100, 15);

        jLabel9.setFont(new Font("Arial", 0, 12));
        jLabel9.setText("Estilo Franja"); // NOI18N
        jPanel1.add(jLabel9);
        jLabel9.setBounds(10, 290, 100, 15);

        jComboBox7.setFont(new Font("Arial", 0, 12));
        jComboBox7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox7ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox7);
        jComboBox7.setBounds(130, 290, 170, 21);

        jPanel3.setBackground(new Color(51, 51, 51));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 2, true));
        jPanel3.setFocusable(false);

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(LEADING).addGap(0, 226, Short.MAX_VALUE));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(LEADING).addGap(0, 336, Short.MAX_VALUE));

        jPanel1.add(jPanel3);
        jPanel3.setBounds(310, 10, 230, 340);

        jButton1.setFont(new Font("Arial", 0, 10));
        jButton1.setText(" "); // NOI18N
        jButton1.setFocusable(false);
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(130, 140, 170, 21);

        jButton2.setFont(new Font("Arial", 0, 10));
        jButton2.setText(" "); // NOI18N
        jButton2.setFocusable(false);
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(130, 170, 170, 21);

        jButton3.setFont(new Font("Arial", 0, 10));
        jButton3.setText(" "); // NOI18N
        jButton3.setFocusable(false);
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(130, 200, 170, 21);

        jButton4.setFont(new Font("Arial", 0, 10));
        jButton4.setText(" "); // NOI18N
        jButton4.setFocusable(false);
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(130, 230, 170, 21);

        jButton5.setFont(new Font("Arial", 0, 10));
        jButton5.setText(" "); // NOI18N
        jButton5.setFocusable(false);
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(130, 260, 170, 21);

        jButton8.setFont(new Font("Arial", 0, 12));
        jButton8.setText("Al Azar"); // NOI18N
        jButton8.setFocusable(false);
        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);
        jButton8.setBounds(209, 320, 90, 23);

        jComboBox4.setFont(new Font("Arial", 0, 12));
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Equipamiento Principal", "Equipamiento Secundario"}));
        jComboBox4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox4);
        jComboBox4.setBounds(9, 102, 292, 19);

        jTabbedPane1.addTab("Equipo", jPanel1);

        jPanel2.setMaximumSize(new java.awt.Dimension(200, 20));
        jPanel2.setMinimumSize(new java.awt.Dimension(200, 20));
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 20));

        jLabel10.setFont(new Font("Arial", 0, 12));
        jLabel10.setText("Nombre"); // NOI18N

        jLabel11.setFont(new Font("Arial", 0, 12));
        jLabel11.setText("Color de Piel"); // NOI18N

        jLabel12.setFont(new Font("Arial", 0, 12));
        jLabel12.setText("Color del pelo"); // NOI18N

        jLabel13.setFont(new Font("Arial", 0, 12));
        jLabel13.setText("N°"); // NOI18N

        jTextField3.setFont(new Font("Arial", 0, 12));
        jTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                jTextField3FocusGained(evt);
            }

            public void focusLost(FocusEvent evt) {
                jTextField3FocusLost(evt);
            }
        });
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTextField3KeyPressed(evt);
            }

            public void keyReleased(KeyEvent evt) {
                jTextField3KeyReleased(evt);
            }
        });

        jTextField4.setFont(new Font("Arial", 0, 12));
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                jTextField4FocusGained(evt);
            }

            public void focusLost(FocusEvent evt) {
                jTextField4FocusLost(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                jTextField4KeyPressed(evt);
            }

            public void keyReleased(KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jButton6.setFont(new Font("Arial", 0, 12));
        jButton6.setText(" "); // NOI18N
        jButton6.setFocusable(false);
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new Font("Arial", 0, 12));
        jButton7.setText(" "); // NOI18N
        jButton7.setFocusable(false);
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new Font("Arial", 0, 12));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Velocidad(00,00 m/iter)"); // NOI18N
        jLabel15.setMaximumSize(new java.awt.Dimension(200, 20));
        jLabel15.setMinimumSize(new java.awt.Dimension(200, 20));
        jLabel15.setPreferredSize(new java.awt.Dimension(200, 20));

        jLabel16.setFont(new Font("Arial", 0, 12));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Remate  (000,00 m/iter)"); // NOI18N
        jLabel16.setMaximumSize(new java.awt.Dimension(200, 20));
        jLabel16.setMinimumSize(new java.awt.Dimension(200, 20));
        jLabel16.setPreferredSize(new java.awt.Dimension(200, 20));

        jLabel17.setFont(new Font("Arial", 0, 12));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Precisión (   %)"); // NOI18N
        jLabel17.setMaximumSize(new java.awt.Dimension(200, 20));
        jLabel17.setMinimumSize(new java.awt.Dimension(200, 20));
        jLabel17.setPreferredSize(new java.awt.Dimension(200, 20));

        jButton9.setFont(new Font("Arial", 0, 12));
        jButton9.setText("Usar estos colores en todos"); // NOI18N
        jButton9.setFocusable(false);
        jButton9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jSlider1.setFocusable(false);
        jSlider1.setMaximumSize(new java.awt.Dimension(200, 20));
        jSlider1.setMinimumSize(new java.awt.Dimension(200, 20));
        jSlider1.setPreferredSize(new java.awt.Dimension(200, 20));
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jSlider2.setFocusable(false);
        jSlider2.setMaximumSize(new java.awt.Dimension(200, 20));
        jSlider2.setMinimumSize(new java.awt.Dimension(200, 20));
        jSlider2.setPreferredSize(new java.awt.Dimension(200, 20));
        jSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                jSlider2StateChanged(evt);
            }
        });

        jSlider3.setFocusable(false);
        jSlider3.setMaximumSize(new java.awt.Dimension(200, 20));
        jSlider3.setMinimumSize(new java.awt.Dimension(200, 20));
        jSlider3.setPreferredSize(new java.awt.Dimension(200, 20));
        jSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                jSlider3StateChanged(evt);
            }
        });

        jPanel9.setFocusable(false);

        jLabel20.setFont(new Font("Arial", 0, 12));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("0.0"); // NOI18N
        jLabel20.setAlignmentX(1.0F);

        jProgressBar1.setBackground(new Color(102, 102, 102));
        jProgressBar1.setFont(new Font("Arial", 0, 10));
        jProgressBar1.setForeground(new Color(0, 51, 255));
        jProgressBar1.setMaximum(200);
        jProgressBar1.setOrientation(1);
        jProgressBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jProgressBar1.setFocusable(false);

        jLabel19.setFont(new Font("Arial", 0, 12));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Creditos"); // NOI18N

        GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(LEADING).addGroup(jPanel9Layout.createSequentialGroup().addContainerGap()
            .addGroup(jPanel9Layout.createParallelGroup(LEADING)
                .addComponent(jProgressBar1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addComponent(jLabel20, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addComponent(jLabel19, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)).addContainerGap()));
        jPanel9Layout.setVerticalGroup(jPanel9Layout.createParallelGroup(LEADING).addGroup(GroupLayout.Alignment.TRAILING,
            jPanel9Layout.createSequentialGroup().addComponent(jLabel19).addPreferredGap(RELATED)
                .addComponent(jProgressBar1, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE).addPreferredGap(RELATED)
                .addComponent(jLabel20, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)));

        jButton18.setFont(new Font("Arial", 0, 12));
        jButton18.setText("Este Jugador es Portero");
        jButton18.setFocusable(false);
        jButton18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(LEADING)
                .addComponent(jLabel16, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addComponent(jSlider3, GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
                .addComponent(jLabel17, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addComponent(jSlider2, GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE).addComponent(jSlider1, 0, 0, Short.MAX_VALUE)
                .addComponent(jLabel15, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE).addComponent(jLabel12).addGroup(
                    jPanel2Layout.createSequentialGroup().addComponent(jLabel10).addGap(47, 47, 47).addGroup(
                        jPanel2Layout.createParallelGroup(LEADING)
                            .addComponent(jButton18, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE).addGroup(
                            jPanel2Layout.createSequentialGroup().addComponent(jTextField3, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                .addGap(18, 18, 18).addComponent(jLabel13).addPreferredGap(RELATED)
                                .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))).addGap(7, 7, 7)).addGroup(
                    jPanel2Layout.createSequentialGroup().addComponent(jLabel11).addGap(22, 22, 22).addGroup(
                        jPanel2Layout.createParallelGroup(LEADING)
                            .addComponent(jButton9, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addComponent(jButton7, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                            .addComponent(jButton6, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))).addPreferredGap(RELATED)
            .addComponent(jPanel9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
            .addGroup(jPanel2Layout.createParallelGroup(LEADING)
                .addComponent(jPanel9, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(
                    jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(BASELINE).addComponent(jLabel10)
                        .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(RELATED).addComponent(jButton18, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(
                        jPanel2Layout.createParallelGroup(BASELINE).addComponent(jLabel11)
                            .addComponent(jButton6, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)).addPreferredGap(RELATED).addGroup(
                        jPanel2Layout.createParallelGroup(BASELINE).addComponent(jLabel12)
                            .addComponent(jButton7, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)).addPreferredGap(RELATED)
                        .addComponent(jButton9, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(28, 28, 28)
                        .addComponent(jLabel15, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(RELATED)
                        .addComponent(jSlider1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(RELATED)
                        .addComponent(jLabel16, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(RELATED)
                        .addComponent(jSlider2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(RELATED)
                        .addComponent(jLabel17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(RELATED)
                        .addComponent(jSlider3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addContainerGap()));

        jTabbedPane1.addTab("Jugadores", jPanel2);

        jPanel4.setFont(new Font("Arial", 0, 14));

        jComboBox3.setFont(new Font("Arial", 0, 12));
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"1"}));
        jComboBox3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox2.setFont(new Font("Arial", 0, 12));
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"Normal", "Inicio Sacando", "Inicio Recibiendo"}));
        jComboBox2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

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

        jLabel14.setFont(new Font("Arial", 0, 12));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("0,0"); // NOI18N

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

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel18.setFont(new Font("Arial", 0, 12));
        jLabel18.setText("Fuerza"); // NOI18N
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel18.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel18.setMinimumSize(new java.awt.Dimension(50, 15));
        jLabel18.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel8.add(jLabel18);

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

        jLabel23.setFont(new Font("Arial", 0, 12));
        jLabel23.setText("Angulo"); // NOI18N
        jLabel23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel23.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel23.setMinimumSize(new java.awt.Dimension(50, 15));
        jLabel23.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel8.add(jLabel23);

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

        jLabel24.setFont(new Font("Arial", 0, 12));
        jLabel24.setText("Angulo Z"); // NOI18N
        jLabel24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel24.setMaximumSize(new java.awt.Dimension(50, 15));
        jLabel24.setMinimumSize(new java.awt.Dimension(50, 15));
        jLabel24.setPreferredSize(new java.awt.Dimension(50, 15));
        jPanel8.add(jLabel24);

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

        jButton14.setFont(new Font("Arial", 0, 12));
        jButton14.setText(">"); // NOI18N
        jButton14.setFocusable(false);
        jButton14.setMaximumSize(new java.awt.Dimension(60, 23));
        jButton14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton14);

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

        jPanel13.setBackground(new Color(0, 255, 0));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0)));
        jPanel13.setToolTipText("Alcance de jugadores");

        GroupLayout jPanel13Layout = new GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(jPanel13Layout.createParallelGroup(LEADING).addGap(0, 48, Short.MAX_VALUE));
        jPanel13Layout.setVerticalGroup(jPanel13Layout.createParallelGroup(LEADING).addGap(0, 22, Short.MAX_VALUE));

        jPanel8.add(jPanel13);

        jToggleButton2.setFont(new Font("Arial", 0, 12));
        jToggleButton2.setText("Constantes");
        jToggleButton2.setFocusable(false);
        jToggleButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

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

        jTabbedPane1.addTab("Alineaciones y Simulacion de Remate", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, 385));

        jPanel6.setFocusable(false);
        jPanel6.setLayout(new java.awt.CardLayout());

        jPanel7.setBackground(new Color(102, 102, 102));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
        jPanel7.setFocusable(false);
        jPanel7.setMaximumSize(new java.awt.Dimension(140, 100));
        jPanel7.setMinimumSize(new java.awt.Dimension(140, 100));
        jPanel7.setPreferredSize(new java.awt.Dimension(140, 100));
        jPanel7.setLayout(new java.awt.CardLayout());
        jPanel6.add(jPanel7, "card2");

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(571, 23, 120, 100));

        jList1.setBorder(new javax.swing.border.LineBorder(new Color(0, 0, 0), 1, true));
        jList1.setFont(new Font("Arial", 0, 10));
        jList1.setFocusable(false);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 130, 120, 240));

        jMenu1.setText("Archivo"); // NOI18N
        jMenu1.setFont(new Font("Arial", 0, 12));

        jMenuItem1.setFont(new Font("Arial", 0, 12));
        jMenuItem1.setText("Abrir"); // NOI18N
        jMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new Font("Arial", 0, 12));
        jMenuItem2.setText("Guardar"); // NOI18N
        jMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator1);

        jMenuItem3.setFont(new Font("Arial", 0, 12));
        jMenuItem3.setText("Generar Codigo de Tactica"); // NOI18N
        jMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setFont(new Font("Arial", 0, 12));
        jMenuItem4.setText("Generar Codigo de Tactica, Sin TacticaDetalle"); // NOI18N
        jMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setFont(new Font("Arial", 0, 12));
        jMenuItem5.setText("Generar Codigo de TacticaDetalle"); // NOI18N
        jMenuItem5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setFont(new Font("Arial", 0, 12));
        jMenuItem6.setText("Generar Codigo de Alineaciones"); // NOI18N
        jMenuItem6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator2);

        jMenuItem7.setFont(new Font("Arial", 0, 12));
        jMenuItem7.setText("Validar Tactica"); // NOI18N
        jMenuItem7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void simula(Graphics graphics, double x, double y, double remate, double error) {
        if (sim) {
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
            Color yellow = new Color(1f, 1f, 0f, .5f);
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
            sim = false;
        }
    }

    private void updateSlider() {

        if (!pintando) {
            JugadorImpl[] jugs = (JugadorImpl[]) impl.getPlayers();
            for (int i : jList1.getSelectedIndices()) {
                JugadorImpl j = jugs[i];
                j.setVelocidad(((double) jSlider1.getValue()) / 100d);
                j.setRemate(((double) jSlider2.getValue()) / 100d);
                j.setPresicion(((double) jSlider3.getValue()) / 100d);
                jLabel15.setText("Velocidad(" + convertVelocidad(j.getSpeed()) + ")");
                jLabel16.setText("Remate   (" + convertRemate(j.getPower()) + ")");
                jLabel17.setText("Error    (" + convertError(j.getPrecision()) + ")");
            }
            repintaCreditos();
        }
    }

    private boolean repintaCreditos() {
        double cre = Constants.CREDITOS_INICIALES;
        jProgressBar1.setMaximum((int) (cre * 10));
        for (PlayerDetail j : impl.getPlayers()) {
            cre = cre - j.getSpeed();
            cre = cre - j.getPower();
            cre = cre - j.getPrecision();
        }
        boolean ok = true;
        if (cre < 0) {
            ok = false;
        }
        jProgressBar1.setValue((int) (cre * 10));
        if (cre < 0) {
            jLabel20.setForeground(Color.red);
        } else {
            jLabel20.setForeground(Color.black);
        }
        jLabel20.setText(df.format(cre));
        return ok;
    }

    private void jMenuItem2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int i = 0;
        for (i = 0; i < model.getSize(); i++) {
            impl.getPlayers()[i] = (PlayerDetail) model.get(i);
        }
        if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                TacticDetailImpl.save(impl, jfc.getSelectedFile());
            } catch (IOException ex) {
                logger.error("Error al guardar tactica detalle", ex);
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                impl = TacticDetailImpl.loadFichero(jfc.getSelectedFile());
                for (int i = 0; i < model.getSize(); i++) {
                    model.set(i, impl.getPlayers()[i]);
                }
                model1.removeAllElements();
                for (int i = 0; i < impl.getAlineacionCount(); i++) {
                    model1.addElement("" + (i + 1));
                }
                jComboBox3.setSelectedIndex(0);
                newImpl = true;
                update = true;
                repinta();
                repintaCreditos();
                repintaModel();
            } catch (IOException ex) {
                logger.error("Error al cargar TacticDetail", ex);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        String codalin = "";
        int numSaca = -1, numRecibe = -1;
        for (int idx = 0; idx < impl.getAlineacionCount(); idx++) {
            if (numSaca == -1 && impl.getTipoAlineacion(idx) == 1) {
                numSaca = idx + 1;
            }
            if (numRecibe == -1 && impl.getTipoAlineacion(idx) == 2) {
                numRecibe = idx + 1;
            }
            codalin = codalin + "    Position alineacion" + (idx + 1) + "[]=new Position[]{\n";
            int i = 0;
            Position[] Positiones = impl.getAlineacion(idx);
            for (PlayerDetail j : impl.getPlayers()) {
                i++;
                codalin =
                    codalin + "        new Position(" + Positiones[i - 1].getX() + "," + Positiones[i - 1].getY() + ")" + (i < 11 ? "," : "") + "\n";
            }
            codalin = codalin + "    };\n\n";
        }

        if (numSaca == -1 || numRecibe == -1) {
            JOptionPane
                .showMessageDialog(this, "Debe definir por lo menos una alineacion para sacar y otra para recibir", "", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String paclas = JOptionPane.showInputDialog(this, "ingrese el nombre de la clase (paquete.clase)?", name);
        name = paclas;
        if (paclas != null) {
            String paquete = null;
            String clase = "";
            int idx = paclas.lastIndexOf(".");
            if (idx != -1) {
                paquete = paclas.substring(0, idx);
                clase = paclas.substring(idx + 1);
            } else {
                clase = paclas;
            }

            String codigo = "";
            if (paquete != null) {
                codigo = codigo + "package " + paquete + ";\n\n";
            }
            codigo =
                codigo + "import java.awt.Color;\n" + "import org.javahispano.javacup.model.*;\n" + "import org.javahispano.javacup.model.util.*;\n" +
                    "import org.javahispano.javacup.render.EstiloUniforme;\n" + "import org.javahispano.javacup.model.command.*;\n" +
                    "import org.javahispano.javacup.model.engine.GameSituations;\n" + "import java.util.List;\n\n" + "public class " + clase +
                    " implements Tactic {\n\n" + codalin + "    class TacticDetailImpl implements TacticDetail {\n\n" +
                    "        public String getTacticName() {\n" + "            return \"" + impl.getTacticName() + "\";\n" + "        }\n\n" +
                    "        public String getCountry() {\n" + "            return \"" + impl.getCountry() + "\";\n" + "        }\n\n" +
                    "        public String getCoach() {\n" + "            return \"" + impl.getCoach() + "\";\n" + "        }\n\n" +
                    "        public Color getShirtColor() {\n" + "            return new Color(" + impl.getShirtColor().getRed() + ", " +
                    impl.getShirtColor().getGreen() + ", " + impl.getShirtColor().getBlue() + ");\n" + "        }\n\n" +
                    "        public Color getShortsColor() {\n" + "            return new Color(" + impl.getShortsColor().getRed() + ", " +
                    impl.getShortsColor().getGreen() + ", " + impl.getShortsColor().getBlue() + ");\n" + "        }\n\n" +
                    "        public Color getShirtLineColor() {\n" + "            return new Color(" + impl.getShirtLineColor().getRed() + ", " +
                    impl.getShirtLineColor().getGreen() + ", " + impl.getShirtLineColor().getBlue() + ");\n" + "        }\n\n" +
                    "        public Color getSocksColor() {\n" + "            return new Color(" + impl.getSocksColor().getRed() + ", " +
                    impl.getSocksColor().getGreen() + ", " + impl.getSocksColor().getBlue() + ");\n" + "        }\n\n" +
                    "        public Color getGoalKeeper() {\n" + "            return new Color(" + impl.getGoalKeeper().getRed() + ", " +
                    impl.getGoalKeeper().getGreen() + ", " + impl.getGoalKeeper().getBlue() + "        );\n";
            String estilo = null;
            switch (impl.getStyle()) {
                case FRANJA_DIAGONAL:
                    estilo = "FRANJA_DIAGONAL";
                    break;
                case FRANJA_HORIZONTAL:
                    estilo = "FRANJA_HORIZONTAL";
                    break;
                case FRANJA_VERTICAL:
                    estilo = "FRANJA_VERTICAL";
                    break;
                case LINEAS_HORIZONTALES:
                    estilo = "LINEAS_HORIZONTALES";
                    break;
                case LINEAS_VERTICALES:
                    estilo = "LINEAS_VERTICALES";
                    break;
                case SIN_ESTILO:
                    estilo = "SIN_ESTILO";
                    break;
            }

            codigo =
                codigo + "        }\n\n" + "        public EstiloUniforme getStyle() {\n" + "            return EstiloUniforme." + estilo + ";\n" +
                    "        }\n\n";

            codigo = codigo + "        public Color getShirtColor2() {\n" + "            return new Color(" + impl.getShirtColor2().getRed() + ", " +
                impl.getShirtColor2().getGreen() + ", " + impl.getShirtColor2().getBlue() + ");\n" + "        }\n\n" +
                "        public Color getShortsColor2() {\n" + "            return new Color(" + impl.getShortsColor2().getRed() + ", " +
                impl.getShortsColor2().getGreen() + ", " + impl.getShortsColor2().getBlue() + ");\n" + "        }\n\n" +
                "        public Color getShirtLineColor2() {\n" + "            return new Color(" + impl.getShirtLineColor2().getRed() + ", " +
                impl.getShirtLineColor2().getGreen() + ", " + impl.getShirtLineColor2().getBlue() + ");\n" + "        }\n\n" +
                "        public Color getSocksColor2() {\n" + "            return new Color(" + impl.getSocksColor2().getRed() + ", " +
                impl.getSocksColor2().getGreen() + ", " + impl.getSocksColor2().getBlue() + ");\n" + "        }\n\n" +
                "        public Color getGoalKeeper2() {\n" + "            return new Color(" + impl.getGoalKeeper2().getRed() + ", " +
                impl.getGoalKeeper2().getGreen() + ", " + impl.getGoalKeeper2().getBlue() + "        );\n";

            switch (impl.getStyle2()) {
                case FRANJA_DIAGONAL:
                    estilo = "FRANJA_DIAGONAL";
                    break;
                case FRANJA_HORIZONTAL:
                    estilo = "FRANJA_HORIZONTAL";
                    break;
                case FRANJA_VERTICAL:
                    estilo = "FRANJA_VERTICAL";
                    break;
                case LINEAS_HORIZONTALES:
                    estilo = "LINEAS_HORIZONTALES";
                    break;
                case LINEAS_VERTICALES:
                    estilo = "LINEAS_VERTICALES";
                    break;
                case SIN_ESTILO:
                    estilo = "SIN_ESTILO";
                    break;
            }

            codigo =
                codigo + "        }\n\n" + "        public EstiloUniforme getStyle2() {\n" + "            return EstiloUniforme." + estilo + ";\n" +
                    "        }\n\n" + "        class JugadorImpl implements PlayerDetail {\n\n" + "            String nombre;\n" +
                    "            int numero;\n" + "            Color piel, pelo;\n" + "            double velocidad, remate, presicion;\n" +
                    "            boolean portero;\n" + "            Position Position;\n\n" +
                    "            public JugadorImpl(String nombre, int numero, Color piel, Color pelo,\n" +
                    "                    double velocidad, double remate, double presicion, boolean portero) {\n" +
                    "                this.nombre=nombre;\n" + "                this.numero=numero;\n" + "                this.piel=piel;\n" +
                    "                this.pelo=pelo;\n" + "                this.velocidad=velocidad;\n" + "                this.remate=remate;\n" +
                    "                this.presicion=presicion;\n" + "                this.portero=portero;\n" + "            }\n\n" +
                    "            public String getPlayerName() {\n" + "                return nombre;\n" + "            }\n\n" +
                    "            public Color getSkinColor() {\n" + "                return piel;\n" + "            }\n\n" +
                    "            public Color getHairColor() {\n" + "                return pelo;\n" + "            }\n\n" +
                    "            public int getNumber() {\n" + "                return numero;\n" + "            }\n\n" +
                    "            public boolean isGoalKeeper() {\n" + "                return portero;\n" + "            }\n\n" +
                    "            public double getSpeed() {\n" + "                return velocidad;\n" + "            }\n\n" +
                    "            public double getPower() {\n" + "                return remate;\n" + "            }\n\n" +
                    "            public double getPrecision() {\n" + "                return presicion;\n" + "            }\n\n" + "        }\n\n" +
                    "        public PlayerDetail[] getPlayers() {\n" + "            return new PlayerDetail[]{\n";
            int i = 0;
            for (PlayerDetail j : impl.getPlayers()) {
                i++;
                codigo = codigo + "                new JugadorImpl(\"" + j.getPlayerName() + "\", " + j.getNumber() + ", new Color(" +
                    j.getSkinColor().getRed() + "," + j.getSkinColor().getGreen() + "," + j.getSkinColor().getBlue() + "), new Color(" +
                    j.getHairColor().getRed() + "," + j.getHairColor().getGreen() + "," + j.getHairColor().getBlue() + ")," + j.getSpeed() + "d," +
                    j.getPower() + "d," + j.getPrecision() + "d, " + j.isGoalKeeper() + ")" + (i < 11 ? "," : "") + "\n";
            }
            codigo = codigo + "            };\n" + "        }\n" + "    }\n\n" + "    TacticDetail detalle=new TacticDetailImpl();\n" +
                "    public TacticDetail getDetail() {\n" + "        return detalle;\n" + "    }\n\n" +
                "    public Position[] getStartPositions(GameSituations sp) {\n" + "    return alineacion" + numSaca + ";\n" + "    }\n\n" +
                "    public Position[] getNoStartPositions(GameSituations sp) {\n" + "    return alineacion" + numRecibe + ";\n" + "    }\n\n" +
                "    public List<Command> execute(GameSituations sp) {\n" + "        return null;\n" + "    }\n" + "}";
            jTextArea1.setText(codigo);
            jDialog1.setLocationRelativeTo(null);
            jTextArea1.selectAll();
            jDialog1.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    Position transformAsistente(Position pos) {
        double x = 15 + sx / 2 + sx * pos.getY() / Constants.LARGO_CAMPO_JUEGO;
        double y = 10 + sy / 2 + sy * pos.getX() / Constants.ANCHO_CAMPO_JUEGO;
        return new Position(x, y);
    }

    double transformAsistente(double r) {
        return sx * r / Constants.LARGO_CAMPO_JUEGO;
    }

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

    private void jMenuItem4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        String paclas = JOptionPane.showInputDialog(this, "ingrese el nombre de la clase (paquete.clase)?", name);
        name = paclas;
        if (paclas != null) {
            String paquete = null;
            String clase = "";
            int idx = paclas.lastIndexOf(".");
            if (idx != -1) {
                paquete = paclas.substring(0, idx);
                clase = paclas.substring(idx + 1);
            } else {
                clase = paclas;
            }
            String codalin = "";
            int numSaca = -1, numRecibe = -1;
            for (idx = 0; idx < impl.getAlineacionCount(); idx++) {
                if (numSaca == -1 && impl.getTipoAlineacion(idx) == 1) {
                    numSaca = idx + 1;
                }
                if (numRecibe == -1 && impl.getTipoAlineacion(idx) == 2) {
                    numRecibe = idx + 1;
                }
                codalin = codalin + "    Position alineacion" + (idx + 1) + "[]=new Position[]{\n";
                int i = 0;
                Position[] Positiones = impl.getAlineacion(idx);
                for (PlayerDetail j : impl.getPlayers()) {
                    i++;
                    codalin =
                        codalin + "        new Position(" + Positiones[i - 1].getX() + "," + Positiones[i - 1].getY() + ")" + (i < 11 ? "," : "") +
                            "\n";
                }
                codalin = codalin + "    };\n\n";
            }

            String codigo = "";
            if (paquete != null) {
                codigo = "package " + paquete + ";\n\n";
            }
            codigo = codigo + "import org.javahispano.javacup.model.*;\n" + "import org.javahispano.javacup.model.util.Position;\n" +
                "import org.javahispano.javacup.model.engine.GameSituations;\n" + "import org.javahispano.javacup.model.command.*;\n" +
                "import java.util.List;\n\n" + "public class " + clase + " implements Tactic {\n\n" + codalin +
                "    public Position[] getStartPositions(GameSituations sp) {\n" + "    return alineacion" + numSaca + ";\n" + "    }\n\n" +
                "    public Position[] getNoStartPositions(GameSituations sp) {\n" + "    return alineacion" + numRecibe + ";\n" + "    }\n\n" +
                "    public TacticDetail getDetail() {\n" + "        throw new UnsupportedOperationException(\"No implementado.\");\n" + "    }\n\n" +
                "    public List<Command> execute(GameSituations sp) {\n" +
                "        throw new UnsupportedOperationException(\"No implementado.\");\n" + "    }\n" + "}";
            jTextArea1.setText(codigo);
            jDialog1.setLocationRelativeTo(null);
            jTextArea1.selectAll();
            jDialog1.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        String codigo =
            "public class TacticDetailImpl implements TacticDetail {\n\n" + "        public String getTacticName() {\n" + "            return \"" +
                impl.getTacticName() + "\";\n" + "        }\n\n" + "        public String getCountry() {\n" + "            return \"" +
                impl.getCountry() + "\";\n" + "        }\n\n" + "        public String getCoach() {\n" + "            return \"" + impl.getCoach() +
                "\";\n" + "        }\n\n" + "        public Color getShirtColor() {\n" + "            return new Color(" +
                impl.getShirtColor().getRed() + ", " + impl.getShirtColor().getGreen() + ", " + impl.getShirtColor().getBlue() + ");\n" +
                "        }\n\n" + "        public Color getShortsColor() {\n" + "            return new Color(" + impl.getShortsColor().getRed() +
                ", " + impl.getShortsColor().getGreen() + ", " + impl.getShortsColor().getBlue() + ");\n" + "        }\n\n" +
                "        public Color getShirtLineColor() {\n" + "            return new Color(" + impl.getShirtLineColor().getRed() + ", " +
                impl.getShirtLineColor().getGreen() + ", " + impl.getShirtLineColor().getBlue() + ");\n" + "        }\n\n" +
                "        public Color getSocksColor() {\n" + "            return new Color(" + impl.getSocksColor().getRed() + ", " +
                impl.getSocksColor().getGreen() + ", " + impl.getSocksColor().getBlue() + ");\n" + "        }\n\n" +
                "        public Color getGoalKeeper() {\n" + "            return new Color(" + impl.getGoalKeeper().getRed() + ", " +
                impl.getGoalKeeper().getGreen() + ", " + impl.getGoalKeeper().getBlue() + "        );\n";
        String estilo = null;
        switch (impl.getStyle()) {
            case FRANJA_DIAGONAL:
                estilo = "FRANJA_DIAGONAL";
                break;
            case FRANJA_HORIZONTAL:
                estilo = "FRANJA_HORIZONTAL";
                break;
            case FRANJA_VERTICAL:
                estilo = "FRANJA_VERTICAL";
                break;
            case LINEAS_HORIZONTALES:
                estilo = "LINEAS_HORIZONTALES";
                break;
            case LINEAS_VERTICALES:
                estilo = "LINEAS_VERTICALES";
                break;
            case SIN_ESTILO:
                estilo = "SIN_ESTILO";
                break;
        }

        codigo = codigo + "        }\n\n" + "        public EstiloUniforme getStyle() {\n" + "            return EstiloUniforme." + estilo + ";\n" +
            "        }\n\n";

        codigo = codigo + "        public Color getShirtColor2() {\n" + "            return new Color(" + impl.getShirtColor2().getRed() + ", " +
            impl.getShirtColor2().getGreen() + ", " + impl.getShirtColor2().getBlue() + ");\n" + "        }\n\n" +
            "        public Color getShortsColor2() {\n" + "            return new Color(" + impl.getShortsColor2().getRed() + ", " +
            impl.getShortsColor2().getGreen() + ", " + impl.getShortsColor2().getBlue() + ");\n" + "        }\n\n" +
            "        public Color getShirtLineColor2() {\n" + "            return new Color(" + impl.getShirtLineColor2().getRed() + ", " +
            impl.getShirtLineColor2().getGreen() + ", " + impl.getShirtLineColor2().getBlue() + ");\n" + "        }\n\n" +
            "        public Color getSocksColor2() {\n" + "            return new Color(" + impl.getSocksColor2().getRed() + ", " +
            impl.getSocksColor2().getGreen() + ", " + impl.getSocksColor2().getBlue() + ");\n" + "        }\n\n" +
            "        public Color getGoalKeeper2() {\n" + "            return new Color(" + impl.getGoalKeeper2().getRed() + ", " +
            impl.getGoalKeeper2().getGreen() + ", " + impl.getGoalKeeper2().getBlue() + "        );\n";

        switch (impl.getStyle2()) {
            case FRANJA_DIAGONAL:
                estilo = "FRANJA_DIAGONAL";
                break;
            case FRANJA_HORIZONTAL:
                estilo = "FRANJA_HORIZONTAL";
                break;
            case FRANJA_VERTICAL:
                estilo = "FRANJA_VERTICAL";
                break;
            case LINEAS_HORIZONTALES:
                estilo = "LINEAS_HORIZONTALES";
                break;
            case LINEAS_VERTICALES:
                estilo = "LINEAS_VERTICALES";
                break;
            case SIN_ESTILO:
                estilo = "SIN_ESTILO";
                break;
        }

        codigo = codigo + "        }\n\n" + "        public EstiloUniforme getStyle2() {\n" + "            return EstiloUniforme." + estilo + ";\n" +
            "        }\n\n";

        codigo = codigo + "        class JugadorImpl implements PlayerDetail {\n\n" + "            String nombre;\n" + "            int numero;\n" +
            "            Color piel, pelo;\n" + "            double velocidad, remate, presicion;\n" + "            boolean portero;\n" +
            "            Position Position;\n\n" + "            public JugadorImpl(String nombre, int numero, Color piel, Color pelo,\n" +
            "                    double velocidad, double remate, double presicion, boolean portero) {\n" + "                this.nombre=nombre;\n" +
            "                this.numero=numero;\n" + "                this.piel=piel;\n" + "                this.pelo=pelo;\n" +
            "                this.velocidad=velocidad;\n" + "                this.remate=remate;\n" + "                this.presicion=presicion;\n" +
            "                this.portero=portero;\n" + "            }\n\n" + "            public String getPlayerName() {\n" +
            "                return nombre;\n" + "            }\n\n" + "            public Color getSkinColor() {\n" +
            "                return piel;\n" + "            }\n\n" + "            public Color getHairColor() {\n" +
            "                return pelo;\n" + "            }\n\n" + "            public int getNumber() {\n" + "                return numero;\n" +
            "            }\n\n" + "            public boolean isGoalKeeper() {\n" + "                return portero;\n" + "            }\n\n" +
            "            public double getSpeed() {\n" + "                return velocidad;\n" + "            }\n\n" +
            "            public double getPower() {\n" + "                return remate;\n" + "            }\n\n" +
            "            public double getPrecision() {\n" + "                return presicion;\n" + "            }\n\n" + "        }\n\n" +
            "        public PlayerDetail[] getPlayers() {\n" + "            return new PlayerDetail[]{\n";
        int i = 0;
        for (PlayerDetail j : impl.getPlayers()) {
            i++;
            codigo = codigo + "                new JugadorImpl(\"" + j.getPlayerName() + "\", " + j.getNumber() + ", new Color(" +
                j.getSkinColor().getRed() + "," + j.getSkinColor().getGreen() + "," + j.getSkinColor().getBlue() + "), new Color(" +
                j.getHairColor().getRed() + "," + j.getHairColor().getGreen() + "," + j.getHairColor().getBlue() + ")," + j.getSpeed() + "d," +
                j.getPower() + "d," + j.getPrecision() + "d, " + j.isGoalKeeper() + ")" + (i < 11 ? "," : "") + "\n";
        }
        codigo = codigo + "            };\n" + "        }\n" + "    }\n\n";
        jTextArea1.setText(codigo);
        jDialog1.setLocationRelativeTo(null);
        jTextArea1.selectAll();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        int idx;
        String codalin = "";
        int numSaca = -1, numRecibe = -1;
        for (idx = 0; idx < impl.getAlineacionCount(); idx++) {
            if (numSaca == -1 && impl.getTipoAlineacion(idx) == 1) {
                numSaca = idx + 1;
            }
            if (numRecibe == -1 && impl.getTipoAlineacion(idx) == 2) {
                numRecibe = idx + 1;
            }
            codalin = codalin + "    Position alineacion" + (idx + 1) + "[]=new Position[]{\n";
            int i = 0;
            Position[] Positiones = impl.getAlineacion(idx);
            for (PlayerDetail j : impl.getPlayers()) {
                i++;
                codalin =
                    codalin + "        new Position(" + Positiones[i - 1].getX() + "," + Positiones[i - 1].getY() + ")" + (i < 11 ? "," : "") + "\n";
            }
            codalin = codalin + "    };\n\n";
        }

        jTextArea1.setText(codalin);
        jDialog1.setLocationRelativeTo(null);
        jTextArea1.selectAll();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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
            sim = true;
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
                jTextField2.requestFocus();
                jTextField2.setSelectionStart(0);
                jTextField2.setSelectionEnd(jTextField2.getText().length());
            }
        } else {
            p0 = transformAsistente(impl.getAlineacion(jComboBox3.getSelectedIndex())[jList1.getSelectedIndex()]);
        }
    }//GEN-LAST:event_jPanel5MousePressed

    private void jButton12ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int idx = jComboBox3.getSelectedIndex();
        int size = jComboBox3.getModel().getSize();
        if (size == 1) {
            JOptionPane.showMessageDialog(this, "No puede eliminar todas las alineaciones", "", JOptionPane.WARNING_MESSAGE);
        } else {
            model1.removeElementAt(size - 1);
            impl.delAlineacion(idx);
            jPanel5.repaint();
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        int idx = jComboBox3.getSelectedIndex();
        impl.addAlineacion(idx);
        int size = jComboBox3.getModel().getSize() + 1;
        model1.addElement("" + size);
        jComboBox3.setSelectedIndex(size - 1);
    }//GEN-LAST:event_jButton11ActionPerformed

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

    private void jButton9ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        PlayerDetail j = getJugador();
        Color piel = j.getSkinColor();
        Color pelo = j.getHairColor();
        for (PlayerDetail jj : impl.getPlayers()) {
            ((JugadorImpl) jj).setColorPelo(pelo);
            ((JugadorImpl) jj).setColorPiel(piel);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        repinta();
        jTextField3.requestFocus();
        jTextField3.setSelectionStart(0);
        jTextField3.setSelectionEnd(jTextField3.getText().length());
    }//GEN-LAST:event_jList1ValueChanged

    private void jButton7ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Color c = JColorChooser.showDialog(this, "Color del Pelo", getJugador().getHairColor());
        if (c != null) {
            getJugador().setColorPelo(c);
            jButton7.setBackground(c);
            update = true;
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Color c = JColorChooser.showDialog(this, "Color de la Piel", getJugador().getSkinColor());
        if (c != null) {
            getJugador().setColorPiel(c);
            jButton6.setBackground(c);
            update = true;
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField4KeyReleased(KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        try {
            getJugador().setNumero(Integer.parseInt(jTextField4.getText().trim()));
        } catch (Exception ex) {
        }
        repintaModel();
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField4KeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyPressed
        try {
            getJugador().setNumero(Integer.parseInt(jTextField4.getText().trim()));
        } catch (Exception ex) {
        }
        repintaModel();
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField4KeyPressed

    private void jTextField4FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusLost
        try {
            getJugador().setNumero(Integer.parseInt(jTextField4.getText().trim()));
        } catch (Exception ex) {
            jTextField4.setText("" + getJugador().getNumber());
        }
        repintaModel();
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField4FocusLost

    private void jTextField4FocusGained(FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
        jTextField4.selectAll();
    }//GEN-LAST:event_jTextField4FocusGained

    private void jTextField3KeyReleased(KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
        getJugador().setNombre(jTextField3.getText());
        repintaModel();
        jPanel5.repaint();
    }//GEN-LAST:event_jTextField3KeyReleased

    private void jTextField3KeyPressed(KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyPressed
        int idx = jList1.getSelectedIndex();
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            jList1.setSelectedIndex((idx + 1) % 11);
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (idx == 0) {
                jList1.setSelectedIndex(10);
            } else {
                jList1.setSelectedIndex(idx - 1);
            }
        } else {
            getJugador().setNombre(jTextField3.getText());
        }
        repintaModel();
    }//GEN-LAST:event_jTextField3KeyPressed

    private void jTextField3FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusLost
        getJugador().setNombre(jTextField3.getText().trim());
        repintaModel();
    }//GEN-LAST:event_jTextField3FocusLost

    private void jTextField3FocusGained(FocusEvent evt) {//GEN-FIRST:event_jTextField3FocusGained
        jTextField3.selectAll();
    }//GEN-LAST:event_jTextField3FocusGained

    private void jComboBox4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        uniformeAlternativo = jComboBox4.getSelectedIndex() == 1;
        repinta();
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void jButton8ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (jComboBox4.getSelectedIndex() == 0) {
            azar();
        } else {
            azar2();
        }
        update = true;
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton5ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (jComboBox4.getSelectedIndex() == 0) {
            Color col = JColorChooser.showDialog(this, "Color del Portero", impl.getGoalKeeper());
            if (col != null) {
                impl.setColorPortero(col);
                jButton5.setBackground(col);
                repinta();
                update = true;
            }
        } else {
            Color col = JColorChooser.showDialog(this, "Color del Portero", impl.getGoalKeeper2());
            if (col != null) {
                impl.setColorPortero2(col);
                jButton5.setBackground(col);
                repinta();
                update = true;
            }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jComboBox4.getSelectedIndex() == 0) {
            Color col = JColorChooser.showDialog(this, "Color de la Franja", impl.getShirtLineColor());
            if (col != null) {
                impl.setColorFranja(col);
                jButton4.setBackground(col);
                repinta();
                update = true;
            }
        } else {
            Color col = JColorChooser.showDialog(this, "Color de la Franja", impl.getShirtLineColor2());
            if (col != null) {
                impl.setColorFranja2(col);
                jButton4.setBackground(col);
                repinta();
                update = true;
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jComboBox4.getSelectedIndex() == 0) {
            Color col = JColorChooser.showDialog(this, "Color de las Calcetas", impl.getSocksColor());
            if (col != null) {
                impl.setColorCalcetas(col);
                jButton3.setBackground(col);
                repinta();
                update = true;
            }
        } else {
            Color col = JColorChooser.showDialog(this, "Color de las Calcetas", impl.getSocksColor2());
            if (col != null) {
                impl.setColorCalcetas2(col);
                jButton3.setBackground(col);
                repinta();
                update = true;
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (jComboBox4.getSelectedIndex() == 0) {
            Color col = JColorChooser.showDialog(this, "Color de los Pantalones", impl.getShortsColor());
            if (col != null) {
                impl.setColorPantalon(col);
                jButton2.setBackground(col);
                repinta();
                update = true;
            }
        } else {
            Color col = JColorChooser.showDialog(this, "Color de los Pantalones", impl.getShortsColor2());
            if (col != null) {
                impl.setColorPantalon2(col);
                jButton2.setBackground(col);
                repinta();
                update = true;
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jComboBox4.getSelectedIndex() == 0) {
            Color col = JColorChooser.showDialog(this, "Color de la Camiseta", impl.getShirtColor());
            if (col != null) {
                impl.setColorCamiseta(col);
                jButton1.setBackground(col);
                repinta();
                update = true;
            }
        } else {
            Color col = JColorChooser.showDialog(this, "Color de la Camiseta", impl.getShirtColor2());
            if (col != null) {
                impl.setColorCamiseta2(col);
                jButton1.setBackground(col);
                repinta();
                update = true;
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox7ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed
        if (jComboBox4.getSelectedIndex() == 0) {
            impl.setEstiloPrincipal((EstiloUniforme) jComboBox7.getSelectedItem());
        } else {
            impl.setEstilo2((EstiloUniforme) jComboBox7.getSelectedItem());
        }
        repinta();
        newImpl = true;
    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jTextField2FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        impl.setEntrenador(jTextField2.getText().trim());
    }//GEN-LAST:event_jTextField2FocusLost

    private void jComboBox1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jComboBox1FocusLost
        impl.setPais("" + jComboBox1.getSelectedItem());
    }//GEN-LAST:event_jComboBox1FocusLost

    private void jTextField1FocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        impl.setNombre(jTextField1.getText().trim());
    }//GEN-LAST:event_jTextField1FocusLost

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

    private void jButton14ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        sim = true;
        jPanel5.repaint();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jSlider1StateChanged(ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        updateSlider();
    }//GEN-LAST:event_jSlider1StateChanged

    private void jSlider2StateChanged(ChangeEvent evt) {//GEN-FIRST:event_jSlider2StateChanged
        updateSlider();
    }//GEN-LAST:event_jSlider2StateChanged

    private void jSlider3StateChanged(ChangeEvent evt) {//GEN-FIRST:event_jSlider3StateChanged
        updateSlider();
    }//GEN-LAST:event_jSlider3StateChanged

    private void jMenuItem7ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            TacticValidate.validateDetail("tactica", impl);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validacion", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Validacion OK", "Validacion", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void formWindowClosing(WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        props.setProperty("currentDir", jfc.getCurrentDirectory().getAbsolutePath());
        try {
            props.store(new FileOutputStream("props"), "properties");
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowClosing

    private void jToggleButton2ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        jPanel5.repaint();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

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
            sim = true;
            jPanel5.repaint();
        } catch (Exception e) {
            jTextField7.setText("0");
        }
    }//GEN-LAST:event_jPanel5MouseWheelMoved

    private void jButton18ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        JugadorImpl[] jugs = ((JugadorImpl[]) (impl.getPlayers()));
        int idx = jList1.getSelectedIndex();
        for (int i = 0; i < 11; i++) {
            jugs[i].setPortero(i == idx);
        }
        update = true;
        repaint();
    }//GEN-LAST:event_jButton18ActionPerformed

    private JugadorImpl getJugador() {
        return (JugadorImpl) jList1.getSelectedValue();
    }

    private void azar() {
        impl.uniformePrincipalAlAzar();
        repinta();
    }

    private void azar2() {
        impl.uniformeSecundarioAlAzar();
        repinta();
    }

    private void repintaModel() {
        for (int i = 0; i < model.getSize(); i++) {
            model.set(i, impl.getPlayers()[i]);
        }
    }

    private void repinta() {
        pintando = true;
        jPanel5.repaint();
        PlayerDetail j = (PlayerDetail) jList1.getSelectedValue();
        for (int i = 0; i < model.getSize(); i++) {
            model.set(i, impl.getPlayers()[i]);
        }
        int selected = jComboBox4.getSelectedIndex();
        if (selected == 0) {
            jButton1.setBackground(impl.getShirtColor());
            jButton2.setBackground(impl.getShortsColor());
            jButton3.setBackground(impl.getSocksColor());
            jButton4.setBackground(impl.getShirtLineColor());
            jButton5.setBackground(impl.getGoalKeeper());
            int idx = -1;
            for (int i = 0; i < estilos.length; i++) {
                if (estilos[i].equals(impl.getStyle())) {
                    idx = i;
                }
            }
            if (idx > -1) {
                jComboBox7.setSelectedIndex(idx);
            }
        } else {
            jButton1.setBackground(impl.getShirtColor2());
            jButton2.setBackground(impl.getShortsColor2());
            jButton3.setBackground(impl.getSocksColor2());
            jButton4.setBackground(impl.getShirtLineColor2());
            jButton5.setBackground(impl.getGoalKeeper2());
            int idx = -1;
            for (int i = 0; i < estilos.length; i++) {
                if (estilos[i].equals(impl.getStyle2())) {
                    idx = i;
                }
            }
            if (idx > -1) {
                jComboBox7.setSelectedIndex(idx);
            }
        }
        jTextField1.setText(impl.getTacticName());
        jComboBox1.setSelectedItem(impl.getCountry());
        jTextField2.setText(impl.getCoach());
        int num = ((EstiloUniforme) jComboBox7.getSelectedItem()).getNumero();
        PoligonosData d = data[num - 1];
        intx = d.intx;
        inty = d.inty;
        cols = d.cols;
        jTextField3.setText(j.getPlayerName());
        jTextField4.setText("" + j.getNumber());
        jButton6.setBackground(j.getSkinColor());
        jButton7.setBackground(j.getHairColor());
        jSlider1.setValue((int) (j.getSpeed() * 100));
        jSlider2.setValue((int) (j.getPower() * 100));
        jSlider3.setValue((int) (j.getPrecision() * 100));
        jLabel15.setText("Velocidad(" + convertVelocidad(j.getSpeed()) + ")");
        jLabel16.setText("Remate   (" + convertRemate(j.getPower()) + ")");
        jLabel17.setText("Error    (" + convertError(j.getPrecision()) + ")");
        jList1.repaint();
        jPanel1.repaint();
        pintando = false;
    }

    @Override
    public void run() {
        try {
            cgc.start();
        } catch (Exception ex) {
            logger.error("Error al iniciar el gameContainer", ex);
        }
    }
}

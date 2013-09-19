package org.javahispano.javacup.tacticas.jvc2012.chr2012.tactica.utilidades;


import org.javahispano.javacup.model.util.Constants;
import org.javahispano.javacup.model.util.Position;


/**
 * Clase en la que se definen las constantes utilizadas en la t�ctica.
 * 
 * @author Christian Onwuzor Mart�n (chr -> christian.onwuzor@gmail.com)
 */
public class Entorno {

     // L�mite considerado para la l�nea defensiva.
    public static final double LIMITE_DEFENSA = -21;

    // L�mite considerado para la l�nea del medio del campo.
    public static final double LIMITE_MEDIO = 21;

    // L�mite hasta el que un jugador puede avanzar sobre el eje de las Y
    public static final double LIMITE_AVANCE_Y = 25;
    
    // Distancia m�xima a la que se puede realizar un marcaje.
    public static final double ALCANCE_MARCAJE = 10;

    // Distancia a la que un jugador puede ir a por el balón.
    public static final double ALCANCE_BALON = 10;

    // Distancia a la que realizan los marcajes los jugadores.
    public static final double DISTANCIA_MARCAJE_MEDIO = 2;
    public static final double DISTANCIA_MARCAJE_DEFENSA = 0.6;

    public static final char SECTOR_DEFENSA = 'D';
    public static final char SECTOR_MEDIO = 'M';
    public static final char SECTOR_ATAQUE = 'A';

    // La distancia que desplaza un jugador la pelota mientras avanza.
    public static final double TOQUE = 0.4;

    // Fuerza m�xima de cualquier jugador para realiza un golpeo de bal�n.
    public static final double FUERZA_MAXIMA_GOLPEO = 1;

    //TODO LO CAMBIAMOS PARA PROBAR
    // Distancia a la que es aconsejable realizar un tiro si se puede.
    public static final double DISTANCIA_FORZAR_TIRO = 14;

    // Distancia m�nima desde la que se puede realizar un tiro.
    public static final double DISTANCIA_MINIMA_TIRO = 23;

    // La m�xima distancia a la que llega un jugador con fuerza m�xima con un golpeo bajo.
    public static final double DISTANCIA_MAX_GOLEPO_BAJO = 60;

    //TODO QUIZAS SE PUEDA CALCULAR VISIONANDO EL CODIGO CONTANDO LA DISTANCIA DE LA BOLA M�S LO QUE DECELERA; ETC.
    // La m�xima distancia a la que llega un jugador con fuerza m�xima con un golpeo alto.
    public static final double DISTANCIA_MAX_GOLPEO_ALTO = Constants.LARGO_CAMPO_JUEGO - Constants.ANCHO_AREA_CHICA;

    // La distancia minima a la que se puede encontrar un rival para poder avanzar.
    public static final double DISTANCIA_AVANZAR = 6;

    // La distancia m�nima a la que debe estar un rival para no cortar un pase.
    public static final double DISTANCIA_CORTE_PASE = Constants.DISTANCIA_CONTROL_BALON * 5;

    // La distancia m�nima a la que debe estar un portero para no cortar un pase.
    public static final double DISTANCIA_CORTE_TIRO = Constants.DISTANCIA_CONTROL_BALON_PORTERO + 0.2;

    // La distancia m�nima a la que se tienen que encontrar un jugador para poder
    // pasarle la pelota (as� evitamos bloqueos y pases muy cortos)
    public static final double DISTANCIA_MIN_PASE = Constants.DISTANCIA_CONTROL_BALON * 1.5;

    // La distancia m�nima a la que se tiene que encontrar un rival del destino del pase del portero.
    public static final double DISTANCIA_RIVALES_PASE_PORTERO = 15;
    
    // La distancia a la que se tiene que encontrar el otro jugador para hacerle un pase.
    public static final double MIN_DISTANCIA_PASE = 12;
    
    // La distancia a la que se tiene que encontrar el otro jugador para hacerle un pase hacia adelante.
    public static final double MIN_DISTANCIA_PASE_ADELANTE = 3;
    
    // La distancia de seguridad que tiene que tener un jugador para que no le roben la pelota desde atr�s
    public static final double DISTANCIA_RETAGUARDIA = 4;
    
    // El tiempo minimo que tiene que tardar un jugador en llegar antes a por la bola que el rival.
    public static final double TIEMPO_MIN_AVANCE = 1;
    
    // El tiempo minimo que tiene que tardar el portero en llegar antes a por la bola que el rival.
    public static final double TIEMPO_MIN_PORTERO = 0.4;
    
    // Delimita los lados de la zona del portero.
    public static final double LADO_ZONA_PORTERO = 13;

    // Posici�n en el eje X a partir de la que se anunla el tiro a puerta vac�a.
    public static final double X_ANULA_PUERTA_VACIA = 5;
    
    // Posici�n en el eje Y a partir de la que se anunla el tiro a puerta vac�a.
    public static final double Y_ANULA_PUERTA_VACIA = 50;
    

    public static final Position alineacionNormal[] = new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(19.97202797202797,-23.28054298642534),
        new Position(-19.97202797202797,-25.656108597285066),
        new Position(7.37062937062937,-28.031674208144796),
        new Position(-6.6573426573426575,-27.794117647058822),
        new Position(7.608391608391608,2.6628959276018098),
        new Position(22.825174825174827,10.839366515837104),
        new Position(-7.608391608391608,0.98868778280543),
        new Position(-11.888111888111888,35.88235294117647),
        new Position(8.804195804195804,28.993212669683256),
        new Position(-25.202797202797203,9.038461538461538)
    };

    public static Position alineacionSacando[] = new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(17.356643356643357,-26.368778280542987),
        new Position(-20.447552447552447,-25.418552036199095),
        new Position(6.6573426573426575,-30.6447963800905),
        new Position(-7.608391608391608,-29.694570135746606),
        new Position(6.6573426573426575,-13.778280542986426),
        new Position(22.825174825174827,-2.3755656108597285),
        new Position(-9.034965034965035,-12.352941176470589),
        new Position(0.0, 0.0),
        new Position(2.377622377622378,-2.8506787330316743),
        new Position(-27.104895104895103,-1.6628959276018098)
    };

    public static Position alineacionRecibiendo[] = new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(14.74125874125874,-30.88235294117647),
        new Position(-16.405594405594407,-31.59502262443439),
        new Position(5.230769230769231,-33.49547511312217),
        new Position(-4.755244755244756,-32.54524886877828),
        new Position(4.27972027972028,-18.29185520361991),
        new Position(15.216783216783217,-12.115384615384617),
        new Position(-8.083916083916083,-18.529411764705884),
        new Position(-10.937062937062937,-5.226244343891403),
        new Position(-1.4265734265734267,-8.927601809954751),
        new Position(-22.58741258741259,-14.015837104072398)
    };

    public static Zona zona[] = new Zona[] {
        new Zona(-LADO_ZONA_PORTERO, LADO_ZONA_PORTERO, Constants.centroArcoInf.getY() + LADO_ZONA_PORTERO, Constants.centroArcoInf.getY()),

        new Zona((Constants.ANCHO_CAMPO_JUEGO/4)+ 0.00001, Constants.ANCHO_CAMPO_JUEGO/2, Entorno.LIMITE_DEFENSA, -Constants.LARGO_CAMPO_JUEGO/2),
        new Zona(-Constants.ANCHO_CAMPO_JUEGO/2, -Constants.ANCHO_CAMPO_JUEGO/4, Entorno.LIMITE_DEFENSA, -Constants.LARGO_CAMPO_JUEGO/2),
        new Zona(0.00001, Constants.ANCHO_CAMPO_JUEGO/4, Entorno.LIMITE_DEFENSA, -Constants.LARGO_CAMPO_JUEGO/2),
        new Zona((-Constants.ANCHO_CAMPO_JUEGO/4)+0.00001, 0, Entorno.LIMITE_DEFENSA, -Constants.LARGO_CAMPO_JUEGO/2),

        new Zona(0.00001, Constants.ANCHO_CAMPO_JUEGO/4, Entorno.LIMITE_MEDIO, Entorno.LIMITE_DEFENSA),
        new Zona((Constants.ANCHO_CAMPO_JUEGO/4) + 0.00001, Constants.ANCHO_CAMPO_JUEGO/2, Entorno.LIMITE_MEDIO, Entorno.LIMITE_DEFENSA),
        new Zona((-Constants.ANCHO_CAMPO_JUEGO/4) + 0.00001, 0, Entorno.LIMITE_MEDIO, Entorno.LIMITE_DEFENSA),
        new Zona(-Constants.ANCHO_CAMPO_JUEGO/2, 0, Constants.LARGO_CAMPO_JUEGO/2, Entorno.LIMITE_MEDIO),

        new Zona(0, Constants.ANCHO_CAMPO_JUEGO/2, Constants.LARGO_CAMPO_JUEGO/2, Entorno.LIMITE_MEDIO),
        new Zona(-Constants.ANCHO_CAMPO_JUEGO/2, -Constants.ANCHO_CAMPO_JUEGO/4, Entorno.LIMITE_MEDIO, Entorno.LIMITE_DEFENSA),
    };
    
    
    // Creamos un array que identifica la prioridad normal de pases de un jugador.
    public static String[] valoracionNormal = new String[] {

        "1|2|6|10|5|7|9|8", //0

        "6|5|9|8|3",	  	//1
        "10|7|8|9|4",	  	//2
        "5|7|1|6|9|8",	  	//3
        "7|5|2|10|8|9",	  	//4
        
        "6|9|10|8|7|1|3", 	//5
        "9|8|10|5|7|1|3", 	//6
        "10|8|6|9|5|2|4", 	//7
        "9|10|6|7|5",	  	//8
        
        "8|6|10|5|7",	  	//9
        "8|9|6|7|5|2|4",  	//10
    };
    
    
    // Creamos un array que identifica la prioridad de ataque de pases de un jugador.
    public static String[] valoracionAtaque = new String[] {

        "6|10|9|8|5|7", 	//0

        "6|9|8|10|5|7",	  	//1
        "10|8|9|6|7|5",	  	//2
        "6|9|8|10|5|7",	  	//3
        "10|8|9|6|7|5",	  	//4
        
        "9|8|6|10|7", 		//5
        "9|8|10", 			//6
        "8|9|10|6|5", 		//7
        "9|6|10",	  		//8
        
        "8|10|6",	  		//9
        "8|9|6",  			//10
    };
}
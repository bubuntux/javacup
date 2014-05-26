package org.dsaw.javacup.tacticas.jvc2012.rchavarria;

import org.dsaw.javacup.model.util.Constants;
import org.dsaw.javacup.model.util.Position;

/**
 * Alineaciones
 * 
 * @author rchavarria
 * 
 */
public class LineUps {

    Position iStart[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-19.734265734265733,-30.6447963800905),
        new Position(-7.846153846153847,-23.755656108597286),
        new Position(6.419580419580419,-23.28054298642534),
        new Position(19.020979020979023,-29.21945701357466),
        new Position(-27.58041958041958,-2.6131221719457014),
        new Position(-8.797202797202797,-10.452488687782806),
        new Position(5.944055944055944,-11.165158371040723),
        new Position(23.776223776223777,-1.900452488687783),
        new Position(-4.755244755244756,-1.900452488687783),
        new Position(0.7132867132867133,-0.47511312217194573)
    };

    Position rivalStarts[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-25.916083916083913,-23.518099547511312),
        new Position(-11.888111888111888,-30.407239819004527),
        new Position(7.37062937062937,-30.16968325791855),
        new Position(24.48951048951049,-23.28054298642534),
        new Position(-27.104895104895103,-4.038461538461538),
        new Position(-5.468531468531468,-9.97737556561086),
        new Position(5.230769230769231,-9.502262443438914),
        new Position(24.965034965034967,-3.5633484162895925),
        new Position(-10.223776223776223,-1.1877828054298643),
        new Position(10.223776223776223,-0.7126696832579186)
    };

    Position leftOffensive[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-18.545454545454543,-17.57918552036199),
        new Position(-5.706293706293707,-13.065610859728507),
        new Position(3.804195804195804,-13.303167420814479),
        new Position(11.888111888111888,-19.004524886877828),
        new Position(-18.545454545454543,17.34162895927602),
        new Position(-7.846153846153847,8.789592760180994),
        new Position(0.23776223776223776,10.927601809954751),
        new Position(8.083916083916083,17.34162895927602),
        new Position(-22.349650349650346,31.83257918552036),
        new Position(0.4755244755244755,30.88235294117647)
    };

    Position defensive[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-18.78321678321678,-28.744343891402718),
        new Position(-11.174825174825173,-35.15837104072398),
        new Position(8.083916083916083,-33.73303167420815),
        new Position(20.20979020979021,-28.50678733031674),
        new Position(-12.125874125874127,-19.717194570135746),
        new Position(0.0,-28.98190045248869),
        new Position(6.419580419580419,-14.96606334841629),
        new Position(16.167832167832167,-17.34162895927602),
        new Position(-5.468531468531468,-15.203619909502263),
        new Position(-0.7132867132867133,5.226244343891403)
    };

    Position centralOffensive[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-16.643356643356643,-19.479638009049776),
        new Position(-8.321678321678322,-8.076923076923077),
        new Position(3.5664335664335667,-7.601809954751132),
        new Position(15.454545454545453,-18.054298642533936),
        new Position(-22.11188811188811,15.203619909502263),
        new Position(-7.846153846153847,6.176470588235294),
        new Position(10.461538461538462,8.314479638009049),
        new Position(22.349650349650346,20.667420814479637),
        new Position(-5.944055944055944,20.90497737556561),
        new Position(3.090909090909091,27.794117647058822)
    };

    Position rightOffensive[]=new Position[]{
        new Position(0.2595419847328244,-50.41044776119403),
        new Position(-14.265734265734267,-15.203619909502263),
        new Position(-5.706293706293707,-9.97737556561086),
        new Position(4.9930069930069925,-9.97737556561086),
        new Position(15.692307692307693,-15.91628959276018),
        new Position(-13.076923076923078,14.728506787330318),
        new Position(9.272727272727272,8.552036199095022),
        new Position(0.4755244755244755,12.115384615384617),
        new Position(17.356643356643357,15.441176470588236),
        new Position(-5.468531468531468,29.457013574660635),
        new Position(21.3986013986014,27.31900452488688)
    };

    public Position[] getStartPositions() {
        return iStart;
    }

    public Position[] getNoStartPositions() {
        return rivalStarts;
    }

    public Position[] get(int i) {
        switch (i) {
        case 3:
            return leftOffensive;
        case 4:
            return defensive;
        case 5:
            return centralOffensive;
        case 6:
            return rightOffensive;
        }

        throw new IllegalArgumentException(
                "Lineup index should be between 3 and 6, not " + i);
    }

    public int selectAccordingToBallPosition(Position ballPosition) {
        if(ballInRivalField(ballPosition)){
            if(ballInRightThird(ballPosition)) {
                return 6;
            } else if (ballInLeftThird(ballPosition)){
                return 3;
            } else {
                return 5;
            }
        } else {
            return 4;
        }
    }
    
    private boolean ballInRivalField(Position ballPosition) {
        return ballPosition.getX() > 0;
    }

    /**
     * Si 'y' es positiva, esta en la parte izqda.
     * Si 'y' es mayor que un tercio de la mitad positiva del ancho, estamos en el tercio superior
     * 
     *  campo de juego
     *      ���=========================+ centro del campo (y = 0)
     *      ���-------------------------+ tercio inferor ((-1) * Constants.ANCHO_CAMPO_JUEGO / 2 / 3)
     *                                  |
     *      ���-------------------------+ parte superior (y = (-1) * Constants.ANCHO_CAMPO_JUEGO / 2)
     * 
     * @param ballPosition
     * @return
     */
    private boolean ballInRightThird(Position ballPosition) {
        double y = ballPosition.getY();
        boolean rightThird = (y > 0) 
                          && (Math.abs(y) >= (Constants.ANCHO_CAMPO_JUEGO / 2 / 3));

        if(rightThird) {
            //System.out.println("ball in right third: y='" + y +"'");
        }
        return rightThird;
    }

    /**
     * Si 'y' es positiva, esta en la parte izqda.
     * Si 'y' es mayor que un tercio de la mitad positiva del ancho, estamos en el tercio superior
     * 
     *  campo de juego
     *      ���-------------------------+ parte superior (y = Constants.ANCHO_CAMPO_JUEGO / 2)
     *                                  |
     *      ���-------------------------+ tercio inferor (Constants.ANCHO_CAMPO_JUEGO / 2 / 3)
     *      ���=========================+ centro del campo (y = 0)
     * 
     * @param ballPosition
     * @return
     */
    private boolean ballInLeftThird(Position ballPosition) {
        double y = ballPosition.getY();
        boolean leftThird = (y > 0) 
                         && (y >= (Constants.ANCHO_CAMPO_JUEGO / 2 / 3));
        
        if(leftThird) {
            //System.out.println("ball in left third: y='" + y +"'");
        }
        return leftThird;
    }

}

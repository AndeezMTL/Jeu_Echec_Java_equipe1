package ca.mv.projet.models.pieces;

import ca.mv.projet.models.Echiquier;
import ca.mv.projet.models.cases.Position;

public class Tour extends Piece {

    public Tour(boolean estBlanche) {
        super(estBlanche);
    }

    @Override
    public boolean peutBouger(Position posCourante, Position posDestination, Echiquier echiquier) {
        int diffX = posDestination.getX() - posCourante.getX();
        int diffY = posDestination.getY() - posCourante.getY();

        // Vérifie si le déplacement est vertical ou horizontal
        if (diffX == 0 || diffY == 0) {

            // Vérifie s'il n'y a pas d'obstacles sur la ligne ou la colonne
            int stepX = diffX == 0 ? 0 : (diffX > 0 ? 1 : -1);
            int stepY = diffY == 0 ? 0 : (diffY > 0 ? 1 : -1);
            int x = posCourante.getX() + stepX;
            int y = posCourante.getY() + stepY;

            while (x != posDestination.getX() || y != posDestination.getY()) {
                Piece caseOccupeePiece = echiquier.getPieceAtPosition(new Position(x, y));
                if (caseOccupeePiece != null) {
                    if (caseOccupeePiece.isEstBlanc() == this.isEstBlanc()) {
                        return false;
                    }
                    return true; //la pièce peut bouger, la case est occupée par une pièce ennemie (donc on va la capturer)
                }
                x += stepX;
                y += stepY;
            }
            return true;
        }
        return false;
    }
}

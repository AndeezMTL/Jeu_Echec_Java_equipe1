package ca.mv.projet;

import ca.mv.projet.models.Grille;
import ca.mv.projet.models.Joueur;
import ca.mv.projet.models.Echiquier;
import ca.mv.projet.models.cases.Case;
import ca.mv.projet.models.cases.Position;
import ca.mv.projet.models.pieces.Piece;
import ca.mv.projet.models.pieces.Pion;
import ca.mv.projet.models.pieces.Roi;
import ca.mv.projet.AppController;

public class Jeu {
    // TODO: ajouter le code manquant
    private Echiquier echiquier;
    private Joueur j1;
    private Joueur j2;
    private Grille grille;
    private int tourDeJeux;


    public Echiquier getEchiquier() {
        return echiquier;
    }

    public Joueur getJ1() {
        return j1;
    }

    public Joueur getJ2() {
        return j2;
    }

    public Grille getGrille() {
        return grille;
    }

    public Jeu() {
        // TODO: ajouter le code et les paramètres appropriés

        this.echiquier = new Echiquier();
        this.j1 = new Joueur(Utilities.j1_name, true);
        this.j2 = new Joueur(Utilities.j2_name, false);
        this.grille = new Grille(this.echiquier);
        this.tourDeJeux = 0;
    }

    public boolean estTourDesBlanc() {
        return (this.tourDeJeux % 2) == 0;
    }

    public void mancheJouee(Position pFrom, Position pTo) {
        if (estTourDesBlanc() && echiquier.getPieceAtPosition(pFrom).isEstBlanc()) {
            ResultatManche manche = executeMove(pFrom, pTo);
            switch (manche){
                case INVALIDE : {
                    break;
                }
                case DEPLACEMENT :
                case CAPTURE : {
                    tourDeJeux++;
                    break;
                }
                case ECHEC : {
                    System.out.println("Fin de partie. Le roi " + (echiquier.getPieceAtPosition(pTo).isEstBlanc() ? "Blanc" : "Noir") + " à été capturé.");
                    break;
                }

            }

        } else {
            System.out.println("Ce n'est pas votre tour");
        }
    }

    public ResultatManche executeMove(Position pFrom, Position pTo) {
        if (echiquier.estOccupe(pFrom)) {
            if (echiquier.estValidMouve(pFrom, pTo)) {
                Piece pieceBouffe = echiquier.getPieceAtPosition(pTo);
                if (pieceBouffe != null) {
                    pieceBouffe.die();
                    if (pieceBouffe.getClass() == Roi.class) {
                        // win
                        return ResultatManche.ECHEC;
                    }
                    grille.creerGrille();
                    echiquier.setCaseParPosition(pFrom, pTo);
                    return ResultatManche.CAPTURE;
                }
                echiquier.setCaseParPosition(pFrom, pTo);
                return ResultatManche.DEPLACEMENT;
            } else {
                return ResultatManche.INVALIDE;
            }
        }
        return ResultatManche.INVALIDE;
    }


}


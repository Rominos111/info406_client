package fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.view;


import fr.groupe4.clientprojet.display.mainwindow.panels.centerpanel.view.CenterPanel;
import fr.groupe4.clientprojet.display.mainwindow.panels.leftpanel.controller.EventLeftPanel;
import fr.groupe4.clientprojet.utils.Location;
import fr.groupe4.clientprojet.display.view.RoundButton;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Créé le panel de gauche de la fenêtre
 * Celui qui contient la liste des projets et le bouton 'calendrier' et 'profil'
 */
public class LeftPanel extends JPanel {
    /**
     * La largeur des boutons
     */
    private final int TAILLE_BOUTONS = 25; // TODO: Le final pourra être enlevé quand on ajoutera les paramètres
    /**
     * La liste des boutons
     */
    private ArrayList<RoundButton> buttons;
    /**
     * le panel du centre
     */
    private CenterPanel centerPanel;
    /**
     * le nombre de projets
     * le début de la liste des projets
     */
    private int nbProjet, debutListe = 0;
    private boolean first = true;

    /**
     * Le constructeur
     *
     * @param centerPanel : le panel du centre
     */
    public LeftPanel(CenterPanel centerPanel) {
        buttons = new ArrayList<>();
        this.centerPanel = centerPanel;


        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, Color.BLACK), new EmptyBorder(20, 10, 20, 10)));

        drawContent();
    }

    /**
     * Renvoie la liste des boutons
     *
     * @return : les boutons
     */
    public ArrayList<RoundButton> getButtons() {
        return buttons;
    }

    /**
     * Dessine le contenu du panel
     */
    private void drawContent() {
        EventLeftPanel eventLeftPanel = new EventLeftPanel(this, centerPanel);
        Font buttonFont = new Font("Arial", Font.PLAIN, TAILLE_BOUTONS);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(25, 0, 25, 0);

        // Boutons projets
        drawProjectButton(eventLeftPanel, buttonFont, c);
        c.insets = new Insets(25, 0, 25, 0);


        // Boutons du bas (calendrier, profil)
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(new MatteBorder(3, 0, 0, 0, Color.BLACK));
        bottomPanel.setBackground(Color.WHITE);
        c.gridy = 0;
        RoundButton button = new RoundButton(new File(Location.getPath() + "/data/img/calendar.png"));
        buttons.add(button);
        button.setActionCommand(CenterPanel.CALENDAR);
        button.addActionListener(eventLeftPanel);
        button.setFont(buttonFont);
        bottomPanel.add(button, c);
        c.gridy = 1;
        button = new RoundButton(new File(Location.getPath() + "/data/img/user.png"));
        buttons.add(button);
        button.setActionCommand(CenterPanel.USER);
        if (first) {
            button.setSelected(true);
            first = false;
        }
        button.addActionListener(eventLeftPanel);
        button.setFont(buttonFont);
        bottomPanel.add(button, c);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Dessine la liste des projets
     *
     * @param eventLeftPanel : le listener du panel
     * @param buttonFont : la police des boutons
     * @param c : la contrainte du panel
     */
    private void drawProjectButton(EventLeftPanel eventLeftPanel, Font buttonFont, GridBagConstraints c) {
        JPanel projectPanel = new JPanel(new BorderLayout());
        projectPanel.addMouseWheelListener(eventLeftPanel);
        setNbProjets(10); // TODO: Cette variable sera déterminé par le nombre de projets reçu par le modèle
        int nbProjetsMax = 5; // TODO: Valeur à déterminer en fonction de la taille de la fenêtre


        // Les boutons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);

        for (int i = debutListe; i < nbProjetsMax + debutListe; i++) {
            c.gridy = i;
            if (i < nbProjet) {
                RoundButton button = new RoundButton(Integer.toString(i));
                button.setActionCommand(Integer.toString(i));
                button.addActionListener(eventLeftPanel);
                button.setFont(buttonFont);
                buttonPanel.add(button, c);
                buttons.add(button);
            } else {
                JLabel label = new JLabel(" ");
                label.setFont(buttonFont);
                buttonPanel.add(label, c);
            }
        }

        projectPanel.add(buttonPanel, BorderLayout.CENTER);


        // Label haut et bas pour quand il y a des projets non affichés
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        JLabel haut = new JLabel(". . ."), bas = new JLabel(". . .");
        haut.setFont(new Font("Monospace", Font.BOLD, TAILLE_BOUTONS/2));
        bas.setFont(new Font("Monospace", Font.BOLD, TAILLE_BOUTONS/2));

        if (debutListe > 0) {
            haut.setText(". . .");
        } else {
            haut.setText(" ");
        }
        JPanel hautPanel = new JPanel(new GridBagLayout());
        hautPanel.setBackground(Color.WHITE);
        hautPanel.add(haut, c);
        projectPanel.add(hautPanel, BorderLayout.NORTH);

        if (nbProjet > debutListe + nbProjetsMax) {
            bas.setText(". . .");
        } else {
            bas.setText(" ");
        }
        JPanel basPanel = new JPanel(new GridBagLayout());
        basPanel.setBackground(Color.WHITE);
        basPanel.add(bas, c);
        projectPanel.add(basPanel, BorderLayout.SOUTH);


        add(projectPanel, BorderLayout.NORTH);
    }

    /**
     * Défini le nombre de projet
     *
     * @param nbProjets : le nombre de projet
     */
    public void setNbProjets(int nbProjets) {
        this.nbProjet = nbProjets;
    }

    /**
     * Renvoie le début de la liste des projets
     *
     * @return : le début de la liste des projets
     */
    public int getDebutListe() {
        return debutListe;
    }

    /**
     * Défini le début de la liste des projets
     *
     * @param debutListe : début de la liste
     */
    public void setDebutListe(int debutListe) {
        if (debutListe < nbProjet && debutListe >= 0) {
            this.debutListe = debutListe;
        }
    }

    /**
     * Redéssine le panel
     */
    public void redraw() {
        removeAll();
        validate();
        revalidate();
        repaint();
        drawContent();
    }
}
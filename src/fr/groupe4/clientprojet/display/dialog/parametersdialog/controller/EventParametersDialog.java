package fr.groupe4.clientprojet.display.dialog.parametersdialog.controller;

import fr.groupe4.clientprojet.display.dialog.parametersdialog.view.ParametersDialog;
import fr.groupe4.clientprojet.model.parameters.Parameters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Le listener du dialog des paramètres
 */
public class EventParametersDialog extends WindowAdapter implements ActionListener {
    /**
     * Le dialog des paramètres
     */
    private final ParametersDialog source;

    /**
     * Variables statiques pour les 3 boutons du dialog
     */
    public static final String OK = "ok", CANCEL = "cancel", APPLY = "apply";

    /**
     * Le constructeur
     *
     * @param source Le dialog des paramètres
     */
    public EventParametersDialog(ParametersDialog source) {
        this.source = source;
    }

    /**
     * Fenêtre fermée
     *
     * @param e Event
     */
    @Override
    public void windowClosing(WindowEvent e) {
        source.dispose();
    }

    /**
     * Action effectuée
     *
     * @param e Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case OK:
                applyParameters();
                source.dispose();
                break;

            case CANCEL:
                source.dispose();
                break;

            case APPLY:
                applyParameters();
                break;

            default:
        }
    }

    /**
     * Applique les paramètres
     */
    private void applyParameters() {
        Parameters.setServerUrl(source.getServerUrl());
        Parameters.setThemeName(source.getTheme());
    }
}

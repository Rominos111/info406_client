package fr.groupe4.clientprojet.display.mainwindow.panels.projectpanel.view;

import fr.groupe4.clientprojet.communication.Communication;
import fr.groupe4.clientprojet.display.view.draw.DrawPanel;
import fr.groupe4.clientprojet.display.view.messagepanel.view.MessagePanel;
import fr.groupe4.clientprojet.display.view.slide.view.Slide;
import fr.groupe4.clientprojet.model.project.Project;
import fr.groupe4.clientprojet.model.project.ProjectList;

import javax.swing.*;
import java.awt.*;

/**
 * Le panel des projets
 */
public class ProjectPanel extends DrawPanel {
    /**
     * Le projet qui est affiché
     */
    private Project project;

    /**
     * Le constructeur
     *
     * @param projectName : Le nom du projet
     */
    public ProjectPanel(String projectName) {
        Communication comm = Communication.builder().sleepUntilFinished().startNow().getProjectList().build();

        ProjectList list = (ProjectList) comm.getResult();

        assert list != null;
        for (Project p: list) {
            if (p.getName().equals(projectName)) {
                project = p;
            }
        }

        drawContent();
    }

    /**
     * Dessine le contenu
     */
    @Override
    protected void drawContent() {
        setLayout(new BorderLayout());

        // Titre
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        c.insets = new Insets(20, 0, 0, 0);
        JLabel title = new JLabel(project.getName());
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        titlePanel.add(title, c);

        add(titlePanel, BorderLayout.NORTH);

        // Les slides
        Slide slides = new Slide();
        slides.addSlide(homePanel(), "HOME");
        slides.addSlide(taskPanel(), "TASK");
        slides.addSlide(messagePanel(), "MESSAGE");

        add(slides, BorderLayout.CENTER);
    }

    /**
     * Dessine le premier slide du projet
     *
     * @return : le jpanel
     */
    private JPanel homePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("HOME"), BorderLayout.NORTH);
        panel.add(new JLabel(project.getDescription()), BorderLayout.CENTER);
        // TODO : Construire panel accueil
        return panel;
    }

    /**
     * Dessine le slide des tâches du projet
     *
     * @return : le jpanel
     */
    private JPanel taskPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("TÂCHES"));
        // TODO : Construire panel tâches
        return panel;
    }

    /**
     * Dessine le slide de la messagerie du projet
     *
     * @return : le jpanel
     */
    private JPanel messagePanel() {
        MessagePanel m = new MessagePanel(Communication.builder().getUserMessageList(0));
        m.setIdProject(project.getId());
        return m;
    }
}

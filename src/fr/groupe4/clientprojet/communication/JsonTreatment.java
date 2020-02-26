package fr.groupe4.clientprojet.communication;

import fr.groupe4.clientprojet.project.Project;
import fr.groupe4.clientprojet.project.ProjectList;
import fr.groupe4.clientprojet.resource.human.HumanResource;
import fr.groupe4.clientprojet.resource.human.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static fr.groupe4.clientprojet.communication.enums.CommunicationStatus.*;
import static fr.groupe4.clientprojet.communication.enums.HTMLCode.*;

/**
 * Traite le JSON de la classe Communication
 */
final class JsonTreatment {
    /**
     * Singleton pour que Communication puisse vérifier l'accès à ses token
     */
    private static final JsonTreatment singleton = new JsonTreatment();

    /**
     * Constructeur vide, utile seulement pour le singleton
     */
    private JsonTreatment() {}

    /**
     * Fait quelque chose du contenu de la réponse de l'API <br>
     * synchronized pour éviter les conflits de threads lors de plusieurs traitements simultanés
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    protected static void doSomethingWithData(Communication comm, Object jsonObject) {
        switch (comm.typeOfCommunication) {
            case LOGIN:
                login(comm, jsonObject);
                break;

            case UPDATE_CONNECTION:
                updateConnection(comm, jsonObject);
                break;

            case LIST_PROJECTS:
                listProjects(comm, jsonObject);
                break;

            case GET_USER_INFOS:
                getUserInfos(comm, jsonObject);
                break;

            case GET_HUMAN_RESOURCE:
                getHumanResource(comm, jsonObject);
                break;

            case CREATE_PROJECT:
                createProject(comm, jsonObject);
                break;

            default:
                System.err.println("Traitement JSON : type de communication non reconnu : " + comm.typeOfCommunication.toString());
                break;
        }
    }

    /**
     * Connexion
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void login(Communication comm, Object jsonObject) {
        if (comm.status.equals(STATUS_SUCCESS)) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONObject jsonRequestToken = (JSONObject) jsonContent.get("requests-token");
            Communication.setRequestToken(singleton, (String) jsonRequestToken.get("value"));

            JSONObject jsonRenewToken = (JSONObject) jsonContent.get("renew-token");
            Communication.setRenewToken(singleton, (String) jsonRenewToken.get("value"));
        }
    }

    /**
     * Création de projet
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void createProject(Communication comm, Object jsonObject) {
    }

    /**
     * Récupère une ressource humaine
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getHumanResource(Communication comm, Object jsonObject) {
        if (comm.status.equals(STATUS_SUCCESS)) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            comm.communicationResult = new HumanResource(
                    (long) jsonContent.get("id"),
                    (String) jsonContent.get("firstname"),
                    (String) jsonContent.get("lastname"),
                    (String) jsonContent.get("job"),
                    (String) jsonContent.get("role"),
                    (String) jsonContent.get("description"));
        }
    }

    /**
     * Récupère les infos de l'utilisateur
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void getUserInfos(Communication comm, Object jsonObject) {
        if (comm.htmlCode == HTML_OK) {
            JSONObject jsonContent = (JSONObject) jsonObject;
            JSONObject jsonDataContent = (JSONObject) jsonContent.get("data");
            JSONObject jsonControlContent = (JSONObject) jsonDataContent.get("control");
            JSONObject jsonUserContent = (JSONObject) jsonDataContent.get("user");

            Communication c = Communication
                    .builder()
                    .startNow()
                    .sleepUntilFinished()
                    .getHumanRessource((long) jsonUserContent.get("id_h_resource"))
                    .build();

            HumanResource humanResource = (HumanResource) c.getResult();

            comm.communicationResult = new User(humanResource,
                    (String) jsonControlContent.get("ip"),
                    (String) jsonControlContent.get("type"),
                    (long) jsonUserContent.get("id"),
                    (String) jsonUserContent.get("username"),
                    (String) jsonUserContent.get("email"));
        }
        else {
            System.err.println("Déconnecté en cours de route ?");
        }
    }

    /**
     * Mise à jour de la connexion
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void updateConnection(Communication comm, Object jsonObject) {
        if (comm.htmlCode == HTML_OK) {
            JSONObject jsonContent = (JSONObject) jsonObject;

            JSONObject jsonTokenContent = (JSONObject) jsonContent.get("requests-token");

            Communication.setRequestToken(singleton, (String) jsonTokenContent.get("value"));
        }
        else if (comm.htmlCode == HTML_FORBIDDEN) {
            System.err.println("Update interdite !?");
        }
        else {
            System.err.println("Update malformée !?");
        }
    }

    /**
     * Liste les projets
     *
     * @param comm Communication à traiter
     * @param jsonObject Contenu à traiter
     */
    private static void listProjects(Communication comm, Object jsonObject) {
        JSONObject jsonContent = (JSONObject) jsonObject;
        JSONArray projects = (JSONArray) jsonContent.get("projects");

        ProjectList projectsArray = new ProjectList();

        for (Object projectObject : projects) {
            JSONObject jsonProjectSet = (JSONObject) projectObject;
            Object[] keySet = jsonProjectSet.keySet().toArray();
            String key = String.valueOf(keySet[0]);

            JSONObject jsonProject = (JSONObject) jsonProjectSet.get(key);

            Project project = new Project(
                    (long) jsonProject.get("id"),
                    (String)jsonProject.get("name"),
                    (String) jsonProject.get("description"),
                    (long) jsonProject.get("deadline"),
                    (String) jsonProject.get("status"));

            projectsArray.add(project);
        }

        comm.communicationResult = projectsArray;
    }
}

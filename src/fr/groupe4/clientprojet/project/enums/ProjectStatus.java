package fr.groupe4.clientprojet.project.enums;

public enum ProjectStatus {
    PENDING("PENDING"),
    FINISHED("FINISHED"),
    ONGOING("ONGOING"),
    CANCELED("CANCELED");

    private String msg;

    ProjectStatus(String msg) {
        this.msg = msg.toUpperCase();
    }

    /**
     * Transforme une String en son status associé
     *
     * @param msg Message
     *
     * @return Status associé
     */
    public static ProjectStatus fromString(String msg) {
        ProjectStatus[] statuses = ProjectStatus.values();

        ProjectStatus statusResult = null;

        for (ProjectStatus status : statuses) {
            if (status.msg.equalsIgnoreCase(msg)) {
                statusResult = status;
            }
        }

        return statusResult;
    }

    /**
     * Transforme un status en String
     *
     * @return Message
     */
    @Override
    public String toString() {
        return msg;
    }
}

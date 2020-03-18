package fr.groupe4.clientprojet.model.message.enums;

import fr.groupe4.clientprojet.logger.Logger;
import fr.groupe4.clientprojet.logger.enums.LoggerOption;
import org.jetbrains.annotations.NotNull;

public enum MessageResource {
    MESSAGE_RESOURCE_HUMAN("HUMANRESOURCE"),
    MESSAGE_RESOURCE_PROJECT("PROJECT"),
    MESSAGE_RESOURCE_HUMAN_ALLOCATION("HUMANRESOURCE_ALLOCATION"),
    MESSAGE_RESOURCE_MATERIAL_ALLOCATION("MATERIALRESOURCE_ALLOCATION");

    private String msg;

    MessageResource(String msg) {
        this.msg = msg.toUpperCase();
    }

    /**
     * Transforme une String en son enum associée
     *
     * @param msg Message
     *
     * @return Enum associé
     */
    @NotNull
    public static MessageResource fromString(String msg) throws IllegalArgumentException {
        MessageResource[] vars = MessageResource.values();

        MessageResource result = null;

        for (MessageResource var : vars) {
            if (var.msg.equalsIgnoreCase(msg)) {
                result = var;
            }
        }

        if (result == null) {
            String errorMsg = "Pas d'enum provenant de la chaine '" + msg + "'";
            Logger.error(errorMsg, LoggerOption.LOG_FILE_ONLY);
            throw new IllegalArgumentException(errorMsg);
        }

        return result;
    }

    /**
     * Transforme une enum en String
     *
     * @return Message
     */
    @Override
    public String toString() {
        return msg;
    }
}

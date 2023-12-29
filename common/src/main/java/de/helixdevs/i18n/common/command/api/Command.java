package de.helixdevs.i18n.common.command.api;

public abstract class Command {

    private final String label;
    private final String permission;

    public Command(String label, String permission) {
        this.label = label;
        this.permission = permission;
    }

    public String getLabel() {
        return label;
    }

    public String getPermission() {
        return permission;
    }

    public abstract void execute(Sender sender, String[] arguments);
}

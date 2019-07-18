package me.nosmakos.borndisease.utilities;

public enum Permission {
    RESCUE_INHALER("rd.rescue_inhaler"),
    SYRINGE("rd.syringe"),
    IMMUNE("rd.immune"),
    ITEMS_OP("rd.items.*"),
    ITEMS_COMMAND("rd.items.cmd"),
    OP("rd.*");

    private String type;

    Permission(String type) {
        this.type = type;
    }

    public String get() {
        return this.type;
    }

}
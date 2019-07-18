package me.nosmakos.borndisease.utilities;

public enum CureItem {

    /* Usable Tablets or Inhalers*/
    RESCUE_INHALER(),
    NAPROXEN(),
    ASPIRIN(),
    IBUPROFEN(),

    /* Liquids */
    NITROGLYCERIN(),
    INSULIN(),
    ALTEPLASE(),

    /* Syringes */
    INSULIN_SYRINGE(),
    NITROGLYCERIN_SYRINGE(),
    ALTEPLASE_SYRINGE(),
    SYRINGE();

    CureItem() {
    }

    public boolean hasUses() {
        return this == RESCUE_INHALER || this == IBUPROFEN || this == ASPIRIN || this == NAPROXEN;
    }

    public int getUses() {
        return this == RESCUE_INHALER ? 120 : this == IBUPROFEN ? 10 : this == ASPIRIN ? 5 : this == NAPROXEN ? 10 : 0;
    }

    public boolean isLiquid() {
        return this == INSULIN || this == NITROGLYCERIN || this == ALTEPLASE;
    }

    public String get() {
        return "custom-items." + name().toLowerCase();
    }
}

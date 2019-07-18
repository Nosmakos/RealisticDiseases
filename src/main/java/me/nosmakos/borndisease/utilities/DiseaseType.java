package me.nosmakos.borndisease.utilities;

import java.util.Random;

public enum DiseaseType {

    DIABETES(CureItem.INSULIN_SYRINGE),
    ASTHMA(CureItem.RESCUE_INHALER),
    HEART_DISEASE(CureItem.NITROGLYCERIN_SYRINGE),
    FEVER(CureItem.NAPROXEN),
    HEADACHE(CureItem.ASPIRIN),
    DEHYDRATION(null), /* Water Bottle */
    HYPOTENSION(null), /* Water Bottle - Low Blood Pressure(Υπόταση) */
    STROKE(CureItem.ALTEPLASE_SYRINGE),
    MIGRAINE(CureItem.IBUPROFEN),
    CAUGH(CureItem.IBUPROFEN),

    NONE(null);

    private CureItem cureItem;

    DiseaseType(CureItem cureItem) {
        this.cureItem = cureItem;
    }

    public CureItem getCureItem() {
        return cureItem;
    }

    public DiseaseType getRandomDisease(){
        DiseaseType[] diseaseTypes = {FEVER, HEADACHE, HYPOTENSION, CAUGH, MIGRAINE, STROKE, DEHYDRATION};
        return diseaseTypes[new Random().nextInt(diseaseTypes.length)];
    }
}

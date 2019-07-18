package me.nosmakos.borndisease.users;

import me.nosmakos.borndisease.utilities.DiseaseType;

import java.util.List;

public class User {

    private List<DiseaseType> diseaseType;

    public User(List<DiseaseType> diseaseType) {
        this.diseaseType = diseaseType;
    }

    public List<DiseaseType> getDiseaseTypes() {
        return diseaseType;
    }

    public void addDiseaseType(DiseaseType diseaseType) {
        this.diseaseType.add(diseaseType);
    }

    public void removeDiseaseType(DiseaseType diseaseType) {
        this.diseaseType.remove(diseaseType);
    }

    public boolean hasDiseaseType(DiseaseType diseaseType) {
        return this.diseaseType.contains(diseaseType);
    }
}

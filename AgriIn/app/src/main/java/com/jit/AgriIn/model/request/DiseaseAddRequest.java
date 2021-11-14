package com.jit.AgriIn.model.request;

import java.io.File;

/**
 * @author crazyZhangxl on 2018/10/29.
 * Describe:
 */
public class DiseaseAddRequest {
    private String diseaseName;
    private String bigCategory;
    private String symptom;
    private String treatment;
    private File image;

    public String getBigCategory() {
        return bigCategory;
    }



    public String getDiseaseName() {
        return diseaseName;
    }


    public String getSymptom() {
        return symptom;
    }

    public String getTreatment() {
        return treatment;
    }

    public File getImage() {
        return image;
    }

    public DiseaseAddRequest(String diseaseName, String bigCategory,  String symptom, String treatment, File image) {
        this.diseaseName = diseaseName;
        this.bigCategory = bigCategory;
        this.symptom = symptom;
        this.treatment = treatment;
        this.image = image;
    }

    public DiseaseAddRequest(String diseaseName, String bigCategory, String symptom, String treatment) {
        this.diseaseName = diseaseName;
        this.bigCategory = bigCategory;
        this.symptom = symptom;
        this.treatment = treatment;
    }
}

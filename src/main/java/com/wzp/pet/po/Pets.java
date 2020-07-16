package com.wzp.pet.po;

public class Pets {
    private Integer pet_id;
    private Integer user_id;
    private String petsName;
    private String petsImage;
    private String pet_sex;
    private String pet_age;
    private String pet_species;
    private String pet_content;

    public int getPet_id() {
        return pet_id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPetsName() {
        return petsName;
    }

    public void setPetsName(String petsName) {
        this.petsName = petsName;
    }

    public String getPetsImage() {
        return petsImage;
    }

    public void setPetsImage(String petsImage) {
        this.petsImage = petsImage;
    }

    public String getPet_sex() {
        return pet_sex;
    }

    public void setPet_sex(String pet_sex) {
        this.pet_sex = pet_sex;
    }

    public String getPet_age() {
        return pet_age;
    }

    public void setPet_age(String pet_age) {
        this.pet_age = pet_age;
    }

    public String getPet_species() {
        return pet_species;
    }

    public void setPet_species(String pet_species) {
        this.pet_species = pet_species;
    }

    public String getPet_content() {
        return pet_content;
    }

    public void setPet_content(String pet_content) {
        this.pet_content = pet_content;
    }
}

package br.edu.ifpe.tads.pdm.appalertaaedes.model;

public class Focus {
    private String title;
    private String description;
    private String imageDirectory;

    public Focus(String title, String description, String imageDirectory) {
        this.title = title;
        this.description = description;
        this.imageDirectory = imageDirectory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }
}

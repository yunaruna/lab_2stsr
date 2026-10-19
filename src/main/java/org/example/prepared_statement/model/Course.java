package org.example.prepared_statement.model;

public class Course {

    private Long id;
    private String title;
    private Integer hours;
    private String description;
    private String difficultyLevel;

    public Course() {}

    public Course(String title, Integer hours, String description, String difficultyLevel) {
        this.title = title;
        this.hours = hours;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getHours() { return hours; }
    public void setHours(Integer hours) { this.hours = hours; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", hours=" + hours +
                ", description='" + description + '\'' +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                '}';
    }
}

package com.fate;

public class Student {

    private String name;

    private int age;

    private int stature;

    private String school;

    private Student(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.stature = builder.stature;
        this.school = builder.school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStature() {
        return stature;
    }

    public void setStature(int stature) {
        this.stature = stature;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public static class Builder{

        private String name;

        private int age;

        private int stature;

        private String school;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setStature(int stature) {
            this.stature = stature;
            return this;
        }

        public Builder setSchool(String school) {
            this.school = school;
            return this;
        }

        public Student build(){
            return new Student(this);
        }
    }
}

package com.passpoint;


import java.io.File;

public class Send {

    class Person {
//        public String firstName;
//        public String middleName;
//        public String lastName;
        public File name;
        public File signature;

        public Person(File name, File signature) {
//            this.firstName = firstName;
//            this.middleName = middleName;
//            this.lastName = lastName;
            this.name = name;
            this.signature = signature;
        }
    }

    public String IdDevice;
    public String place;
    public Person person;

    public Send(String idDevice, String place, File name, File signature) {
        this.IdDevice = idDevice;
        this.place = place;
        this.person = new Person(name, signature);
    }
}

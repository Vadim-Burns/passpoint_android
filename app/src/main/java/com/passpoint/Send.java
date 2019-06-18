package com.passpoint;


public class Send {

    class Person {
        public String firstName;
        public String middleName;
        public String lastName;
        public byte[] signature;

        public Person(String firstName, String middleName, String lastName, byte[] signature) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
            this.signature = signature;
        }
    }

    public String IdDevice;
    public String place;
    public Person person;

    public Send(String idDevice, String place, String firstName, String middleName, String lastName, byte[] sign) {
        this.IdDevice = idDevice;
        this.place = place;
        this.person = new Person(firstName, middleName, lastName, sign);
    }
}

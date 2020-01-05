public class ConsoleCodeIgnoreButDontDelete {
    /*
    THOUGHT WE COULD USE THIS CODE WHEN CREATING NEW DB FROM SCRATCH SO WE ALL HAVE SAME VERSION
    NOTE : ALL POSTGRES TABLE AND COLUMN NAMES MUST BE IN LOWERCASE

create table patients (
                          id SERIAL PRIMARY KEY,
                          firstname varchar(128) NOT NULL,
                          lastname varchar(128) NOT NULL,
                          phonenum varchar(32),
                          address varchar (128),
                          dob varchar(128),
                          email varchar(128)
);

create table doctors (
                          id SERIAL PRIMARY KEY,
                          firstname varchar(128) NOT NULL,
                          lastname varchar(128) NOT NULL,
                          pagernum varchar(32),
                          email varchar(128)
);

create table medicalcentre(
                            id SERIAL PRIMARY KEY,
                            name varchar(128),
                            address varchar(128)
);

create table linkpatientdoctormedcentre (
                                 id serial primary key,
                                 patientid int NOT NULL,
                                 doctorid int NOT NULL,
                                 medcentreid int NOT NULL
);



insert into medicalCentre (name, address) values('Fulham Medical Centre','Lillie Road SW6 7QR');
insert into medicalCentre (name, address) values('Ethod Medical Centre','Exhibition Road SW7 2BB');

insert into patients (firstname, lastname, phonenum, address, dob, email) values('Alejandra','Gutierrez','1212121212', 'RSM Cafe Prince Consort Road', '10/10/1999','alex@gmail.com');
insert into patients (firstname, lastname, phonenum, address, dob, email) values('Chloe','Orsini','1313131313', 'Library Queens Gate', '20/06/1999','chloe@gmail.com');
insert into patients (firstname, lastname, phonenum, address, dob, email) values('Jonas','Rauchlaus','3232323232', 'Tyger Tyger Picadilly Circus', '7/02/1999','jonas@gmail.com');

insert into doctors (firstname, lastname, pagernum, email) values('Joao','Pereira','3636363636', 'joao@gmail.com');
insert into doctors (firstname, lastname, pagernum, email) values('Charlie','Pitcairn','4545454545', 'charlie@gmail.com');

insert into linkPatientDoctorMedCentre (patientid, doctorid, medcentreid) values(1,1,1);
insert into linkPatientDoctorMedCentre (patientid, doctorid, medcentreid) values(2,1,1);
insert into linkPatientDoctorMedCentre (patientid, doctorid, medcentreid) values(3,2,2);

     */

}

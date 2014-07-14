CREATE TABLE CHECKING (ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL, USER_COMMENT VARCHAR(5000), ID_BOOKING INTEGER, ID_SCHOOL INTEGER, PRIMARY KEY (ID));
CREATE TABLE SCHOOL (ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL, NAME VARCHAR(255), PASSWORD VARCHAR(512), PRIMARY KEY (ID));
CREATE TABLE BOOKING (ID INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL, DURATION INTEGER, START_DATE TIMESTAMP, USER_COMMENT VARCHAR(5000), ID_SCHOOL INTEGER, PRIMARY KEY (ID));
ALTER TABLE CHECKING ADD CONSTRAINT CHECKINGID_BOOKING FOREIGN KEY (ID_BOOKING) REFERENCES BOOKING (ID);
ALTER TABLE CHECKING ADD CONSTRAINT CHECKING_ID_SCHOOL FOREIGN KEY (ID_SCHOOL) REFERENCES SCHOOL (ID);
ALTER TABLE BOOKING ADD CONSTRAINT BOOKING_ID_SCHOOL FOREIGN KEY (ID_SCHOOL) REFERENCES SCHOOL (ID);
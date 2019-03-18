/*Assignment3 
 * CSC 370 2019 Winter
 * Zimeng Ming/Yunkun Li
 * V00844078/V00799473
 */


/* Question1 */
CREATE TABLE Classes(
	class varchar(30),
	ship_type varchar(30),
	country varchar(30),
	numGuns int,
	bore int,
	displacement int
);

CREATE TABLE Ships(
	name varchar(30) ,
	class varchar(30) ,
	launched int
);

CREATE TABLE Battles(
	name varchar(30),
	date_fought DATE
);

CREATE TABLE Outcomes(
	ship varchar(30) ,
	battle varchar(30),
	battle_result varchar(30)
);


INSERT INTO Classes VALUES('Bismarck','bb','Germany',8,15,42000);
INSERT INTO Classes VALUES('Kongo','bc','Japan',8,14,32000);
INSERT INTO Classes VALUES('North Carolina','bb','USA',9,16,37000);
INSERT INTO Classes VALUES('Renown','bc','Gt. Britain',6,15,32000);
INSERT INTO Classes VALUES('Revenge','bb','Gt. Britain',8,15,29000);
INSERT INTO Classes VALUES('Tennessee','bb','USA',12,14,32000);
INSERT INTO Classes VALUES('Yamato','bb','Japan',9,18,65000);

INSERT INTO Ships VALUES('California','Tennessee',1921);
INSERT INTO Ships VALUES('Haruna','Kongo',1915);
INSERT INTO Ships VALUES('Hiei','Kongo',1914);
INSERT INTO Ships VALUES('Iowa','Iowa',1943);
INSERT INTO Ships VALUES('Kirishima','Kongo',1914);
INSERT INTO Ships VALUES('Kongo','Kongo',1913);
INSERT INTO Ships VALUES('Missouri','Iowa',1944);
INSERT INTO Ships VALUES('Musashi','Yamato',1942);
INSERT INTO Ships VALUES('New Jersey','Iowa',1943);
INSERT INTO Ships VALUES('North Carolina','North Carolina',1941);
INSERT INTO Ships VALUES('Ramilles','Revenge',1917);
INSERT INTO Ships VALUES('Renown','Renown',1916);
INSERT INTO Ships VALUES('Repulse','Renown',1916);
INSERT INTO Ships VALUES('Resolution','Revenge',1916);
INSERT INTO Ships VALUES('Revenge','Revenge',1916);
INSERT INTO Ships VALUES('Royal Oak','Revenge',1916);
INSERT INTO Ships VALUES('Royal Sovereign','Revenge',1916);
INSERT INTO Ships VALUES('Tennessee','Tennessee',1920);
INSERT INTO Ships VALUES('Washington','North Carolina',1941);
INSERT INTO Ships VALUES('Wisconsin','Iowa',1944);
INSERT INTO Ships VALUES('Yamato','Yamato',1941);

INSERT INTO Battles VALUES('North Atlantic','27-May-1941');
INSERT INTO Battles VALUES('Guadalcanal','15-Nov-1942');
INSERT INTO Battles VALUES('North Cape','26-Dec-1943');
INSERT INTO Battles VALUES('Surigao Strait','25-Oct-1944');

INSERT INTO Outcomes VALUES('Bismarck','North Atlantic', 'sunk');
INSERT INTO Outcomes VALUES('California','Surigao Strait', 'ok');
INSERT INTO Outcomes VALUES('Duke of York','North Cape', 'ok');
INSERT INTO Outcomes VALUES('Fuso','Surigao Strait', 'sunk');
INSERT INTO Outcomes VALUES('Hood','North Atlantic', 'sunk');
INSERT INTO Outcomes VALUES('King George V','North Atlantic', 'ok');
INSERT INTO Outcomes VALUES('Kirishima','Guadalcanal', 'sunk');
INSERT INTO Outcomes VALUES('Prince of Wales','North Atlantic', 'damaged');
INSERT INTO Outcomes VALUES('Rodney','North Atlantic', 'ok');
INSERT INTO Outcomes VALUES('Scharnhorst','North Cape', 'sunk');
INSERT INTO Outcomes VALUES('South Dakota','Guadalcanal', 'ok');
INSERT INTO Outcomes VALUES('West Virginia','Surigao Strait', 'ok');
INSERT INTO Outcomes VALUES('Yamashiro','Surigao Strait', 'sunk');

/*test line
	SELECT *
	FROM Classes

DELETE FROM SHIPS;
DELETE FROM Outcomes;
DELETE FROM BATTLES ;
DELETE FROM CLASSES;

Drop table Ships
Drop table Outcomes;
Drop table BATTLES;
Drop table CLASSES;
*/

/*-----------------------------------------------------------------------------*/
/*Question2*/
/*T1:*/
SELECT name
FROM CLASSES  JOIN SHIPS USING (class) 
WHERE Displacement >35000 AND Launched >=1921 ;

/*T2:  */
CREATE VIEW ships_Detail AS 
SELECT * FROM Classes JOIN Ships USING(class);

SELECT ship AS name,displacement,numGuns
FROM Outcomes LEFT OUTER JOIN ships_Detail ON Outcomes.ship=Ships_Detail.name
WHERE battle = 'Guadalcanal' ;

DROP VIEW ships_Detail
/*T3:   */
SELECT name FROM SHIPS  UNION SELECT ship FROM Outcomes ;

/*T4:  */
SELECT a1.country
FROM Classes a1,Classes a2
WHERE a1.ship_type='bb' AND a2.ship_type='bc' AND a1.country=a2.country;

/*T5:  */
CREATE VIEW outcomesWithDate AS 
	SELECT Outcomes.ship, Outcomes.battle, Outcomes.battle_result, Battles.date_fought
	FROM Outcomes JOIN Battles ON Outcomes.battle=Battles.name;

SELECT ship
FROM outcomesWithDate
GROUP BY ship
HAVING(count(battle)>2);

DROP VIEW outcomesWithDate;

/*T6   */
SELECT DISTINCT country
FROM Ships JOIN Classes ON Ships.class=Classes.class
WHERE numGuns=(SELECT max(numGuns)FROM Ships JOIN Classes ON Ships.class=Classes.class);

/*T7    */ 
CREATE VIEW shipClasses as
	SELECT Ships.name, Classes.numGuns,Classes.bore, Classes.class
	FROM Ships JOIN Classes ON Ships.class=Classes.class ;

CREATE VIEW boreOrder AS 
	SELECT bore,MAX(numGuns) AS maxNumGuns FROM shipClasses GROUP BY bore;
	
SELECT s1.name 
FROM shipClasses s1, boreorder x1
WHERE s1.bore=x1.bore AND s1.numGuns=x1.maxNumGuns;
/*
DROP VIEW shipSunk
DROP VIEW boreOrder
*/

/*T8    */

/*This is the view of the total sunk ships*/
CREATE VIEW shipSunk AS 
	SELECT ship AS name
	FROM Outcomes WHERE battle_result='sunk';

/*Create a view of all the classed that have 3 or more ships*/
CREATE VIEW shipClass2 AS 
		 SELECT class
		 FROM Ships LEFT OUTER JOIN  Classes USING(class)
		 GROUP BY class 
		 HAVING(count(name)>=3);

/*create a view of all the ships of the class that have 3 or more ships*/
CREATE VIEW shipClass3 AS 
	SELECT Ships.name,Ships.class 
	FROM Ships JOIN shipClass2 ON Ships.class=shipClass2.class;

SELECT class,count(name)
FROM shipClass2 LEFT OUTER JOIN (SELECT SC.name,SC.class FROM shipClass3 SC, shipSunk SS WHERE SC.name=SS.name) USING (class)
GROUP BY class;

/*
DROP VIEW shipClass2;
DROP VIEW shipClass3;
*/

/*---------------------Question3-----------------*/
/*T1   */
INSERT INTO Classes VALUES('Vittorio Veneto','bb','Italy',NULL,15,41000);
INSERT INTO Ships VALUES ('Vittorio Veneto','Vittorio Veneto',1940);
INSERT INTO Ships VALUES ('Italia','Vittorio Veneto',1940);
INSERT INTO Ships VALUES ('Roma','Vittorio Veneto',1942);



/*T2   */

DELETE FROM Classes WHERE NOT EXISTS (
	SELECT class 
	FROM shipClass2
	WHERE Classes.class=shipClass2.class) 
	
	
/*T3  */

/* for the using for the following statement, I do the Question4 first, so the constraint
 * is bulid before this question. In order to avoid it, First Drop the constraint first
 */
/*
ALTER TABLE Classes DROP CONSTRAINT check_bore;
*/
	
UPDATE Classes
SET bore=bore*2.5, displacement=displacement/1.1;
	
	
	
/*---------------------Question4-----------------*/
/*T1   */
ALTER TABLE Classes ADD CONSTRAINT classes_pk PRIMARY KEY (class);
CREATE TABLE Exceptions(
  row_id ROWID,
  owner VARCHAR2(30),
  table_name VARCHAR2(30),
  constraint VARCHAR2(30)
);

ALTER TABLE Ships ADD CONSTRAINT ship_to_classes_fk FOREIGN KEY(class)
REFERENCES Classes(class)
  EXCEPTIONS INTO Exceptions;

SELECT Ships.*, constraint
FROM Ships, Exceptions
WHERE Ships.rowid=Exceptions.row_id;

DELETE FROM Ships
WHERE class IN(
  SELECT class FROM Ships, Exceptions
  WHERE Ships.rowid=Exceptions.row_id
);

ALTER TABLE Ships ADD CONSTRAINT ship_to_classes_fk FOREIGN KEY(class)
REFERENCES Classes(class);

DELETE FROM Exceptions;

/*T2  */
ALTER TABLE Battles ADD CONSTRAINT battles_pk PRIMARY KEY(name);
ALTER TABLE Outcomes ADD CONSTRAINT outcome_to_battles_fk FOREIGN KEY(battle)
REFERENCES Battles(name)
  EXCEPTIONS INTO Exceptions;

SELECT Outcomes.*, constraint
FROM Outcomes, Exceptions
WHERE Outcomes.rowid=Exceptions.row_id;

DELETE FROM Outcomes
WHERE battle IN(
  SELECT battle FROM Outcomes, Exceptions
  WHERE Outcomes.rowid=Exceptions.row_id
);


/*test line 
 * DROP TABLE Battles;
 * DROP TABLE Outcomes;
 */



/*T3   */
ALTER TABLE Ships ADD CONSTRAINT name_pk PRIMARY KEY(name);
ALTER TABLE Outcomes ADD CONSTRAINT outcome_to_ships_fk FOREIGN KEY(ship) REFERENCES Ships(name) EXCEPTIONS INTO Exceptions;

SELECT Outcomes.*, constraint
FROM Outcomes, Exceptions
WHERE Outcomes.rowid=Exceptions.row_id;

DELETE FROM Outcomes
WHERE ship IN(
  SELECT ship FROM Outcomes, Exceptions
  WHERE Outcomes.rowid=Exceptions.row_id
);

ALTER TABLE Outcomes ADD CONSTRAINT outcome_to_ships_fk FOREIGN KEY(ship) REFERENCES Ships(name);

SELECT *
FROM Outcomes


/*T4  */
ALTER TABLE Classes ADD CONSTRAINT check_bore CHECK (bore <=16) ;


/* test line
 * ALTER TABLE Classes ADD CONSTRAINT check_bore CHECK (bore <16);
   ALTER TABLE Classes DROP CONSTRAINT check_bore	
 
   INSERT INTO Classes VALUES('Bismarcasdasdsadk','bb','Germany',8,20,42000);

*/

/* T5  */

ALTER TABLE Classes ADD CONSTRAINT check_guns CHECK(NumGuns <=9 OR bore<=14) ;

/*test line
 * INSERT INTO Classes VALUES('Bismarcasdasdsadk','bb','Germany',12,20,42000);
 */

/*T6   */
CREATE OR REPLACE VIEW OutcomesView AS SELECT ship, battle, battle_result
FROM Outcomes O
WHERE NOT EXISTS (
SELECT *
FROM Ships S, Battles B
WHERE S.name=O.ship AND O.battle=B.name AND
S.launched > TO_NUMBER(TO_CHAR(B.date_fought, 'yyyy')) 
)WITH CHECK OPTION;

SELECT * FROM OutcomesView
DROP VIEW OutcomesView;

/* From the Hint, the check statement is not valid because 'Musashi' is not exist in Ships
 * anymore becuase of the previous questions, so I change the test ship to Roma
 
INSERT INTO OutcomesView (ship, battle, battle_result) VALUES('Roma', 'North Atlantic','ok');
*/

/*SELECT *
 FROM Ships  
*/

/*T7  */
CREATE OR REPLACE VIEW ShipsView AS SELECT name, class, launched
FROM Ships O
WHERE NOT EXISTS (
SELECT *
FROM Ships S
WHERE S.name=S.class AND O.class=S.class AND
S.launched >  O.launched
)WITH CHECK OPTION;

/* Test line
SELECT * FROM ShipsView ;

INSERT INTO ShipsView(name,class,launched) VALUES ('aa','Kongo',1910);
DELETE FROM Ships WHERE name='aa'
*/


/*T8   */
CREATE OR REPLACE VIEW BattleView AS SELECT ship,battle, battle_result
FROM Outcomes O
WHERE NOT EXISTS (
SELECT *
FROM Outcomes I
WHERE EXISTS(
SELECT *
FROM Ships S, Battles B, Ships K, Battles P
WHERE S.name=I.ship AND I.battle=B.name AND I.battle_result ='sunk' AND S.name=K.name AND K.name=O.ship  AND O.battle=P.name
AND P.date_fought> B.date_fought ))
WITH CHECK OPTION;


/* Test Line
DROP VIEW BattleView;
DELETE FROM Outcomes WHERE ship='Kirishima';
SELECT * FROM BattleView;
SELECT * FROM Outcomes;
INSERT INTO BattleView VALUES ('Kirishima','Surigao Strait','ok');
*/


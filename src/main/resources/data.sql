INSERT INTO USER ( USER_NAME , USER_FULL_NAME )
VALUES
       ( 'Alex', 'Full Alex') ,
       ( 'Petr', 'Full Petr') ,
       ( 'Max', 'Full Max') ,
       ( 'Ivan', 'Full Ivan');

INSERT INTO PROJECT ( PROJECT_ID , TITLE , URL , README , COMMITS )
VALUES
    ( 1, 'Project 1', 'http://go.com/project1', 'Describe project 1', 2) ,
    ( 2, 'Project 2', 'http://go.com/project2', 'Describe project 2', 1) ,
    ( 3, 'Project 3', 'http://go.com/project3', 'Describe project 3', 5) ,
    ( 4, 'Project 4', 'http://go.com/project4', 'Describe project 4', 20) ;

INSERT INTO USER_PROJECT_RELATION ( PROJECT_ID , USER_NAME )
VALUES
       ( 0, 'Bob'),
       ( 1, 'Alex'),
       ( 1, 'Petr'),
       ( 1, 'Max'),
       ( 2, 'Max'),
       ( 3, 'Petr'),
       ( 4, 'Alex');




CREATE TABLE staff(
  id INT PRIMARY KEY AUTO_INCREMENT,
  fio VARCHAR(255),
  department INT,
  personalNumber VARCHAR(255),
  workNumber VARCHAR(255),
  homeNumber VARCHAR(255)
  );

CREATE TABLE departments(
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  phone VARCHAR(255)
);
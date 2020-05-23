<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <title>Добавление сотрудника</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Добавление сотрудника</h1>
</div>
<div>
    <form class="add_form" method="post" action="user">
        <input type="name" class="form-control" id="fio" placeholder="Введите ФИО" name="fio">
        <label>Какой отдел?</label>
        <select class="form-control" id="classSelect" name="country">
                <option>Отдел разработки</option>
                <option>Отдел тестирования</option>
                <option>Отдел продаж</option>
                <option>Отдел продаж</option>
            </select>
        &emsp;
        <input type="phone" class="form-control" id="personalNumber" placeholder="Введите личный телефон" name="personalNumber">
        &emsp;
        <input type="phone" class="form-control" id="workNumber" placeholder="Введите рабочий телефон" name="workNumber">
        &emsp;
        <input type="phone" class="form-control" id="homeNumber" placeholder="Введите домашний телефон" name="homeNumber">
        <button type="submit" class="btn btn-primary">Отравить</button>
    </form>
</div>
</body>
</html>
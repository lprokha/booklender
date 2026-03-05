<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Booklender — Сотрудники</title>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
</head>

<body>

<div class="container py-5">

<h1 class="mb-4">Сотрудники</h1>

<table class="table table-striped">

<thead>
<tr>
<th>ID</th>
<th>Имя</th>
<th>Email</th>
<th>Взято книг</th>
</tr>
</thead>

<tbody>

<#list employees as employee>

<tr>

<td>${employee.id}</td>

<td>${employee.fullName}</td>

<td>${employee.email}</td>

<td>
<#if employee.currentBooks??>
${employee.currentBooks?size}
<#else>
0
</#if>
</td>

</tr>

</#list>

</tbody>

</table>

<a href="/books" class="btn btn-outline-secondary">
<i class="bi bi-arrow-left me-2"></i>К книгам
</a>

</div>

</body>
</html>
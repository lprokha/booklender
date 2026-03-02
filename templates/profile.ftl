<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Booklender — Профиль</title>

  <link rel="preconnect" href="https://fonts.googleapis.com" />
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
  <link
    href="https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,100..900;1,100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
    rel="stylesheet"
  />

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css" />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" />

  <link rel="stylesheet" href="css/style.css" />
</head>

<body>
<header>
  <nav class="navbar navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
    <div class="container-xxl">
      <a class="navbar-brand fw-semibold" href="/books">Booklender</a>

      <button
        class="navbar-toggler text-white fs-3 bg-orange lh-1"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <i class="bi bi-list-nested"></i>
      </button>

      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mx-auto">
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/books">Книги</a>
          </li>
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/profile">Профиль</a>
          </li>
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/login">Вход</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</header>

<main class="bg-light">
  <div class="container-xxl py-5">
    <div class="d-flex align-items-center justify-content-between mb-4">
      <h1 class="fw-semibold m-0">Профиль</h1>
      <a href="/books" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left me-2"></i>К книгам
      </a>
    </div>

    <#-- Если пользователь реально авторизован -->
    <#if isReal?? && isReal>
      <div class="alert alert-success">
        <i class="bi bi-check2 me-2"></i>Успешный вход
      </div>

      <form method="post" action="/logout" class="mb-4">
        <button class="btn btn-outline-danger" type="submit">
          <i class="bi bi-box-arrow-right me-2"></i>Выйти
        </button>
      </form>

    <#-- Если страница открыта напрямую -->
    <#else>
      <div class="alert alert-warning">
        <i class="bi bi-info-circle me-2"></i>
        Страница открыта напрямую. Показаны демонстрационные данные.
      </div>
    </#if>

    <div class="card border-0 shadow-sm">
      <div class="card-body p-4">
        <div class="mb-2">
          <span class="text-muted">Email:</span>
          <strong>${email}</strong>
        </div>
        <div>
          <span class="text-muted">Имя:</span>
          <strong>${fullName}</strong>
        </div>
      </div>
    </div>

  </div>
</main>

<footer class="footer bg-dark">
  <div class="container-xxl py-4">
    <div class="text-white text-opacity-50 small">2026 Booklender</div>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
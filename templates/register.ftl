<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Booklender — Регистрация</title>

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
            <a class="nav-link active" href="/book">О книге</a>
          </li>
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/employee">Сотрудник</a>
          </li>
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/employees">Сотрудники</a>
          </li>
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/register">Регистрация</a>
          </li>
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/login">Вход</a>
          </li>
          <li class="nav-item mx-lg-3">
            <a class="nav-link active" href="/profile">Профиль</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</header>

<main class="bg-light">
  <div class="container-xxl py-5">
    <div class="d-flex align-items-center justify-content-between mb-4">
      <h1 class="fw-semibold m-0">Регистрация</h1>
      <a href="/books" class="btn btn-outline-secondary">
        <i class="bi bi-arrow-left me-2"></i>К книгам
      </a>
    </div>

    <#if flashShown?? && flashShown>
      <#if flashSuccess?? && flashSuccess>
        <div class="alert alert-success">
          <i class="bi bi-check2-circle me-2"></i>${flashMessage}
        </div>
      <#else>
        <div class="alert alert-danger">
          <i class="bi bi-exclamation-triangle me-2"></i>${flashMessage}
        </div>
      </#if>
    </#if>

    <div class="card border-0 shadow-sm">
      <div class="card-body p-4">
        <form method="post" action="/register" class="row g-3">
          <div class="col-12 col-lg-6">
            <label class="form-label">Email (идентификатор)</label>
            <input class="form-control" name="email" type="text" />
          </div>

          <div class="col-12 col-lg-6">
            <label class="form-label">Имя</label>
            <input class="form-control" name="fullName" type="text" />
          </div>

          <div class="col-12 col-lg-6">
            <label class="form-label">Пароль</label>
            <input class="form-control" name="password" type="password" />
          </div>

          <div class="col-12">
            <button class="btn bg-orange text-white" type="submit">
              <i class="bi bi-person-plus me-2"></i>Зарегистрироваться
            </button>
            <a class="btn btn-link" href="/login">Уже есть аккаунт? Войти</a>
          </div>
        </form>
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
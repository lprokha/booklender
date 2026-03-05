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

    <#if isReal?? && isReal>

      <div class="alert alert-success">
        <i class="bi bi-check2 me-2"></i>Вы вошли в систему
      </div>

      <form method="post" action="/logout" class="mb-4">
        <button class="btn btn-outline-danger" type="submit">
          <i class="bi bi-box-arrow-right me-2"></i>Выйти
        </button>
      </form>

      <div class="card border-0 shadow-sm mb-4">
        <div class="card-body p-4">
          <div class="mb-2">
            <span class="text-muted">Email:</span>
            <strong>${email}</strong>
          </div>

          <div class="mb-2">
            <span class="text-muted">Имя:</span>
            <strong>${fullName}</strong>
          </div>

          <div>
            <span class="text-muted">Можно взять ещё:</span>
            <strong>${left}</strong>
          </div>
        </div>
      </div>

      <h3 class="mb-3">Текущие книги</h3>

      <#if currentBooks?? && currentBooks?size gt 0>
        <ul class="list-group mb-5">

          <#list currentBooks as book>
            <li class="list-group-item d-flex justify-content-between align-items-center">

              <div>
                <strong>${book.title}</strong><br>
                <span class="text-muted">${book.author}</span>
              </div>

              <a href="/book?id=${book.id}" class="btn btn-sm btn-outline-primary">
                Открыть
              </a>

            </li>
          </#list>

        </ul>
      <#else>
        <p class="text-muted mb-5">У вас нет взятых книг</p>
      </#if>


      <h3 class="mb-3">История книг</h3>

      <#if pastBooks?? && pastBooks?size gt 0>
        <ul class="list-group">

          <#list pastBooks as book>
            <li class="list-group-item">
              <strong>${book.title}</strong>
              <span class="text-muted">— ${book.author}</span>
            </li>
          </#list>

        </ul>
      <#else>
        <p class="text-muted">Вы ещё не возвращали книги</p>
      </#if>


    <#else>

      <div class="alert alert-warning">
        <i class="bi bi-info-circle me-2"></i>
        Чтобы увидеть свой профиль, необходимо войти в систему.
      </div>

      <a href="/login" class="btn btn-primary">
        Перейти к входу
      </a>

    </#if>

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
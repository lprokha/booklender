<!DOCTYPE html>
<html lang="ru">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Booklender — ${book.title}</title>

    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,100..900;1,100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
      rel="stylesheet"
    />

    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
    />

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

        <a href="/books" class="btn btn-outline-secondary mb-4">
          <i class="bi bi-arrow-left me-2"></i>Назад к списку
        </a>

        <div class="card border-0 shadow-sm">
          <div class="row g-0">
            <div class="col-12 col-lg-5">
              <img
                src="${book.imagePath}"
                class="w-100 h-100 object-fit-cover book-cover-big"
                alt="${book.title}"
              />
            </div>

            <div class="col-12 col-lg-7">
              <div class="card-body p-4">
                <h1 class="fw-semibold mb-2">${book.title}</h1>
                <p class="text-muted mb-3">Автор: ${book.author}</p>

                <#if book.issuedToName??>
                  <div class="alert alert-warning mb-0">
                    <i class="bi bi-person-check me-2"></i>
                    Книга выдана: <strong>${book.issuedToName}</strong>
                  </div>
                <#else>
                  <div class="alert alert-success mb-0">
                    <i class="bi bi-check2 me-2"></i>
                    Книга свободна
                  </div>
                </#if>
              </div>
            </div>
          </div>
        </div>

      </div>
    </main>

    <footer class="footer bg-dark">
      <div class="container-xxl py-4">
        <div class="text-white text-opacity-50 small">
          2026 Booklender
        </div>
      </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
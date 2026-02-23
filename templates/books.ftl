<!DOCTYPE html>
<html lang="ru">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Booklender — Книги</title>

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
            </ul>

            <div class="text-white-50 small d-none d-lg-block">
              Всего: ${books?size}
            </div>
          </div>
        </div>
      </nav>
    </header>

    <main class="bg-light">
      <div class="container-xxl py-5">
        <div class="d-flex align-items-center justify-content-between mb-4">
          <h1 class="fw-semibold m-0">Список книг</h1>
          <div class="text-muted">Всего: ${books?size}</div>
        </div>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4">
          <#list books as book>
            <div class="col mb-3">
              <div class="card h-100 border-0 shadow-sm position-relative">
                <img
                  src="${book.imagePath}"
                  class="card-img-top book-cover"
                  alt="${book.title}"
                />

                <div class="card-body">
                  <p class="fw-medium fs-6 mb-1">${book.title}</p>
                  <p class="text-muted mb-0">${book.author}</p>
                </div>

                <div class="card-footer bg-transparent border-0 pt-0 pb-3">
                  <#if book.issuedToName??>
                    <p class="mb-0 text-warning">
                      <i class="bi bi-person-check me-1"></i> Выдана: ${book.issuedToName}
                    </p>
                  <#else>
                    <p class="mb-0 text-success">
                      <i class="bi bi-check2 me-1"></i> Свободна
                    </p>
                  </#if>
                </div>

                <a href="/book" class="stretched-link"></a>
              </div>
            </div>
          </#list>
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
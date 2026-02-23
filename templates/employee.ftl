<!DOCTYPE html>
<html lang="ru">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Booklender — Сотрудник</title>

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
            </ul>
          </div>
        </div>
      </nav>
    </header>

    <main class="bg-light">
      <div class="container-xxl py-5">

        <div class="d-flex align-items-center justify-content-between mb-4">
          <div>
            <h1 class="fw-semibold m-0">${employee.fullName}</h1>
            <div class="text-muted">ID: ${employee.id}</div>
          </div>
          <a href="/books" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left me-2"></i>К списку книг
          </a>
        </div>

        <div class="row g-4">
          <div class="col-12 col-lg-6">
            <div class="card border-0 shadow-sm h-100">
              <div class="card-body">
                <h2 class="h5 fw-semibold mb-3">
                  <i class="bi bi-book me-2"></i>Книги на руках
                </h2>

                <#if employee.currentBooks?size == 0>
                  <div class="text-muted">Сейчас нет книг на руках</div>
                <#else>
                  <ul class="list-group list-group-flush">
                    <#list employee.currentBooks as book>
                      <li class="list-group-item d-flex justify-content-between align-items-start">
                        <div>
                          <div class="fw-medium">${book.title}</div>
                          <div class="text-muted small">${book.author}</div>
                        </div>
                        <span class="badge text-bg-warning">на руках</span>
                      </li>
                    </#list>
                  </ul>
                </#if>

              </div>
            </div>
          </div>

          <div class="col-12 col-lg-6">
            <div class="card border-0 shadow-sm h-100">
              <div class="card-body">
                <h2 class="h5 fw-semibold mb-3">
                  <i class="bi bi-clock-history me-2"></i>Ранее брал(а)
                </h2>

                <#if employee.pastBooks?size == 0>
                  <div class="text-muted">Пока нет истории</div>
                <#else>
                  <ul class="list-group list-group-flush">
                    <#list employee.pastBooks as book>
                      <li class="list-group-item">
                        <div class="fw-medium">${book.title}</div>
                        <div class="text-muted small">${book.author}</div>
                      </li>
                    </#list>
                  </ul>
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
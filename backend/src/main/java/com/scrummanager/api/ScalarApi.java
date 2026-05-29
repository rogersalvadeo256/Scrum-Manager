package com.scrummanager.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScalarApi {

    @GetMapping(value = "/docs", produces = MediaType.TEXT_HTML_VALUE)
    public String scalarDocs() {
        return """
                <!doctype html>
                <html lang="pt-BR">
                  <head>
                    <title>Scrum Manager – External API Reference</title>
                    <meta charset="utf-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1" />
                  </head>
                  <body>
                    <script
                      id="api-reference"
                      data-url="/v3/api-docs/external"
                      data-configuration='{"theme":"purple","layout":"modern"}'
                      src="https://cdn.jsdelivr.net/npm/@scalar/api-reference"></script>
                  </body>
                </html>
                """;
    }
}

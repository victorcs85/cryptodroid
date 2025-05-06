pipeline {
    agent any

    environment {
        GRADLE_OPTS = "-Dorg.gradle.daemon=false"
        REPORT_DIR = "reports"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Spotless') {
            steps {
                sh './gradlew spotlessCheck || ./gradlew spotlessApply'
            }
        }

        stage('Detekt') {
            steps {
                sh './gradlew detekt'
            }
        }

        stage('Unit Tests') {
            steps {
                sh './gradlew testDebugUnitTest'
            }
        }

        stage('UI Tests') {
            steps {
                // Requer emulador ou dispositivo conectado no ambiente
                catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                    sh './gradlew connectedDebugAndroidTest'
                }
            }
        }

        stage('Gerar Relatórios') {
            steps {
                sh '''
                    rm -rf $REPORT_DIR
                    mkdir -p $REPORT_DIR

                    cp -r ./build/reports/tests/testDebugUnitTest $REPORT_DIR/unit_tests || true
                    cp -r ./build/reports/androidTests/connected $REPORT_DIR/instrumented_tests || true
                    cp ./build/reports/lint-results-debug.html $REPORT_DIR/lint.html || true
                    cp ./build/reports/detekt/detekt-report.html $REPORT_DIR/detekt.html || true
                '''
                script {
                    writeFile file: "$REPORT_DIR/index.html", text: generateHtml()
                }
            }
        }

        stage('Publicar Artefatos') {
            steps {
                archiveArtifacts artifacts: "$REPORT_DIR/**", allowEmptyArchive: true
            }
        }
    }
}

def generateHtml() {
    return """
    <!DOCTYPE html>
    <html lang="pt-BR">
    <head>
        <meta charset="UTF-8">
        <title>Relatórios de Qualidade</title>
        <style>
            body { font-family: Arial, sans-serif; background: #f8f9fa; padding: 2rem; }
            h1 { color: #343a40; }
            ul { list-style: none; padding: 0; }
            li { margin: 1rem 0; }
            a {
                text-decoration: none;
                color: #007bff;
                font-size: 1.2rem;
                padding: 0.5rem 1rem;
                background-color: #e9ecef;
                border-radius: 5px;
                display: inline-block;
            }
            a:hover {
                background-color: #ced4da;
            }
        </style>
    </head>
    <body>
        <h1>Relatórios de Testes e Análises</h1>
        <ul>
            <li><a href="unit_tests/index.html" target="_blank">✅ Testes Unitários</a></li>
            <li><a href="instrumented_tests/index.html" target="_blank">📱 Testes Instrumentados</a></li>
            <li><a href="lint.html" target="_blank">🧹 Lint</a></li>
            <li><a href="detekt.html" target="_blank">🔍 Detekt</a></li>
        </ul>
    </body>
    </html>
    """
}

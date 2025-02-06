pipeline {
    agent any

    environment {
        VENV_DIR = 'venv'
        APP_PORT = '5000'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/your-username/azure-tiger.git',
                    credentialsId: 'github-token'
            }
        }

        stage('Setup Environment') {
            steps {
                echo 'Setting up the virtual environment...'
                sh '''
                    if [ -d "$VENV_DIR" ]; then
                        rm -rf $VENV_DIR
                    fi
                    python3 -m venv $VENV_DIR
                    . $VENV_DIR/bin/activate
                    pip install --upgrade pip
                    pip install -r requirements.txt
                '''
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh '''
                    . $VENV_DIR/bin/activate
                    python -m unittest discover -s tests
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                // Add deployment logic here
            }
        }

        stage('Run') {
            steps {
                echo 'Starting the application...'
                sh '''
                    . $VENV_DIR/bin/activate
                    nohup python app.py &
                '''
            }
        }
    }

    post {
        success {
            echo 'Application deployed and running successfully.'
        }
        failure {
            echo 'Pipeline encountered errors. Please review the logs.'
        }
    }
}

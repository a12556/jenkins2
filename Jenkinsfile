pipeline {
	agent { dockerfile true }
    stages {
        stage('Test') {
            steps {
                echo 'Hi'
				sh 'ls'
                sh 'curl localhost:80'
            }
        }
    }
	}
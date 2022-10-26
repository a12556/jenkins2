pipeline {
	agent {	
		dockerfile {
            filename 'Dockerfile'
            alwaysPull false
        }
	}
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
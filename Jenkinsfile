pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
        }
    }
	stages {
		stage('Test') {
			steps {
				sh 'echo "Hello World"'
				sh 'ls -la'
			  }
		  }
		}
}
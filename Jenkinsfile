pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
			args "-u root"
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
pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
			label "docker-nodes"
			args "-v /tmp:/tmp"
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
pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
            additionalBuildArgs "--build-arg UID=113"
			alwaysPull true
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
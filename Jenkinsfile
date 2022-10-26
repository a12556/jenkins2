pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
            additionalBuildArgs "--build-arg UID=113"
        }
    }

stages {
    stage('Test') {
          agent {
              docker {
                  image 'node:14-alpine'
                  alwaysPull true
              }
          }
          steps {
              sh 'node --version'
          }
      }
    }
}
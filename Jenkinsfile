pipeline {
    agent { docker { image 'node:latest' } }
    stages {
        stage('build Backend') {
            steps {
				sh 'echo "this is build backend"'
				sh '''
					cd backend
					npm install
					npm run build
				'''	
				sh 'echo "finish build backend"'
            }
        }
        stage('build frontend') {
            steps {
				sh 'echo "this is build frontend"'
				sh '''
					cd frontend
					npm install
					npm run test
				'''	
				sh 'echo "finish build frontend"'
			}

        }
    }
}
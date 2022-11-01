pipeline {
    environment {
        registryCredential = "dockerhub_thanhtran"
		registry = "thanh145/demo1dockerfile"
        dockerImage = ''
		web = 'web_httpd'
		IMAGE_TAG='1'
    }
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', changelog: false, credentialsId: 'GitHubThanh', poll: false, url: 'https://github.com/a12556/jenkins2.git'
            }
        }
        stage('Building') {
            steps {
                script {
                        dockerImage = docker.build registry + ":$BUILD_NUMBER" 
                }
            }
        }
        stage('Publish') {
            steps {
				script {
					docker.withRegistry('', registryCredential){
						dockerImage.push()  
						}
			}
			}
        }
		stage('deploy web to server') {
			steps {         
				script {
                    sh "docker run -d --restart always --name httpd_web -p 3000:80 $registry:$BUILD_NUMBER"
                }
			}
        }
   
        stage('Remove') {
            steps {
                script {
                    sh "docker rmi $registry:$BUILD_NUMBER"
                }
                
            }
        }
    }
}

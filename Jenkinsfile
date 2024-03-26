pipeline {
    agent any 
    tools {
        maven "MAVEN_HOME"
        jdk "JDK_HOME"
    }
    stages {
        stage('Checkout') {
           steps{
                echo "Checkout steps"
                git branch: 'main',  url: 'https://github.com/thoanvo/nd035-pj4.git'
                echo 'Pul from github successfully'
           }
      }
      stage('Initialize'){
            steps{
                echo "PATH = ${MAVEN_HOME}/bin:${PATH}"
                echo "MAVEN_HOME = /opt/maven"
            }
        }
      stage('Build') { 
            steps {
                echo "Build steps"
                 dir("/var/lib/jenkins/workspace/ud4") {
                 sh 'mvn -B -DskipTests clean package'
                }
            }
        }
        stage('Test'){ 
            steps { 
               echo "Test steps"
                sh 'mvn test'
            }
        } 
        stage('Deploy') { 
            steps { 
                  echo "Deploy steps"
            } 
        } 
       
    }
}
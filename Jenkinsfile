pipeline {
    agent any
     tools {
  maven 'MAVEN3'
  }

    stages {
     stage('Code checkout') {
            steps {
             checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/amrtarek456/kubernetes-devops-security.git']])
    }
        }
    

    stage ('Build') {
      steps {
      sh "mvn clean package -DskipTests=true"
        archive 'target/*.jar'
      }
    }

    stage('Docker Build and Push') {
      steps {
        script
        {
          echo 'Building Image ...'
          sh "docker build -t 40.121.81.242:8083/app:${BUILD_NUMBER} ."
          echo 'Pushing image to docker hosted rerpository on Nexus'
          withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'PSW', usernameVariable: 'USER')]){
          sh "echo ${PSW} | docker login -u ${USER} --password-stdin 40.121.81.242:8083"
          sh "docker push 40.121.81.242:8083/app:${BUILD_NUMBER}"
        }
      }
    }

   
}
}
}

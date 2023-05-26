def codeCheckout(){
        checkout scmGit(branches: [[name: '*/main']], 
                        extensions: [], 
                        userRemoteConfigs: [[url: 'https://github.com/amrtarek456/kubernetes-devops-security.git']])

}
def codeScan(){
script{
          withSonarQubeEnv("sonarqube") {
          sh "mvn sonar:sonar -f ./pom.xml"
            }
     }
}
def build(){
        sh "mvn clean package -DskipTests=true"
        archive 'target/*.jar'
}
def pushJar(){
script{
                def mavenPom = readMavenPom file: './pom.xml'
                nexusArtifactUploader artifacts: [[artifactId: 'MyWebApp',
                                                   classifier: '',
                                                   file: "MyWebApp/target/MyWebApp.jar",
                                                   type: 'jar']],
                  credentialsId: "NEXUS_CRED",
                  groupId: 'com.dept.app',
                  nexusUrl: '40.121.81.242:8081/',
                  nexusVersion: 'nexus3', 
                  protocol: 'http',
                  repository: 'myapp',
                  version: "${mavenPom.version}"
                }
                 }
}
def pushImage(){
          echo 'Building Image ...'
          sh "docker build -t 40.121.81.242:8083/app:${BUILD_NUMBER} ."
          echo 'Pushing image to docker hosted rerpository on Nexus'
          withCredentials([usernamePassword(credentialsId: 'nexus', passwordVariable: 'PSW', usernameVariable: 'USER')]){
          sh "echo ${PSW} | docker login -u ${USER} --password-stdin 40.121.81.242:8083"
          sh "docker push 40.121.81.242:8083/app:${BUILD_NUMBER}"
    }
}


return this

pipeline {
    agent any
     tools {
  maven 'MAVEN3'
  }
  

  stages 
  {
      stage('Init'){
            steps{
                script{
                    gv_script = load "script.groovy"
                }
            }
       }
     stage('Code checkout') {
            steps {
                script{
                 gv_script.codeCheckout()
                }
             }
       } 
    

    stage ('Build') {
      steps 
      {
        script{
                 gv_script.build()
                }
      }
    }

    stage('Docker Build and Push') 
    {
      steps {
        script{
          gv_script.pushImage()
        }
       }
      }  
  }
  
}

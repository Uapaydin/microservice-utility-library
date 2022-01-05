@Library('PipelineExternalLib@master') _

pipeline {

  	agent { label 'tocpt2-openjdk11-mvn36' }

    options {
        skipDefaultCheckout false
        disableConcurrentBuilds()
    }

    environment {
        appServiceName =  "maya-crm"
        softwareModuleName = "sol-crm-ms-util"	
        gitCredential 	= "ccs-bitbucket-user"
      	gitBranch 	    = "${env.BRANCH_NAME}"
        appVersion = "${env.BRANCH_NAME}-${env.BUILD_NUMBER}"
        // Artifact Variables
        artifactName = "microservice-util-0.0.2.jar"
        artifactoryRepoPath = "local-mvn-libs-release/${appServiceName}/${softwareModuleName}/${appVersion}/${artifactName}"        
        artifactPath = "./target/${artifactName}"		        
    }

    stages {
        stage('Configure'){
            steps{
                script{
                    sh "mkdir -p /home/jenkins/.m2 && cd /home/jenkins/.m2 && curl -o settings.xml https://artifactory.turkcell.com.tr/artifactory/turkcell-tools/maven/new-mvn-settings.xml"
                }
            }
        }
        stage('Build'){
            steps{
                script{
                sh "mvn -version"
                    sh "mvn clean install -DskipTests -s /home/jenkins/.m2/settings.xml"
                }
            }
        }
        stage('to Artifactory') {
          steps {
            script {
              pom = readMavenPom file: 'pom.xml'
              pomVersion = pom.version
              echo pom.version
              sourcePath = "/home/jenkins/workspace/sol-maya_sol-crm-ms-util_master/target/sol-crm-ms-util-${pomVersion}.jar"
              sourcePathPom = "/home/jenkins/workspace/sol-maya_sol-crm-ms-util_master/pom.xml"
              targetPath = "local-mvn-libs-release/com/turkcell/sol-crm/${softwareModuleName}/${pomVersion}/${softwareModuleName}-${pomVersion}.jar"
              targetPathPom = "local-mvn-libs-release/com/turkcell/sol-crm/${softwareModuleName}/${pomVersion}/${softwareModuleName}-${pomVersion}.pom"

              rtUpload (
                  serverId: 'artifactory.turkcell.com.tr',
                  spec: """{
                          "files": [
                              {
                                  "pattern": "${sourcePath}",
                                  "target": "${targetPath}"
                              },
                              {
                                  "pattern": "${sourcePathPom}",
                                  "target": "${targetPathPom}"
                              }
                          ]
                      }"""
              )
            }
          }
        }      
      
      
      
      
/*		stage('publish artifact') {
            steps{
                script {
                    genericLibrary.v2uploadArtifact(env, "${artifactPath}", "${artifactoryRepoPath}")					
                }
            }
        }*/      		
    }

    post {
        success {
            echo "Successfully"
            script {

                try {
                    echo 'Mail atiliyor ...'
                    mail to: 'firatsahindal@turkcell.com.tr ',
                            subject: "Succeded Pipeline: ${currentBuild.fullDisplayName}",
                            body: "Everything is okey with ${env.BUILD_URL} "
                }
                catch (e) {
                    echo 'Mail atarken hata alindi!!!'
                    echo 'Err: ' + e.toString()
                }
            }
        }

        failure {
            script {

                try {
                    echo 'Mail atiliyor ...'
                    mail to: 'firatsahindal@turkcell.com.tr ',
                            subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                            body: "Something is wrong with ${env.BUILD_URL} started by ${env.BUILD_USER}"
                }
                catch (e) {
                    echo 'Mail atarken hata alindi!!!'
                    echo 'Err: ' + e.toString()
                }
            }
        }

        unstable {
            echo "Unstable"
            script {

                try {
                    echo 'Mail atiliyor ...'
                    mail to: 'firatsahindal@turkcell.com.tr ',
                            subject: "Succeded Pipeline: ${currentBuild.fullDisplayName}",
                            body: "Everything is okey with ${env.BUILD_URL} "
                }
                catch (e) {
                    echo 'Mail atarken hata alindi!!!'
                    echo 'Err: ' + e.toString()
                }
            }

        }
    }
}
